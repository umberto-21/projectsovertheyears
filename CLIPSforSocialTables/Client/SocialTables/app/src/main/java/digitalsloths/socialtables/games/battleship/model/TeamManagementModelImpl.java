package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunication;
import digitalsloths.socialtables.games.battleship.model.communication.TeamManagementCommunication;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;
import retrofit2.Callback;

/**
 * name TeamManagementModelImpl.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic per la gestione del team;
 * use Utilizzata da TeamManagamentPresenter per la business//Logic per la gestione del team;
 */
public class TeamManagementModelImpl extends JoinTeamModelImpl implements TeamManagementModel {

    private static final String TAG = "TeamManagementModelImpl";
    private TeamManagementCommunication _teamManagementCommunication;

    public TeamManagementModelImpl(TeamManagementCommunication teamManagementCommunication) {

        super (teamManagementCommunication);
       //Log.v(TAG, ".TeamManagementModelImpl(...)");
        _teamManagementCommunication = teamManagementCommunication;
       //Log.v(TAG, ".TeamManagementModelImpl(...)");
    }

    /**
     * @name removeTeamMember
     * @desc Rimuove il profilo passato come paramentro; chiama il metodo removeTeamMember (profile : Profile) : void sull'oggetto di tipo TeamManagementCommunication;
     * @param {Profile} profile - Indica il profilo da rimuovere
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.TeamManagementModel
     */
    @Override
    public void removeTeamMember(Profile profile, Callback<Void> callback) {
       //Log.v(TAG, ".removeTeamMember(" + (new Gson()).toJson(profile) + ",...)");
        _teamManagementCommunication.removeTeamMember (profile,callback);
       //Log.v(TAG, ".removeTeamMember(" + (new Gson()).toJson(profile) + ",...)");
    }

}
