package com.nikita.al_fp.dao.mapper_replaced;

import com.nikita.al_fp.entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Person person = new Person();

        if (resultSet.getString("id")!=null
                ||resultSet.getInt("id")!=0) {
            person.setId(resultSet.getInt("id"));
        }

        person.setId(resultSet.getInt("id"));

        person.setName(resultSet.getString("name"));
        person.setBirthYear(resultSet.getInt("birth_year"));

        return person;
    }
}
