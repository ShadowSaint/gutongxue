package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.crawler.ZuiyouCrawler;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.utilities.ReturnJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Shadow on 2016/12/6.
 */
//如果脚本出了什么问题,手动更新一下
@Controller
public class CrawlerController {
    @Autowired
    GtxDao gtxDao;

    @RequestMapping("/crawler/zuiyou")
    public ResponseEntity<String> zuiyou(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        try {
            ZuiyouCrawler zuiyouCrawler=new ZuiyouCrawler();
            zuiyouCrawler.getInfo(gtxDao);
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            json = ReturnJsonUtil.returnFailJsonString(null, "参数传递错误: "+e.getMessage());
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }

}
