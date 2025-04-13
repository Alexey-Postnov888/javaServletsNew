package org.example.servlets.javaservletsnew.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "username", unique = true, nullable = false)
    private String _username;

    @Column(name = "password", nullable = false)
    private String _password;

    @Column(name = "email", unique = true)
    private String _email;

    public User(String username, String password, String email) {
        _username = username;
        _password = password;
        _email = email;
    }

    public User() {

    }

    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public String getEmail() { return _email; }
}
