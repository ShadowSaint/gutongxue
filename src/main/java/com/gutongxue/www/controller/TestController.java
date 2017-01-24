package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.utilities.MailUtil;
import com.gutongxue.www.utilities.ReturnJsonUtil;
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
    @RequestMapping("/api/mail")
    public ResponseEntity<String> sendMail(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        try {
            MailUtil.send_email("测试服务器上邮件服务","测试服务器上发送邮件");
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            json = ReturnJsonUtil.returnFailJsonString(null, "参数传递错误: "+e.getMessage());
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }
}
