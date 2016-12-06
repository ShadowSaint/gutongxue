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
        String a = "{\n" +
                "\t\"offset\": 0,\n" +
                "\t\"filter\": \"video\",\n" +
                "\t\"tab\": \"video\",\n" +
                "\t\"direction\": \"down\",\n" +
                "\t\"auto\": 0,\n" +
                "\t\"h_av\": \"3.1.2\",\n" +
                "\t\"h_dt\": 0,\n" +
                "\t\"h_os\": 23,\n" +
                "\t\"h_model\": \"ZUK Z2121\",\n" +
                "\t\"h_did\": \"861305030160585_02:00:00\",\n" +
                "\t\"h_nt\": 1,\n" +
                "\t\"h_m\": 4725996,\n" +
                "\t\"h_ch\": \"lenovo\",\n" +
                "\t\"h_ts\": 1481010526422,\n" +
                "\t\"token\": \""+"5846676a277f28471857abc0"+"\"\n" +
                "}";
        System.out.println(a);
    }

}
