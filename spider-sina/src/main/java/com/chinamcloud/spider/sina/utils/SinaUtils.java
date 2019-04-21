package com.chinamcloud.spider.sina.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinaUtils {

    public static long transTime(String timeStr) {
        if (timeStr.contains("秒前")) {
            long curTime = new Date().getTime();
            int minute = Integer.parseInt(timeStr.replace("秒前", ""));
            return (curTime - (minute * 1000));
        } else if (timeStr.contains("分钟前")) {
            long curTime = new Date().getTime();
            int minute = Integer.parseInt(timeStr.replace("分钟前", ""));
            return (curTime - (minute * 60 * 1000));
        } else if (timeStr.contains("今天")) {
            String timeS = timeStr.replace("今天 ", "");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                long nowTime = System.currentTimeMillis();
                long todayStartTime = nowTime - (nowTime)%(1000*3600*24);
                return simpleDateFormat.parse(timeS).getTime() + todayStartTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            String timeS = LocalDate.now().getYear() + "年" + timeStr;
            try {
                return simpleDateFormat.parse(timeS).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static String getNicknameId(String href) {
        String regex = "weibo.com\\/(\\w+)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(href);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
