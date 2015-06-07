package io.gebi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yava on 15/4/23.
 */
public class TimeUtils {
    public static final long HOUR = 60 * 60 * 1000;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;

    private static String[] DAYS_OF_WEEK = new String[]{
            "周一", "周二", "周三", "周四",
            "周五", "周六", "周日"};

    //时间戳显示
    public static String humanTime(long milliseconds) {
        String formatTime = "";
        Date date = new Date(milliseconds);
        long beginOfToday = getBeginOfToday();
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (milliseconds >= beginOfToday) {
            //今天
            sdf.applyPattern("今天 HH:mm");
            formatTime = sdf.format(date);
        } else if (milliseconds >= beginOfToday - DAY) {
            //昨天
            formatTime = "昨天";
        } else if (milliseconds >= getBeginOfWeek()) {
            //本周
            formatTime = DAYS_OF_WEEK[getDayOfWeek(date) - 1];
        } else {
            sdf.applyPattern("MM月mm日");
            formatTime = sdf.format(date);
        }
        return formatTime;
    }

    public static long getBeginOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getBeginOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTimeInMillis();
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        return index;
    }
}
