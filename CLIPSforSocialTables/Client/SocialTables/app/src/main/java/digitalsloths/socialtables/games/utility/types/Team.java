package digitalsloths.socialtables.games.utility.types;

import java.util.List;

import digitalsloths.socialtables.types.Profile;

/**
 *file Team.java
 *author Saccon Daniele
 *date 03/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un team.
 *use Utilizzato dai giochi per gestire le informazioni riguardanti un team.
 */
public interface Team {
    /**
     * @name getCaptain
     * @desc Ritorna il profilo del giocatore che è il capitano del team.
     * @returns {Profile}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public Profile getCaptain();
    /**
     * @name setCaptain
     * @desc Modifica il capitano della squadra.
     * @param {Profile} captain - Rappresenta il profilo del capitano della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public void setCaptain(Profile captain);
    /**
     * @name getTeammates
     * @desc Ritorna la lista contenente i profili dei giocatori della squadra.
     * @returns {List}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public List<Profile> getTeammates();
    /**
     * @name setTeammates
     * @desc Modifica la lista dei componenti del team.
     * @param {List} teammates - Rappresenta la lista dei profili della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public void setTeammates(List<Profile> teammates);
    /**
     * @name getTeamName
     * @desc Ritorna il nome della squadra.
     * @returns {int}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public String getTeamName();
    /**
     * @name setTeamName
     * @desc Setta il nome del team.
     * @param {String} teamName - Rappresenta il nuovo nome della squadra.
     * @returns {void}
     * @memberOf Client.Games.Utility.Types.Team
     */
    public void setTeamName(String nameTeam);
    /**
     * @name getId
     * @desc Ritorna l'identificativo della squadra.
     * @returns {Integer}
     * @memberOf Client.Games.Utility.Types.TeamImpl
     */
    public Integer getId();

    public boolean equals(Team t);
}
