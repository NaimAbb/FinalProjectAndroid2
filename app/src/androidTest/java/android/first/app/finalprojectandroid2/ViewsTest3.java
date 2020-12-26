package android.first.app.finalprojectandroid2;

import android.first.app.finalprojectandroid2.Actitvties.HomeActivity;
import android.first.app.finalprojectandroid2.Actitvties.MainActivity;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;
import android.first.app.finalprojectandroid2.Fragments.NewsSourcesFragment;

import androidx.test.rule.ActivityTestRule;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ViewsTest3 {
    @Rule
    public FragmentTestRule<HomeActivity, NewsSourcesFragment> fragmentTestRule =
            new FragmentTestRule<>(HomeActivity.class, NewsSourcesFragment.class);

    @Before
    public void setUp(){

    }

    @Test
    public  void test(){

        onView(withId(R.id.NextFragment)).perform(click());    }
}
