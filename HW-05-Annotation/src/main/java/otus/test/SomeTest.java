package otus.test;

import org.junit.Assert;
import otus.testframework.annotation.After;
import otus.testframework.annotation.Before;
import otus.testframework.annotation.Test;

public class SomeTest {
    private boolean isBeforeExecuted = false;

    @Before
    public void setUp() {
        isBeforeExecuted = true;
        System.out.println("@Before executed");
    }

    @Test
    public void testShouldBeOk() {
        Assert.assertTrue(isBeforeExecuted);
        Assert.assertTrue(true);
    }

    @Test
    public void testShouldBeFail() {
        Assert.fail();
    }

    @Test
    public void testShouldBeThrowException() {
        throw new RuntimeException("Test threw exception");
    }

    @After
    public void tearDown() {
        System.out.println("@After executed");
    }
}
