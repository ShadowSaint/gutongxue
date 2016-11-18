package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.utilities.OssUtil;
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
 * Created by Shadow on 2016/10/8.
 */
@Controller
public class IndexController {
    @Autowired
    GtxDao gtxDao;

    @RequestMapping("/")
    public String printIndexPage(){
        return "joke";
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> printIndex(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        List<Image> imageList=gtxDao.getImageList("",0,999999);
        for (Image image:imageList){
            OssUtil.deleteFileByOssUrl(image.getUrl());
        }
        json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
    }


}
