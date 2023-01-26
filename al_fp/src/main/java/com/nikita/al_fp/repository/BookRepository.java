package com.nikita.al_fp.repository;

import com.nikita.al_fp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<List<Book>> findBooksByPersonId(int id);
    Optional<List<Book>> findBooksByName(String name);
    Optional<List<Book>> findBooksByNameIsStartingWith(String startWith);
    Book findByAuthor(String author);
    Book findTopByOrderByIdDesc();

    @Query(
            value = "SELECT * FROM USERS u WHERE u.status = 1",
            nativeQuery = true) //Nice example
    List<Book> findUsers(String name);

}
