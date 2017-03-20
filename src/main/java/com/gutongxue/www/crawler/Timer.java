package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.utilities.DeleteFileUtil;
import com.gutongxue.www.utilities.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Created by Shadow on 2016/12/6.
 */
@Component
public class Timer {
    private static final Logger logger= LoggerFactory.getLogger(Timer.class);
    private static final String IMG_DOWNLOAD_DIR = File.separator + "gutongxue" + File.separator + "images" + File.separator;

    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨0点15执行
     */
//    @Scheduled(cron="0/15 * * * * ?")
    @Scheduled(cron="0 15 0 * * ?")
    public void runCrawler(){
        //避免重启项目导致脚本重新运行,所以规定,7点钟以后不准运行
        LocalDateTime localDateTime=LocalDateTime.now();
        int hour=localDateTime.getHour();//24小时制,放心用
        if (hour>=7){
            //输出日志,按理说这个方法不应该被执行
            logger.warn("7点后脚本意外运行");
            return;
        }
        //出于服务器性能瓶颈限制,目前暂时不打算做成异步的
        try {
            //多玩每日囧图
            int duoWanImageCount= DuoWanCrawler.getDuoWanInfo(0,gtxDao);
            Thread.sleep(1000*60);
            //多玩爆笑gif
            int duoWanGifCount= DuoWanCrawler.getDuoWanInfo(1,gtxDao);
            Thread.sleep(1000*60);
            //抓笑话集
            int jokejiCount= JokeJiCrawler.getJokeJiInfo(gtxDao);
            Thread.sleep(1000*60);
            //抓开心麻花
            int mahuaCount= MaHuaCrawler.getMaHuaInfo(gtxDao);
            Thread.sleep(1000*60);
            int neiHanDuanZiCount=NeiHanDuanZiCrawler.getNeiHanDuanZiInfo(gtxDao);
            //数据库记录
            gtxDao.insertLogCrawler(TimeUtil.getTodayByFormat("yyyy-MM-dd HH:mm:ss"),
                            "今日脚本正常更新完毕\n" +
                            "多玩每日囧图更新数量为: "+duoWanImageCount+"\n"+
                            "多玩每日gif更新数量为: "+duoWanGifCount+"\n"+
                            "笑话集更新数量为: "+jokejiCount+"\n"+
                            "开心麻花更新数量为: "+mahuaCount+"\n"+
                            "内涵段子更新数量为:"+neiHanDuanZiCount);
            DeleteFileUtil.DeleteFolder(IMG_DOWNLOAD_DIR);
            File file=new File(IMG_DOWNLOAD_DIR);
            if (!file.exists()){
                file.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
