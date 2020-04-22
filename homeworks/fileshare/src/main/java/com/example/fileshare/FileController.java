package com.example.fileshare;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService, DirectoryRepository directoryRepository) {
        this.storageService = storageService;
        this.directoryRepository = directoryRepository;
    }

    private final DirectoryRepository directoryRepository;

//    @GetMapping("/")
//    public String listAllFiles(Model model) {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
//                        .path("/download/")
//                        .path(path.getFileName().toString())
//                        .toUriString())
//                .collect(Collectors.toList()));
//
//        return "welcome";
//    }

    @GetMapping("/")
    public String listAllDirectories(Model model){
        System.out.println("String listAllDirectories(Model model)");
        model.addAttribute("files", storageService.loadAll());
//              .map(
//                  path -> ServletUriComponentsBuilder.fromCurrentContextPath()
////            .path("/download/")
//              .path(path.getFileName().toString())
//              .toUriString())
//              .collect(Collectors.toList()));
        System.out.println("files:");
        System.out.println(model.getAttribute("files"));
        return "welcome";
    }

    @PostMapping("/create-folder")
    public void CreateFolder(@RequestParam("path") String path, @RequestParam("name") String name){
        //TODO: create folder
        System.out.println("void CreateFolder(@RequestParam(\"path\") String path, @RequestParam(\"name\") String name)");
        storageService.create(path, name);

    }

    @PutMapping("update-folder")
    public String UpdateFolder(@RequestParam("path") String path, @RequestParam("name") String name){
//        TODO: rename
//        storageService.load(path);
//        storageService.update(path, );
        return "redirect:/";
    }

    @DeleteMapping("/delete-folder")
    public String DeleteFolder(@RequestParam("path") String path){
        //TODO: delete folder
        storageService.delete(path);
        return "redirect:/";
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

}