package orm;

import orm.ormframework.DataSet;

public class UserDataSet extends DataSet {

    private final String name;
    private final int age;

    protected UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
