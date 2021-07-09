package microservices.battleship.types;

/**
 *file Shot.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un colpo sparato da un giocatore.
 *use Viene utilizzata per gestire le informazioni riguardanti un colpo sparato da un giocatore.
 */
public interface Shot {
    /**
     * @name getPosition
     * @desc Ritorna la posizione del colpo sparato.
     * @returns {Position}
     * @memberOf Server.Microservices.Battleship.Types.Shot
     */
    public Position getPosition ();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShotImpl} s - Rappresenta il colpo da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Shot
     */
    public boolean equals(Shot s);
}
