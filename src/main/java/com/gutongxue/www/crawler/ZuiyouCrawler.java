package com.gutongxue.www.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Video;
import com.gutongxue.www.utilities.HtmlUtil;
import com.gutongxue.www.utilities.MailUtil;
import com.gutongxue.www.utilities.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Shadow on 2016/11/16.
 */
@Component
public class ZuiyouCrawler {

    /**
     * 每天凌晨1点执行,一次抓40个
     */
    public int getInfo(GtxDao gtxDao){
        int count=0;
        try {
            int page=0;
            String today= TimeUtil.getToday();
            while (count<40){
                if (page>100){
                    MailUtil.send_email("抓取 最右视频 脚本运行超次,请检查");
                    break;
                }
                try {
                    String sourceUrl="http://tbapi.ixiaochuan.cn/index/recommend";
                    //初步断定,只要没有h_m这个属性,校验就能通过,它应该是控制了过期时间,没有它剩下的全是1都没问题
                    String jsonString= HtmlUtil.sendPost(sourceUrl,
                            "{\n" +
                                    "\t\"offset\": 0,\n" +
                                    "\t\"filter\": \"video\",\n" +
                                    "\t\"tab\": \"video\",\n" +
                                    "\t\"direction\": \"down\",\n" +
                                    "\t\"auto\": 0,\n" +
                                    "\t\"h_av\": \"3.1.2\",\n" +
                                    "\t\"h_dt\": 0,\n" +
                                    "\t\"h_os\": 23,\n" +
                                    "\t\"h_model\": \"ZUK Z2121\",\n" +
                                    "\t\"h_did\": \"1\",\n" +
                                    "\t\"h_nt\": 1,\n" +
                                    "\t\"h_ch\": \"lenovo\",\n" +
                                    "\t\"h_ts\": 1,\n" +
                                    "\t\"token\": \"1\"\n" +
                                    "}",
                            "utf-8");
                    JSONObject apiJson= JSON.parseObject(jsonString);
                    JSONObject dataJson=apiJson.getJSONObject("data");
                    JSONArray listJsonArray=dataJson.getJSONArray("list");
                    if (listJsonArray.size()==0){
                        break;
                    }
                    for (Object object:listJsonArray){
                        JSONObject itemJson= (JSONObject) object;
                        Video video=new Video();
                        String description=itemJson.getString("content").replace("最右","").replace("小右","");
                        video.setDescription(description);
                        JSONObject videoJson=itemJson.getJSONObject("videos");
                        String mark="";
                        for (String key:videoJson.keySet()){
                            mark = key;
                            break;
                        }
                        if (mark==null||mark.equals("")){
                            continue;
                        }
                        List<Video> videoList=gtxDao.getVideoList(" and video_mark = '"+mark+"'",0,9999);
                        if (videoList.size()>0){
                            continue;
                        }
                        video.setMark(mark);
                        String cover="http://tbfile.ixiaochuan.cn/img/view/id/"+mark+"/sz/480x120";
                        video.setCover(cover);
                        JSONObject markJson=videoJson.getJSONObject(mark);
                        String url=markJson.getString("urlext");
                        if (url==null||url.equals("")){
                            continue;
                        }
                        video.setUrl(url);
                        video.setDate(today);
                        gtxDao.insertVideo(video);
                        count++;
                    }
                }catch (Exception e){
                    page++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("抓取 最右视频 脚本出错,错误原因:"+e);
        }
        return count;
    }
}
