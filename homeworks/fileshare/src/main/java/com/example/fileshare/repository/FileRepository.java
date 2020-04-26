package com.example.fileshare.repository;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAllByAuthor(User user);

    List<File> findAllByAuthorAndParent(User user, File parent);

    File findById(int id);
}
