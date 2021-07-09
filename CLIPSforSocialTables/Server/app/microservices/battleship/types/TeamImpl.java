package microservices.battleship.types;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

/**
 *file TeamImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni che rappresentano un team.
 *use Implementa i metodi offerti dall'interfaccia Team, utilizzati per la gestione del profilo di un team.
 */
public class TeamImpl implements Team {
    private String _nameTeam;
    private final Integer _id;
    private Profile _captain;
    private List<Profile> _teammates;
    private static final String TAG = "Team";
    private static final int MAX =6;
    /**
     * @name TeamImpl
     * @desc Costruttore della classe.
     * @param {String} nameTeam - Rappresenta il nome della squadra.
     * @param {Integer} id - Rappresenta l'identificativo del team.
     * @param {Profile} captain - Rappresenta il profilo del capitano della squadra.
     * @param {List} teammates - Rappresenta la lista dei componenti della squadra.
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    public TeamImpl(String nameTeam, Integer id, Profile captain, List<Profile> teammates){
        String methodSignature = ".TeamImpl(" + (new Gson()).toJson(nameTeam) + ", " + (new Gson()).toJson(id) + ", " + (new Gson()).toJson(captain) + ", " + (new Gson()).toJson(teammates) +")";
     //play.Logger.info(TAG + methodSignature);
        _nameTeam = nameTeam;
        _id = id;
        _captain = captain;
        _teammates = teammates;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name Team Impl
     * @desc Costruttore della classe senza parametro id.
     * @param {String} nameTeam - Rappresenta il nome della squadra.
     * @param {Profile} captain - Rappresenta il profilo del capitano della squadra.
     * @param {List} teammates - Rappresenta la lista dei componenti della squadra.
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    public TeamImpl(String nameTeam, Profile captain, List<Profile> teammates){
        String methodSignature = ".TeamImpl(" + (new Gson()).toJson(nameTeam) + "," + (new Gson()).toJson(captain) + "," + (new Gson()).toJson(teammates) + ")";
     //play.Logger.info(TAG + methodSignature);
        _nameTeam = nameTeam;
        _id = null;
        _captain = captain;
        _teammates = teammates;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getNameTeam
     * @desc Rappresenta il nome del team.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public String getNameTeam() {
     //play.Logger.info(TAG + ".getNameTeam()");
     //play.Logger.info(TAG + ".getNameTeam() return: " + (new Gson()).toJson(_nameTeam));
        return _nameTeam;
    }
    /**
     * @name setNameTeam
     * @desc Setta il nome del team.
     * @param {String} nameTeam - Rappresenta il nome del team.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public void setNameTeam(String nameTeam) {
     //play.Logger.info(TAG + ".setNameTeam(" + (new Gson()).toJson(nameTeam) + ")");
        _nameTeam = nameTeam;
     //play.Logger.info(TAG + ".setNameTeam(" + (new Gson()).toJson(nameTeam) + ") return: void");
    }

    /**
     * @name getId
     * @desc Ritorna l'identificativo della squadra.
     * @returns {Integer}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public Integer getId() {
     //play.Logger.info(TAG + ".getId()");
     //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }

    /**
     * @name getCaptain
     * @desc Ritorna il profilo del capitano della squadra.
     * @returns {Profile}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public Profile getCaptain() {
     //play.Logger.info(TAG + ".getCaptain()");
     //play.Logger.info(TAG + ".getCaptain() return: " + (new Gson()).toJson(_captain));
        return _captain;
    }

    /**
     * @name SetCaptain
     * @desc Setta il capitano della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public void setCaptain(Profile captain) {
     //play.Logger.info(TAG + ".setCaptain(" + (new Gson()).toJson(captain) + ")");
        _captain = captain;
     //play.Logger.info(TAG + ".setCaptain(" + (new Gson()).toJson(captain) + ") return: void");
    }

    /**
     * @name getTeammates
     * @desc Ritorna la lista dei componenti della squadra.
     * @returns {List}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public List<Profile> getTeammates() {
     //play.Logger.info(TAG + ".getTeammates()");
     //play.Logger.info(TAG + ".getTeammates() return: " + (new Gson()).toJson(_teammates));
        return _teammates;
    }

    /**
     * @name setTeammates
     * @desc Setta la lista dei componenti della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.TeamImpl
     */
    @Override
    public void setTeammates(List<Profile> teammates) {
     //play.Logger.info(TAG + ".setUsername(" + (new Gson()).toJson(teammates) + ")");
        _teammates = teammates;
     //play.Logger.info(TAG + ".setCaptain(" + (new Gson()).toJson(teammates) + ") return: void");
    }

    /**
     * @name isFull
     * @desc Ritorna true se il team è al completo e non si potranno aggiungere altri membri, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamImpl
     */
    public boolean isFull () {
     //play.Logger.info(TAG + ".isFull()");
        boolean result =false;
        if (_teammates.size() +1 == MAX ) result =true;
     //play.Logger.info(TAG + ".isFull() return: " + (new Gson()).toJson(result));
        return result;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TeamImpl} t - Rappresenta il team da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamImpl
     */
    @Override
    public boolean equals(Team t) {
        boolean ok = true;
        Iterator<Profile> iterator_1 = t.getTeammates().iterator();
        Iterator<Profile> iterator = this._teammates.iterator();
        for (; iterator.hasNext(); iterator.next()) {
            if (iterator != iterator_1)
                ok = false;
        }
        return (this.getId().equals(t.getId()))&&(this.getCaptain()==t.getCaptain())&&(this.getNameTeam().equals(t.getNameTeam()))&&ok;
    }
}
