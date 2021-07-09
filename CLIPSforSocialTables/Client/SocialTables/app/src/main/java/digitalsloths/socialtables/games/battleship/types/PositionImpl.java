package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file PositionImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti la posizione di un oggetto nel campo di battaglia.
 *use Utilizzato da Battleship per gesetire le informazioni riguardanti la posizione di un oggetto nel campo di battaglia (es. colpo, nave).
 */

public class PositionImpl implements Position{
    private int _x;
    private int _y;
    private static final String TAG = "Position";

    /**
     * @name PositionImpl
     * @desc Costruttore della classe.
     * @param {int} x - Rappresenta la coordinata X della posizione.
     * @param {int} y - Rappresenta la coordinata Y della posizione.
     * @memberOf Client.Games.Battleship.Types.PositionImpl
     */
    public PositionImpl (int x, int y) {
       //Log.v(TAG, ".PositionImpl(" + (new Gson()).toJson(x) + ","+ (new Gson()).toJson(y) + ")");
        _x = x;
        _y = y;
       //Log.v(TAG, ".PositionImpl(" + (new Gson()).toJson(x) + ","+ (new Gson()).toJson(y) + ") return : costruttore");
    }
    /**
     * @name getX
     * @desc Ritorna la coordinata X della posizione.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.PositionImpl
     */
    @Override
    public int getX() {
       //Log.v(TAG, ".getX()");
       //Log.v(TAG, ".getX() return : " + (new Gson()).toJson(_x));
        return _x;
    }
    /**
     * @name getY
     * @desc Ritorna la coordinata Y della posizione.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Position
     */
    @Override
    public int getY() {
       //Log.v(TAG, ".getY()");
       //Log.v(TAG, ".getY() return : " + (new Gson()).toJson(_y));
        return _y;
    }
    @Override
    public boolean equals(Position position){
        return this.getX() == this.getY() && position.getX() == position.getY();
    }
}
