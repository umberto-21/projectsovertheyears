package digitalsloths.socialtables.battleship;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import digitalsloths.socialtables.games.battleship.presenter.FinalScorePresenterImpl;

/**
 * Created by Federico on 04/07/2016.
 */
public class FinalScoreTest {

    @Rule
    public ActivityTestRule<FinalScorePresenterImpl> mActivityRule = new ActivityTestRule<>(FinalScorePresenterImpl.class);

    @Test
    public void createTeamTest(){
        sleep(25000);
    }

    private void sleep(int millSec){
        try {
            Thread.sleep(millSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
