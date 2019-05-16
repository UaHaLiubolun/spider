package com.chinamcloud.spider;

import com.chinamcloud.spider.model.DataConversion;
import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageModelTran {

    public static List<PageModel> getPageModel(Page page) {
        List<Map> maps = (List<Map>) page.getRequest().getExtras().get("PageModel");
        List<PageModel> pageModels1 = new ArrayList<>();
        for (Map map : maps) {
            PageModel newPageModel = new PageModel();
            newPageModel.setHelpUrl((List<String>) map.get("helpUrl"));
            newPageModel.setTargetUrl((List<String>) map.get("targetUrl"));
            newPageModel.setOwnProcess(map.get("ownProcess") == null ? null : map.get("ownProcess").toString());
            List<Map> extracts = (List<Map>)map.get("extracts");
            newPageModel.setExtracts(new ArrayList<>());
            if (extracts != null) {
                for (Map extract : extracts) {
                    newPageModel.getExtracts().add(transExtract(extract));
                }
            }
            pageModels1.add(newPageModel);
        }
        return pageModels1;
    }

    private static Extract transExtract(Map extractMap) {
        Extract extract = new Extract();
        extract.setValue((List<String>) extractMap.get("value"));
        extract.setFiled(String.valueOf(extractMap.get("filed")));
        switch (String.valueOf(extractMap.get("type"))) {
            case "XPath":
                extract.setType(Extract.Type.XPath);
                break;
        }
        switch (String.valueOf(extractMap.get("source"))) {
            case "Html":
                extract.setSource(Extract.Source.Html);
                break;
        }
        extract.setMulti(Boolean.valueOf(String.valueOf(extractMap.get("multi"))));
        extract.setNotNull(Boolean.valueOf(String.valueOf(extractMap.get("notNull"))));
        Map map = (Map) extractMap.get("dataConversion");
        if (map != null) {
            DataConversion dataConversion = new DataConversion();
            dataConversion.setExpression(String.valueOf(map.get("expression")));
            dataConversion.setFunction(String.valueOf(map.get("function")));
            extract.setDataConversion(dataConversion);
        }
        return extract;
    }
}
