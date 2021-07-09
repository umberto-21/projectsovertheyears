package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file GridImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Implementa l'interfaccia che gestisce le informazioni riguardanti il campo di battaglia.
 *use Utilizzato da Field per gestire lo stato del campo da gioco.
 */
public class GridImpl implements Grid {
    private Cell [][] _cells;
    private int _heigth;
    private int _length;
    private static final String TAG = "Grid";
    /**
     * @name getCell
     * @desc Ritorna la cella specificata dai parametri.
     * @param {int} x - Rappresenta la coordinata x della cella.
     * @param {int} y - Rappresenta la coordinata y della cella.
     * @returns {Client.Games.Battleship.Types.Cell}
     * @memberOf Client.Games.Battleship.Types.GridImpl
     */
    public GridImpl (int h, int l) {
       //Log.v(TAG, ".FieldImpl(" + (new Gson()).toJson(h) + ","+ (new Gson()).toJson(l) + ")");
        _heigth =h;
        _length =l;
        _cells = new Cell [_heigth] [_length];
        for(int y=0; y<_heigth; y++){
            for(int x=0; x<_length; x++){
                _cells[y][x] = new CellImpl();
            }
        }
       //Log.v(TAG, ".FieldImpl(" + (new Gson()).toJson(h) + ","+ (new Gson()).toJson(l) + ") return : costruttore");
    }
    /**
     * @name getCell
     * @desc Ritorna la cella specificata dai parametri.
     * @param {int} x - Rappresenta la coordinata x della cella.
     * @param {int} y - Rappresenta la coordinata y della cella.
     * @returns {Client.Games.Battleship.Types.Cell}
     * @memberOf Client.Games.Battleship.Types.GridImpl
     */
    @Override
    public Cell getCell(int x, int y) {
       //Log.v(TAG, ".getCell(" + (new Gson()).toJson(x) + ","+ (new Gson()).toJson(y) +")");
       //Log.v(TAG, ".getCell() return : " + (new Gson()).toJson(_cells [y] [x]));
        return _cells [y] [x];
    }
    /**
     * @name getHeigth
     * @desc Ritorna l'altezza della griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.GridImpl
     */
    @Override
    public int getHeight() {
       //Log.v(TAG, ".getHeight()");
       //Log.v(TAG, ".getHeight() return : " + (new Gson()).toJson(_heigth));
        return _heigth;
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.GridImpl
     */
    @Override
    public int getLength() {
       //Log.v(TAG, ".getLength()");
       //Log.v(TAG, ".getLength() return : " + (new Gson()).toJson(_length));
        return _length;
    }

    /**
     * @name equals
     * @desc Ritorna true se i due Grid sono uguali.
     * @param {GridImpl} g - Rappresenta la griglia da confrontare.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Grid
     */
    @Override
    public boolean equals(Grid grid){
        if (grid.getHeight()!=this._heigth){
            return false;
        }else if(grid.getLength()!=this._length){
            return false;
        }else {
            boolean esito = true;
            for (int i = 0; i < _heigth && esito; i++) {
                for (int j = 0; j < _length && esito; j++) {
                    if (this._cells[i][j]!=grid.getCells()[i][j])
                        esito=false;
                }
            }
            return esito;
        }
    }

    /**
     * @name getGrid
     * @desc Ritorna la la griglia del campo da gioco.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.GridImpl
     */
    @Override
    public Cell[][] getCells() {
        return _cells;
    }
}
