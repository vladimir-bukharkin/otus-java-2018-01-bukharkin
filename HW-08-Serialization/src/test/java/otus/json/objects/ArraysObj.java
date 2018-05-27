package otus.json.objects;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ArraysObj {
    private final String[] stringArr;
    private final NotAggregationObj[] objArr;
    private final float[] floatArr;

    private final Collection<?> collection;

    public ArraysObj(String[] stringArr, NotAggregationObj[] objArr, float[] floatArr, Collection<?> collection) {
        this.stringArr = stringArr;
        this.objArr = objArr;
        this.floatArr = floatArr;
        this.collection = collection;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ArraysObj arraysObj = (ArraysObj) object;
        return Arrays.equals(stringArr, arraysObj.stringArr) &&
                Arrays.equals(objArr, arraysObj.objArr) &&
                Arrays.equals(floatArr, arraysObj.floatArr) &&
                Objects.equals(collection, arraysObj.collection);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(collection);
        result = 31 * result + Arrays.hashCode(stringArr);
        result = 31 * result + Arrays.hashCode(objArr);
        result = 31 * result + Arrays.hashCode(floatArr);
        return result;
    }
}
