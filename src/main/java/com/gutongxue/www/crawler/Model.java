package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.utilities.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class Model {
    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨3点执行
     */
//    @Scheduled(cron="0 0 3 * * ?")
        @Scheduled(cron="0/15 * * * * ?")
    public void getInfo(){
        try {

        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("抓取 XXXXXXXXX 脚本出错,错误原因:"+e);
        }
    }
}
