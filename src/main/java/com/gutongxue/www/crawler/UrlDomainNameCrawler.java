package com.gutongxue.www.crawler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shadow on 2016/11/11.
 */
public class UrlDomainNameCrawler {
    private static String path = "D:/";
    private static String filenameTemp;
    static String items[] = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void main(String[] args) {
        try {
            creatTxtFile("buyableUrl");
            List<String> urlList = getBuyableUrl();
            for (String url : urlList) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getBuyableUrl() {
        List<String> urlList = new ArrayList<>();
        try {
            for (int a = 0; a < items.length; a++) {
                for (int b = 0; b < items.length; b++) {
                    for (int c = 0; c < items.length; c++) {
                        for (int d = 0; d < items.length; d++) {
                            for (int e = 0; e < items.length; e++) {
                                for (int f = 0; f < items.length; f++) {
                                    String url = items[a] + items[b] + items[c] + items[d] + items[e] + items[f];
                                    String html = sendPost(url);
                                    if (html != null && !html.equals("")) {
                                        JSONObject htmlJO = JSON.parseObject(html);
                                        if (htmlJO != null && !htmlJO.equals("")) {
                                            JSONObject resultJo = htmlJO.getJSONObject("result");
                                            if (resultJo != null && !resultJo.equals("")) {
                                                JSONArray accurateJA = resultJo.getJSONArray("accurate");
                                                if (accurateJA.size() > 0) {
                                                    String status = accurateJA.getJSONObject(0).getString("status");
                                                    if (status != null && status.equals("UNREGISTERED")) {
                                                        System.out.println(url);
                                                        writeTxtFile(url);
                                                        urlList.add(url);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlList;
    }

    private static String sendPost(String buyableUrl) {
        try {
            StringBuffer html = new StringBuffer();
            URL url = new URL("https://cloud.baidu.com/api/bcd/order/search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727)");

            StringBuilder sb = new StringBuilder();
            String param = "{\"keyword\":\"" + buyableUrl + ".com\",\"type\":\"\",\"mode\":\"NORMAL\"}";
            sb.append(param);
            conn.getOutputStream().write(param.toString().getBytes());
            // 将参数头的数据写入到输出流中

            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return html.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    private static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        filenameTemp = path + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr 新内容
     * @throws IOException
     */
    private static boolean writeTxtFile(String newStr)
            throws IOException {
// 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
// 文件路径
            File file = new File(filenameTemp);
// 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

// 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
// System.getProperty("line.separator")
// 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
// TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

}


