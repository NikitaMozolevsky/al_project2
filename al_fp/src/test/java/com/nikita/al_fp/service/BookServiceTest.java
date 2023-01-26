package com.nikita.al_fp.service;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import com.nikita.al_fp.repository.BookRepository;
import com.nikita.al_fp.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**What's part of the Spring Test Context: @Repository, EntityManager, TestEntityManager, DataSource*/

/*@Transactional*/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //для статических методов
/*@SpringBootTest*/
class BookServiceTest {

    private Book existBook;
    private Person existPerson;
    private boolean gettingPersonIdForBookIsNull = false;

    @Autowired
    private BookService bookService;

    @MockBean
    /*@Autowired*/ //смена для bookAdd() теста
    private BookRepository bookRepository;

    @MockBean
    private PersonRepository personRepository;

    @BeforeAll
    public void before() {
        /*existBook = bookRepository.findTopByOrderByIdDesc();
        existPerson = personRepository.findTopByOrderByIdDesc();*/
        Book person = new Book(1,1, "Gaz12", "Amand, Gas",1899);
        System.out.println("test");
    }

    @Test
    void bookIsExistTrue() {
        /*Assertions.assertTrue(bookRepository.findById(1).isPresent());*/
    }

    @Test
    void bookIsExistFalse() {
        /*Assertions.assertFalse(bookRepository.findById(0).isEmpty());*/
    }

    @Test
    void findPersonByBookIdTrue() {
        Book book = bookRepository.findTopByOrderByIdDesc();
        Optional<Person> person = personRepository.findById(book.getId());
        if (bookService.findPersonByBookId(book.getId()).isPresent()&&
        person.isPresent()) {
            assertThat(bookService.findPersonByBookId(book.getId()).get()==person.get()).isTrue();
        }

        //Mockito verify - проверка тестов
        //проверка один ли раз был вызов bookRep, и происходил ли он по методу save(),
        //с аргументом user
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);

        //было ли сохранение любого класса
        Mockito.verify(bookRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Book.class));

        //проверка был ли вызван метод findById() с определенной последовательностью аргументов
        Mockito.verify(bookRepository, Mockito.times(1))
                .findById(
                        ArgumentMatchers.eq(2)
                        //методы ArgumentMatchers:
                        //anyString() - была ли вызвана любая строка
                        //
                );
        //ошибка т.к. Mockito.verify() можно вызвать только для Mock объектов,
        //если bookRepository не Mock объект

        //проверка были вообще вызовы метода
        Mockito.verify(bookRepository, Mockito.times(0))
                .findById(
                        ArgumentMatchers.anyInt()
                );
    }

    @Test
    void addBookTest() {
        Book book = new Book();

        book.setId(2);

        boolean isBookCreated = bookService.addBook(book);

        assertThat(isBookCreated);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }


    /*@Rule //не работает
    public final ExpectedException exception = ExpectedException.none();*/

    //junit <=4.3
    /*@Test(expected = ArithmeticException.class)*/ //проверка на наличие исключения
    public void throwExceptionTrueTest() {
        int i = 0;

        int i1 = 1/i;
        //не работает
        /*assertThatThrownBy(ArithmeticException.class);*/
    }

    @Test //случай когда пользователь с таким id уже существует
    void addBookFailedTest() {
        Book book = new Book();

        //установка для пользователя автора
        book.setAuthor("Author");

        //не дает создать в сервисе пользователя если таковой уже есть,
        //видимо эмулируется его создание и при попытке создать
        //пользователя с таким именем автора возникает ошибка
        Mockito.doReturn(new Book()).when(bookRepository).findByAuthor("Author");

        //проверка записалась ли книга с таким автором
        boolean isBookCreated = bookService.addBook(book);

        assertThat(!isBookCreated);


    }

    @Test
     void findPersonByBookIdFalse() {
        Book book = bookRepository.findTopByOrderByIdDesc();
        Optional<Person> person = personRepository.findById(book.getId());
        if (bookService.findPersonByBookId(book.getId()).isPresent()&&
                person.isPresent()) {
            assertThat(bookService.findPersonByBookId(book.getId()).get()!=person.get()).isFalse();
        }
    }

    @AfterAll
    public void after() {

        System.out.println("after");
    }
}