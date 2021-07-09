package digitalsloths.socialtables.games.utility.types;

/**
 *file Gama.java
 *author Saccon Daniele
 *date 03/05/2016
 *brief Interfaccia che gestisce l’identificazione di un particolare gioco.
 *use Utilizzato da UtilityManager per avviare un particolare gioco.
 */
public interface Game {
    /**
     * @name getId
     * @desc Ritorna l'id del gioco.
     * @returns {Integer}
     * @memberOf Client.Games.Utility.Types.Game
     */
    public Integer getId();
    /**
     * @name getNome
     * @desc Ritorna il nome del gioco.
     * @returns {String}
     * @memberOf Client.Games.Utility.Types.Game
     */
    public String getName();
    /**
     * @name Equals
     * @desc Ritorna true se i 2 giochi sono uguali.
     * @returns {Boolean}
     * @memberOf Client.Games.Utility.Types.Game
     */
    public boolean equals(Game game);
}
