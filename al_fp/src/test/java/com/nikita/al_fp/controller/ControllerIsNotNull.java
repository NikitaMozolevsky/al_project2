package com.nikita.al_fp.controller;

import com.nikita.al_fp.controllers.BookController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ControllerIsNotNull {

    @Autowired
    private BookController bookController;

    @Test
    void test() {
        assertThat(bookController).isNotNull();
    }
}
