package com.gutongxue.www.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by Shadow on 2016/11/11.
 */
public class TestUtil {
    public static void main(String[] args) {
        Instant instant=Instant.now();
        String sourceUrl="http://ic.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&count=30&min_time="+instant.getEpochSecond()+"&screen_width=1080&iid=6856958363&device_id=34666038486&ac=wifi&channel=baidu&aid=7&app_name=joke_essay&version_code=580&version_name=5.8.0&device_platform=android&ssmix=a&device_type=ZUK+Z2121&device_brand=ZUK&os_api=23&os_version=6.0.1&uuid=861305030160585&openudid=74978bd8655e44fd&manifest_version_code=580&resolution=1080*1920&dpi=480&update_version_code=5804";
        System.out.println(HtmlUtil.sendGetGzip(sourceUrl,"utf-8"));

    }

}
