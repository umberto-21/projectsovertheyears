package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import digitalsloths.socialtables.games.battleship.model.communication.LeaderboardCommunication;
import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name LeaderboardModelImpl.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la bussines//Logic\g{} legata alla gestione della classifica;
 * use Utilizzata da LeaderboardPresenter per gestire la bussines//Logic\g{} legata alla gestione della classifica;
 */
public class LeaderboardModelImpl implements LeaderboardModel {
    private static final String TAG = "LeaderboardModelImpl";

    private LeaderboardCommunication _leaderboardCommunication;

    /**
     * @name LeaderboardModelImpl
     * @desc costruttore;
     * @param {LeaderboardCommunication} communication - parametro di tipo LeaderboardCommunication
     * @memberOf Client.Games.Battleship.Model.LeaderboardModelImpl
     */
    public LeaderboardModelImpl (LeaderboardCommunication communication) {
       //Log.v(TAG, ".LeaderboardModelImpl(" + (new Gson()).toJson(communication) + ")");
        _leaderboardCommunication =communication;
       //Log.v(TAG, ".LeaderboardModelImpl(" + (new Gson()).toJson(communication) + ")");
    }

    /**
     * @name getLeaderBoard
     * @desc Ritorna una lista di Score che rappresenta la classifica del gioco Battleship nel locale dove si trova l'utente; chiama il metodo getLeaderBoard () : List sull'oggetto di tipo LeaderboardCommunication
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.LeaderboardModel
     */
    @Override
    public List<Score> getLeaderBoard() {
       //Log.v(TAG, ".getLeaderBoard()");
        List<Score> leaderboardList = null; //_leaderboardCommunication.getLeaderBoard (); //-->no implemented method
       //Log.v(TAG, ".getLeaderBoard() return: " + (new Gson()).toJson(leaderboardList));
        return leaderboardList;
    }
}
