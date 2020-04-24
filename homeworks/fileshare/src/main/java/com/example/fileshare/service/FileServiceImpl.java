package com.example.fileshare.service;

import com.example.fileshare.model.File;
import com.example.fileshare.model.User;
import com.example.fileshare.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void save(User user, String fileName, File parent) {
        File file = new File(fileName, user, parent);
        fileRepository.save(file);
    }

    @Override
    public void delete(User user, int id) {
        File file = fileRepository.findByAuthorAndId(user, id);
        fileRepository.delete(file);
    }

    @Override
    public void update(User user, int id, String newName) {
        File file = fileRepository.findByAuthorAndId(user, id);
        file.setName(newName);
        fileRepository.save(file);
    }

    @Override
    public List<File> findRootFiles(User user) {
        return fileRepository
                .findAllByAuthor(user)
                .stream()
                .filter(file -> file.parent == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<File> findFiles(User user, File parent) {
        return fileRepository.findAllByAuthorAndParent(user, parent);
    }

    @Override
    public File findFileByAuthorAndId(User user, Integer id) {
        return null;
    }

    @Override
    public File findFileById(int id) {
        return fileRepository.findById(id);
    }
}
