package otus;

import otus.test.SomeTest;
import otus.testframework.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException {

        TestRunner.run(SomeTest.class.getName());
    }
}
