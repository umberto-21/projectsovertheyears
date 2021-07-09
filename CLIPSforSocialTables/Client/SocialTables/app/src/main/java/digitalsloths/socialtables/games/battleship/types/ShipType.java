package digitalsloths.socialtables.games.battleship.types;

/**
 *file ShipType.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti una nave.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti una nave.
 */
public interface ShipType {
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.ShipType
     */
    public int getLength();
    /**
     * @name Equals
     * @desc Ritorna true se il tipo della nave è = ad un altro.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.ShipType
     */
    public boolean equals(ShipType shipType);
}
