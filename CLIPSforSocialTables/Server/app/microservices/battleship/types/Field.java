package microservices.battleship.types;

/**
 *file Field.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti il campo di battaglia del gioco Battleship.
 *use Viene utilizzata per gestire le informazioni riguardanti il campo di battaglia del gioco Battleship.
 */
public interface Field {
    /**
     * @name getShootGrid
     * @desc Ritorna la griglia del campo con i colpi sparati.
     * @returns {Grid}
     * @memberOf Server.Microservices.Battleship.Types.Field
     */
    public Grid getShootGrid();
    /**
     * @name set_shootGrid
     * @desc Setta la griglia del campo.
     * @param {Grid} shootGrid - Rappresenta la griglia del campo.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    public void set_shootGrid(Grid shootGrid);
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {FieldImpl} f - Rappresenta il Field da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Field
     */
    public boolean equals(Field f);
}
