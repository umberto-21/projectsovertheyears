package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

/**
 *file FieldImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Implementa l'interfaccia che gestisce le informazioni riguardanti il campo di battaglia del gioco Battleship.
 *use Utilizzato dal package Battleship per gestire lo stato del campo da gioco.
 */
public class FieldImpl implements Field {
    private Grid _shootGrid;
    private static final String TAG = "Field";
    /**
     * @name FieldImpl
     * @desc Costruttore della classe.
     * @param {Grid} grid - Rappresenta la griglia del campo da gioco.
     * @param {List } shipList - Rappresenta la lista di navi posizionate sul campo.
     * @memberOf Client.Games.Battleship.Types.FieldImpl
     */
    public FieldImpl(Grid grid){
       //Log.v(TAG, ".FieldImpl(" + (new Gson()).toJson(grid) + ")");
        _shootGrid = grid;
       //Log.v(TAG, ".FieldImpl(" + (new Gson()).toJson(grid) + ") return : costruttore");
    }

    /**
     * @name getGrid
     * @desc Ritorna la griglia del campo da gioco.
     * @returns {Client.Games.Battleship.Types.Grid}
     * @memberOf Client.Games.Battleship.Types.FieldImpl
     */
    @Override
    public Grid getGrid() {
       //Log.v(TAG, ".getGrid()");
       //Log.v(TAG, ".getGrid() return : " + (new Gson()).toJson(_shootGrid));
        return _shootGrid;
    }
    /**
     * @name Equals
     * @desc Ritorna true se i 2 campi sono uguali.
     * @returns {boolean }
     * @memberOf Client.Games.Battleship.Types.FieldImpl
     */
    @Override
    public boolean equals(Field field){

        return this._shootGrid.equals(field.getGrid());
    }
}
