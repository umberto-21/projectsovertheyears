package digitalsloths.socialtables.games.battleship.types;

/**
 *file Cell.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un settore del campo di battaglia.
 *use Utilizzato da Grid per gestire lo stato del campo da gioco.
 */
public interface Cell {
    /**
     * @name isHit
     * @desc Ritorna true sse la cella è colpita.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */
    public boolean isHit ();
    /**
     * @name isMiss
     * @desc Ritorna true sse la cella non contiene una nave è una volta sparato risulta acqua.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */
    public boolean isMiss ();

    //public State getState();
    /**
     * @name isUnknow
     * @desc Ritorna true sse è sconosciuto lo stato della cella.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */
    public boolean isUnknown ();
    /**
     * @name isSunk
     * @desc Dice se la cella rappresenta un pezzo della nave affondata.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */
    public boolean isSunk ();

    /**
     * @name Equal
     * @desc Dice se la cella rappresenta è uguale al contenuto di un'altra.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */

    //public boolean Equal(Cell cell);

}
