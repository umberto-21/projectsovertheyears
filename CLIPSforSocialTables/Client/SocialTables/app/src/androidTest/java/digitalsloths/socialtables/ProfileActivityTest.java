package digitalsloths.socialtables;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * ProfilePresenter test class
 * Created by Federico Casotto on 01/07/2016.
 */
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<MainMenuPresenterImpl> mActivityRule = new ActivityTestRule<>(MainMenuPresenterImpl.class);

    @Test
    public void shouldBeRenamedCorrectly(){

        final String username = "John";

        onView(withId(R.id.profileMenuButton)).perform(click());
        onView(withId(R.id.newUsernameText)).perform(click(), typeText(username));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.actualUsername)).check(matches(withText(username)));

    }

}
