package otus.json.objects;

import java.util.Map;
import java.util.Objects;

public class MapObj {
    private final Map<EnumObj, NotAggregationObj> map;

    public MapObj(Map<EnumObj, NotAggregationObj> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MapObj mapObj = (MapObj) object;
        return Objects.equals(map, mapObj.map);
    }

    @Override
    public int hashCode() {

        return Objects.hash(map);
    }
}
