package spider.filter.example.netease;

import com.sun.xml.internal.ws.api.pipe.Pipe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.Page;
import spider.filter.PageFilter;
import spider.model.example.netease.PlayInfo;
import spider.pipeline.Pipeline;
import spider.pipeline.impl.mysql.example.netease.NeteasePipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayListInfoFilter implements PageFilter {

    public void filter(Page page) {
        Document doc = Jsoup.parse(page.getRawText());

        PlayInfo playInfo = new PlayInfo();

        playInfo.setId(Long.parseLong(page.getRequest().getUrl().split("=")[1]));
        playInfo.setName(doc.select("h2[class='f-ff2 f-brk']").text());

        Element userEle = doc.select("a[class='s-fc7']").first();
        playInfo.setAuthor(userEle.text());
        playInfo.setAuthorId(Long.parseLong(userEle.attr("href").split("=")[1]));

        playInfo.setCollectCount(transNums(doc.select("a[class='u-btni u-btni-fav ']")
                .select("i").text()
                .replace("(", "")
                .replace(")", "")));

        playInfo.setShareCount(
                transNums(
                        doc.select("a[class='u-btni u-btni-share ']")
                                .select("i").text()
                                .replace("(", "")
                                .replace(")", "")));

        String labels = "";

        Elements labelEles = doc.select("a[class='u-tag']");
        for (Element ele : labelEles) {
            labels = labels + ele.select("i").text() + ",";
        }
        playInfo.setLabels(labels);

        playInfo.setDes(doc.select("p[class='intr f-brk']").text());
        playInfo.setPlayCount(Long.parseLong(doc.select("strong[class='s-fc6']").text()));
        playInfo.setNum(Integer.parseInt(doc.select("span[id='playlist-track-count']").text()));

        Map<String, Object> items = new HashMap<>();
        items.put("playInfo", playInfo);
        page.setItems(items);

        List<Pipeline> pipelines = new ArrayList<>();
        pipelines.add(new NeteasePipeline());
        page.setPipelines(pipelines);
    }


    private long transNums(String num) {
        if (num.indexOf("万") != -1) {
            return Long.parseLong(num.replace("万", "")) * 10000;
        } else {
            return Long.parseLong(num);
        }
    }
}
