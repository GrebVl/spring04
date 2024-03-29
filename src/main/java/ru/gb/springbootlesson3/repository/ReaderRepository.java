package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderRepository {
    private final List<Reader> list = new ArrayList<>();

    public ReaderRepository() {
        list.add(new Reader("Костя"));
        list.add(new Reader("Василий"));
        list.add(new Reader("Семен"));
    }

    public Reader findById(long id){
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Reader findByName(String name){
        return list.stream().filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void createReader(Reader reader){
        list.add(reader);
    }

    public List<Reader> getAllReaders(){
        return list;
    }

    public void deleteReader(long id){
        list.remove(findById(id));
    }
}
