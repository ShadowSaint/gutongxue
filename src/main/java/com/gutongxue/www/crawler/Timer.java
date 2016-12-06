package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.utilities.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Shadow on 2016/12/6.
 */
@Component
public class Timer {
    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨0点15执行
     */
//    @Scheduled(cron="0/15 * * * * ?")
    @Scheduled(cron="0 15 0 * * ?")
    public void runCrawler(){
        try {
            //抓多玩
            DuowanCrawler duowanCrawler=new DuowanCrawler();
            //多玩每日囧图
            int duowanImageCount=duowanCrawler.getEverydayImage(gtxDao);
            Thread.sleep(1000*60);
            //多玩爆笑gif
            int duowanGifCount=duowanCrawler.getEverydayGifImage(gtxDao);
            Thread.sleep(1000*60);
            //抓笑话集
            JokejiCrawler jokejiCrawler=new JokejiCrawler();
            int jokejiCount=jokejiCrawler.getInfo(gtxDao);
            Thread.sleep(1000*60);
            //抓开心麻花
            MahuaCrawler mahuaCrawler=new MahuaCrawler();
            int mahuaCount=mahuaCrawler.getInfo(gtxDao);
            Thread.sleep(1000*60);
            //抓最右
            ZuiyouCrawler zuiyouCrawler=new ZuiyouCrawler();
            int zuiyouCount=zuiyouCrawler.getInfo(gtxDao);
            Thread.sleep(1000*60);
            //发邮件
            MailUtil.send_email("今日脚本正常更新完毕\n" +
                    "多玩每日囧图更新数量为: "+duowanImageCount+"\n"+
                    "多玩每日gif更新数量为: "+duowanGifCount+"\n"+
                    "笑话集更新数量为: "+jokejiCount+"\n"+
                    "开心麻花更新数量为: "+mahuaCount+"\n"+
                    "最右更新数量为: "+zuiyouCount);
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("运行定时器脚本出错,错误原因:"+e);
        }
    }
}
