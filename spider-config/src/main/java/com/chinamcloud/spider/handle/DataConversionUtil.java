package com.chinamcloud.spider.handle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConversionUtil {


    public long fmtDate(Object value, String expression) {
        if (value == null) return 0;
        String[] expressions = expression.split("Split");
        if (expressions.length == 0)  return 0;
        for (int i = 0; i < expressions.length; i++) {
            String ex = expressions[i];
            if (ex.equals("")) continue;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ex);
            try {
                Date date = simpleDateFormat.parse((String) value);
                return date.getTime();
            } catch (ParseException e) {
                System.out.println("TimeParseFiled");
            }
        }
        return 0;
    }


    public long fmtDateV2(Object value, String expression) {
        if (value == null) return 0;
        String[] expressions = expression.split("Split");
        if (expressions.length != 2)  return 0;
        Pattern pattern = Pattern.compile(expressions[0]);
        Matcher matcher = pattern.matcher((String) value);
        if (matcher.find()) {
            String time = matcher.group(1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expressions[1]);
            try {
                Date date = simpleDateFormat.parse(time);
                return date.getTime();
            } catch (Exception e) {
                System.out.println("TimeParseFiled");
            }
        }
        return 0;
    }

    public String delTag(Object value, String tags) {
        if (value == null) return "";
        String[] tag = tags.split(";");
        if (tag.length == 0) return value.toString();
        Document document = Jsoup.parse(value.toString());
        for (String temp:
             tag) {
            String[] e = temp.split(":");
            if (e.length != 2) continue;
            if (e[0].equalsIgnoreCase("class")) {
                document.getElementsByClass(e[1]).remove();
            } else if (e[0].equalsIgnoreCase("id")) {
                document.getElementById(e[1]).remove();
            } else {
                document.getElementsByAttributeValueMatching(e[0], e[1]);
            }
        }
        return document.body().html();
    }

    public static void main(String[] args) {
        DataConversionUtil dataConversionUtil = new DataConversionUtil();
        String time = "class:lb;class:zan-wap;class:p-tags";
        String value = "<div id=\"p-detail\"> \n" +
                " <div class=\"video-url\" style=\"display:none\"> \n" +
                " </div> \n" +
                " <div class=\"lb\"> \n" +
                "  <div class=\"standard_lb\"> \n" +
                "   <span class=\"tj\">图集</span> \n" +
                "   <span class=\"origin\"></span> \n" +
                "   <div class=\"swiper-container2\" id=\"swiperContainer2\"> \n" +
                "    <div class=\"swiper-wrapper\"> \n" +
                "    </div> \n" +
                "    <div class=\"swiper-pagination\"></div> \n" +
                "    <div class=\"swiper_arrow arrow_left s_arrow_left\"></div> \n" +
                "    <div class=\"swiper_arrow arrow_right s_arrow_right\"></div> \n" +
                "    <div class=\"swiper-scrollbar\"></div> \n" +
                "    <span class=\"lb-left s_arrow_left\"></span> \n" +
                "    <span class=\"lb-right s_arrow_right\"></span> \n" +
                "   </div> \n" +
                "  </div> \n" +
                " </div> \n" +
                " <p>　　<strong><font color=\"navy\">原标题：高速公路：春节假期免费通行 三大火车站引入网约车 增加定制公交运力 地铁适时延长末班车</font></strong></p> \n" +
                " <p>　　2019年春运工作动员大会昨天召开。2019年春运将从1月21日开始至3月1日结束，共计40天，预计1月30日各条高速路将迎来离京高峰。今年春节假期期间，全国高速路免费通行，全国范围内高速路拥堵超过10公里须立刻上报疏通。</p> \n" +
                " <p>　　2019年春运期间进出京客流总量预计达4306万人次；其中铁路2946万人次、民航1190万人次、公路省际客运170万人次。交通部门加强三大火车站接驳保障，公交增加定制公交、夜班公交、高铁大巴运力，地铁将适时延长末班车。同时，三大火车站将引入网约车缓解市民打车难。</p> \n" +
                " <p>　　省际客运继续实行实名制联网售票，继续推行并进一步开发“送票上门”等服务，重点省际客运站根据需要适当延长开放时间，同时探索提供多种形式的售取票服务。</p> \n" +
                " <p>　　<strong><font color=\"navy\">公路</font></strong></p> \n" +
                " <p style=\"COLOR: navy\"><strong><font color=\"navy\">　　高速路春节假期免费</font></strong></p> \n" +
                " <p>　　北京青年报记者从北京市交通委获悉，今年春节假期期间，全国范围内，继续实行小客车免费通行政策，高速路拥堵超过10公里须立刻上报疏通。</p> \n" +
                " <p>　　2019年春运期间本市省际道路客运量约170万人次，同比减少7.0%左右。客流构成主要以务工、探亲、学生客流为主；客流主要方向为北京周边的天津、河北、山西、内蒙古、山东、辽宁、河南等省(区、市)。</p> \n" +
                " <p>　　各客运站在保证每日正常班次的同时，计划开行加班车2653班次，计划筹措备班运力876辆。</p> \n" +
                " <p>　　<strong><font color=\"navy\">铁路</font></strong></p> \n" +
                " <p style=\"COLOR: navy\"><strong><font color=\"navy\">　　节前节后加开始发直通临客</font></strong></p> \n" +
                " <p>　　2019年春运期间，北京地区三站图定始发终到客车达到527.5对，较2018年春运期间增加47.5对。2019年北京地区节前加开始发直通临客65对，节后65.5对。</p> \n" +
                " <p>　　铁路部门继续拓展互联网售票、电话售票、手机客户端售票等便捷售票渠道，不断提高旅客购票便利性。继续提供学生优惠票、务工人员团体票等票种，推行提前30天订票、提前15天免费退票、自助售（取）票机进校园等举措。 同时，市内各火车站继续增设售票窗口，实施车站咨询服务台外移等便民措施。</p> \n" +
                " <p>　　<strong><font color=\"navy\">接驳</font></strong></p> \n" +
                " <p><strong><font color=\"navy\">　　三大火车站引入网约车服务</font></strong></p> \n" +
                " <p>　　北京铁路、交通、城管等多部门成立联合工作小组，采取多种措施提高运力，方便市民出行。</p> \n" +
                " <p>　　出租汽车加强运营调度，重点做好机场、火车站和主要客流集散地的运力保障；遇恶劣天气机场、火车站出现运力严重短缺时，引导空驶出租车和组织备班车辆进行运力保障。</p> \n" +
                " <p>　　交通部门加强三大火车站接驳保障方案，公交增加定制公交、夜班公交、高铁大巴运力，地铁将适时延长末班车。同时，三大火车站将引入网约车缓解市民打车难。（文/记者 刘珜）</p> \n" +
                " <div class=\"zan-wap\"> \n" +
                "  <a href=\"javascript:void(0);\" class=\"zan\"> </a> \n" +
                "  <div class=\"zan-i\">\n" +
                "   +1\n" +
                "  </div> \n" +
                "  <div class=\"zan-v\"> \n" +
                "   <span></span> \n" +
                "  </div> \n" +
                " </div> \n" +
                " <div class=\"p-tags\" id=\"\"> \n" +
                "  <div class=\"p-kwap\"></div> \n" +
                "  <span class=\"p-jc\"> <span class=\"tiyi1\"> <a href=\"javascript:void(0);\" class=\"advise\">【纠错】</a> \n" +
                "    <div class=\"tiyi01\" id=\"advisebox01\" style=\"display:none\"> \n" +
                "     <div> \n" +
                "      <iframe id=\"jc_link1\" style=\"width:600px;height:350px; float:left;\" border=\"0\" name=\"search\" marginwidth=\"0\" framespacing=\"0\" marginheight=\"0\" frameborder=\"0\" noresize scrolling=\"no\" vspale=\"0\"></iframe> \n" +
                "     </div> \n" +
                "     <div class=\"tiyi03\"> \n" +
                "      <div id=\"jc_close1\" style=\"cursor:pointer\">\n" +
                "       <img src=\"http://www.xinhuanet.com/images/syicon/space.gif\" width=\"24\" height=\"24\" border=\"0\">\n" +
                "      </div> \n" +
                "     </div> \n" +
                "    </div> </span> 责任编辑： 程瑶 </span> \n" +
                " </div> \n" +
                "</div>";
        String test = dataConversionUtil.delTag(value, time);
        System.out.println(test);
    }

}
