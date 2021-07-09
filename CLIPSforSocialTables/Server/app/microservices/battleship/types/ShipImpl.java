package microservices.battleship.types;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *file ShipImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti una nave.
 *use Implementa i metodi offerti dall'interfaccia Ship, utilizzati per gestire le informazioni riguardanti una nave.
 */
public class ShipImpl extends ShipTypeImpl implements Ship {
    private boolean _isVertical;
    //private int _length;
    private Position _position;
    private static final String TAG = "Ship";

    /**
     * @name ShipImpl
     * @desc Costruttore della classe.
     * @param {boolean} isVertical - Dice se la nave è verticale.
     * @param {int} length - Rappresenta la lunghezza della nave.
     * @param {Position} position - Rappresenta la posizione della nave.
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    public ShipImpl (boolean isVertical, int length, Position position) {
        super(length);
        String methodSignature = ".ShipImpl(" + (new Gson()).toJson(isVertical) + ", " + (new Gson()).toJson(length) + ", " + (new Gson()).toJson(position) +")";
     //play.Logger.info(TAG + methodSignature);
        _isVertical = isVertical;
        //_length = length;
        _position = position;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza della nave.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    /*
    @Override
    public int getLength() {
     //play.Logger.info(TAG + ".getLength()");
     //play.Logger.info(TAG + ".getLength() return: " + (new Gson()).toJson(_length));
        return _length;
    }
    */
    /**
     * @name getPosition
     * @desc Ritorna la posizione della nave.
     * @returns {Position}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    @Override
    public Position getPosition() {
     //play.Logger.info(TAG + ".getPosition()");
     //play.Logger.info(TAG + ".getPosition() return: " + (new Gson()).toJson(_position));
        return _position;
    }
    /**
     * @name isVertical
     * @desc Ritorna true se la nave è posizionata in verticale, false se in orizzontale.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Ship
     */
    @Override
    public boolean isVertical() {
     //play.Logger.info(TAG + ".isVertical()");
     //play.Logger.info(TAG + ".isVertical() return: " + (new Gson()).toJson(_isVertical));
        return _isVertical;
    }
    /**
     * @name calculateShipPositions
     * @desc Calcola la posizione della nave.
     * @returns {List }
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    @Override
    public List<Position> calculateShipPositions() {
     //play.Logger.info(TAG + ".calculateShipPositions()");
        LinkedList<Position> positionLinkedList =new LinkedList<Position>();
        positionLinkedList.add(getPosition());
        if (isVertical()) {
            for (int i=1; i<getLength(); i++) {
                Position p =new PositionImpl(getPosition().getX(), getPosition().getY() +i);
                positionLinkedList.add(p);
            }
        }
        else {
            for (int i=1; i<getLength(); i++) {
                Position p =new PositionImpl(getPosition().getX() +i, getPosition().getY());
                positionLinkedList.add(p);
            }
        }
     //play.Logger.info(TAG + ".calculateShipPositions() return: " + (new Gson()).toJson(positionLinkedList));
        return positionLinkedList;
    }
    /**
     * @name isHit
     * @desc Dice se la nave è stata colpita.
     * @param {Shot} shot - Rappresenta il colpo subito dalla nave.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    @Override
    public boolean isHit(Shot shot) {
     //play.Logger.info(TAG + ".calculateShipPositions("+ (new Gson()).toJson(shot) +")");
                List<Position> positionList =calculateShipPositions();
        Iterator<Position> itr =positionList.iterator();
        Position shootPosition =shot.getPosition();
        boolean hit =false;
        while (itr.hasNext() && !hit) {
            Position p =itr.next();
            if (p.getX() == shootPosition.getX() && p.getY() == shootPosition.getY()) {
                hit =true;
            }
        }
     //play.Logger.info(TAG + ".calculateShipPositions( "+ (new Gson()).toJson(shot) + ") return: " + (new Gson()).toJson(hit));
        return hit;
    }
    /**
     * @name isSink
     * @desc Dice se la nave è stata affondata.
     * @param {Grid} grid - Rappresenta il campo da gioco.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    public boolean isSink (Grid grid) {
     //play.Logger.info(TAG + ".calculateShipPositions("+ (new Gson()).toJson(grid) +")");
        List<Position> positionList =calculateShipPositions();
        boolean sink =true;
        Iterator<Position> itr =positionList.iterator();
        while (itr.hasNext() && sink) {
            Position p =itr.next();
            if (!grid.getCell(p.getX(), p.getY()).isHit()) sink =false;
        }
     //play.Logger.info(TAG + ".calculateShipPositions( "+ (new Gson()).toJson(grid) + ") return: " + (new Gson()).toJson(sink));
        return sink;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ShipImpl} s - Rappresenta la ship da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ShipImpl
     */
    @Override
    public boolean equals(Ship s) {
        return (this.isVertical()==s.isVertical())&&(this.getPosition()==s.getPosition()&&(this.getLength()==s.getLength()));
    }
}
