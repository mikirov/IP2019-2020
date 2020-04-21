package com.example.fileshare;


public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}