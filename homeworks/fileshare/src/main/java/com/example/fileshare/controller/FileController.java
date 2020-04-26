package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.UserRepository;
import com.example.fileshare.service.FileService;
import com.example.fileshare.service.LinkService;
import com.example.fileshare.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileController {

    private final StorageService storageService;

    private final LinkService linkService;

    private final UserRepository userRepository;

    private final FileService fileService;

    public FileController(StorageService storageService, LinkService linkService, UserRepository userRepository, FileService fileService) {
        this.storageService = storageService;
        this.linkService = linkService;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }


    @GetMapping("file/download/{uniqueString}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable("uniqueString") String uniqueString){
        String fileName = linkService.getLinkByGeneratedName(uniqueString).getFile().getName();
        Resource resource = storageService.loadAsResource(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/file/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/file/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("parentId") Integer parentId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        String StoredFileName = storageService.store(file);

        File parent = fileService.findFileById(parentId);
        if(parent.getAuthor().equals(user)){
            fileService.save(user, StoredFileName, parent, false);
        }

        return "redirect:/";
    }
}
