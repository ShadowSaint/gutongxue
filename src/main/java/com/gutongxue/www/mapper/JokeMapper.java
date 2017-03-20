package com.gutongxue.www.mapper;

import com.gutongxue.www.domain.Joke;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shadow on 2016/11/17.
 */
public class JokeMapper implements RowMapper {
    @Override
    public Joke mapRow(ResultSet resultSet, int i) throws SQLException {
        Joke joke=new Joke();
        joke.setId(resultSet.getInt("id"));
        joke.setContent(resultSet.getString("joke_content"));
        return joke;
    }
}
