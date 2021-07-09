package digitalsloths.socialtables.games.utility.types;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

import digitalsloths.socialtables.types.Profile;

/**
 *file TeamImpl.java
 *author Saccon Daniele
 *date 03/05/2016
 *brief Implementa l’interfaccia che gestisce le informazioni riguardanti un team.
 *use Utilizzato dai giochi per gestire le informazioni riguardanti un team.
 */
public class TeamImpl implements Team{

    private String _nameTeam;
    private Profile _captain;
    private List<Profile> _teammates;
    private final Integer _id;
    private static final String TAG = "Team";

    /**
     * @name Team
     * @desc Costruttore della classe.
     * @param {Profile} captain - Rappresenta il profilo del capitano del team.
     * @param {List} teammates - Rappresenta la lista di profili del team.
     * @param {Integer} id - Rappresenta l'identificativo del team.
     * @param {String} teamName - Rappresenta il nome del team.
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    public TeamImpl(String teamName, Profile captain, List<Profile> teammates, Integer id){
       //Log.v(TAG, ".TeamImpl(" + (new Gson()).toJson(teamName) + ","+ (new Gson()).toJson(captain) +", " + (new Gson()).toJson(teammates) +  ", "+ (new Gson()).toJson(id) + ")");
        _captain = captain;
        _teammates = teammates;
        _nameTeam = teamName;
        _id = id;
       //Log.v(TAG, ".TeamImpl(" + (new Gson()).toJson(teamName) + ","+ (new Gson()).toJson(captain) +", " + (new Gson()).toJson(teammates) +  ", "+ (new Gson()).toJson(id) + ") return : costruttore");
    }
    /**
     * @name Team
     * @desc Costruttore della classe.
     * @param {Profile} captain - Rappresenta il profilo del capitano del team.
     * @param {List} teammates - Rappresenta la lista di profili del team.
     * @param {String} teamName - Rappresenta il nome del team.
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    public TeamImpl(String teamName, Profile captain, List<Profile> teammates){
       //Log.v(TAG, ".TeamImpl(" + (new Gson()).toJson(teamName) + ","+ (new Gson()).toJson(captain) +", " + (new Gson()).toJson(teammates) + ")");
        _captain = captain;
        _teammates = teammates;
        _nameTeam = teamName;
        _id = null;
       //Log.v(TAG, ".TeamImpl(" + (new Gson()).toJson(teamName) + ","+ (new Gson()).toJson(captain) +", " + (new Gson()).toJson(teammates)+") return : costruttore");
    }
    /**
     * @name getCaptain
     * @desc Ritorna il profilo del giocatore che è il capitano del team.
     * @returns {Profile}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public Profile getCaptain() {
       //Log.v(TAG, ".getCaptain()");
       //Log.v(TAG, ".getCaptain() return : " + (new Gson()).toJson(_captain));
        return _captain;

    }
    /**
     * @name setCaptain
     * @desc Modifica il capitano della squadra.
     * @param {Profile} captain - Rappresenta il profilo del capitano della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public void setCaptain(Profile captain) {
       //Log.v(TAG, ".setCaptain(" + (new Gson()).toJson(captain) + ")");
        _captain = captain;
       //Log.v(TAG, ".setCaptain(" + (new Gson()).toJson(captain) + ") return : void");
    }
    /**
     * @name getTeammates
     * @desc Ritorna la lista contenente i profili dei giocatori della squadra.
     * @returns {List}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public List<Profile> getTeammates() {
       //Log.v(TAG, ".getTeammates()");
       //Log.v(TAG, ".getTeammates() return : " + (new Gson()).toJson(_teammates));
        return _teammates;
    }
    /**
     * @name setTeammates
     * @desc Modifica la lista dei componenti del team.
     * @param {List} teammates - Rappresenta la lista dei profili della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public void setTeammates(List<Profile> teammates) {
       //Log.v(TAG, ".setTeammates(" + (new Gson()).toJson(teammates) + ")");
        _teammates = teammates;
       //Log.v(TAG, ".setTeammates(" + (new Gson()).toJson(teammates) + ") return : void");
    }
    /**
     * @name getTeamName
     * @desc Ritorna il nome della squadra.
     * @returns {int}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public String getTeamName() {
       //Log.v(TAG, ".getTeamName()");
       //Log.v(TAG, ".getTeamName() return : " + (new Gson()).toJson(_nameTeam));
        return _nameTeam;
    }
    /**
     * @name setTeamName
     * @desc Setta il nome del team.
     * @param {String} teamName - Rappresenta il nuovo nome della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public void setTeamName(String teamName) {
       //Log.v(TAG, ".setTeamName(" + (new Gson()).toJson(teamName) + ")");
        _nameTeam = teamName;
       //Log.v(TAG, ".setTeamName(" + (new Gson()).toJson(teamName) + ") return : void");
    }
    /**
     * @name getId
     * @desc Ritorna l'identificativo della squadra.
     * @returns {Integer}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    @Override
    public Integer getId() {
       //Log.v(TAG, ".getId()");
       //Log.v(TAG, ".getId() return : " + (new Gson()).toJson(_id));
        return _id;
    }

    @Override
    public boolean equals(Team t) {
        boolean ok = true;

        Iterator<Profile> iterator_1 = t.getTeammates().iterator();
        Iterator<Profile> iterator = this._teammates.iterator();
        for (; iterator.hasNext(); iterator.next()) {
            if (iterator != iterator_1)
                ok = false;
        }
        return (this.getId().equals(t.getId()))&&(this.getCaptain()==t.getCaptain())&&(this.getTeamName().equals(t.getTeamName()))&&ok;
    }
}
