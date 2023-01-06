package com.nikita.al_fp.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UtilDao {
    static Optional<String> getString(String name, JdbcTemplate jdbcTemplate, String selectPersonNameByNameSql) {
        List<String> strLst = jdbcTemplate.query(selectPersonNameByNameSql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, name);

        if (strLst.isEmpty()) {
            return Optional.empty();
        } else { // list contains exactly 1 element
            return Optional.ofNullable(strLst.get(0));
        }
    }
}
