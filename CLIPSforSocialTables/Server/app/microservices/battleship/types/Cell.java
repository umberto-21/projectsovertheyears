package microservices.battleship.types;

/**
 *file Cell.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un settore del campo di battaglia del gioco Battleship.
 *use Viene utilizzata per rappresentare una cella del campo di battaglia del gioco Battleship.
 */
public interface Cell {
    /**
     * @name isHit
     * @desc Ritorna true se c'è una nave nella cella e quindi indica che è stata colpita, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public boolean isHit ();
    /**
     * @name isMiss
     * @desc Ritorna true se non c'è una nave nella cella e quindi indica che non è stato colpito niente, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public boolean isMiss ();
    /**
     * @name isUnknown
     * @desc Ritorna true se nessuno ha ancora sparato in quella cella, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public boolean isUnknown ();
    /**
     * @name isSunk
     * @desc Dice se la cella rappresenta un pezzo della nave affondata.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public boolean isSunk ();
    /**
     * @name setState
     * @desc Setta lo stato della cella.
     * @param {State} state - Rappresenta lo stato della cella.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public void setState (CellImpl.State state);
    /**
     * @name getState
     * @desc Ritorna lo stato della cella.
     * @returns {State}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public CellImpl.State getState();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {Cell} c - Rappresenta la cella da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Cell
     */
    public boolean equals(Cell c);
}
