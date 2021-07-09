package microservices.battleship.types;

/**
 *file Game.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un gioco.
 *use Viene utilizzata per gestire le informazioni riguardanti un gioco.
 */
public interface Game {
    /**
     * @name getName
     * @desc Ritorna il nome del gioco.
     * @returns {String}
     * @memberOf Server.Microservices.Battleship.Types.Game
     */
    public String getName();
    /**
     * @name getId
     * @desc Ritorna l'id del gioco.
     * @returns {Integer}
     * @memberOf Server.Microservices.Battleship.Types.Game
     */
    public Integer getId();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {Game} g - Rappresenta il gioco da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Game
     */
    public boolean equals(Game g);
}
