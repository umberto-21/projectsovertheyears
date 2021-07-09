package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Observable;
import java.util.concurrent.Future;

import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunication;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import retrofit2.Callback;

/**
 * name JoinTeamModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic legata all'inserimento nella squadra da parte dell'utente;
 * use Utilizzata da JointeamPresenter per la gestione della business//Logic legata all'inserimento nella squadra da parte dell'utente;
 */
public class JoinTeamModelImpl extends Observable implements JoinTeamModel {

    private static final String TAG = "JoinTeamModelImpl";
    private JoinTeamCommunication _joinTeamCommunication;

    public JoinTeamModelImpl(JoinTeamCommunication communication) {
       //Log.v(TAG, ".JoinTeamModelImpl(... )");
        _joinTeamCommunication = communication;
       //Log.v(TAG, ".JoinTeamModelImpl(...) cotruito");
    }

    @Override
    public Future<Team> getTeamFuture(){
        return _joinTeamCommunication.getTeamFuture();
    }
    /**
     * @name leaveTeam
     * @desc Toglie l'utente dal team; chiama il metodo leaveTeam () : void sull'oggetto di tipo JoinTeamCommunication;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModel
     */
    @Override
    public void leaveTeam(Callback<Void> callback) {
       //Log.v(TAG, ".leaveTeam()");
        _joinTeamCommunication.leaveTeam (callback);
       //Log.v(TAG, ".leaveTeam() return void");
    }

    /**
     * @name addTeamObserver
     * @desc Aggiunge un ascoltatore;
     * @param {Runnable} runnable - Rappresenta l'oggetto che ha subito un cambiamento;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModelImpl
     */
    public void addTeamObserver (Runnable runnable) {
       //Log.v(TAG, ".addTeamObserver()");
        _joinTeamCommunication.addTeamObserver(runnable);
       //Log.v(TAG, ".addTeamObserver() return: void ");
    }

    public void removeTeamObserver() {

       //Log.v(TAG, ".removeTeamObserver()");
        _joinTeamCommunication.removeTeamObserver();
       //Log.v(TAG, ".removeTeamObserver() return: void ");
    }

    /**
     * @name getTeam
     * @desc Ritorna il team;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModel
     */
    @Override
    public void getTeam(Callback<TeamImpl> callback) {
       //Log.v(TAG, ".getTeam(...)");
        _joinTeamCommunication.getTeam (callback);
       //Log.v(TAG, ".getTeam(...) return: void ");
    }
}
