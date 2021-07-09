package digitalsloths.socialtables.games.battleship.model.communication;

import digitalsloths.socialtables.types.Profile;
import retrofit2.Callback;

/**
 * file: TeamManagementCommunication.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief: Gestisce la comunicazione con il server per le funzionalità legate alla gestione del team;
 * use: Viene utilizzata per comunicare con il server;
 */
public interface TeamManagementCommunication extends  JoinTeamCommunication {
    /**
     * @name removeTeamMember
     * @desc rimuove il profilo indicato dal parametro
     * @param {Client.Types.Profile} profile - indica il profilo da rimuovere
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.TeamManagementCommunication
     */
    public void removeTeamMember(Profile profile, Callback<Void> callback);
}
