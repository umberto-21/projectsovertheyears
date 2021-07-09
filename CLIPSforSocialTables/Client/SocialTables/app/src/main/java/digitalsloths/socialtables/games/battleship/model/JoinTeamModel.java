package digitalsloths.socialtables.games.battleship.model;

import java.util.Observable;
import java.util.concurrent.Future;

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
public interface JoinTeamModel {

    Future<Team> getTeamFuture();

    /**
     * @name leaveTeam
     * @desc Toglie l'utente dal team; chiama il metodo leaveTeam () : void sull'oggetto di tipo JoinTeamCommunication;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModel
     */
    public void leaveTeam (Callback<Void> callback);

    /**
     * @name addTeamObserver
     * @desc Aggiunge un ascoltatore;
     * @param {Runnable} runnable - Rappresenta l'oggetto che ha subito un cambiamento;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModelImpl
     */
    public void addTeamObserver (Runnable runnable);

    void removeTeamObserver();


    /**
     * @name getTeam
     * @desc Ritorna il team;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Model.JoinTeamModel
     */
    public void getTeam (Callback<TeamImpl> callback);

}
