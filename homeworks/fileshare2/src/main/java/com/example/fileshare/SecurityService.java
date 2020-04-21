package com.example.fileshare;

public interface SecurityService {
    String findLoggedInEmail();

    void autoLogin(String email, String password);
}