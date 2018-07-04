package orm;

import orm.ormframework.Executor;
import orm.ormframework.connection.ConnectionHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        try (Connection fs = ConnectionHelper.getConnection()) {
            Executor executor = new Executor(fs);
            executor.prepareTables();
        }
    }
}
