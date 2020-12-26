package android.first.app.finalprojectandroid2;

import android.first.app.finalprojectandroid2.Actitvties.HomeActivity;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;
import android.first.app.finalprojectandroid2.Fragments.LatestNewsFragment;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecyclerViewTest {

    //public ActivityScenarioRule<HomeActivity> activityScenarioRule = new ActivityScenarioRule<HomeActivity>(HomeActivity.class);

    @Rule
    public FragmentTestRule<HomeActivity, GeneralChatFragment> fragmentTestRule =
            new FragmentTestRule<>(HomeActivity.class, GeneralChatFragment.class);


    @Test
    public void testTitleGetsReversed() {
        onView(withId(R.id.favorites))
                .check(matches(isDisplayed()));
        onView(withId(R.id.favorites)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.shinestar)));
//        String textToCompare = "bears";
//
//        onView(withText(textToCompare)).check(matches(isDisplayed()));
//
//
//        onView(recyclerView)
//                .perform(RecyclerViewActions.actionOnItemAtPosition(R.id.shinestar, click()));

    }

}

class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}