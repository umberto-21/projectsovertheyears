package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file ShipImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Implementa l’interfaccia che gestisce le informazioni riguardanti una nave.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti una nave.
 */
public class ShipImpl extends ShipTypeImpl implements Ship {
    private boolean _isVertical;
    //private int _length;
    private Position _position;
    private static final String TAG = "Ship";

    /**
     * @name ShipImpl
     * @desc Costruttore della classe.
     * @param {boolean} vertical - Dice se la posizione è posizionata in verticale o no.
     * @param {int} length - Rappresenta la lunghezza della nave.
     * @param {Position} position - Rappresenta la posizione della nave.
     * @memberOf Client.Games.Battleship.Types.ShipImpl
     */
    public ShipImpl (boolean vertical, int length, Position position) {
        super(length);
       //Log.v(TAG, ".ShipImpl(" + (new Gson()).toJson(vertical) + ","+ (new Gson()).toJson(length) + ","+ (new Gson()).toJson(position) + ")");
        _isVertical = vertical;
        //_length = length;
        _position = position;
       //Log.v(TAG, ".ShipImpl(" + (new Gson()).toJson(vertical) + ","+ (new Gson()).toJson(length) + ","+ (new Gson()).toJson(position) +  ") return : costruttore");
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.ShipImpl
     */
    /*
    @Override
    public int getLength() {
       //Log.v(TAG, ".getLength()");
       //Log.v(TAG, ".getLength() return : " + (new Gson()).toJson(_length));
        return _length;
    }
    */
    /**
     * @name getPosition
     * @desc Ritorna la posizione della nave.
     * @returns {Client.Games.Battleship.Types.Position}
     * @memberOf Client.Games.Battleship.Types.ShipImpl
     */
    @Override
    public Position getPosition() {
       //Log.v(TAG, ".getPosition()");
       //Log.v(TAG, ".getPosition() return : " + (new Gson()).toJson(_position));
        return _position;
    }
    /**
     * @name isVertical
     * @desc Ritorna true sse la nave è in posizione verticale.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.ShipImpl
     */
    @Override
    public boolean isVertical() {
       //Log.v(TAG, ".isVertical()");
       //Log.v(TAG, ".isVertical() return : " + (new Gson()).toJson(_isVertical));
        return _isVertical;
    }
    /**
     * @name Equals
     * @desc Ritorna true sse la nave è uguale ad un'alta.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.ShipImpl
     */
    @Override
    public boolean equals(Ship ship){
        return this._isVertical == ship.isVertical() && this._position == ship.getPosition();
    }
}
