package com.gutongxue.www.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.CrawlerConfig;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.utilities.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by Shadow on 2016/11/16.
 */
@Component
public class DuowanCrawler {

    /**
     * 每天凌晨0点1分执行,抓取今日囧图
     */
    public int getEverydayImage(GtxDao gtxDao){
        int count=0;
        try {
            String today= TimeUtil.getToday();
            //获取数据库里上次抓到哪一期了
            int crawlerProgress=Integer.valueOf(gtxDao.getCrawlerConfig("多玩今日囧图").getProgress());
            //获取今日囧图的html,获取网页上囧图期数的列表
            String listUrl="http://tu.duowan.com/tag/5037.html";
            String listHtml= HtmlUtil.sendGetGzip(listUrl,"utf-8");
            if (listHtml==null||listHtml.equals("")){
                listHtml= HtmlUtil.sendGet(listUrl,"utf-8");
            }
            Document listDocument= Jsoup.parse(listHtml);
            Elements listElements=listDocument.select("em");
            //对列表从旧到新的遍历
            for (int i=listElements.size()-1;i>=0;i--){
                String itemTitle=listElements.get(i).select("a").text().trim();
                if (itemTitle==null||itemTitle.equals("")){
                    continue;
                }
                try {
                    //截取出期数
                    itemTitle=itemTitle.split("：")[0].split("期")[0].split("第")[1];
                    int itemNumber=Integer.valueOf(itemTitle);
                    if (crawlerProgress>=itemNumber){
                        continue;
                    }
                    //获取到拿数据的url
                    String itemUrl=listElements.get(i).select("a").first().attr("href").trim();
                    itemUrl=itemUrl.split("/")[itemUrl.split("/").length-1];
                    itemUrl=itemUrl.split("\\.")[0];
                    itemUrl="http://tu.duowan.com/index.php?r=show/getByGallery/&gid="+itemUrl;
                    //从url里解析出json,然后根据需要解析
                    String imgJsonObjectString=HtmlUtil.sendGetGzip(itemUrl,"utf-8");
                    JSONObject imgJsonObject= JSON.parseObject(imgJsonObjectString);
                    JSONArray imgJsonArray=imgJsonObject.getJSONArray("picInfo");
                    for (Object object:imgJsonArray){
                        JSONObject itemJsonObject= (JSONObject) object;
                        Image image=new Image();
                        image.setDate(today);
                        image.setDescription(itemJsonObject.getString("add_intro").replace("多玩",""));
                        String urlFilePath= ImageUtil.downloadFromUrl(itemJsonObject.getString("url"));
                        String ossUrl;
                        try {
                            GifUtil.addTextWatermarkToGif(new File(urlFilePath), "www.gutongxue.com", new File(urlFilePath));
                            ossUrl= OssUtil.getOSSUrl(urlFilePath,".gif");
                        }catch (Exception e){
                            ossUrl= OssUtil.getOSSUrl(urlFilePath,".png")+"@!watermark";
                        }
                        image.setUrl(ossUrl);
                        image.setSeq((int)( Math.random()*10));
                        List<Image> imageList=gtxDao.getImageList(" and image_description = '"+image.getDescription()+"' ",0,9999999);
                        if (imageList.size()>0){
                            continue;
                        }
                        gtxDao.insertImage(image);
                        count++;
                    }
                    CrawlerConfig crawlerConfig =new CrawlerConfig();
                    crawlerConfig.setName("多玩今日囧图");
                    crawlerConfig.setProgress(String.valueOf(itemNumber));
                    gtxDao.updateCrawlerConfig(crawlerConfig);
                }catch (Exception e){
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("谷同学网站抓取脚本遇到异常","抓取 多玩图库今日囧图 脚本出错,错误原因:"+e);
        }
        return count;
    }

    /**
     * 每天凌晨0点15分执行,抓取搞笑gif
     */
    public int getEverydayGifImage(GtxDao gtxDao){
        int count=0;
        try {
            String today= TimeUtil.getToday();
            //获取数据库里上次抓到哪一期了
            int crawlerProgress=Integer.valueOf(gtxDao.getCrawlerConfig("多玩搞笑gif").getProgress());
            //获取今日囧图的html,获取网页上囧图期数的列表
            String listUrl="http://tu.duowan.com/m/bxgif";
            String listHtml=HtmlUtil.sendGetGzip(listUrl,"utf-8");
            if (listHtml==null||listHtml.equals("")){
                listHtml=HtmlUtil.sendGet(listUrl,"utf-8");
            }
            Document listDocument= Jsoup.parse(listHtml);
            Elements listElements=listDocument.select("em");
            //对列表从旧到新的遍历
            for (int i=listElements.size()-1;i>=0;i--){
                String itemTitle=listElements.get(i).select("a").text().trim();
                if (itemTitle==null||itemTitle.equals("")){
                    continue;
                }
                try {
                    //截取出期数
                    itemTitle=itemTitle.split("：")[0].split("弹")[0].split("第")[1];
                    int itemNumber=Integer.valueOf(itemTitle);
                    if (crawlerProgress>=itemNumber){
                        continue;
                    }
                    //获取到拿数据的url
                    String itemUrl=listElements.get(i).select("a").first().attr("href").trim();
                    itemUrl=itemUrl.split("/")[itemUrl.split("/").length-1];
                    itemUrl=itemUrl.split("\\.")[0];
                    itemUrl="http://tu.duowan.com/index.php?r=show/getByGallery/&gid="+itemUrl;
                    //从url里解析出json,然后根据需要解析
                    String imgJsonObjectString=HtmlUtil.sendGetGzip(itemUrl,"utf-8");
                    JSONObject imgJsonObject= JSON.parseObject(imgJsonObjectString);
                    JSONArray imgJsonArray=imgJsonObject.getJSONArray("picInfo");
                    for (Object object:imgJsonArray){
                        JSONObject itemJsonObject= (JSONObject) object;
                        Image image=new Image();
                        image.setDate(today);
                        image.setDescription(itemJsonObject.getString("add_intro").replace("多玩",""));
                        String urlFilePath= ImageUtil.downloadFromUrl(itemJsonObject.getString("url"));
                        String ossUrl;
                        try {
                            GifUtil.addTextWatermarkToGif(new File(urlFilePath), "www.gutongxue.com", new File(urlFilePath));
                            ossUrl= OssUtil.getOSSUrl(urlFilePath,".gif");
                        }catch (Exception e){
                            ossUrl= OssUtil.getOSSUrl(urlFilePath,".png")+"@!watermark";
                        }
                        image.setUrl(ossUrl);
                        image.setSeq((int)( Math.random()*10));
                        List<Image> imageList=gtxDao.getImageList(" and image_description = '"+image.getDescription()+"' ",0,9999999);
                        if (imageList.size()>0){
                            continue;
                        }
                        gtxDao.insertImage(image);
                        count++;
                    }
                    CrawlerConfig crawlerConfig =new CrawlerConfig();
                    crawlerConfig.setName("多玩搞笑gif");
                    crawlerConfig.setProgress(String.valueOf(itemNumber));
                    gtxDao.updateCrawlerConfig(crawlerConfig);
                }catch (Exception e){
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("谷同学网站抓取脚本遇到异常","抓取 多玩图库搞笑gif 脚本出错,错误原因:"+e);
        }
        return count;
    }
}
