package org.example.servlets.javaservletsnew.models;

import org.example.servlets.javaservletsnew.dbService.DatabaseConnection;
import org.example.servlets.javaservletsnew.dbService.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserStore {

    public static boolean addUser(User user) {
        if (isEmailExists(user.getEmail())) {
            return false;
        }
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static User getUser(String username) {
        User user = null;
        try (Session session = HibernateConfig.getSession()) {
            user = session.byNaturalId(User.class).using("_username", username).load();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return user;
        }
        return user;
    }

    public static boolean isEmailExists(String email) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            User user = session.createQuery(
                            "FROM User WHERE _email = :email", User.class)
                    .setParameter("email", email.trim())
                    .uniqueResult();
            session.getTransaction().commit();
            return user != null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public static boolean validateUser(String username, String password) {
        try {
            User user = getUser(username);
            if (user == null) {
                return false;
            }

            return password != null && password.equals(user.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
