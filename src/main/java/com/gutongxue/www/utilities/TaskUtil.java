package com.gutongxue.www.utilities;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by Shadow on 2016/11/12.
 */
@Component
public class TaskUtil {
    private static final String IMG_DOWNLOAD_DIR =  File.separator + "gutongxue"+File.separator+"images"+ File.separator ;
    /**
     * 每日凌晨一点执行
     */
    @Scheduled(cron="0 0 1 * * ?")
    public void everyDay(){
    }

    /**
     * 每周一23点钟执行,确保抓取脚本已经结束
     */
    @Scheduled(cron="0 23 0 ? * MON")
    public void everyWeek(){


    }


    /**
     * 每月一日凌晨一点执行
     */
    @Scheduled(cron="0 1 0 1 * ?")
    public void everyMonth(){
    }


}
