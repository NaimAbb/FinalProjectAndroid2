package android.first.app.finalprojectandroid2;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TestParametrizedNotation {

    private Boolean isFavorite;
    private Boolean expectedResult;
    private News news;

    @Before
    public void initialize() {
        news = new News();
    }

    // Each parameter should be placed as an argument here
    // Every time runner triggers, it will pass the arguments
    // from parameters we defined in primeNumbers() method

    public TestParametrizedNotation(Boolean isFavorite, Boolean expectedResult) {
        this.isFavorite = isFavorite;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {false, false},
                {false, false},
        });
    }

    // This test will run 4 times since we have 5 parameters defined
    @Test
    public void testIsFavorite() {
        assertEquals(expectedResult, isFavorite);
    }
}
