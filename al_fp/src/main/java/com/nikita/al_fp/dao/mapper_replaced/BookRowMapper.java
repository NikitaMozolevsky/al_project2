package com.nikita.al_fp.dao.mapper_replaced;

import com.nikita.al_fp.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Book book = new Book();

        book.setId(resultSet.getInt("id"));

        if (resultSet.getString("id")!=null
                ||resultSet.getInt("id")!=0) {
            book.setId(resultSet.getInt("id"));
        }

        book.setPersonId(resultSet.getInt("person_id"));

        if (resultSet.getString("person_id")!=null
                ||resultSet.getInt("person_id")!=0) {
            book.setPersonId(resultSet.getInt("person_id"));
        }
        book.setName(resultSet.getString("name"));
        book.setYear(resultSet.getInt("year"));
        book.setAuthor(resultSet.getString("author"));

        return book;
    }
}
