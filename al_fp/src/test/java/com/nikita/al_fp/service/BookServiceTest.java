package com.nikita.al_fp.service;

import com.nikita.al_fp.repository.BookRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class BookServiceTest {

    private final BookRepository bookRepository;

    @Autowired
    BookServiceTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @BeforeAll
    public static void before() {
        System.out.println("test");
    }

    @Test
    void bookIsExist() {

    }
}