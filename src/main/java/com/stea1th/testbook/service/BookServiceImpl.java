package com.stea1th.testbook.service;

import com.stea1th.testbook.entity.Book;
import com.stea1th.testbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;



    @Override
    public Book addBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);

    }

    @Override
    public Page<Book> getByName(String title, Pageable pageable) {
        return bookRepository.findByTitle(title, pageable);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    /*@Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }*/

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
