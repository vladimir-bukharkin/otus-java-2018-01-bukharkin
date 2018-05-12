package otus.testframework;

import otus.testframework.annotation.After;
import otus.testframework.annotation.Before;
import otus.testframework.annotation.Test;
import otus.testframework.util.ClassScanner;
import otus.testframework.util.ReflectionHelper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestRunner {

    public static void runByClassName(String className) throws ClassNotFoundException, InvocationTargetException {
        runByClass(Class.forName(className));
    }

    public static void runByPackageName(String packageName) throws InvocationTargetException, IOException {
        ClassScanner classScanner = new ClassScanner();
        for (Class<?> clazz : classScanner.getClassesInPackage(packageName)) {
            runByClass(clazz);
        }
    }

    public static void runByClass(Class<?> clazz) throws InvocationTargetException {
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
            System.out.println("\u001B[32m    Test successful!\u001B[0m");
        } catch (InvocationTargetException e) {
            if (e.getTargetException().getClass() == AssertionError.class) {
                System.out.println("\u001B[35m    Test fail!\u001B[0m");
            } else {
                System.out.println("\u001B[31m    Test throwed exception!: " + e);
                e.printStackTrace(System.out);
                System.out.print("\u001B[0m");
            }
        }
    }

    private static boolean isTestAnnotationPresent(Class clazz) {
        return Stream.of(clazz.getDeclaredMethods()).anyMatch(m -> m.isAnnotationPresent(Test.class));
    }
}
