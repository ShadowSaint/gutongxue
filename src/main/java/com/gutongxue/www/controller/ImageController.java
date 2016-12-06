package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Image;
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
public class ImageController {
    @Autowired
    GtxDao gtxDao;

    @RequestMapping("/image")
    public String printImagePage(){
        return "image";
    }

    @RequestMapping("/api/image_list")
    public ResponseEntity<String> getImageList(HttpServletRequest request){
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
            List<Image> imageList=gtxDao.getImageList(queryParam,page,size);
            JSONArray imageJsonArray=new JSONArray();
            for (Image image:imageList){
                JSONObject imageJsonObject=new JSONObject();
                imageJsonObject.put("url",image.getUrl());
                imageJsonObject.put("description",image.getDescription());
                imageJsonObject.put("date",image.getDate());
                imageJsonArray.add(imageJsonObject);
            }
            result.put("list",imageJsonArray);
            int count=gtxDao.getImageListCount(queryParam);
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
