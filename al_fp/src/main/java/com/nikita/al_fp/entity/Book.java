package com.nikita.al_fp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {

    public Book(Integer personId, String name, String author, int year) {
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="person_id")
    private Integer personId;

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 30, message = "name should be between 2 and 30 characters")
    private String name;

    @Column(name = "author")
    @Pattern(regexp = "[A-Z]\\w{2,50}, [A-Z]\\w{2,50}", message = "The author's name should be in this format:" +
            "Name, Lastname")
    private String author;

    @Column(name = "year")
    @Min(value = 998, message = "year of release should be greater than 999")
    @Max(value = 2899, message = "year of release should be less than 2900")
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (personId != book.personId) return false;
        if (year != book.year) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        return author != null ? author.equals(book.author) : book.author == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + personId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", year=").append(year);
        sb.append('}');
        return sb.toString();
    }
}
