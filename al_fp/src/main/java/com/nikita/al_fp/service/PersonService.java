package com.nikita.al_fp.service;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import com.nikita.al_fp.repository.BookRepository;
import com.nikita.al_fp.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

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

        return bookRepository.findBooksByPersonId(id);
    }

    public boolean personIsExist(String name) {
        try {
            return personRepository.findByName(name).get(0) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

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
