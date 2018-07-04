package orm.ormframework;

import java.io.IOException;
import java.sql.*;

public class Executor {
    private static final String INSERT_INTO_USER = "insert into user (name, age) values(?, ?)";

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void prepareTables() throws SQLException, IOException {
        for (Class<? extends DataSet> aClass : DataSetScanner.findAll()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(SqlBuilder.createTable(aClass));
            }
        }
    }

    public  <T extends DataSet> void save(T dataSetObj) {
//        execUpdate(INSERT_INTO_USER, statement -> {
//            statement.setString(1, "sdg");
//            statement.setInt(2, 2);
//            statement.execute();
//        });
    }

    private <T extends DataSet> void load(long id, Class<T> clazz) {

    }

    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            handler.handle(result);
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try {
            PreparedStatement stmt = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            prepare.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
