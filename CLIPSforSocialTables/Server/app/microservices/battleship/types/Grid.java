package microservices.battleship.types;

/**
 *file Grid.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti il campo di battaglia di una squadra.
 *use Viene utilizzata per gestire le informazioni riguardanti il campo di battaglia di una squadra.
 */
public interface Grid {
    /**
     * @name getCell
     * @desc Ritorna una cella del campo del gioco Battleship.
     * @param {int} x - Rappresenta l'ascissa della cella del campo da gioco.
     * @param {int} y - Rappresenta l'ascissa della cella del campo da gioco.
     * @returns {Cell}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    public Cell getCell(int x, int y);
    /**
     * @name getHeight
     * @desc Rappresenta la larghezza del campo da gioco.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    public int getHeight();
    /**
     * @name getLength
     * @desc Ritorna la lunghezza del campo da gioco.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    public int getLength();
    /**
     * @name setCell
     * @desc Setta lo stato della cella.
     * @param {int} x - Rappresenta l'ascissa della cella.
     * @param {int} y - Rappresenta l'ordinata della cella.
     * @param {State} state - Rappresenta lo stato della cella.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    public void setCell (int x, int y, CellImpl.State state);
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {GridImpl} g - Rappresenta la griglia da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    public boolean equals(Grid g);
}
