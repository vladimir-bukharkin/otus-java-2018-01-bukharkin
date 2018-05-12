package otus;

import otus.testframework.TestRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IOException {

        TestRunner.runByPackageName(Main.class.getPackage().getName());
//        TestRunner.runByClassName(SomeTest.class.getName());
    }
}
