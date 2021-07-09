package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file ShotImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti un colpo sparato da un giocatore.
 *use Implementa i metodi offerti dall'interfaccia Shoot, utilizzati per gestire le informazioni riguardanti un colpo sparato da un giocatore.
 */
public class ShotImpl implements Shot{

    private Position _position;
    private static final String TAG = "Shot";
    /**
     * @name ShootImpl
     * @desc Costruttore della classe.
     * @param {Position} p - position
     * @memberOf Server.Microservices.Battleship.Types.ShotImpl
     */
    public ShotImpl () {
        //play.Logger.info(TAG + ".ShotImpl(" + (new Gson()).toJson(p) +")");
        _position = new PositionImpl(0,0);
        //play.Logger.info(TAG + ".ShotImpl(" + (new Gson()).toJson(p) +") return: costruttore");
    }
    public ShotImpl (Position p) {
     //play.Logger.info(TAG + ".ShotImpl(" + (new Gson()).toJson(p) +")");
        _position = p;
     //play.Logger.info(TAG + ".ShotImpl(" + (new Gson()).toJson(p) +") return: costruttore");
    }
    /**
     * @name getPosition
     * @desc Ritorna la posizione del colpo sparato.
     * @returns {Position}
     * @memberOf Server.Microservices.Battleship.Types.ShotImpl
     */
    @Override
    public Position getPosition() {
     //play.Logger.info(TAG + ".getPosition()");
     //play.Logger.info(TAG + ".getPosition() return: " + (new Gson()).toJson(_position));
        return _position;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShotImpl} s - Rappresenta il colpo da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShotImpl
     */
    @Override
    public boolean equals(Shot s) {
        return this.getPosition()==s.getPosition();
    }
}
