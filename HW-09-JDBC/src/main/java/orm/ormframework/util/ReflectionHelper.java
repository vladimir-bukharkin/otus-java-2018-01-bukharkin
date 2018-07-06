package orm.ormframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionHelper {

    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object[] args, Field... fields) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                return type.getDeclaredConstructor(toClasses(fields)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    public static void setParentFieldValue(Object object, String name, Object value) {
        Field field = null;
        try {
            field = object.getClass().getSuperclass().getDeclaredField(name); //getField() for public fields
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    public static Object callMethod(Object object, Method method) throws InvocationTargetException {
        try {
            method.setAccessible(true);
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    private static Class<?>[] toClasses(Field[] fields) {
        return Arrays.stream(fields).map(Field::getType).toArray(Class<?>[]::new);
    }
}
