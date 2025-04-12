package org.example.servlets.javaservletsnew.dbService;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory;
import org.apache.tomcat.dbcp.dbcp2.datasources.PerUserPoolDataSourceFactory;
import io.github.cdimascio.dotenv.Dotenv;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.sql.SQLException;
import java.util.Hashtable;

public class EnvAwareDataSourceFactory extends BasicDataSourceFactory {
    private static final Dotenv dotenv = Dotenv.load();

    @Override
    public Object getObjectInstance(Object obj, javax.naming.Name name, Context nameCtx, Hashtable<?, ?> environment) throws SQLException {
        Reference ref = (Reference) obj;
        replaceRefAddr(ref, "username", dotenv.get("DB_USER"));
        replaceRefAddr(ref, "password", dotenv.get("DB_PASSWORD"));
        replaceRefAddr(ref, "url", dotenv.get("DB_URL"));

        return super.getObjectInstance(obj, name, nameCtx, environment);
    }

    private void replaceRefAddr(Reference ref, String key, String value) {
        ref.remove(Integer.parseInt(key));
        ref.add(new StringRefAddr(key, value));
    }
}