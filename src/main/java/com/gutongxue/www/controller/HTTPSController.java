package com.gutongxue.www.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Shadow on 2017/2/7.
 */
@Controller
public class HTTPSController {
    @RequestMapping("/.well-known/acme-challenge/*")
    public ResponseEntity<String> check(HttpServletRequest request, HttpServletResponse response){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        String result="";
        try {
            String URI=request.getRequestURI();
            URI=URI.split("/")[URI.split("/").length-1];
            //文件路径填写你们上一步指定的路径+\.well-known\acme-challenge\
            File file=new File("C:\\Users\\Administrator\\Desktop\\letsencrypt-win-simple.V1.9.1\\.well-known\\acme-challenge\\"+URI);
            InputStream is = new FileInputStream(file);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(("验证文件").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        }catch (Exception e){

        }
        return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
    }
}
