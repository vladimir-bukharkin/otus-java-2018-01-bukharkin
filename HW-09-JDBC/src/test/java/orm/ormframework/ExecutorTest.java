package orm.ormframework;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import orm.UserDataSet;
import orm.testdatasets.TestDataSet1;
import orm.testdatasets.TestDataSet2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ExecutorTest {

    @Before
    public void setUp() throws Exception {
        try (Connection connection = ConnectionHelperT.getConnection()) {
            Executor executor = new Executor(connection);
            executor.dropDataSetTables();
        }
    }

    @Test
    public void testTableCreation() throws IOException, SQLException {
        try (Connection connection = ConnectionHelperT.getConnection()) {
            Executor executor = new Executor(connection);
            executor.prepareTables();

            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'user'", result -> {
                Assert.assertFalse(result.isClosed());
            });
            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'test1'", result -> {
                Assert.assertFalse(result.isClosed());
            });
            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'test2'", result -> {
                Assert.assertFalse(result.isClosed());
            });
        }
    }

    @Test
    public void testTableDrop() throws IOException, SQLException {
        try (Connection connection = ConnectionHelperT.getConnection()) {
            Executor executor = new Executor(connection);
            executor.prepareTables();

            executor.dropDataSetTables();
            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'user'", result -> {
                Assert.assertTrue(result.isClosed());
            });
            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'test1'", result -> {
                Assert.assertTrue(result.isClosed());
            });
            executor.execQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = 'test2'", result -> {
                Assert.assertTrue(result.isClosed());
            });
        }
    }

    @Test
    public void testSave() throws IOException, SQLException {
        try (Connection connection = ConnectionHelperT.getConnection()) {
            Executor executor = new Executor(connection);
            executor.prepareTables();

            TestDataSet1 obj1 = new TestDataSet1();
            UserDataSet obj2 = new UserDataSet("name1", 999);
            TestDataSet2 obj3 = new TestDataSet2("string 1", 1, 45d, -345.367f);
            TestDataSet2 obj4 = new TestDataSet2("string 2", -16, 435d, 0);
            UserDataSet obj5 = new UserDataSet("name2", 98);
            UserDataSet obj6 = new UserDataSet("name3", 0);

            executor.save(obj1);
            executor.save(obj2);
            executor.save(obj3);
            executor.save(obj4);
            executor.save(obj5);
            executor.save(obj6);

            executor.execQuery("SELECT * FROM test1 WHERE id = '1'", result -> {
                Assert.assertEquals(1, result.getInt("id"));
            });

            executor.execQuery("SELECT * FROM test2 WHERE string = 'string 1'", result -> {
                Assert.assertEquals(1, result.getInt("id"));
                Assert.assertEquals(1, result.getInt("vint"));
                Assert.assertEquals(45d, result.getDouble("vDouble"), 0.002);
                Assert.assertEquals(-345.367f, result.getFloat("vFloat"), 0.002);
            });

            executor.execQuery("SELECT * FROM test2 WHERE string = 'string 2'", result -> {
                Assert.assertEquals(2, result.getInt("id"));
                Assert.assertEquals(-16, result.getInt("vint"));
                Assert.assertEquals(435d, result.getDouble("vDouble"), 0.002);
                Assert.assertEquals(0, result.getFloat("vFloat"), 0.002);
            });

            executor.execQuery("SELECT * FROM user WHERE name = 'name1'", result -> {
                Assert.assertEquals(1, result.getInt("id"));
                Assert.assertEquals("name1", result.getString("name"));
                Assert.assertEquals(999, result.getInt("age"));
            });

            executor.execQuery("SELECT * FROM user WHERE name = 'name2'", result -> {
                Assert.assertEquals(2, result.getInt("id"));
                Assert.assertEquals("name2", result.getString("name"));
                Assert.assertEquals(98, result.getInt("age"));
            });

            executor.execQuery("SELECT * FROM user WHERE name = 'name3'", result -> {
                Assert.assertEquals(3, result.getInt("id"));
                Assert.assertEquals("name3", result.getString("name"));
                Assert.assertEquals(0, result.getInt("age"));
            });
        }
    }

    @Test
    public void testLoad() throws IOException, SQLException {
        try (Connection connection = ConnectionHelperT.getConnection()) {
            Executor executor = new Executor(connection);
            executor.prepareTables();

            TestDataSet1 obj1 = new TestDataSet1();
            UserDataSet obj2 = new UserDataSet("name1", 999);
            TestDataSet2 obj3 = new TestDataSet2("string 1", 1, 45d, -345.367f);
            TestDataSet2 obj4 = new TestDataSet2("string 2", -16, 435d, 0);
            UserDataSet obj5 = new UserDataSet("name2", 98);
            UserDataSet obj6 = new UserDataSet("name3", 0);

            executor.save(obj1);
            executor.save(obj2);
            executor.save(obj3);
            executor.save(obj4);
            executor.save(obj5);
            executor.save(obj6);

            obj1.setId(1);
            obj2.setId(1);
            obj3.setId(1);
            obj4.setId(2);
            obj5.setId(2);
            obj6.setId(3);

            TestDataSet1 actualObj1 = executor.load(1, obj1.getClass());
            UserDataSet actualObj2 = executor.load(1, obj2.getClass());
            TestDataSet2 actualObj3 = executor.load(1, obj3.getClass());
            TestDataSet2 actualObj4 = executor.load(2, obj4.getClass());
            UserDataSet actualObj5 = executor.load(2, obj5.getClass());
            UserDataSet actualObj6 = executor.load(3, obj6.getClass());

            Assert.assertEquals(obj1, actualObj1);
            Assert.assertEquals(obj2, actualObj2);
            Assert.assertEquals(obj3, actualObj3);
            Assert.assertEquals(obj4, actualObj4);
            Assert.assertEquals(obj5, actualObj5);
            Assert.assertEquals(obj6, actualObj6);
        }
    }
}
