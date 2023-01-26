package com.nikita.al_fp.service;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import com.nikita.al_fp.repository.BookRepository;
import com.nikita.al_fp.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional()
@Service
public class PersonService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonService(BookRepository bookRepository,
                         PersonRepository personRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void updatePerson(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public Optional<List<Book>> findBooksByPersonId(int id) {
        Optional<List<Book>> optionalBooks = bookRepository.findBooksByPersonId(id);
        if (optionalBooks.isPresent()) {
            List<Book> bookList = optionalBooks.get();
            for(Book book : bookList) {
                if (book.getReceiptDate()==null) {
                    book.setReceiptDate(new Date());
                }
                book.setBookIsOverdue(book.bookIsOverdue());
            }
            return Optional.of(bookList);
        }
        return Optional.empty();
    }

    public boolean personIsExist(String name) {
        try {
            return personRepository.findByName(name).get(0) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @PostConstruct
    public void startProgramProcessPerson() {
        personRepository.deleteAll();
        personRepository.saveAll(List.of(
            new Person("Nikolas, Cage", 1989),
            new Person("Garry, Potter", 1989),
            new Person("Char, Bos", 1989),
            new Person("Egin, Bauer", 1989)
        ));
    }

}
