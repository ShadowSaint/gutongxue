package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.utilities.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class MahuaCrawler {
    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨3点执行
     */
    @Scheduled(cron="0 0 3 * * ?")
    public void getInfo(){
        try {
            String today= TimeUtil.getToday();
            String yesterday=TimeUtil.getYesterdayByFormat("yyyy-MM-dd");
            String sourceUrl="http://www.mahua.com/";
            String sourceHtml= HtmlUtil.sendGetGzip(sourceUrl,"utf-8");
            String url= Jsoup.parse(sourceHtml).select("span.joke-title").first().select("a").first().attr("href").trim();
            String html=HtmlUtil.sendGetGzip(url,"utf-8");
            Document document=Jsoup.parse(html);
            String time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
            while (time!=null&&!time.equals("")&&(time.equals(today)||time.equals(yesterday))){
                Elements imgElements=document.select("div.joke-content img");
                if (imgElements.size()>0){
                    String urlFilePath= ImageUtil.downloadFromUrl(imgElements.first().attr("src"));
                    String ossUrl= OssUtil.getOSSUrl(urlFilePath);
                    Image image=new Image();
                    image.setDate(today);
                    image.setUrl(ossUrl);
                    image.setSeq((int)( Math.random()*10));
                    String description=document.select("h1.joke-title").first().text().trim();
                    image.setDescription(description);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("抓取 http://www.mahua.com/ 脚本出错,错误原因:"+e.getMessage());
        }
    }
}
