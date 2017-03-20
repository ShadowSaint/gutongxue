package com.gutongxue.www.mapper;

import com.gutongxue.www.domain.Video;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shadow on 2016/11/16.
 */
public class VideoMapper implements RowMapper {
    @Override
    public Video mapRow(ResultSet resultSet, int i) throws SQLException {
        Video video=new Video();
        video.setId(resultSet.getInt("id"));
        video.setUrl(resultSet.getString("video_url"));
        video.setCover(resultSet.getString("video_cover"));
        video.setDescription(resultSet.getString("video_description"));
        return video;
    }
}
