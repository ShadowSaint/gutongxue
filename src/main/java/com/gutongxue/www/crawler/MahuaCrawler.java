package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.utilities.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class MahuaCrawler {

    /**
     * 每天凌晨0点45执行
     */
    public int getInfo(GtxDao gtxDao){
        int count=0;
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
                //如果是今天的话,跳过这一条
                if (time.equals(today)){
                    url=document.select("div.joke-content").first().attr("onclick").replace("javascript:location.href='","").replace("'","");
                    html=HtmlUtil.sendGetGzip(url,"utf-8");
                    document=Jsoup.parse(html);
                    time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
                    continue;
                }
                if (imgElements.size()>0){
                    String urlFilePath= ImageUtil.downloadFromUrl(imgElements.first().attr("src"));
                    String ossUrl;
                    try {
                        GifUtil.addTextWatermarkToGif(new File(urlFilePath), "www.gutongxue.com", new File(urlFilePath));
                        ossUrl= OssUtil.getOSSUrl(urlFilePath,".gif");
                    }catch (Exception e){
                        ossUrl= OssUtil.getOSSUrl(urlFilePath,".png")+"@!watermark";
                    }
                    if (ossUrl.contains("null")){
                        continue;
                    }
                    Image image=new Image();
                    image.setDate(today);
                    image.setUrl(ossUrl);
                    image.setSeq((int)( Math.random()*10));
                    List<Image> imageList=gtxDao.getImageList(" and image_description = '"+image.getDescription()+"' ",0,9999999);
                    if (imageList.size()>0){
                        continue;
                    }
                    String description=document.select("h1.joke-title").first().text().trim();
                    image.setDescription(description);
                    gtxDao.insertImage(image);
                    count++;
                }else {
                    String content=document.select("div.joke-content").html().trim();
                    Joke joke=new Joke();
                    joke.setContent(content);
                    joke.setDate(today);
                    List<Joke> jokeList=gtxDao.getJokeList(" and joke_content = '"+content+"'",0,999999);
                    if (jokeList.size()>0){
                        continue;
                    }
                    gtxDao.insertJoke(joke);
                    count++;
                }
                String nextUrl=document.select("div.joke-content").first().attr("onclick").replace("javascript:location.href='","").replace("'","");
                if (nextUrl.equals(url)){
                    break;
                }else {
                    url=nextUrl;
                }
                html=HtmlUtil.sendGetGzip(url,"utf-8");
                document=Jsoup.parse(html);
                time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("谷同学网站抓取脚本遇到异常","抓取 http://www.mahua.com/ 脚本出错,错误原因:"+e);
        }
        return count;
    }
}
