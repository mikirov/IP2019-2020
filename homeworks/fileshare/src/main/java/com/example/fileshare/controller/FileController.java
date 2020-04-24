package com.example.fileshare.controller;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.FileRepository;
import com.example.fileshare.service.FileService;
import com.example.fileshare.service.LinkService;
import com.example.fileshare.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {

    private final StorageService storageService;

    private final LinkService linkService;

    private final FileService fileService;

    public FileController(StorageService storageService, LinkService linkService, FileService fileService) {
        this.storageService = storageService;
        this.linkService = linkService;
        this.fileService = fileService;
    }

    @GetMapping("/download/{uniqueString}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable("uniqueString") String uniqueString){
        String fileName = linkService.getLinkByGeneratedName(uniqueString).getFileName();
        Resource resource = storageService.loadAsResource(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return "redirect:/";
    }



    @PostMapping("/create-folder")
    public String createFolder(@RequestParam("name") String name,
                               @RequestParam("parentId") Integer parentId,
                               Authentication authentication){
        User user = (User)authentication.getPrincipal();
        File parent = fileService.findFileByAuthorAndId(user, parentId);
        fileService.save(user, name, parent);
        return "redirect:/";

    }


    @PutMapping("update-folder")
    public String updateFolder(@RequestParam("newName") String newName,
                               @RequestParam("id") Integer folderId,
                               Authentication authentication)
    {
        User user = (User)authentication.getPrincipal();
        fileService.update(user, folderId, newName);
        return "redirect:/";
    }

    @DeleteMapping("/delete-folder")
    public String deleteFolder(@RequestParam("id") Integer folderId,
                               Authentication authentication)
    {
        User user = (User)authentication.getPrincipal();
        fileService.delete(user, folderId);
        return "redirect:/";
    }

}
