package digitalsloths.socialtables.games.battleship.model.communication;

import java.util.Observer;
import java.util.concurrent.Future;

import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import retrofit2.Callback;

/**
 * file: JoinTeamCommunication.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief:Interfaccia che gestisce la comunicazione con il per le funzionalità di inserimento nella squadra;
 * use:Utilizzata da JoinTeamPresenter per gestire la comunicazione con il per le funzionalità di inserimento nella squadra;
 */
public interface JoinTeamCommunication {

    /**
     * @name getTeam
     * @desc Ritorna il team di cui l'utente fa parte;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    void getTeam(Callback<TeamImpl> callback);

    Future<Team> getTeamFuture();

    /**
     * @name leaveTeam
     * @desc Togli l'utente dal team;
     * @returns {void }
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    void leaveTeam(Callback<Void> callback);

    void addTeamObserver(Runnable runnable); // TODO: 6/12/16

    void removeTeamObserver(); // TODO: 6/12/16
}
