package otus.json.objects;

import java.util.List;
import java.util.Objects;

public class AggregationObj {
    private final NotAggregationObj nAObj;
    private final EnumObj enumObj;
    private final ArraysObj arraysObj;
    private final MapObj mapObj;
    private final List<AggregationObj> otherAggregationObj;
    private final transient String ignoredField;

    public AggregationObj(NotAggregationObj nAObj,
                          EnumObj enumObj,
                          ArraysObj arraysObj,
                          MapObj mapObj,
                          List<AggregationObj> otherAggregationObj,
                          String ignoredField) {
        this.nAObj = nAObj;
        this.enumObj = enumObj;
        this.arraysObj = arraysObj;
        this.mapObj = mapObj;
        this.otherAggregationObj = otherAggregationObj;
        this.ignoredField = ignoredField;
    }

    public String getIgnoredField() {
        return ignoredField;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AggregationObj that = (AggregationObj) object;
        return Objects.equals(nAObj, that.nAObj) &&
                enumObj == that.enumObj &&
                Objects.equals(arraysObj, that.arraysObj) &&
                Objects.equals(mapObj, that.mapObj) &&
                Objects.equals(otherAggregationObj, that.otherAggregationObj);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nAObj, enumObj, arraysObj, mapObj, otherAggregationObj);
    }
}
