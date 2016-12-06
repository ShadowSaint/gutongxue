package com.gutongxue.www.mapper;

import com.gutongxue.www.domain.CrawlerConfig;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shadow on 2016/11/16.
 */
public class CrawlerMapper implements RowMapper {
    @Override
    public CrawlerConfig mapRow(ResultSet resultSet, int i) throws SQLException {
        CrawlerConfig crawlerConfig =new CrawlerConfig();
        crawlerConfig.setName(resultSet.getString("crawler_name"));
        crawlerConfig.setProgress(resultSet.getString("crawler_progress"));
        return crawlerConfig;
    }
}
