package com.gutongxue.www.dao.impl;

import com.gutongxue.www.dao.GtxDao;
import com.gutongxue.www.domain.CrawlerConfig;
import com.gutongxue.www.domain.Image;
import com.gutongxue.www.domain.Joke;
import com.gutongxue.www.domain.Video;
import com.gutongxue.www.mapper.CrawlerMapper;
import com.gutongxue.www.mapper.ImageMapper;
import com.gutongxue.www.mapper.JokeMapper;
import com.gutongxue.www.mapper.VideoMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Shadow on 2016/11/11.
 */
public class GtxDaoImpl implements GtxDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertJoke(Joke joke) {
        String sql = "insert into gtx_base_joke (joke_content,joke_source,gmt_create,gmt_modified) values (?,?,now(),now())";
        jdbcTemplate.update(sql,new Object[]{joke.getContent(),joke.getSource()});
    }

    @Override
    public void insertImage(Image image) {
        String sql = "insert into gtx_base_image (image_url,image_description,image_source,gmt_create,gmt_modified) values (?,?,?,now(),now())";
        jdbcTemplate.update(sql,new Object[]{image.getUrl(),image.getDescription(),image.getSource()});
    }

    @Override
    public void insertVideo(Video video) {
        String sql = "insert into gtx_base_video (video_url,video_cover,video_description,video_source,gmt_create,gmt_modified) values (?,?,?,?,now(),now())";
        jdbcTemplate.update(sql,new Object[]{video.getUrl(),video.getCover(),video.getDescription(),video.getSource()});
    }

    @Override
    public CrawlerConfig getCrawlerConfig(String name) {
        try {
            String sql = "select * from gtx_crawler_progress where crawler_name = ? limit 0,1";
            CrawlerConfig crawlerConfig = (CrawlerConfig) jdbcTemplate.queryForObject(sql,new Object[]{name},new CrawlerMapper());
            return crawlerConfig;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void updateCrawlerConfig(CrawlerConfig crawlerConfig) {
        String sql = "update gtx_crawler_progress set crawler_progress = ? where crawler_name = ?";
        jdbcTemplate.update(sql,new Object[]{crawlerConfig.getProgress(), crawlerConfig.getName()});
    }

    @Override
    public List<Joke> getJokeList(String queryParam, int page, int size) {
        String sql = "select * from gtx_base_joke where 1=1 "+queryParam+" order by gmt_modified desc limit ?,?";
        List<Joke> jokeList=jdbcTemplate.query(sql,new Object[]{page*size,size},new JokeMapper());
        return jokeList;
    }

    @Override
    public int getJokeListCount(String queryParam) {
        String sql="select count(*) from gtx_base_joke where 1=1 "+queryParam;
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    @Override
    public List<Image> getImageList(String queryParam, int page, int size) {
        String sql = "select * from gtx_base_image where 1=1 "+queryParam+" order by gmt_modified desc limit ?,?";
        List<Image> imageList=jdbcTemplate.query(sql,new Object[]{page*size,size},new ImageMapper());
        return imageList;
    }

    @Override
    public int getImageListCount(String queryParam) {
        String sql = "select count(*) from gtx_base_image where 1=1 "+queryParam;
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    @Override
    public List<Video> getVideoList(String queryParam, int page, int size) {
        String sql = "select * from gtx_base_video where 1=1 "+queryParam+" order by gmt_modified desc limit ?,?";
        List<Video> videoList=jdbcTemplate.query(sql,new Object[]{page*size,size},new VideoMapper());
        return videoList;
    }

    @Override
    public int getVideoListCount(String queryParam) {
        String sql = "select count(*) from gtx_base_video where 1=1 "+queryParam;
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    @Override
    public void insertLogCrawler(String time, String content) {
        String sql = "insert into gtx_log_crawler (log_time,log_content) values (?,?)";
        jdbcTemplate.update(sql,new Object[]{time,content});
    }
}
