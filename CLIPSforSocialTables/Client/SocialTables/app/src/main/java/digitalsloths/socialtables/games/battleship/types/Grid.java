package digitalsloths.socialtables.games.battleship.types;

/**
 *file Grid.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti il campo di battaglia.
 *use Utilizzato da Field per gestire lo stato del campo da gioco.
 */
public interface Grid {
    /**
     * @name getCell
     * @desc Ritorna la cella specificata dai parametri.
     * @param {int} x - Rappresenta la coordinata x della cella.
     * @param {int} y - Rappresenta la coordinata y della cella.
     * @returns {Client.Games.Battleship.Types.Cell}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    public Cell getCell(int x, int y);
    /**
     * @name getHeigth
     * @desc Ritorna l'altezza della griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    public int getHeight();
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    public int getLength();
    /**
     * @name Equals
     * @desc Ritorna true se i grid sono uguali.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    public boolean equals(Grid grid);
    /**
     * @name getGrid
     * @desc Ritorna la la griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    public Cell [][] getCells();
}
