package com.gutongxue.www.controller;

import com.gutongxue.www.utilities.GRQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Shadow on 2016/12/7.
 */
@Controller
public class TestController {
    private static final Logger LOGGER= LoggerFactory.getLogger(TestController.class);
    @RequestMapping("/api/test/log")
    public ResponseEntity<String> apiTestLog(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        String jsonString="";
        try {
            LOGGER.info("info级别日志,方法:{}","TestController.apiTestLog()");
            LOGGER.debug("debug级别日志,方法:{}","TestController.apiTestLog()");
            LOGGER.error("error级别日志,方法:{}","TestController.apiTestLog()");
            LOGGER.warn("warn级别日志,方法:{}","TestController.apiTestLog()");
            jsonString= GRQUtil.returnJsonString("请求成功",true,"日志已经输出");
        }catch (Exception e){
            jsonString= GRQUtil.returnJsonString("请求失败",false,e.getMessage());
        }
        return new ResponseEntity<String>(jsonString,responseHeaders, HttpStatus.OK);
    }
}
