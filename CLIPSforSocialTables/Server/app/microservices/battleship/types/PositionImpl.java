package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file PositionImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti la posizione di un oggetto nel campo di battaglia dal gioco Battleship.
 *use Implementa i metodi offerti dall'interfaccia Position, utilizzati per gestire le informazioni riguardanti la posizione di un oggetto nel campo di battaglia dal gioco Battleship.
 */
public class PositionImpl implements Position {
    private int _x;
    private int _y;
    private static final String TAG = "Position";


    public PositionImpl () {
        String methodSignature = ".PositionImpl()";
        //play.Logger.info(TAG + methodSignature);
        _x =0;
        _y =0;
        //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name PositionImpl
     * @desc Costruttore della classe.
     * @param {int} x - Rappresenta l'ascissa della posizione.
     * @param {int} y - Rappresenta l'ordinata della posizione.
     * @memberOf Server.Microservices.Battleship.Types.PositionImpl
     */
    public PositionImpl (int x, int y) {
        String methodSignature = ".PositionImpl(" + (new Gson()).toJson(x) + "," + (new Gson()).toJson(y) +")";
     //play.Logger.info(TAG + methodSignature);
        _x =x;
        _y =y;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getX
     * @desc Ritorna l'ascissa della posizione sul campo del gioco Battleship.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.PositionImpl
     */
    @Override
    public int getX() {
     //play.Logger.info(TAG + ".getX()");
     //play.Logger.info(TAG + ".getX() return: " + (new Gson()).toJson(_x));
        return _x;
    }
    /**
     * @name getY
     * @desc Ritorna l'ordinata della posizione sul campo del gioco Battleship.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.PositionImpl
     */
    @Override
    public int getY() {
     //play.Logger.info(TAG + ".getY()");
     //play.Logger.info(TAG + ".getY() return: " + (new Gson()).toJson(_y));
        return _y;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {PositionImpl} p - Rappresenta la position da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.PositionImpl
     */
    @Override
    public boolean equals(Position p) {
        return (this.getX()==p.getX())&&(this.getY()==p.getY());
    }
}
