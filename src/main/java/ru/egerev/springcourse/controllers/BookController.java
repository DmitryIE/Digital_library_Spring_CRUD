package ru.egerev.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egerev.springcourse.dao.BookDAO;
import ru.egerev.springcourse.models.Book;
import ru.egerev.springcourse.models.LibraryReader;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("book", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute ("libraryReader") LibraryReader libraryReader) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("libraryReaders", bookDAO.allReaders());
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        bookDAO.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";
        bookDAO.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/{id}/free")
    public String free(@PathVariable("id") int id) {
        bookDAO.freeBook(id);
        return "redirect:/book/{id}";
    }

    @PatchMapping("/{id}/take")
    public String take(@PathVariable("id") int id, @ModelAttribute("libraryReader")LibraryReader libraryReader) {
        bookDAO.takeBook(id, libraryReader.getName());
        return "redirect:/book/{id}";
    }



}
