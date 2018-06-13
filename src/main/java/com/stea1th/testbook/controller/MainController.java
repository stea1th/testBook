package com.stea1th.testbook.controller;

import com.stea1th.testbook.entity.Book;
import com.stea1th.testbook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAllBooks(Model model){
        model.addAttribute("allBooks", bookService.getAll());
        return "bookspage";
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAllBooks(Model model, Pageable pageable){
        Page<Book> books = bookService.findAll(pageable);
        PageWrapper<Book> page = new PageWrapper<>(books, "/");
        model.addAttribute("allBooks", page.getContent());
        model.addAttribute("page", page);
        return "bookspage";
    }


    @GetMapping("new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("addbook")
    public String saveBook(@RequestParam String title, @RequestParam String author, Model model){
        bookService.addBook(new Book(title, author));
        return "redirect:/";
    }

    @RequestMapping("delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("book/{id}")
    public String showBook(@PathVariable Long id, Model model){
        model.addAttribute("book" , bookService.getById(id));
        return "bookupdate";
    }

    @PostMapping("book/{id}")
    public String updateBook(@PathVariable Long id, @RequestParam String title, @RequestParam String author
            , Model model, Pageable pageable){
        Book book = bookService.getById(id);
        if(title.length()!=0)
            book.setTitle(title);
        if(author.length()!=0)
            book.setAuthor(author);
        bookService.updateBook(book);
        model.addAttribute("allBooks", bookService.findAll(pageable));
        return "redirect:/";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model, Pageable pageable){
        Page<Book> books = bookService.getByName(filter, pageable);
        PageWrapper<Book> page = new PageWrapper<>(books, "/");
        model.addAttribute("allBooks", page.getContent());
        model.addAttribute("page", page);
        return "bookspage";

    }
}
