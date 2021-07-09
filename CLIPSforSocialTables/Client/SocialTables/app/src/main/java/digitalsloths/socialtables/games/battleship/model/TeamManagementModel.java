package digitalsloths.socialtables.games.battleship.model;

import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.types.Profile;
import retrofit2.Callback;

/**
 * name TeamManagementModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic per la gestione del team;
 * use Utilizzata da TeamManagamentPresenter per la business//Logic per la gestione del team;
 */
public interface TeamManagementModel extends JoinTeamModel {

    /**
     * @name removeTeamMember
     * @desc Rimuove il profilo passato come paramentro; chiama il metodo removeTeamMember (profile : Profile) : void sull'oggetto di tipo TeamManagementCommunication;
     * @param {Profile} profile - Indica il profilo da rimuovere
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.TeamManagementModel
     */
    public void removeTeamMember (Profile profile, Callback<Void> callback);

}
