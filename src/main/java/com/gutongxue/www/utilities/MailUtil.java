package com.gutongxue.www.utilities;

import com.gutongxue.www.domain.Mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {
    public static void  main(String args[]){
        try {
//            send_email();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send_email(String content){
        try {
            //经过一个小时的努力,终于决定告别智障QQ邮箱,是由于QQ邮箱智障的授权码导致的,妈的腿
            String to = "3003277@qq.com";
            String subject = "谷同学网站抓取脚本遇到异常";
//            String content = "正文";
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.163.com");
            properties.put("mail.smtp.port", "25");
            properties.put("mail.smtp.auth", "true");
            Authenticator authenticator = new Mail("grq860712914@163.com", "grq860712913");
            javax.mail.Session sendMailSession = javax.mail.Session.getDefaultInstance(properties, authenticator);
            MimeMessage mailMessage = new MimeMessage(sendMailSession);
            mailMessage.setFrom(new InternetAddress("grq860712914@163.com"));
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mailMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("grq860712914@163.com"));
            mailMessage.setSubject(subject, "UTF-8");
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            html.setContent(content.trim(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            mailMessage.setContent(mainPart);
            Transport.send(mailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

