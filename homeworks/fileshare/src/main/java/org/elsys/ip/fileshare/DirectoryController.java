package org.elsys.ip.fileshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public class DirectoryController {
    @Autowired
    DirectoryRepository directoryRepository;

    @GetMapping("/")
    public String getDirectory(@RequestParam("name") String name) {
        return "Hello, the time at the server is now " + new Date() + "\n";
//        Directory rootDirectory = directoryRepository.findOne(name);
//        return rootDirectory.name;
    }

}
