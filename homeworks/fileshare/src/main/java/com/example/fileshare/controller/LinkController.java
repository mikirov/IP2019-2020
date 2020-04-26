package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.Link;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.UserRepository;
import com.example.fileshare.service.FileService;
import com.example.fileshare.service.LinkService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LinkController {


    private final LinkService linkService;

    private final FileService fileService;

    private final UserRepository userRepository;

    public LinkController(LinkService linkService, FileService fileService, UserRepository userRepository) {
        this.linkService = linkService;
        this.fileService = fileService;

        this.userRepository = userRepository;
    }

    @GetMapping("/{generatedName}")
    public String getFile(Model model,
                          @PathVariable String generatedName)
    {
        Link link = this.linkService.getLinkByGeneratedName(generatedName);
        if(link == null){
            model.addAttribute("error", "Invalid link");
            return "link";
        }

        File file = this.fileService.findFileById(link.getFile().getId());
        List<File> files = new ArrayList<>();
        files.add(file);
        if(file.isFolder()){
            User owner = file.getAuthor();
            files.addAll(this.fileService.findFiles(owner, file));
        }
        model.addAttribute("files", files);
        return "index";
    }

    @PostMapping("/link/create")
    public String getLink(Model model,
                          @RequestParam("id") Integer fileId){

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        File file = this.fileService.findFileById(fileId);
        if(!file.getAuthor().equals(user)){
            model.addAttribute("error", "Can't create link to file you don't own");
            return "link";
        }
        String generatedName = this.linkService.save(file);
        model.addAttribute("link", generatedName);
        return "link";
    }

    @DeleteMapping("/link/delete")
    public String deleteLink(Model model,
                             @RequestParam("generatedName") String generatedName){

        this.linkService.delete(generatedName);
        return "redirect:/";
    }

}
