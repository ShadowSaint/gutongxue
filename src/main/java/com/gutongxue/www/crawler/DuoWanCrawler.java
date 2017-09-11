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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by Shadow on 2016/11/16.
 */
@Component
public class DuoWanCrawler {
    private static final Logger logger= LoggerFactory.getLogger(DuoWanCrawler.class);

    public static int getDuoWanInfo(int type,GtxDao gtxDao){
        int count=0;
        //获取数据库里上次抓到哪一期了
        String key;
        if (type==0){
            key="多玩今日囧图";
        }else {
            key="多玩搞笑gif";
        }
        CrawlerConfig crawlerConfig=gtxDao.getCrawlerConfig(key);
        if (GRQUtil.checkNull(crawlerConfig)){
            logger.error("getDuoWanInfo方法致命错误:从数据库中获取 {} 时,获取不到最新抓取到第几期了",key);
            return 0;
        }
        int crawlerProgress=Integer.valueOf(crawlerConfig.getProgress());
        String listUrl;
        if (type==0){
            listUrl="http://tu.duowan.com/tag/5037.html";
        }else {
            listUrl="http://tu.duowan.com/m/bxgif";
        }
        String listHtml= HtmlUtil.sendGetGzip(listUrl,"utf-8");
        if (GRQUtil.checkNull(listHtml)){
            listHtml= HtmlUtil.sendGet(listUrl,"utf-8");
        }
        if (GRQUtil.checkNull(listHtml)){
            logger.error("getDuoWanInfo方法致命错误:获取 {} 列表htmlbody错误",key);
            return 0;
        }

        try {
            Document listDocument= Jsoup.parse(listHtml);
            Elements listElements=listDocument.select("em");
            String itemTitle="";
            String itemUrl="";
            //对列表从旧到新的遍历
            for (int i=listElements.size()-1;i>=0;i--){
                itemTitle=listElements.get(i).select("a").text().trim();
                if (GRQUtil.checkNull(itemTitle)){
                    continue;
                }
                try {
                    //截取出期数
                    if (type==0){
                        itemTitle=itemTitle.split("：")[0].split("期")[0].split("第")[1];
                    }else {
                        itemTitle=itemTitle.split("：")[0].split("弹")[0].split("第")[1];
                    }
                    int itemNumber=Integer.valueOf(itemTitle);
                    if (crawlerProgress>=itemNumber){
                        continue;
                    }
                    //获取到拿数据的url
                    itemUrl=listElements.get(i).select("a").first().attr("href").trim();
                    itemUrl=itemUrl.split("/")[itemUrl.split("/").length-1];
                    itemUrl=itemUrl.split("\\.")[0];
                    itemUrl="http://tu.duowan.com/index.php?r=show/getByGallery/&gid="+itemUrl;
                    //从url里解析出json,然后根据需要解析
                    String imgJsonObjectString=HtmlUtil.sendGetGzip(itemUrl,"utf-8");
                    if (GRQUtil.checkNull(imgJsonObjectString)){
                        imgJsonObjectString=HtmlUtil.sendGet(itemUrl,"utf-8");
                    }
                    JSONObject imgJsonObject= JSON.parseObject(imgJsonObjectString);
                    JSONArray imgJsonArray=imgJsonObject.getJSONArray("picInfo");
                    for (Object object:imgJsonArray){
                        JSONObject itemJsonObject= (JSONObject) object;
                        Image image=new Image();
                        image.setSource(0);
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
                        List<Image> imageList=gtxDao.getImageList(" and image_description = '"+image.getDescription()+"' ",0,9999999);
                        if (imageList.size()>0){
                            continue;
                        }
                        gtxDao.insertImage(image);
                        count++;
                    }
                    crawlerConfig =new CrawlerConfig();
                    crawlerConfig.setName(key);
                    crawlerConfig.setProgress(String.valueOf(itemNumber));
                    gtxDao.updateCrawlerConfig(crawlerConfig);
                }catch (Exception e){
                    logger.warn("抓取多玩 {} 时出错,网址为: {}",itemTitle,itemUrl);
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

}
