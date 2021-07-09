package digitalsloths.socialtables.games.battleship.types;

/**
 *file Ship.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti una nave.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti una nave.
 */
public interface Ship extends ShipType{
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Ship
     */
    //public int getLength();
    /**
     * @name getPosition
     * @desc Ritorna la posizione della nave.
     * @returns {Client.Games.Battleship.Types.Position}
     * @memberOf Client.Games.Battleship.Types.Ship
     */
    public Position getPosition();
    /**
     * @name isVertical
     * @desc Ritorna true sse la nave è in posizione verticale.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Ship
     */
    public boolean isVertical();
    public boolean equals(Ship ship);
    /**
     * @name Equals
     * @desc Ritorna true sse la nave è uguale ad un'altra.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Ship
     */
}
