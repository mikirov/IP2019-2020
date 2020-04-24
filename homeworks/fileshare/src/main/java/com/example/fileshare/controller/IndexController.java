package com.example.fileshare.controller;

import com.example.fileshare.service.FileService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final FileService fileService;

    public IndexController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String listAll(Model model){

        return "index";
    }


}
