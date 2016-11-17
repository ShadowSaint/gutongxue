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
                "                \"id\":8158909,\n" +
                "                \"mid\":855684,\n" +
                "                \"content\":\"某玩家自制一个亚索的一个通关游戏，哈哈哈哈塞给。。痛也给痛！面对疾风吧小学生\",\n" +
                "                \"reviews\":174,\n" +
                "                \"likes\":578,\n" +
                "                \"up\":624,\n" +
                "                \"ct\":1478358159,\n" +
                "                \"imgs\":Array[1],\n" +
                "                \"videos\":{\n" +
                "                    \"26807016\":{\n" +
                "                        \"thumb\":26807016,\n" +
                "                        \"url\":\"http://gslb.miaopai.com/stream/HlHDWTF138tVd-0PcIZEiw__.mp4\",\n" +
                "                        \"dur\":159,\n" +
                "                        \"h5url\":\"http://www.miaopai.com/show/HlHDWTF138tVd-0PcIZEiw__.htm\",\n" +
                "                        \"priority\":1,\n" +
                "                        \"webinfo\":{\n" +
                "                            \"url\":\"http://www.miaopai.com/show/HlHDWTF138tVd-0PcIZEiw__.htm\",\n" +
                "                            \"topheight\":70,\n" +
                "                            \"height\":359,\n" +
                "                            \"aspect_ratio\":1\n" +
                "                        },\n" +
                "                        \"urlsrc\":\"http://tbvideo.ixiaochuan.cn/zyvd/a9/80/f4bda8c18f5ab92f7cb180e20157\",\n" +
                "                        \"urlext\":\"http://gslb.miaopai.com/stream/HlHDWTF138tVd-0PcIZEiw__.mp4\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"status\":2,\n" +
                "                \"share\":49,\n" +
                "                \"member\":Object{...},\n" +
                "                \"topic\":Object{...},\n" +
                "                \"god_reviews\":Array[1]\n" +
                "            }";
        JSONObject jsonObject= JSON.parseObject(a);
        for (Object o:jsonObject.keySet()){
            System.out.println(o);
        }
    }

}
