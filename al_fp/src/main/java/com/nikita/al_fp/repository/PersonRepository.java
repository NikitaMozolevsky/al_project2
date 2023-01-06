package com.nikita.al_fp.repository;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name);
}
