package com.nikita.al_fp.service;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import com.nikita.al_fp.repository.BookRepository;
import com.nikita.al_fp.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Service
@Transactional()
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public BookService(BookRepository bookRepository,
                       PersonRepository personRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> findPersonByBookId(int id) {

        /*Query query = entityManager.createQuery("select personId from Book where bookName=:id");*/
        Optional<Person> person = Optional.empty();
        Optional<Book> book = bookRepository.findById(id);
        if (book.get().getPersonId()!=null) {
            int personId = book.get().getPersonId();
            person = personRepository.findById(personId);
        }

        /*Query query = entityManager.createQuery("from Book where personId = " + id);
        personRepository.getReferenceById()*/

        return person;

    }

    public boolean bookIsExist(String name) {
        return !bookRepository.findBooksByName(name).get().isEmpty();
        /*try {
            Book stringOptional = bookRepository.findBooksByName(name).get().get(0);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }*/
    }

    public void deletePersonFromBook(int bookId) {
        Book book = findById(bookId).get();
        book.setPersonId(null);
        updateBook(bookId, book);
    }

    public void startProgramProcessBook() {
        bookRepository.deleteAll();
        int personIndex = personRepository.findAll().get(ZERO).getId();

        bookRepository.saveAll(List.of(
                new Book(personIndex, "1989", "Billy, Harington", 2014),
                new Book(personIndex, "Fitch", "Adolf, Hitler", 1998),
                new Book(personIndex, "Gaz", "Amand, Gas",1899),
                new Book(personIndex, "Lenin", "Bigi, Boo", 1956),
                new Book(personIndex, "Gosha", "Zak, Snider", 1999),
                new Book(personIndex, "Bitch", "Peter, Parker", 2020),
                new Book(null, "Bitch2", "Peter, Parker", 2022)
        ));
    }
}
