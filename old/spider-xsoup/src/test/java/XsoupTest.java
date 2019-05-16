import us.codecraft.xsoup.Xsoup;

public class XsoupTest {

    public static void main(String[] args) {
        String html = "<div class=\"article\" id=\"artibody\">\n" +
                "\t\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\t\twindow.sina_survey_config = {\n" +
                "    \t\t\t\t\tsurveyCss: true,               //调查问答样式true, false, http （不使用默认样式配置false或者不传此参数）\n" +
                "    \t\t\t\t\tresultCss: true,               //调查结果样式true, false, http （不使用默认样式配置false或者不传此参数）\n" +
                "    \t\t\t\t\tsource: 'vote'               //通过来源设置图片宽高 sina(手浪), vote(默认)\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t</script>\n" +
                "                \n" +
                "\n" +
                "                \t\t\t\t<div class=\"img_wrapper\">\n" +
                "\t\t\t\t\t<img alt=\"#新浪观影团#第1251期电影《昨日青空》全国免费抢票\" src=\"//n.sinaimg.cn/ent/transform/20/w560h260/20180719/ctgY-hfnsvza7772714.jpg\">\n" +
                "\t\t\t\t\t<span class=\"img_descr\">#新浪观影团#第1251期电影《昨日青空》全国免费抢票</span>\n" +
                "\t\t\t\t</div><link id=\"weibo2017Css\" rel=\"stylesheet\" href=\"//finance.sina.com.cn/other/src/sinapagewidgets/SinaPageWeibo2017.css\"><p>　　[观影时间和地点]</p>\n" +
                "<p>　　2018年7月28日 周六 下午场次</p>\n" +
                "<p>　　北京地区：<a href=\"https://weibo.com/sddyy\" target=\"_blank\">首都电影院</a> 西单大悦城店</p>\n" +
                "<p>　　外埠地区：微博私信发放免费观影兑换码</p>\n" +
                "<p>　　[观影人数]</p>\n" +
                "<p>　　50人（25对每人2张票）</p>\n" +
                "<p>　　[参与规则]</p>\n" +
                "<p>　　点击：<a href=\"https://weibo.com/3216836631/GqE0tdzB8\" target=\"_blank\">电影《昨日青空》全国免费抢票微博</a>，按规则转发并私信留下信息即可。</p>\n" +
                "\t\t\t\t<!-- 微博列表 -->\n" +
                "\t\t\t\t<div class=\"page-widget-2017\"><div class=\"weibo-2017\" data-sudaclick=\"component_weibocard_p\"><div class=\"wb-feed\" id=\"wb01\"></div></div></div>\n" +
                "\t\t\t\t<script type=\"text/javascript\">\n" +
                "\t\t\t\tSinaPage.loadWidget({\n" +
                "\t\t\t\t    trigger: {\n" +
                "\t\t\t\t        id: 'wb01'\n" +
                "\t\t\t\t    },\n" +
                "\t\t\t\t    require: [\n" +
                "        \t\t\t\t{\n" +
                "\t\t        \t\t\turl: \"//n.sinaimg.cn/finance/fe/doT.min.js\"\n" +
                "\t\t    \t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\turl: \"//finance.sina.com.cn/other/src/sinapagewidgets/SinaPageWeibo2017.js\"\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t    onAfterLoad: function () {\n" +
                "\t\t\t\t\t\tnew SinaPageWeibo.WeiboCard({\n" +
                "                        \twrap:'wb01',\n" +
                "                        \tid:'4263503173235106'\n" +
                "                    \t});\n" +
                "\t\t\t\t    }\n" +
                "\t\t\t\t});\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t</script>\n" +
                "\t\t\t\t<!-- /微博列表 -->\n" +
                "<p>　　[影片简介]</p>\n" +
                "<p>　　片名：<a href=\"http://ent.sina.com.cn/ku/movie_info_index.d.html?type=movie&amp;id=fynfvar5004873\" target=\"_blank\">《昨日青空》</a></p>\n" +
                "<p>　　导演：奚超</p>\n" +
                "<p>　　编剧：口袋巧克力（原著）</p>\n" +
                "<p>　　类型：爱情、动画</p>\n" +
                "<p>　　制片国家/地区：中国大陆</p>\n" +
                "<p>　　语言：汉语普通话</p>\n" +
                "<p>　　片长：82分钟</p>\n" +
                "<p>　　上映：2018-07-27（中国大陆）</p>\n" +
                "<p>　　剧情：</p>\n" +
                "<p>　　中国首部青春题材动画电影《昨日青空》将于2018年暑期全国上映。该片改编自口袋巧克力同名人气漫画作品，由咕咚导演奚超执导。影片以中国南方小镇兰溪为实际取景地，讲述了小镇上的几位高三学生，有关梦想、友谊、亲情和初恋等的成长经历，描绘出一段极具中国风、清新唯美的青春故事。</p>\n" +
                "<p>　　[北京活动地点和乘车路线]</p>\n" +
                "<p>　　地址：北京西城区西单北大街甲131号大悦城商场十层</p>\n" +
                "<p>　　乘车路线：可乘1、10、22、37、52、70、728在西单路口东下车。可乘102、105、109、22、46、47、603、604、626、690、在西单商场下车。也可乘地铁1号线在西单下车走西北出口。自驾前往，有地下停车场。</p>\n" +
                "<p>　　[观影作业]</p>\n" +
                "<p>　　请与同去的朋友都要在观影结束后24小时内用[用手机客户端或电脑客户端的微博电影点评功能]进入<a href=\"https://weibo.com/p/100120180338\" target=\"_blank\">电影《昨日青空》点评页面</a>提交140字以上原创观后感，欢迎大家使用写长评功能发布长影评。（具体方法如下图所示）。格式为： @新浪观影团 @电影昨日青空官微 +真实观影感受。（观后感提交越认真，未来的观影机会可是越多哦，严禁任何形式的抄袭，感谢大家的配合，未按时提交者或抄袭者将会列入黑名单，请珍惜你的观影机会哦。）　</p>\n" +
                "<div class=\"img_wrapper\"><img src=\"http://n.sinaimg.cn/ent/transform/20171023/DnhM-fymzqse2921649.jpg\" alt=\"\" data-link=\"\"><span class=\"img_descr\"></span></div>\n" +
                "<p>　　[抢票提示]　　</p>\n" +
                "<p>　　每期观影活动均需参与活动的本人到场核实真实身份签到领票，请勿替代观影，如请假需至少提前半天时间（6小时）私信有效。因非本人前来而造成的无法进场我们概不负责。活动中奖资格一旦发现转送、出售等违规行为，将立即取消其活动资格格并计入系统黑名单（3个月内不能参加观影活动），请您珍惜您的观影机会，谢谢配合。</p>\n" +
                "<p>　　[关于我们]</p>\n" +
                "<p>　　#新浪观影团#是新浪娱乐频道为广大电影爱好者提供免费观赏国内外最新影片、首映礼、主创见面会等机会的平台。观影团成员们会自发将微影评、明星见面会现场报道以微博的形式发表与网友们分享，形成强大的电影口碑影响力，打造我们的观影风向标！想要了解更多，请进入我们的专题首页吧：新浪观影团。</p>\n" +
                "<p>　　注：违反以下活动规则将列入系统黑名单，请您珍惜您的观影机会：</p>\n" +
                "<p>　　1。未出席且未请假者，及请假未提前半天私信的网友（开演前临时请假按缺席处理）。</p>\n" +
                "<p>　　2。未在24小时内按要求提交微影评的网友。</p>\n" +
                "<p>　　3。观后感作业有抄袭现象的网友。（欢迎大家私信我们举报）</p>\n" +
                "<p>　　1）只要看清活动规则，每一条都很规范的按要求做，获奖的机率会非常大哦！</p>\n" +
                "<p>　　2）如果你以前参与过活动并按要求为影片打过分认真写过影评的网友，欢迎在报名时放上你的往期作业链接，我们会优先考虑选择你！</p>\n" +
                "\t\t\t\t\n" +
                "                <!-- 非定向300*250按钮    17/09  wenjing  begin -->\n" +
                "                <div id=\"left_hzh_ad\">\n" +
                "                    <script async=\"\" charset=\"utf-8\" src=\"//d5.sina.com.cn/litong/zhitou/sinaads/release/sinaads.js\"></script>\n" +
                "                    <script language=\"javascript\" type=\"text/javascript\" src=\"//d2.sina.com.cn/d1images/button/rotator.js\"></script>\n" +
                "                    <script type=\"text/javascript\">\n" +
                "                      (function(){\n" +
                "                        var adScript = document.createElement('script');\n" +
                "                        adScript.src = '//d1.sina.com.cn/litong/zhitou/sinaads/demo/changwy/link/yl_left_hzh_20160119.js';\n" +
                "                        document.getElementsByTagName('head')[0].appendChild(adScript);\n" +
                "                        })();\n" +
                "                    </script>\n" +
                "\n" +
                "                </div>\n" +
                "                <!-- 非定向300*250按钮  end -->\n" +
                "            </div>";

        String result = Xsoup.select(html, "/newsText()").get();
        System.out.println(result);
    }
}
