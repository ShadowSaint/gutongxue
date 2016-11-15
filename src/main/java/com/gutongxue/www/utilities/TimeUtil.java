package com.gutongxue.www.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Shadow on 2016/11/15.
 */
public class TimeUtil {
    public static void main(String[] args) {

    }
    public static String getToday(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getTodayByFormat(String timeFormat){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat(timeFormat);
        return format.format(date);
    }
    public static String getYesterdayByFormat(String format){
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        //通过秒获取下一天日期
        long time = (date.getTime() / 1000) - 60 * 60 * 24;//秒
        date.setTime(time * 1000);//毫秒
        String tomorrow = sf.format(date).toString();
        return tomorrow;
    }
}
