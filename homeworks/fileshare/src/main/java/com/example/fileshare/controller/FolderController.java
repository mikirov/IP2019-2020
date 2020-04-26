package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.UserRepository;
import com.example.fileshare.service.FileService;
import com.example.fileshare.service.LinkService;
import com.example.fileshare.service.StorageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FolderController {

    private final FileService fileService;

    private final UserRepository userRepository;

    public FolderController(StorageService storageService, LinkService linkService, FileService fileService, UserRepository userRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
    }



    @PostMapping("/folder/create")
    public String createFolder(@RequestParam("name") String name,
                               @RequestParam("parentId") Integer parentId){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        File parent = fileService.findFileById(parentId);
        if(parent.getAuthor().equals(user)){
            fileService.save(user, name, parent, true);
        }
        else{
            //TODO: set error message
        }
        return "redirect:/";

    }


    @PutMapping("folder/update")
    public String updateFolder(@RequestParam(value = "newName", required = false) String newName,
                               @RequestParam("id") Integer folderId,
                               @RequestParam(value = "newParentId", required = false) Integer newParentId)
    {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(newName != null){
            fileService.updateName(user, folderId, newName);

        }
        if(newParentId != null){
            File parent = fileService.findFileById(newParentId);
            if(parent.getAuthor().equals(user)){
                fileService.updateParent(user, folderId, parent);
            }
            else{
                //TODO: error message
            }
        }
        return "redirect:/";
    }

    @DeleteMapping("/folder/delete")
    public String deleteFolder(@RequestParam("id") Integer folderId)
    {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        fileService.delete(user, folderId);
        return "redirect:/";
    }

}
