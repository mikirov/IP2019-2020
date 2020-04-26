package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.UserRepository;
import com.example.fileshare.service.FileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final FileService fileService;

    private final UserRepository userRepository;

    public IndexController(FileService fileService, UserRepository userRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String listAll(Model model, @RequestParam(value = "parentId", required = false) Integer parentId){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<File> files = new ArrayList<>();
        if(parentId != null){
            File parent = fileService.findFileById(parentId);
            files.addAll(fileService.findFiles(user, parent));
        }
        else{
            files.addAll(fileService.findRootFiles(user));
        }
        model.addAttribute("files",files);
        return "index";
    }

}
