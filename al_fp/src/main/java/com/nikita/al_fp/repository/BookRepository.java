package com.nikita.al_fp.repository;

import com.nikita.al_fp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<List<Book>> findBooksByPersonId(int id);
    Optional<List<Book>> findBooksByName(String name);
}
