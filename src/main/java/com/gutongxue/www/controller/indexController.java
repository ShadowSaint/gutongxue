package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.utilities.ReturnJsonUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Shadow on 2016/10/8.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String printIndexPage(){
        return "index";
    }
    @RequestMapping("/api/joke")
    public ResponseEntity<String> getJoke(HttpServletRequest request){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        String json = "";
        try {
            int page=Integer.valueOf(request.getParameter("page"));
            String content="我：老婆你骨折过吗？\n" +
                    "老婆：有啊！\n" +
                    "我：什么位置？\n" +
                    "老婆：我掉过牙。\n" +
                    "我。。。";
            content=content.replace("\n","</br>");
            JSONArray jsonArray=new JSONArray();
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            jsonArray.add(page+content);
            result.put("list",jsonArray);
            result.put("count",101);
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            json = ReturnJsonUtil.returnFailJsonString(result, "参数传递错误");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }
}
