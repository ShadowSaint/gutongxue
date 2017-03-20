package com.gutongxue.www.crawler;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.utilities.GRQUtil;
import com.gutongxue.www.utilities.HtmlUtil;
import com.gutongxue.www.utilities.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class JokeJiCrawler {
    private static final Logger logger = LoggerFactory.getLogger(JokeJiCrawler.class);


    /**
     * 每天凌晨0点30执行,抓取昨天的内容,
     * 如果内容为图片,存入图片库,如果内容为笑话,存入笑话库
     */
    public static int getJokeJiInfo(GtxDao gtxDao) {
        int count = 0;
        String today = TimeUtil.getToday();
        String yesterdayYearMonth = TimeUtil.getYesterdayByFormat("yyyy_M");
        String jokeListUrl = "http://www.jokeji.cn/DateUpdate_" + yesterdayYearMonth + ".htm";
        String jokeListUrlHtml = HtmlUtil.sendGetGzip(jokeListUrl, "gbk");
        if (GRQUtil.checkNull(jokeListUrl)) {
            logger.error("getJokeJiInfo方法致命错误:获取列表htmlbody错误");
            return 0;
        }
        Document jokeListDoc = Jsoup.parse(jokeListUrlHtml);
        Elements jokeListElements = jokeListDoc.select("tbody tbody tbody tr");
        String yesterdayYearMonthDay = TimeUtil.getYesterdayByFormat("yyyy-M-d");
        String jokeUrl="";
        for (Element element : jokeListElements) {
            try {
                Elements dateElements = element.select("span.date");
                if (dateElements.size() == 0) {
                    continue;
                }
                String date = dateElements.first().text().trim();
                if (date == null || !date.equals(yesterdayYearMonthDay)) {
                    continue;
                }
                jokeUrl = element.select("a").first().attr("href").trim();
                jokeUrl = "http://www.jokeji.cn" + jokeUrl;
                String jokeUrlHtml = HtmlUtil.sendGetGzip(jokeUrl, "gbk");
                Document jokeDoc = Jsoup.parse(jokeUrlHtml);
                Elements jokeElements = jokeDoc.select("p");
                String jokeContent = jokeElements.toString();
                if (jokeContent != null && !jokeContent.equals("")) {
                    Joke joke = new Joke();
                    joke.setSource(0);
                    joke.setContent(jokeContent);
                    List<Joke> jokeList = gtxDao.getJokeList(" and joke_content = '" + jokeContent + "'", 0, 999999);
                    if (jokeList.size() > 0) {
                        continue;
                    }
                    gtxDao.insertJoke(joke);
                    count++;
                }
            } catch (Exception e) {
                logger.warn("抓取 {} 脚本出错,错误原因:" + e,jokeUrl);
                continue;
            }
        }
        return count;
    }

}
