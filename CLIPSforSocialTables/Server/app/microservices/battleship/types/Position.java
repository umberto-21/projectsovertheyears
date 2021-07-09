package microservices.battleship.types;

/**
 *file Position.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti la posizione di un oggetto nel campo di battaglia dal gioco Battleship.
 *use Viene utilizzata per gestire le informazioni riguardanti la posizione di un oggetto nel campo di battaglia dal gioco Battleship.
 */
public interface Position {
    /**
     * @name getX
     * @desc Ritorna l'ascissa della posizione sul campo del gioco Battleship.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Position
     */
    public int getX ();
    /**
     * @name getY
     * @desc Ritorna l'ordinata della posizione sul campo del gioco Battleship.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Position
     */
    public int getY ();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {PositionImpl} p - Rappresenta la position da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Position
     */
    public boolean equals(Position p);
}
