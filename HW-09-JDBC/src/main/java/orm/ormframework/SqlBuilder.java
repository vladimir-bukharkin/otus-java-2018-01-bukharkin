package orm.ormframework;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

class SqlBuilder {

    static String createTable(Class<? extends DataSet> clazz) {
        String tableName = getTableNameFromClass(clazz);
        String fields = Arrays.stream(clazz.getDeclaredFields())
                .map(TableField::new)
                .map(TableField::toString)
                .collect(Collectors.joining(", "));

        return  "CREATE TABLE IF NOT EXISTS " + tableName +
                " (id integer primary key AUTOINCREMENT not NULL" +
                (fields.isEmpty() ? "" : ", " + fields) + ")";
    }

    static String dropTableIfExist(Class<? extends DataSet> clazz) {
        String tableName = getTableNameFromClass(clazz);
        return  "DROP TABLE IF EXISTS " + tableName;
    }

    static String loadObject(long id, Class<? extends DataSet> clazz) {
        String tableName = getTableNameFromClass(clazz);
        return  "SELECT * FROM " + tableName + " WHERE id = " + id;
    }

    static String getTableNameFromClass(Class<? extends DataSet> clazz) {
        return clazz.getSimpleName().replace("DataSet", "").toLowerCase();
    }

    private static class TableField {
        private final String name;
        private final FieldType fieldType;

        private TableField(Field field) {
            this.name = field.getName();
            Class clazz = field.getType();
            if (String.class == clazz) {
                fieldType = FieldType.STRING;
            } else if (int.class == clazz || long.class == clazz || Integer.class == clazz || Long.class == clazz) {
                fieldType = FieldType.INTEGER;
            } else if (double.class == clazz || float.class == clazz || Double.class == clazz || Float.class == clazz) {
                fieldType = FieldType.REAL;
            } else {
                throw new RuntimeException("Field type doesn't support");
            }
        }

        @Override
        public String toString() {
            return name + " " + fieldType.getValue();
        }

        private enum FieldType {
            STRING("text"),
            INTEGER("integer"),
            REAL("real");

            private final String value;

            FieldType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }
}
