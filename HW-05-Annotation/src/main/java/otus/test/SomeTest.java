package otus.test;

import org.junit.Assert;
import otus.testframework.annotation.After;
import otus.testframework.annotation.Before;
import otus.testframework.annotation.Test;

public class SomeTest {
    private boolean isBeforeExecuted = false;

    @Before
    public void setUp() throws Exception {
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
        Assert.assertFalse(true);
    }

    @Test
    public void testShouldBeException() {
        throw new RuntimeException("Exception");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("@After executed");
    }
}
