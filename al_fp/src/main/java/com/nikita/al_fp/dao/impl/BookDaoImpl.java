package com.nikita.al_fp.dao.impl;

import com.nikita.al_fp.dao.BookDao;
import com.nikita.al_fp.dao.mapper_replaced.BookRowMapper;
import com.nikita.al_fp.dao.mapper_replaced.PersonRowMapper;
import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nikita.al_fp.dao.UtilDao.getString;

/*@Component*/
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public static final String INSERT_INTO_BOOK_TEST_SQL = "INSERT INTO book (person_id, name, author, year) VALUES (?, ?, ?, ?)";
    public static final String INSERT_INTO_BOOK_SQL = "INSERT INTO book (person_id, name, author, year) VALUES (null, ?, ?, ?)";
    public static final String UPDATE_BOOK_SQL = "UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?";
    public static final String DELETE_BOOK_SQL = "DELETE FROM book WHERE id = ?";
    public static final String SELECT_BOOK_SQL = "SELECT * FROM book";
    public static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE book.id = ?";
    public static final String UPDATE_BOOK_PERSON_ID_SQL = "UPDATE book SET person_id = ? WHERE id = ?";
    public static final String DELETE_BOOK_PERSON_BY_BOOK_ID_SQL = "UPDATE book SET person_id = null WHERE id = ?";
    public static final String SELECT_BOOK_BY_NAME_SQL = "SELECT book.name FROM book WHERE book.name = ?";
    public static final String SELECT_PERSON_BY_BOOK_ID_SQL = "SELECT * FROM person JOIN book on person.id = book.person_id " +
            "WHERE book.id = ?";

    public static final String SELECT_FIRST_PERSON_ID = "SELECT person.id FROM person";

    /*@Autowired*/
    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        startProgramProcessBook();
    }

    @Override
    public void insertBook(Book book) {
        jdbcTemplate.update(INSERT_INTO_BOOK_SQL, book.getName(), book.getAuthor(), book.getYear());
    }

    @Override
    public void updateBook(int id, Book book) {
        jdbcTemplate.update(UPDATE_BOOK_SQL,
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK_SQL, id);
    }



    public List<Book> selectBook() {
        return jdbcTemplate.query(SELECT_BOOK_SQL, new BookRowMapper());
    }

    @Override
    public Optional<Book> selectBookById(int id) {
        return jdbcTemplate.query(SELECT_BOOK_BY_ID_SQL, new BookRowMapper(), id)
                .stream().findAny();
    }

    public Optional<Person> selectPersonByBookId(int id) {
        return jdbcTemplate.query(SELECT_PERSON_BY_BOOK_ID_SQL, new PersonRowMapper(), id)
                .stream().findAny();
    }

    @Override
    public Optional<String> selectBookNameByBookName(String name) {
        return getString(name, jdbcTemplate, SELECT_BOOK_BY_NAME_SQL);
    }

    @Override
    public void assignBookOwner(int userId, int bookId) {
        jdbcTemplate.update(UPDATE_BOOK_PERSON_ID_SQL, userId, bookId);
    }

    @Override
    public void deletePersonFromBook(int bookId) {
        jdbcTemplate.update(DELETE_BOOK_PERSON_BY_BOOK_ID_SQL, bookId);
    }


    public void startProgramProcessBook() {

        List<Integer> list = jdbcTemplate.queryForList(SELECT_FIRST_PERSON_ID, Integer.class);
        int currentPersonFirstId = list.get(0);

        jdbcTemplate.update("DELETE FROM book");

        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId, "1989", "Billy Harington", 2014);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId, "Fitch", "Adolf Hitler", 1998);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId+1, "Gaz", "Amand Gas",1899);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId+1, "Lenin", "Bigi Boo", 1956);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId+2, "Gosha", "Zak Snider", 1999);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, currentPersonFirstId+2, "Bitch", "Peter Parker", 2020);
        jdbcTemplate.update(INSERT_INTO_BOOK_TEST_SQL, null, "Bitch2", "Peter Parker", 2022);
    }
}
