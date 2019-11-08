package com.example.rest;

import lombok.Data;

@Data
public class Book {
    private String author;
    private String ISBN;
    private String year;
    private String title;
    private float price;
    private String coverPhoto;

}
