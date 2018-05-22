package otus.objects;

public class Attachment {
    private final String name;
    private final double doubleValue;
    private final int intValue;
    private final int[] intValues;
    private final boolean booleanValue;


    public Attachment(String name, double doubleValue, int intValue, int[] intValues, boolean booleanValue) {
        this.name = name;
        this.doubleValue = doubleValue;
        this.intValue = intValue;
        this.intValues = intValues;
        this.booleanValue = booleanValue;
    }

    public String getName() {
        return name;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public int[] getIntValues() {
        return intValues;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }
}
