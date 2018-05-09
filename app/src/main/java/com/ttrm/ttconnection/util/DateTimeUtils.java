package com.ttrm.ttconnection.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016-07-06.
 */
public class DateTimeUtils {
    private static final String TAG = "DateTimeUtils";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat_3 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormat_4 = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat dateFormat_5 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static SimpleDateFormat dateFormat_6 = new SimpleDateFormat("MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat_7 = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat dateFormat_8 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat_9 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat_a = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormat_b = new SimpleDateFormat("HHmmss");
    private static SimpleDateFormat dateFormat_10 = new SimpleDateFormat("ddHHmmss");
    private static SimpleDateFormat dateFormat_11 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateFormat_12 = new SimpleDateFormat("MM-dd HH:mm");
    private static Calendar c = Calendar.getInstance();

    /**
     * @param time
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(String time) {
        if (TextUtils.isEmpty(time))
            return "2000-01-01 00:00:00";
        String reTime = "";
        try {
            reTime = dateFormat_2.format(dateFormat.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formateDate1(Long t){
        if(t>0) {
            t=t*1000;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = new Long(t);
            String d = format.format(time);
            return d;
        }else {
            return "";
        }
    }

    /**
     * @param time
     * @return
     */
    public static String formatDateStrToOtherStr15(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_9.format(dateFormat.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }


    public static String formatDateStrToOtherStr3(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_9.format(dateFormat_3.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrToOtherStr16(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_11.format(dateFormat.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrToOtherStr17(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_12.format(dateFormat.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrToOtherStr10(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_6.format(dateFormat.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrToOtherStrLine(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_7.format(dateFormat.parse(time));
        } catch (Exception e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrToOtherStrLine1(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_8.format(dateFormat.parse(time));
        } catch (Exception e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStrOneLine(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_8.format(dateFormat.parse(time));
        } catch (Exception e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStr_a(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_8.format(dateFormat_a.parse(time));
        } catch (Exception e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStr_b(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_11.format(dateFormat_b.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;
    }

    public static String formatDateStr8(String time) {
        String reTime = "";
        try {
            reTime = dateFormat_9.format(dateFormat_3.parse(time));
        } catch (ParseException e) {
            MyUtils.Loge(TAG, e.getMessage());
        }
        return reTime;

    }


    public static String getTodayStr14() {
        return dateFormat.format(new Date());
    }

    public static String getTodayStr17() {
        return dateFormat_5.format(new Date());
    }

    public static String getTodayStrWithFormat() {
        return dateFormat_4.format(new Date());
    }

    public static String getTodayStr8() {

        return dateFormat_3.format(new Date());
    }

    public static int getToday_num() {
        int day = c.get(Calendar.DAY_OF_MONTH);

        return (day * 10000) + 1;
    }

    public static boolean isToday(String date) {
        return dateFormat_3.format(new Date()).equals(date);
    }

    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG, e.getMessage());
        }
        return "";
    }

    public static String time2Time(String time) {

        try {
            Calendar c = Calendar.getInstance();
            int nowDay = c.get(Calendar.DAY_OF_YEAR);
            c.setTime(dateFormat.parse(time));
            int logDay = c.get(Calendar.DAY_OF_YEAR);
            if (nowDay == logDay) {
                return "今天 " + dateFormat_11.format(dateFormat.parse(time));
            } else if (nowDay - 1 == logDay) {
                return "昨天 " + dateFormat_11.format(dateFormat.parse(time));
            } else {
                return dateFormat_7.format(dateFormat.parse(time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            MyUtils.Loge(TAG, e.getMessage());
        }

        return "";
    }

    /**
     * 判断是否是周末
     *
     * @param time
     * @return
     */
    public static boolean isWeekend(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            MyUtils.Loge(TAG, e.getMessage());
        }

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 6 || week == 0) {//0代表周日，6代表周六
            return true;
        }
        return false;
    }
    /**
     * 时间戳 转换成时间格式
     */
    public static String time2Date(Long time){
//        long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }
}
