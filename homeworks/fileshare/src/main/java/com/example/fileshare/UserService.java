package com.example.fileshare;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}