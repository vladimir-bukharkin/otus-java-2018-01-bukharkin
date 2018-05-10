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

    public static void run(String className) throws ClassNotFoundException, InvocationTargetException {
        run(Class.forName(className));
    }

    public static void run(Class clazz) throws InvocationTargetException {
        if (isTestAnnotationPresent(clazz)) {
            Method before = null;
            Method after = null;
            List<Method> tests = new ArrayList<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    if (before != null) throw new RuntimeException("More then one @Before");
                    before = method;
                } else if (method.isAnnotationPresent(After.class)){
                    if (after != null) throw new RuntimeException("More then one @After");
                    after = method;
                } else if (method.isAnnotationPresent(Test.class)) {
                    tests.add(method);
                }
            }

            for (Method testMethod : tests) {
                System.out.println("Test: " + testMethod.getName() + " started");
                Object testObject = ReflectionHelper.instantiate(clazz);
                if (before != null) {
                    ReflectionHelper.callMethod(testObject, before);
                }
                handleTest(testObject, testMethod);

                if (after != null) {
                    ReflectionHelper.callMethod(testObject, after);
                }
                System.out.println("Test: " + testMethod.getName() + " finished");
                System.out.println("____________________________________________________________");
            }
        }
    }

    private static void handleTest(Object testObject, Method testMethod) {
        try {
            ReflectionHelper.callMethod(testObject, testMethod);
            System.out.println("    Test Successful");
        } catch (InvocationTargetException e) {
            if (e.getTargetException().getClass() == AssertionError.class) {
                System.out.println("    Test Fail!");
            } else {
                System.out.println("    Test Exception!");
                e.getTargetException().printStackTrace();
            }
        }
    }

    private static boolean isTestAnnotationPresent(Class clazz) {
        return Stream.of(clazz.getDeclaredMethods()).anyMatch(m -> m.isAnnotationPresent(Test.class));
    }
}
