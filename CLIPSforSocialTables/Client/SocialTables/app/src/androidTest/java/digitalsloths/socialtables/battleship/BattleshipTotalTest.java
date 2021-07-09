package digitalsloths.socialtables.battleship;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.core.deps.guava.base.CharMatcher.is;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BattleshipTotalTest {

    @Rule
    public ActivityTestRule<MainMenuPresenterImpl> mActivityRule = new ActivityTestRule<>(
            MainMenuPresenterImpl.class);

    private void sleep(int millSec){
        try {
            Thread.sleep(millSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(){
        sleep(Integer.MAX_VALUE);
    }


    private void openGamesList(){
        onView(withId(R.id.gamesMenuButton)).perform(click());
    }

    private void openGame(String gameName){
        openGamesList();
        onView(allOf(withText(gameName))).perform(click());
    }

    private void startPlayBattleship(){
        openGame("Battleship");
        sleep(1000);
        onView(withId(R.id.startButton)).perform(click());
    }

    private void createTeam(String teamName) {
        startPlayBattleship();
        sleep(2000);
        onView(withId(R.id.inputTeamName)).perform(click(), typeText(teamName));
        closeSoftKeyboard();
        onView(withId(R.id.okButton)).perform(click());
        sleep(2000);
        onView(withId(R.id.teamName)).check(matches(withText(teamName)));

    }

    @Test
    public void createTeamTest(){

        createTeam("Team Test 1");
        sleep();
    }

    private void joinTeam() {

        startPlayBattleship();
        onView(withText("Team"));

    }

    @Test
    public void joinTeamTest(){
        joinTeam();
        sleep();
    }

    private void startBattle(){
        createTeam("Team Test 1");
        onView(withId(R.id.playButton)).perform(click());

    }

    @Test
    public void startBattleTest(){
        startBattle();
        sleep();
    }





}
