package org.elsys.ip.fileshare;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
//        Directory rootDirectory = directoryRepository.getOne(1);
//        return rootDirectory.name;
    }
}
