package com.gutongxue.www.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by Shadow on 2017/2/13.
 * 谷若琪个人所用,通用工具类,以后我写的每个项目都放进去
 */
public class GRQUtil {
    /**
     * 对返回前台的数据格式化
     * @param message
     * @param status
     * @param result
     * @return
     */
    public static String returnJsonString(String message,boolean status,Object result) {
        JSONObject item=new JSONObject();
        item.put("message", message);
        item.put("status", status);
        item.put("result", result);
        return JSON.toJSONString(item, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty);

    }

    public static boolean checkNull(Object o){
        if (o==null){
            return true;
        }
        if (o instanceof String){
            if ("".equals((String)o)){
                return true;
            }
            return false;
        }
        return false;
    }
}
