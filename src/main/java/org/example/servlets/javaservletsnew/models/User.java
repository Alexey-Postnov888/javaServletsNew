package org.example.servlets.javaservletsnew.models;

public class User {
    private String _username;
    private String _password;
    private String _email;

    public User(String username, String password, String email) {
        _username = username;
        _password = password;
        _email = email;
    }

    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public String getEmail() { return _email; }
}
