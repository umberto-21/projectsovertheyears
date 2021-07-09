package microservices.battleship.types;

/**
 *file ShipType.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti una nave.
 *use Viene utilizzata per gestire le informazioni riguardanti una nave.
 */
public interface ShipType {
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.ShipType
     */
    public int getLength ();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShipTypeImpl} s - Rappresenta la ship da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipType
     */
    public boolean equals(ShipType s);
}
