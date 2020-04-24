package com.example.fileshare.service;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;

import java.util.List;

public interface FileService {

    void save(User user, String fileName, File parent);

    void delete(User user, int id);

    void update(User user, int id, String newName);

    List<File> findRootFiles(User user);

    List<File> findFiles(User user, File parent);

    File findFileByAuthorAndId(User user, Integer id);

    File findFileById(int id);
}
