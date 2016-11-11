package com.gutongxue.www.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by Shadow on 2016/7/28.
 */
public class ReturnJsonUtil {

    /**
     * 返回成功信息
     * @param result 需要返回的值
     * @param errorMessage 提示信息
     * @return
     */
    public static String returnFailJsonString(Object result, String errorMessage) {
        JSONObject item=new JSONObject();
        item.put("message", errorMessage);
        item.put("status", false);
        item.put("result", result);
        String jsonString = JSON.toJSONString(item);
        return jsonString;
    }

    /**
     * 返回失败信息
     * @param result 需要返回的值
     * @param message 提示信息
     * @return
     */
    public static String returnSuccessJsonString(Object result, String message){
        JSONObject item=new JSONObject();
        item.put("message", message);
        item.put("status", true);
        item.put("result", result);
        String jsonString = JSON.toJSONString(item);
        return jsonString;
    }

}
