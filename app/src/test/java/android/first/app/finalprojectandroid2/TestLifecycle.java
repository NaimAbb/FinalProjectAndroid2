package android.first.app.finalprojectandroid2;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestLifecycle {

    News news;

    @BeforeClass
    public static void beforeClassMethod() {
        System.out.println("BeforeClass");
    }

    @Before
    public void beforeMethod() {
        System.out.println("Before");
        news = new News(false);

    }

    @Test
    public void testMethod() {
        System.out.println("Test");
        assertTrue(news.getIsFavorite());
    }

    @After
    public void AfterMethod() {
        System.out.println("After");

    }

    @AfterClass
    public static void AfterClassMethod() {
        System.out.println("AfterClass");

    }

}
