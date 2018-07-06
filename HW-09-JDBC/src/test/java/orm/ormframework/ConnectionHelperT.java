package orm.ormframework;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelperT {

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new JDBC());
//            String url = "jdbc:sqlite:" + "C:\\Users\\ideaProjects\\otus\\otus-java-2017-11-bukharkin\\HW-09-JDBC\\src\\main\\resources\\db.db";
            String url = "jdbc:sqlite:" + "memory";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
