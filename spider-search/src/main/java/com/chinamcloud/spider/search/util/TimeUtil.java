package com.chinamcloud.spider.search.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    public static long tranTime(String timeStr, String regex, String format) {
        long curTime = new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (timeStr.contains("小时前")) {
            String regex1 = "(\\d+)小时前";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                int hours = Integer.parseInt(matcher.group(1));
                return (curTime - (hours * 60 * 60 * 1000));
            }
        } else if (timeStr.contains("分钟前")) {
            String regex1 = "(\\d+)分钟前";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                int hours = Integer.parseInt(matcher.group(1));
                return (curTime - (hours * 60 * 1000));
            }
        } else if (timeStr.contains("昨天")) {
            String regex1 = "(\\d+:\\d+)";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                timeStr = year + "-" +  month + "-" + (day - 1) + " " + matcher.group(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    return simpleDateFormat.parse(timeStr).getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                timeStr = matcher.group(1);
            }
            if (!format.equals("yyyy")) {
                timeStr = year + "-" + timeStr;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                return simpleDateFormat.parse(timeStr).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
