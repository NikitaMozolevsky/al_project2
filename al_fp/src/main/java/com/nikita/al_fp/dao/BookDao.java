package com.nikita.al_fp.dao;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void insertBook(Book book);
    void updateBook(int id, Book book);
    void deleteBook(int id);
    List<Book> selectBook();
    Optional<Book> selectBookById(int id);
    void startProgramProcessBook();
    Optional<Person> selectPersonByBookId(int id);
    Optional<String> selectBookNameByBookName(String name);
    void assignBookOwner(int userId, int bookId);
    void deletePersonFromBook(int bookId);
}
