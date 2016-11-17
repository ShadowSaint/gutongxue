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
    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨1点15分执行,一次抓20个
     */
    @Scheduled(cron="0 15 1 * * ?")
//        @Scheduled(cron="0/15 * * * * ?")
    public void getInfo(){
        try {
            int count=0;
            int page=0;
            String today= TimeUtil.getToday();
            while (count<20){
                if (page>100){
                    MailUtil.send_email("抓取 最右视频 脚本运行超次,请检查");
                    break;
                }
                try {
                    String sourceUrl="http://tbapi.ixiaochuan.cn/index/recommend";
                    String jsonString= HtmlUtil.sendPost(sourceUrl,"{\"h_model\": \"iPhone 6 Plus\",\"h_ch\": \"appstore\",\"h_ts\": 1479294316361,\"h_av\": \"3.0.4\",\"tab\": \"video\",\"h_did\": \"930882205da5ddfc1a80be5f21f191e9bd618e96\",\"filter\": \"video\",\"h_m\": 8650033,\"h_os\": \"8.200000\",\"h_nt\": 1,\"token\": \"582c3d60277f2816db50218f\",\"h_dt\": 1,\"direction\": \"down\"}","utf-8");
                    JSONObject apiJson= JSON.parseObject(jsonString);
                    JSONObject dataJson=apiJson.getJSONObject("data");
                    JSONArray listJsonArray=dataJson.getJSONArray("list");
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
    }
}
