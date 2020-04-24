package com.example.fileshare.repository;


import com.example.fileshare.model.File;
import com.example.fileshare.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByFileName(String fileName);
    Link findByGeneratedName(String generatedName);
    Link findById(int id);

}
