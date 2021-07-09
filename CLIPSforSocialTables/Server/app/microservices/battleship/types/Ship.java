package microservices.battleship.types;

import java.util.List;

/**
 *file Ship.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti una nave.
 *use Viene utilizzata per gestire le informazioni riguardanti una nave.
 */
public interface Ship extends ShipType {
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    //public int getLength ();
    /**
     * @name getPosition
     * @desc Ritorna la posizione della nave.
     * @returns {Position}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    public Position getPosition ();
    /**
     * @name isVertical
     * @desc Ritorna true se la nave è posizionata in verticale, false se in orizzontale.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    public boolean isVertical ();
    /**
     * @name calculateShipPositions
     * @desc Calcola la posizione della nave.
     * @returns {List }
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    public List<Position> calculateShipPositions();
    /**
     * @name isHit
     * @desc Dice se la nave è stata colpita.
     * @param {Shot} shot - Rappresenta il colpo subito dalla nave.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    public boolean isHit (Shot shot);
    /**
     * @name isSink
     * @desc Dice se la nave è stata affondata.
     * @param {Grid} grid - Rappresenta il campo da gioco.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    public boolean isSink (Grid grid);
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShipImpl} s - Rappresenta la ship da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    public boolean equals(Ship s);
}
