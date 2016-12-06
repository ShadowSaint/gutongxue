package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Video;
import com.gutongxue.www.utilities.ReturnJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Shadow on 2016/11/17.
 */
@Controller
public class VideoController {
    @Autowired
    GtxDao gtxDao;

    @RequestMapping("/video")
    public String printVideoPage(){
        return "video";
    }

    @RequestMapping("/api/video_list")
    public ResponseEntity<String> getVideoList(HttpServletRequest request){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        try {
            int page;
            try {
                page = Integer.valueOf(request.getParameter("page"));
            } catch (Exception e) {
                page = 0;
            }
            int size;
            try {
                size = Integer.valueOf(request.getParameter("size"));
            } catch (Exception e) {
                size = 6;
            }
            String queryParam="";
            List<Video> videoList=gtxDao.getVideoList(queryParam,page,size);
            JSONArray videoJsonArray=new JSONArray();
            for (Video video:videoList){
                JSONObject videoJsonObject=new JSONObject();
                videoJsonObject.put("url",video.getUrl());
                videoJsonObject.put("cover",video.getCover());
                videoJsonObject.put("description",video.getDescription());
                videoJsonObject.put("date",video.getDate());
                videoJsonArray.add(videoJsonObject);
            }
            result.put("list",videoJsonArray);
            int count=gtxDao.getVideoListCount(queryParam);
            result.put("count",count);
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            json = ReturnJsonUtil.returnFailJsonString(null, "参数传递错误: "+e.getMessage());
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }
}
