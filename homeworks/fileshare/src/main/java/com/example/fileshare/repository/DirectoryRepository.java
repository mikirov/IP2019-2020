package com.example.fileshare.repository;

import com.example.fileshare.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectoryRepository extends JpaRepository<Directory,Long> {
}
