package orm.testdatasets;

import orm.ormframework.DataSet;

public class TestDataSet2 extends DataSet {
    private final String string;
    private final int vInt;
    private final Double vDouble;
    private final float vFloat;

    public TestDataSet2(String string, int vInt, Double vDouble, float vFloat) {
        this.string = string;
        this.vInt = vInt;
        this.vDouble = vDouble;
        this.vFloat = vFloat;
    }

    public String getString() {
        return string;
    }

    public int getvInt() {
        return vInt;
    }

    public Double getvDouble() {
        return vDouble;
    }

    public float getvFloat() {
        return vFloat;
    }
}
