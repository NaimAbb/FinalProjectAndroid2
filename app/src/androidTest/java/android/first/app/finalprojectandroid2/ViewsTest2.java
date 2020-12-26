package android.first.app.finalprojectandroid2;

import android.first.app.finalprojectandroid2.Actitvties.MainActivity;

import androidx.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class ViewsTest2 {

        @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
            MainActivity.class);



    @Before
    public void setUp(){

    }

    @Test
    public  void test(){
        onView(withId(R.id.Next)).perform(click());
    }
}
