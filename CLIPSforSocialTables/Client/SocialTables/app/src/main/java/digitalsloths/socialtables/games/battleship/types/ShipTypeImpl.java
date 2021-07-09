package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file ShipTypeImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Implementa l'interfaccia che gestisce le informazioni riguardanti una nave.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti una nave.
 */
public class ShipTypeImpl implements ShipType {
    private int _length;
    private static final String TAG = "ShipType";
    /**
     * @name ShipTypeImpl
     * @desc Costruttore della classe.
     * @param {int} length - Rappresenta la lunghezza della nave.
     * @memberOf Client.Games.Battleship.Types.ShipTypeImpl
     */
    public ShipTypeImpl(int length){
       //Log.v(TAG, ".ShipTypeImpl(" + (new Gson()).toJson(length) + ")");
        _length = length;
       //Log.v(TAG, ".ShipTypeImpl(" + (new Gson()).toJson(length) +  ") return : costruttore");
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.ShipTypeImpl
     */
    @Override
    public int getLength() {
       //Log.v(TAG, ".getLength()");
       //Log.v(TAG, ".getLength() return : " + (new Gson()).toJson(_length));
        return _length;
    }
    @Override
    public boolean equals(ShipType shipType){
        return this._length == shipType.getLength();
    }
}
