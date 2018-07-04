package orm.ormframework;

import org.junit.Test;
import orm.ormframework.connection.ConnectionHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TableCreationTest {

    @Test
    public void testTableCreation() throws IOException, SQLException {
        try (Connection fs = ConnectionHelper.getConnection()) {
            Executor executor = new Executor(fs);
            executor.prepareTables();
        }
    }
}
