package com.nikita.al_fp.dao.impl;

import com.nikita.al_fp.dao.PersonDao;
import com.nikita.al_fp.dao.mapper_replaced.BookRowMapper;
import com.nikita.al_fp.dao.mapper_replaced.PersonRowMapper;
import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nikita.al_fp.dao.UtilDao.getString;

/*@Component*/
public class PersonDaoImpl implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    /*@Autowired*/
    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String INSERT_INTO_PERSON_SQL = "INSERT INTO person (name, birth_year) VALUES (?, ?)";
    public static final String UPDATE_PERSON_SQL = "UPDATE person SET name = ?, birth_year = ? WHERE id = ?";
    public static final String DELETE_PERSON_SQL = "DELETE FROM person WHERE id = ?";
    public static final String SELECT_PEOPLE_SQL = "SELECT * FROM person";
    public static final String SELECT_PERSON_BY_ID_SQL = "SELECT * FROM person WHERE person.id = ?";
    public static final String SELECT_PERSON_NAME_BY_NAME_SQL = "SELECT name FROM person WHERE person.name = ?";
    public static final String SELECT_BOOK_BY_PERSON_ID_SQL = "SELECT * FROM book WHERE book.person_id = ?";
    public static final String SELECT_PERSON_NAME_BY_BOOK_ID = """
            SELECT person.name FROM person JOIN book ON book.person_id = person.id WHERE book.id = 
            """;

    public void insertPerson(Person person) {
        jdbcTemplate.update(INSERT_INTO_PERSON_SQL, person.getName(), person.getBirthYear());
    }

    public void updatePerson(int id, Person updatedPerson) {
        jdbcTemplate.update(UPDATE_PERSON_SQL,
                updatedPerson.getName(), updatedPerson.getBirthYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update(DELETE_PERSON_SQL, id);
    }

    public List<Person> selectPeople() {
        return jdbcTemplate.query(SELECT_PEOPLE_SQL, new PersonRowMapper());
    }

    public Optional<Person> selectPerson(int id) {
        return jdbcTemplate.query(SELECT_PERSON_BY_ID_SQL, new PersonRowMapper(), id)
                .stream().findAny();
    }

    public Optional<String> selectPersonNameByPersonName(String name) {
        return getString(name, jdbcTemplate, SELECT_PERSON_NAME_BY_NAME_SQL);
    }

    public Optional<List<Book>> selectBookByPersonId(int id) {
        return Optional.of(jdbcTemplate.query(SELECT_BOOK_BY_PERSON_ID_SQL, new BookRowMapper(), id));
    }

    public Optional<String> selectPersonNameByBookId(String bookId) {
        Optional<String> result;
        try {
            result = Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_PERSON_NAME_BY_BOOK_ID + bookId, String.class));
        }
        catch (EmptyResultDataAccessException e) {
            result = Optional.empty();
        }
        return result;
    }

    /**Performance Test*/

    public void testMultipleUpdate() {
        List<Person> personList = create1000People();

        long before = System.currentTimeMillis();

        for (Person person : personList) {
            jdbcTemplate.update(INSERT_INTO_PERSON_SQL, person.getName(), person.getBirthYear());
        }

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    @Override
    public void testBatchUpdate() {
        final List<Person> personList = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(INSERT_INTO_PERSON_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, personList.get(i).getName());
                preparedStatement.setInt(2, personList.get(i).getBirthYear());
            }

            @Override
            public int getBatchSize() {
                return personList.size();
            }
        });

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            personList.add(new Person(i, "name " + i, i+2000));
        }
        return personList;
    }

    public void startProgramProcessPerson() {
        jdbcTemplate.update("DELETE FROM person");

        jdbcTemplate.update(INSERT_INTO_PERSON_SQL, "Nikolas, Cage", 1989);
        jdbcTemplate.update(INSERT_INTO_PERSON_SQL, "Garry, Potter", 1989);
        jdbcTemplate.update(INSERT_INTO_PERSON_SQL, "Char, Bos", 1989);
        jdbcTemplate.update(INSERT_INTO_PERSON_SQL, "Egin, Bauer", 1989);
    }
}
