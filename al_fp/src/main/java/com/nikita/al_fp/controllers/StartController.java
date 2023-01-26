package com.nikita.al_fp.controllers;

import com.nikita.al_fp.service.BookService;
import com.nikita.al_fp.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping()
public class StartController {

    private final PersonService personService;
    private final BookService bookService;

    public StartController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String initProgramWithData(Model model) {
        personService.startProgramProcessPerson();
        bookService.startProgramProcessBook();
        model.addAttribute("people", personService.findAll());
        return "people/select_person";
    }
}
