package orm.ormframework;

import orm.ormframework.util.ReflectionHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static orm.ormframework.SqlBuilder.getTableNameFromClass;

public class Executor {

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

    public void dropDataSetTables() throws SQLException, IOException {
        for (Class<? extends DataSet> aClass : DataSetScanner.findAll()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(SqlBuilder.dropTableIfExist(aClass));
            }
        }
    }

    public  <T extends DataSet> void save(T dataSetObj) {
        insertObject(dataSetObj);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        List<Object> args = new ArrayList<>();
        Field[] attributes = clazz.getDeclaredFields();

        execQuery(SqlBuilder.loadObject(id, clazz), result -> {
            result.next();
            for (Field attribute : attributes) {
                args.add(getArgFromResultSet(result, attribute));
            }
        });
        T result = ReflectionHelper.instantiate(clazz, args.toArray(), attributes);
        ReflectionHelper.setParentFieldValue(result, "id", id);
        return result;
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
            throw new RuntimeException(e);
        }
    }

    private <T extends DataSet> void insertObject(T dataSetObj) {
        String tableName = getTableNameFromClass(dataSetObj.getClass());
        List<String> attributes = new ArrayList<>();
        List<Consumer<PreparedStatement>> execs = new ArrayList<>();

        Arrays.stream(dataSetObj.getClass().getDeclaredFields()).forEach(field -> {
            attributes.add(field.getName());
            execs.add(preparedStatement -> {
                int paramIndex = attributes.indexOf(field.getName()) + 1;
                execSetPrepareStmt(ReflectionHelper.getFieldValue(dataSetObj, field.getName()), paramIndex, preparedStatement);
            });
        });
        String updateString;
        if (!attributes.isEmpty()) {
            updateString = "INSERT INTO " + tableName + " (" +
                    attributes.stream().collect(Collectors.joining(", ")) +
                    ") VALUES (" +
                    attributes.stream().map(a -> " ?").collect(Collectors.joining(", ")) +
                    ")";
        } else {
            updateString = "INSERT INTO " + tableName + " DEFAULT VALUES";
        }

        execUpdate(updateString, statement -> {
            execs.forEach(e -> {
                e.accept(statement);});
            statement.execute();
        });
    }

    private static void execSetPrepareStmt(Object value, int paramIndex, PreparedStatement preparedStatement) {
        if (value != null) {
            try {
                Class clazz = value.getClass();
                if (String.class == clazz) {
                    preparedStatement.setString(paramIndex, (String) value);
                } else if (int.class == clazz || Integer.class == clazz) {
                    preparedStatement.setInt(paramIndex, (int) value);
                } else if (long.class == clazz || Long.class == clazz) {
                    preparedStatement.setLong(paramIndex, (long) value);
                } else if (double.class == clazz || Double.class == clazz) {
                    preparedStatement.setDouble(paramIndex, (double) value);
                } else if (float.class == clazz || Float.class == clazz) {
                    preparedStatement.setFloat(paramIndex, (float) value);
                } else {
                    throw new RuntimeException("Field type doesn't support");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Object getArgFromResultSet(ResultSet result, Field field) {
        Class clazz = field.getType();
        String fieldName = field.getName();
        try {
            if (String.class == clazz) {
                return result.getString(fieldName);
            } else if (int.class == clazz || Integer.class == clazz) {
                return result.getInt(fieldName);
            } else if (long.class == clazz || Long.class == clazz) {
                return result.getLong(fieldName);
            } else if (double.class == clazz || Double.class == clazz) {
                return result.getDouble(fieldName);
            } else if (float.class == clazz || Float.class == clazz) {
                return result.getFloat(fieldName);
            } else {
                throw new RuntimeException("Field type doesn't support");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
