package com.nikita.al_fp.controllers;

import com.nikita.al_fp.entity.Book;
import com.nikita.al_fp.entity.Person;
import com.nikita.al_fp.service.BookService;
import com.nikita.al_fp.service.PersonService;
import com.nikita.al_fp.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookValidator bookValidator;
    private final PersonService personService;
    private final BookService bookService;
    public static final String BOOK_REDIRECT_PAGE = "redirect:/book";

    @Autowired
    public BookController(BookValidator bookValidator, PersonService personService, BookService bookService) {
        this.bookValidator = bookValidator;
        this.personService = personService;
        this.bookService = bookService;
        personService.startProgramProcessPerson();
        bookService.startProgramProcessBook();
    }

    @GetMapping
    public String selectBookHomePage(Model model) {
        /*model.addAttribute("books", bookService.findAll());
        return "/book/select_book";*/
        return findPaginated(1, false, String.valueOf(false), model);
    }

    @GetMapping("/page/{pageNo}") // + "/{pageSize}" - optional
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam (value = "sortByYear", required = false) boolean sortByYear,
                                @RequestParam (value = "startWith", defaultValue = "false") String startWith,
                                Model model) {
        int pageSize = 3;
        model.addAttribute("currentPage", pageNo);
        Page<Book> bookPage;
        List<Book> bookList;
        //из Page объекта можно получить List
        if (!startWith.equals("false")) {
            bookList = bookService.findAllStartWith(startWith).get();
            model.addAttribute("bookList", bookList);
        } else {
            bookPage = bookService.findAllPagination(pageNo, pageSize, sortByYear);
            bookList = bookPage.getContent();
            model.addAttribute("totalPages", bookPage.getTotalPages());
            model.addAttribute("totalItems", bookPage.getTotalElements());

        }

        model.addAttribute("bookList", bookList);
        model.addAttribute("bookListIsEmpty", bookList.isEmpty());

        return "/book/select_book";
    }

    @GetMapping("/new")
    public String insertIntoBookPage(@ModelAttribute("book") Book book) {
        return "/book/insert_into_book_page";
    }

    @GetMapping("/{id}")
    public String selectBookById(@PathVariable("id") int id,
                                 @ModelAttribute("person") Person person,
                                 @ModelAttribute("new_book") Book book,
                                 Model model) {
        model.addAttribute("book", bookService.findById(id).get());
        model.addAttribute("people", personService.findAll());
        if (bookService.findPersonByBookId(id).isPresent()) {
            model.addAttribute("owner", bookService.findPersonByBookId(id).get());
            model.addAttribute("owner_name", bookService.findPersonByBookId(id).get().getName());
        }
        return "/book/select_book_by_id";
    }

    @GetMapping("/{id}/edit")
    public String updateBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id).get());
        return "/book/update_book_page";
    }

    @PostMapping()
    public String insertIntoBookAct(@Valid @ModelAttribute("book") Book book, // TODO: 11/15/2022
                                    BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/book/insert_into_book_page";
        }

        bookService.addBook(book);
        return BOOK_REDIRECT_PAGE;
    }

    @PatchMapping("/{id}")
    public String updatePersonAct(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                                  @PathVariable int id) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(this.getClass() + "Error");
            return "/book/update_book_page";
        }
        bookService.updateBook(id, book);
        return BOOK_REDIRECT_PAGE;
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return BOOK_REDIRECT_PAGE;
    }
    @DeleteMapping("{id}/person")
    public String deletePersonFromBook(@PathVariable int id) {
        bookService.deletePersonFromBook(id);
        return BOOK_REDIRECT_PAGE;
    }

    @PatchMapping("set/owner")
    public String setBookOwner(@ModelAttribute("new_book") Book book) {
        Book newBook = bookService.findById(book.getId()).get();
        newBook.setPersonId(book.getPersonId());
        newBook.setReceiptDate(new Date());
        bookService.updateBook(book.getId(), newBook);
        return BOOK_REDIRECT_PAGE;
    }
}
