package ru.gb.springbootlesson3.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.services.BookService;
import org.springframework.ui.Model;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        log.info("Поступил запрос на создание книги: bookName = {}"
                , bookRequest.getBookName());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(bookRequest));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/books")
    public String getAllBook(Model model){
        log.info("Поступил запрос на отображение списка книг");
        model.addAttribute("books", service.getAllBooks());
        return "books";
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getIdBook(@PathVariable Long id){
        log.info("Поступил запрос на получение описания книги " + id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getIdBook(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdBook(@PathVariable Long id){
        log.info("Поступил запрос на удаление книги " + id);
        try {
            service.deleteIdBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Книга удалена");
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }



}
