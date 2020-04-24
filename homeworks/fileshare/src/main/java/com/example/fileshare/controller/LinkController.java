package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.Link;
import com.example.fileshare.model.User;
import com.example.fileshare.service.FileService;
import com.example.fileshare.service.LinkService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LinkController {


    private final LinkService linkService;

    private final FileService fileService;

    public LinkController(LinkService linkService, FileService fileService) {
        this.linkService = linkService;
        this.fileService = fileService;
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
        return "link";
    }

    @PostMapping("/link/create")
    public String getLink(Model model,
                          @RequestParam("id") Integer fileId){

        File file = this.fileService.findFileById(fileId);
        this.linkService.save(file);
        return "redirect:/";
    }

    @DeleteMapping("/link/delete")
    public String deleteLink(Model model,
                             @RequestParam("generatedName") String generatedName){
        this.linkService.delete(generatedName);
        return "redirect:/";
    }

}
