package otus.testframework;

import otus.testframework.annotation.After;
import otus.testframework.annotation.Before;
import otus.testframework.annotation.Test;
import otus.testframework.util.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestRunner {

    public static void run(String className) throws ClassNotFoundException {
        run(Class.forName(className));
    }

    public static void run(Class clazz) {
        if (isTestAnnotationPresent(clazz)) {
            Method before = null;
            Method after = null;
            List<Method> tests = new ArrayList<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    before = method;
                } else if (method.isAnnotationPresent(After.class)){
                    after = method;
                } else if (method.isAnnotationPresent(Test.class)) {
                    tests.add(method);
                }
            }

            for (Method testMethod : tests) {
                Object testObject = ReflectionHelper.instantiate(clazz);
                if (before != null) {
                    ReflectionHelper.callMethod(testObject, before);
                }
                handleTest(testObject, testMethod);

                if (after != null) {
                    ReflectionHelper.callMethod(testObject, after);
                }
            }
        }
    }

    private static void handleTest(Object testObject, Method testMethod) {
        try {
            ReflectionHelper.callMethod(testObject, testMethod);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() == AssertionError.class) {
                System.out.println("test fail");
            } else {
                e.getTargetException();
            }
        }

    }

    private static boolean isTestAnnotationPresent(Class clazz) {
        return Stream.of(clazz.getDeclaredMethods()).anyMatch(m -> m.isAnnotationPresent(Test.class));
    }
}
