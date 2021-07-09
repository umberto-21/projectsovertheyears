package digitalsloths.socialtables.games.battleship.types;

import java.util.List;

/**
 *file Field.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti il campo di battaglia del gioco Battleship.
 *use Utilizzato dal package Battleship per gestire lo stato del campo da gioco.
 */
public interface Field {
    /**
     * @name getGrid
     * @desc Ritorna la griglia del campo da gioco.
     * @returns {Client.Games.Battleship.Types.Grid}
     * @memberOf Client.Games.Battleship.Types.Field
     */
    public Grid getGrid();
    /**
     * @name Equals
     * @desc Ritorna true se i 2 campi sono uguali.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Field
     */
    public boolean equals(Field field);
}
