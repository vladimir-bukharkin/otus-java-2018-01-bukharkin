package otus.json.objects;

import java.util.Objects;

public class NotAggregationObj {
    private final String name;
    private final byte byteValue;
    private final int intValue;
    private final long longValue;
    private final char charValue;
    private final double floatValue;
    private final double doubleValue;
    private final boolean booleanValue;
    private final EnumObj enumValue;

    public NotAggregationObj(String name,
                             byte byteValue,
                             int intValue,
                             long longValue,
                             char charValue,
                             double floatValue,
                             double doubleValue,
                             boolean booleanValue,
                             otus.json.objects.EnumObj enumValue) {
        this.name = name;
        this.byteValue = byteValue;
        this.intValue = intValue;
        this.longValue = longValue;
        this.charValue = charValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.enumValue = enumValue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NotAggregationObj that = (NotAggregationObj) object;
        return byteValue == that.byteValue &&
                intValue == that.intValue &&
                longValue == that.longValue &&
                charValue == that.charValue &&
                Double.compare(that.floatValue, floatValue) == 0 &&
                Double.compare(that.doubleValue, doubleValue) == 0 &&
                booleanValue == that.booleanValue &&
                Objects.equals(name, that.name) &&
                enumValue == that.enumValue;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, byteValue, intValue, longValue, charValue, floatValue, doubleValue, booleanValue, enumValue);
    }
}
