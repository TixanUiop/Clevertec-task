package org.evgeny.Utill;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@UtilityClass
public class GetConnection {

    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.username";
    private static final String DB_PASSWORD = "db.password";

    static {
        loadDriver();
    }

    void loadDriver() {
        try {
            GetConnection.class.getClassLoader().loadClass("org.postgresql.Driver");
            log.info("Loaded PostgreSQL Driver");
        }
        catch (ClassNotFoundException e) {
            log.error("Unable to load PostgreSQL Driver", e);
            throw new ClassCastException("Unable to load PostgreSQL Driver");
        }

    }
    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(
                GetProperties.getProperties(DB_URL),
                GetProperties.getProperties(DB_USER),
                GetProperties.getProperties(DB_PASSWORD)
        );
    }

}
