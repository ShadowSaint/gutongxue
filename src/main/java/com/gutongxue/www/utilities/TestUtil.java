package com.gutongxue.www.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Shadow on 2016/11/11.
 */
public class TestUtil {
    public static void main(String[] args) {
        String a="http://ic.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&count=30&min_time="+System.currentTimeMillis()/1000+"&screen_width=1080&ac=wifi&channel=baidu&aid=7&app_name=joke_essay&device_platform=android&device_type=ZUK+Z2121&device_brand=ZUK&os_api=23&os_version=6.0.1&manifest_version_code=580&resolution=1080*1920&dpi=480";
        System.out.println(HtmlUtil.sendGetGzip(a,"utf-8"));

    }

}
