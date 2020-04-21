package com.example.fileshare;

public interface UserService {
    void save(User user);

    User findByEmail(String email);
}
