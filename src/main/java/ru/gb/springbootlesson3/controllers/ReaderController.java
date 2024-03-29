package ru.gb.springbootlesson3.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.entity.*;
import ru.gb.springbootlesson3.services.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequestMapping("reader")
@RequiredArgsConstructor
public class ReaderController {

    @Autowired
    private ReaderService service;

    @PostMapping
    public ResponseEntity<Reader> createBook(@RequestBody ReaderRequest readerRequest) {
        log.info("Поступил запрос на создание читателя: readerName = {}",
                 readerRequest.getReaderName());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createReader(readerRequest));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Reader> getAllReader() {
        log.info("Поступил запрос на отображение списка читателей");
        return service.getAllReaders();
    }

    @GetMapping("/readers")
    public String getReaders(Model model) {
        log.info("Поступил запрос на отображение списка читателей");
        model.addAttribute("readers", service.getAllReaders());
        return "readers";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getIdReader(@PathVariable Long id) {
        log.info("Поступил запрос на получение описания читателя " + id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getIdReader(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reader/{id}")
    public String getReader(Model model, @PathVariable Long id) {
        log.info("Поступил запрос на отображение списка читателей");
        model.addAttribute("name", service.getIdReader(id).getName());
        model.addAttribute("issues", service.getIssue(id));
        
        return "reader";
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getIdReaderIssue(@PathVariable Long id) {
        log.info("Поступил запрос на получение списка выдачи" + id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getIssue(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdReader(@PathVariable Long id) {
        log.info("Поступил запрос на удаление читателя " + id);
        try {
            service.deleteIdReader(id);
            return ResponseEntity.status(HttpStatus.OK).body("Читатель удален");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
