package otus.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class JSONParser {

    @SuppressWarnings("unchecked")
    public JSONObject parse(Object object) throws IllegalAccessException {
        Objects.requireNonNull(object);
        return parseObject(object);
    }

    @SuppressWarnings("unchecked")
    private Object getJSONSuitableObject(Object object) throws IllegalAccessException {
        if (object == null) {
            return null;
        } else {
            Class clazz = object.getClass();
            if (isNotAggregationClass(clazz)) {
                return object;
            } else if (clazz.isArray()) {
                return parseArray(object);
            } else if (Collection.class.isAssignableFrom(clazz)) {
                return parseArray(((Collection) object).toArray());
            } else if (Map.class.isAssignableFrom(clazz)) {
                return parseMap(object);
            } else {
                return parseObject(object);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseObject(Object object) throws IllegalAccessException {
        JSONObject result = new JSONObject();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                Object obj = field.get(object);
                result.put(field.getName(), getJSONSuitableObject(obj));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private JSONArray parseArray(Object object) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object e = Array.get(object, i);
            jsonArray.add(getJSONSuitableObject(e));
        }
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseMap(Object object) throws IllegalAccessException {
        JSONObject result = new JSONObject();
        for (Object mapEntry : ((Map) object).entrySet()) {
            Map.Entry<Object, Object> mapE = (Map.Entry<Object, Object>) mapEntry;
            if (mapE.getKey() == null || isNotAggregationClass(mapE.getKey().getClass())) {
                result.put(getJSONSuitableObject(mapE.getKey()), getJSONSuitableObject(mapE.getValue()));
            } else {
                throw new RuntimeException("Error: can't parse Map key");
            }
        }
        return result;
    }

    private boolean isNotAggregationClass(Class clazz) {
        return String.class == clazz
                || Character.class == clazz
                || Number.class.isAssignableFrom(clazz)
                || clazz.isPrimitive()
                || Boolean.class == clazz
                || clazz.isEnum();
    }
}
