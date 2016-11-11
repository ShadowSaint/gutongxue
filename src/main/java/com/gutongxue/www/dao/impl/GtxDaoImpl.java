package com.gutongxue.www.dao.impl;

import com.gutongxue.www.dao.GtxDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
}
