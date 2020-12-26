package android.first.app.finalprojectandroid2;

import android.first.app.finalprojectandroid2.Actitvties.HomeActivity;
import android.first.app.finalprojectandroid2.Actitvties.MainActivity;
import android.first.app.finalprojectandroid2.Actitvties.infoNews;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)

public class ViewsTest1 {


    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(
            HomeActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public  void test(){
        //onView(withId(R.id.Next)).perform(click());
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()));
    }
}
