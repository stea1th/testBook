package com.stea1th.testbook.controller;

import com.stea1th.testbook.entity.Book;
import com.stea1th.testbook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    private PageWrapper<Book> page;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAllBooks(Model model, Pageable pageable){
        Page<Book> books = bookService.findAll(pageable);
        page = new PageWrapper<>(books, "/");
        model.addAttribute("allBooks", page.getContent());
        model.addAttribute("page", page);
        return "bookspage";
    }

    @GetMapping("new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @GetMapping("read/{id}")
    public String readBook(@PathVariable Long id){
        Book book = bookService.getById(id);
        book.setReadAlready(true);
        bookService.updateBook(book);
        return "redirect:/?page="+(page.getNumber()-1);
    }

    @PostMapping("addbook")
    public String saveBook(Book book){
        bookService.addBook(book);
        return "redirect:/?page="+(page.getNumber()-1);
    }

    @RequestMapping("delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/?page="+(page.getNumber()-1);
    }

    @RequestMapping("book/{id}")
    public String showBook(@PathVariable Long id, Model model){
        model.addAttribute("book" , bookService.getById(id));
        return "bookupdate";
    }

    @PostMapping("book/{id}")
    public String updateBook(@PathVariable Long id, @RequestParam String title, @RequestParam String description,
            @RequestParam String isbn, @RequestParam String printYear,
             Model model, Pageable pageable){
        Book book = bookService.getById(id);
        int count = 0;
        if(title.length()!=0 && title.length() < 101) {
            book.setTitle(title);
            count++;
        }
        if(description.length()!=0 && description.length() < 256) {
            book.setAuthor(description);
            count++;
        }
        if(isbn.length()!=0 && isbn.length() < 21) {
            book.setIsbn(isbn);
            count++;
        }
        if(printYear.length()!=0 && printYear.length() < 5) {
            book.setPrintYear(Integer.parseInt(printYear));
            count++;
        }
        if(count>0)
            book.setReadAlready(false);
        bookService.updateBook(book);
        model.addAttribute("allBooks", bookService.findAll(pageable));
        return "redirect:/?page="+(page.getNumber()-1);
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model, Pageable pageable){
        Page<Book> books = bookService.getByName(filter, pageable);
        page = new PageWrapper<>(books, "/");
        model.addAttribute("allBooksX", page.getContent());
        model.addAttribute("page", page);
        return "filterpage";
    }
}
