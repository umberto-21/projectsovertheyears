package digitalsloths.socialtables.games.utility.model;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.types.GameImpl;
import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.ScoreImpl;

/**
 * Created by Federico on 26/05/2016.
 */
public class LeaderboardModelImpl implements LeaderboardModel {

    private List<Game> _gamesList;
    private List<Score> _scoresList;

    public LeaderboardModelImpl() {

        // da sistemare, non andrebbe creata una nuova lista di giochi
        _gamesList = new ArrayList<>();
        _gamesList.add(new GameImpl(1,"Battleship"));
        _gamesList.add(new GameImpl(2,"Tetris"));

    }

    @Override
    public List<Game> getGamesList() {
        return _gamesList;
    }

    @Override
    public List<Score> getScoresList(Game game) {
        _scoresList = new ArrayList<>();
        _scoresList.add(new ScoreImpl("Team1",2000));
        _scoresList.add(new ScoreImpl("team2",1500));
        _scoresList.add(new ScoreImpl("team3",1200));
        _scoresList.add(new ScoreImpl("team4",1000));
        _scoresList.add(new ScoreImpl("team5",800));
        return _scoresList;
    }
}
