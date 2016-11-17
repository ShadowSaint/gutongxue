package com.gutongxue.www.mapper;

import com.gutongxue.www.domain.Crawler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shadow on 2016/11/16.
 */
public class CrawlerMapper implements RowMapper {
    @Override
    public Crawler mapRow(ResultSet resultSet, int i) throws SQLException {
        Crawler crawler=new Crawler();
        crawler.setName(resultSet.getString("crawler_name"));
        crawler.setProgress(resultSet.getString("crawler_progress"));
        return crawler;
    }
}
