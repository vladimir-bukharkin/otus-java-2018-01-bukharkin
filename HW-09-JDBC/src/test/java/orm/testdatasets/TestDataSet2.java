package orm.testdatasets;

import orm.ormframework.DataSet;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TestDataSet2 that = (TestDataSet2) object;
        return vInt == that.vInt &&
                Float.compare(that.vFloat, vFloat) == 0 &&
                Objects.equals(string, that.string) &&
                Objects.equals(vDouble, that.vDouble);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), string, vInt, vDouble, vFloat);
    }
}
