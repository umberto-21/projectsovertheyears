package digitalsloths.socialtables.games.utility.model;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.types.Score;

/**
 * Created by Federico on 26/05/2016.
 */
public interface LeaderboardModel {

    public List<Game> getGamesList();

    public List<Score> getScoresList(Game game);

}
