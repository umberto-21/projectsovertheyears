package microservices.battleship.types;

import java.util.List;

/**
 *file Team.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un team.
 *use Viene utilizzata per gestire le informazioni che rappresentano un team.
 */
public interface Team {
    /**
     * @name getNameTeam
     * @desc Rappresenta il nome del team.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public String getNameTeam();
    /**
     * @name setNameTeam
     * @desc Setta il nome del team.
     * @param {String} nameTeam - Rappresenta il nome del team.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public void setNameTeam(String nameTeam);

    /**
     * @name getId
     * @desc Ritorna l'identificativo della squadra.
     * @returns {Integer}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public Integer getId();

    /**
     * @name getCaptain
     * @desc Ritorna il profilo del capitano della squadra.
     * @returns {Profile}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public Profile getCaptain();

    /**
     * @name SetCaptain
     * @desc Setta il capitano della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public void setCaptain(Profile captain);

    /**
     * @name getTeammates
     * @desc Ritorna la lista dei componenti della squadra.
     * @returns {List}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public List<Profile> getTeammates();

    /**
     * @name setTeammates
     * @desc Setta la lista dei componenti della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Score.Types.Team
     */
    public void setTeammates(List<Profile> teammates);

    /**
     * @name isFull
     * @desc Ritorna true se il team è al completo e non si potranno aggiungere altri membri, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Team
     */
    public boolean isFull ();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TeamImpl} t - Rappresenta il team da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Team
     */
    public boolean equals(Team t);
}
