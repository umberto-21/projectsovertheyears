package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file ShipTypeImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti una nave.
 *use Implementa i metodi offerti dall'interfaccia Ship, utilizzati per gestire le informazioni riguardanti una nave.
 */
public class ShipTypeImpl implements ShipType{
    private int _length;
    private static final String TAG = "ShipType";
    /**
     * @name ShipTypeImpl
     * @desc Costruttore della classe.
     * @param {int} length - Rappresenta la lunghezza della nave.
     * @memberOf Server.Microservices.Battleship.Types.ShipTypeImpl
     */
    public ShipTypeImpl (int length){
     //play.Logger.info(TAG + ".ShipTypeImpl(" + (new Gson()).toJson(length) + ")");
        _length = length;
     //play.Logger.info(TAG + ".ShipTypeImpl(" + (new Gson()).toJson(length) +") return: costruttore");
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.ShipTypeImpl
     */
    @Override
    public int getLength() {
     //play.Logger.info(TAG + ".getLength()");
     //play.Logger.info(TAG + ".getLength() return: " + (new Gson()).toJson(_length));
        return _length;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShipTypeImpl} s - Rappresenta la ship da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipTypeImpl
     */
    @Override
    public boolean equals(ShipType s) {
        return this.getLength()==s.getLength();
    }
}
