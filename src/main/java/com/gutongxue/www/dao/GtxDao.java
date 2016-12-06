package com.gutongxue.www.dao;

import com.gutongxue.www.domain.CrawlerConfig;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.domain.Video;

import java.util.List;

/**
 * Created by Shadow on 2016/11/11.
 */
public interface GtxDao {
    //脚本
    void insertJoke(Joke joke);
    void insertImage(Image image);
    void insertVideo(Video video);
    CrawlerConfig getCrawlerConfig(String name);
    void updateCrawler(CrawlerConfig crawlerConfig);

    //内容
    List<Joke> getJokeList(String queryParam,int page,int size);
    int getJokeListCount(String queryParam);
    List<Image> getImageList(String queryParam,int page,int size);
    int getImageListCount(String queryParam);
    List<Video> getVideoList(String queryParam,int page,int size);
    int getVideoListCount(String queryParam);
}
