package com.nikita.al_fp.dao;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    List<Person> selectPeople();
    Optional<Person> selectPerson(int id);
    void insertPerson(Person person);
    void updatePerson(int id, Person updatedPerson);
    void deletePerson(int id);
    void testMultipleUpdate();
    void testBatchUpdate();
    Optional<List<Book>> selectBookByPersonId(int id);
    void startProgramProcessPerson();
    Optional<String> selectPersonNameByPersonName(String name);
    Optional<String> selectPersonNameByBookId(String bookId);
}
