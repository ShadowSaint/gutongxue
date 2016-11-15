package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.utilities.HtmlUtil;
import com.gutongxue.www.utilities.MailUtil;
import com.gutongxue.www.utilities.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class JokejiCrawler {
    @Autowired
    GtxDao gtxDao;

    /**
     * 每天凌晨3点执行,抓取昨天的内容
     */
    @Scheduled(cron="0 0 3 * * ?")
    public void getInfo(){
        try {
            String today=TimeUtil.getToday();
            String yesterdayYearMonth= TimeUtil.getYesterdayByFormat("yyyy-M");
            String jokeListUrl="http://www.jokeji.cn/DateUpdate_"+yesterdayYearMonth+".htm";
            String jokeListUrlHtml= HtmlUtil.sendGetGzip(jokeListUrl,"gbk");
            Document jokeListDoc= Jsoup.parse(jokeListUrlHtml);
            Elements jokeListElements=jokeListDoc.select("tbody tbody tbody tr");
            String yesterdayYearMonthDay=TimeUtil.getYesterdayByFormat("yyyy-M-d");
            for (Element element:jokeListElements){
                String date=element.select("span.date").first().text().trim();
                if (date==null||!date.equals(yesterdayYearMonthDay)){
                    continue;
                }
                String jokeUrl=element.select("a").first().attr("href").trim();
                String jokeUrlHtml= HtmlUtil.sendGetGzip(jokeUrl,"gbk");
                Document jokeDoc= Jsoup.parse(jokeUrlHtml);
                Elements jokeElements=jokeDoc.select("p");
                String jokeContent="";
                for (Element jokeElement:jokeElements){
                    jokeContent+=jokeElement.text().trim();
                }
                if (jokeContent!=null&&!jokeContent.equals("")){
                    Joke joke=new Joke();
                    joke.setContent(jokeContent);
                    joke.setDate(today);
                    gtxDao.insertJoke(joke);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            MailUtil.send_email("抓取 http://www.jokeji.cn/ 脚本出错,错误原因:"+e.getMessage());
        }

    }

}
