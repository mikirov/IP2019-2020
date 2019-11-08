package com.example.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private List<Book> books;

    public BookController() {
        this.books = new LinkedList<>();
    }

    @RequestMapping(path="/addBook", method = RequestMethod.PUT)
    public void addBook(Book book){
        this.books.add(book);
    }

    @RequestMapping(path = "/getBook", method = RequestMethod.GET)
    public List<Book> getBook(int page, String startYear, String endYear, String author, String title){
        //TODO: validate parameters
        return this.books.stream()
                .skip(page * 10)
                .filter(book -> Integer.parseInt(book.getYear()) > Integer.parseInt(startYear))
                .filter(book -> Integer.parseInt(book.getYear()) < Integer.parseInt(endYear))
                .filter(book -> book.getTitle().equals(title))
                .filter(book -> book.getAuthor().equals(author))
                .limit(10)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/updateBook", method = RequestMethod.POST)
    public void updateBook(Book book, int index){
        this.books.set(index, book);
    }

    @RequestMapping(path = "/deleteBook", method = RequestMethod.DELETE)
    public void deleteBook(int index){
        this.books.remove(index);
    }



}
