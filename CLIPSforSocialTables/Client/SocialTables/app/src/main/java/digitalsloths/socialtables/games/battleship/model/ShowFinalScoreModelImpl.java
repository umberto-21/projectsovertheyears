package digitalsloths.socialtables.games.battleship.model;

import java.util.List;

import digitalsloths.socialtables.games.battleship.model.communication.ShowFinalScoreCommunication;
import digitalsloths.socialtables.games.utility.types.Score;
import retrofit2.Callback;

/**
 * Created by pablos on 7/4/16.
 */
public class ShowFinalScoreModelImpl implements ShowFinalScoreModel {
    private ShowFinalScoreCommunication showFinalScoreCommunication;

    public ShowFinalScoreModelImpl(ShowFinalScoreCommunication showFinalScoreCommunication) {
        this.showFinalScoreCommunication = showFinalScoreCommunication;
    }

    @Override
    public void getFinalScore(Callback<List<Score>> callback) {
        showFinalScoreCommunication.getEnemyTeamScore(callback);
    }
}
