package com.stea1th.testbook.repository;

import com.stea1th.testbook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long > {

    @Query("select b from Book b where b.title like %:title% ")
    Page<Book> findByTitle(@Param("title")String title, Pageable pageable);

    //@Query
    //Page<Book> findAll(Pageable pageable);

}
