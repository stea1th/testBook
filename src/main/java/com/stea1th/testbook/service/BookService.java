package com.stea1th.testbook.service;

import com.stea1th.testbook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    void delete(long id);
    Page<Book> getByName(String name, Pageable pageable);
    Book updateBook(Book book);
    /*List<Book> getAll();*/
    Book getById(Long id);

    Page<Book> findAll(Pageable pageable);

}
