package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.entity.Issue;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> list = new ArrayList<>();

    public BookRepository() {
        list.add(new Book("Война и мир"));
        list.add(new Book("Мастер и Маргарита"));
        list.add(new Book("Приключения Буратино"));
    }

    public Book findById(long id){
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Book findByName(String name){
        return list.stream().filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void createBook(Book book){
        list.add(book);
    }

    public List<Book> getAllBooks(){
        return list;
    }

    public void deleteBook(long id){
        list.remove(findById(id));
    }
}
