package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Joke;
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
public class JokeController {
    @Autowired
    GtxDao gtxDao;

    @RequestMapping("/joke")
    public String printJokePage(){
        return "joke";
    }

    @RequestMapping("/api/joke_list")
    public ResponseEntity<String> getJokeList(HttpServletRequest request){
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
            List<Joke> jokeList=gtxDao.getJokeList(queryParam,page,size);
            JSONArray jokeJsonArray=new JSONArray();
            for (Joke joke:jokeList){
                JSONObject jokeJsonObject=new JSONObject();
                jokeJsonObject.put("content",joke.getContent());
                jokeJsonObject.put("date",joke.getDate());
                jokeJsonArray.add(jokeJsonObject);
            }
            result.put("list",jokeJsonArray);
            int count=gtxDao.getJokeListCount(queryParam);
            result.put("count",count);
            json = ReturnJsonUtil.returnSuccessJsonString(result, "请求成功");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            json = ReturnJsonUtil.returnFailJsonString(result, "参数传递错误");
            return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
        }
    }
}
