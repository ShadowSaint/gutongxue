package com.gutongxue.www.mapper;

import com.gutongxue.www.domain.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shadow on 2016/11/16.
 */
public class ImageMapper implements RowMapper {
    @Override
    public Image mapRow(ResultSet resultSet, int i) throws SQLException {
        Image image=new Image();
        image.setId(resultSet.getInt("id"));
        image.setUrl(resultSet.getString("image_url"));
        image.setDescription(resultSet.getString("image_description"));
        return image;
    }
}
