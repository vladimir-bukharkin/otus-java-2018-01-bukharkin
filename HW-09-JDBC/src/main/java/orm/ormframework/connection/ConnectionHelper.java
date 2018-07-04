package orm.ormframework.connection;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionHelper {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new JDBC());
            String url = "jdbc:sqlite:" + "C:\\Users\\bukha\\Desktop\\ideaProjects\\otus\\otus-java-2017-11-bukharkin\\HW-09-JDBC\\src\\main\\resources\\db.db";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
