package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.utilities.ReturnJsonUtil;
import com.gutongxue.www.utilities.TaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Shadow on 2016/11/12.
 */
@Controller
public class UrlDomainNameController {
    @Autowired
    GtxDao gtxDao;
    @RequestMapping("/api/url")
    public ResponseEntity<String> getUrl(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        try {
            TaskUtil.task();
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            json = ReturnJsonUtil.returnFailJsonString(result, "参数传递错误");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }

}
