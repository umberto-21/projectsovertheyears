package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import digitalsloths.socialtables.games.battleship.model.communication.WaitingOpponentCommunication;
import retrofit2.Callback;

/**
 * name WaitingOpponentModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic delle funzionalità di attesa avversario;
 * use Utilizzato da WaitingOpponentPresenter per gestire la business//Logic delle funzionalità di attesa avversario;
 */
public class WaitingOpponentModelImpl implements WaitingOpponentModel {

    private static final String TAG = "WaitingOpponentModelImp";
    private WaitingOpponentCommunication _waitingOpponentCommunication;

    /**
     * @name WaitingOpponentModelImpl
     * @desc costruttore;
     * @param {WaitingOpponentCommunication} communication - parametro di tipo WaitingOpponentCommunication;
     * @memberOf Client.Games.Battleship.Model.WaitingOpponentModelImpl
     */
    public WaitingOpponentModelImpl (WaitingOpponentCommunication communication) {
       //Log.v(TAG, ".WaitingOpponentModelImpl()");
        _waitingOpponentCommunication =communication;
       //Log.v(TAG, ".WaitingOpponentModelImpl()");
    }

    /**
     * @name startSearchingOpponent
     * @desc Comunica al server la disponibilità di iniziare la partita; chiama il metodo startSearchingOpponent ()
     * : void sull'oggetto di tipo WaitingOpponentCommunication;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.WaitingOpponentModel
     */
    @Override
    public void startSearchingOpponent(Callback<Void> callback) {
       //Log.v(TAG, ".startSearchingOpponent()");
        _waitingOpponentCommunication.startSearchingOpponent (callback);
       //Log.v(TAG, ".startSearchingOpponent() return: void");
    }

    /**
     * @name stopSearchingOpponent
     * @desc Comunica al server che l'utente esce dalla ricerca di un opponente;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.WaitingOpponentModel
     */
    @Override
    public void stopSearchingOpponent(Callback<Void> callback) {
       //Log.v(TAG, ".stopSearchingOpponent()");
        _waitingOpponentCommunication.stopSearchingOpponent(callback);
       //Log.v(TAG, ".stopSearchingOpponent() return: void");
    }
}
