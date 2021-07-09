package digitalsloths.socialtables.games.utility.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.utility.types.GameImpl;
import digitalsloths.socialtables.games.utility.view.ScoreAdapter;
import digitalsloths.socialtables.games.utility.model.LeaderboardModel;
import digitalsloths.socialtables.games.utility.model.LeaderboardModelImpl;
import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name LeaderboardPresenterImpl.java
 * author Casotto Federico
 * date 15/05/2016
 * brief Interfaccia che gestisce Model:Leaderboard e View::Leaderboard implementando la application//Logic e la presentation//Logic
 * per dare la possibilità all'utente di aprire la classifica del gioco desiderato.
 * use Utilizzata da UtilityManager per gestire la visualizzazione della classifica.
 */
public class LeaderboardPresenterImpl extends AppCompatActivity implements LeaderboardPresenter {

    private LeaderboardModel _leaderboardModel;

    /**
     * @name onCreate
     * @desc Costruttore dell'activity che visualizza la classifica di un gioco;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazioni utili all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_view);

        _leaderboardModel = new LeaderboardModelImpl();

        loadGamesListMenu();

    }

    /**
     * @name loadGamesListMenu
     * @desc Metodo che carica la lista dei giochi disponibili;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    @Override
    public void loadGamesListMenu(){

        //da aggiornare con comunicazione con server, anche se non chiaro utilizzo
        //di questa funzione
        TextView nameAlert = (TextView) findViewById(R.id.gameAlert);
        nameAlert.setText("Battleship");
        showLeaderBoard(new GameImpl(1,"Battleship"));

    }

    /**
     * @name showLeaderBoard
     * @desc Metodo che visualizza la classifica;
     * @param {Game} game - Gioco di cui si vuole visualizzare la classifica;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    @Override
    public void showLeaderBoard(Game game){

        if (game != null){
            List<Score> scores = _leaderboardModel.getScoresList(game);
            final ListView scoreListView = (ListView) findViewById(R.id.scoresListView);
            scoreListView.setAdapter(new ScoreAdapter(this));
            scoreListView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @name getContext
     * @desc Fornisce il contesto dell'Activity di visualizzazione classifica.
     * @returns {Context}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    public Context getContext() {
        return this;
    }

    /**
     * @name getScoresList
     * @desc Fornisce la lista di punteggi migliori disponibili.
     * @returns {List<Score>}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    @Override
    public List<Score> getScoresList() {
        return _leaderboardModel.getScoresList(new GameImpl(1,"Battleship"));
    }

}
