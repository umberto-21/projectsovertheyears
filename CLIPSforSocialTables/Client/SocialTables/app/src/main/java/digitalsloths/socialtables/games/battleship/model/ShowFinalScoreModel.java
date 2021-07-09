package digitalsloths.socialtables.games.battleship.model;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Score;
import retrofit2.Callback;

/**
 * Created by pablos on 7/4/16.
 */
public interface ShowFinalScoreModel {
    void getFinalScore(Callback<List<Score>> callback);
}
