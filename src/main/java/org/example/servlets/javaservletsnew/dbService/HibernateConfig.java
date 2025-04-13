package org.example.servlets.javaservletsnew.dbService;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfig {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Dotenv dotenv = Dotenv.load();

            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", dotenv.get("DB_URL"))
                    .setProperty("hibernate.connection.username", dotenv.get("DB_USER"))
                    .setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"))
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addAnnotatedClass(org.example.servlets.javaservletsnew.models.User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Ошибка инициализации Hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}