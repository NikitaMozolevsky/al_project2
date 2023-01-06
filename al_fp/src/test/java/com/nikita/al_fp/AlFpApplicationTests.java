package com.nikita.al_fp;

import com.nikita.al_fp.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlFpApplicationTests {

	private final BookRepository bookRepository;

	@Autowired
	AlFpApplicationTests(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Test
	void contextLoads() {
	}

}
