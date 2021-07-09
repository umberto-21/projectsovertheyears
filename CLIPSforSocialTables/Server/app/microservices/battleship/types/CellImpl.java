package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file Cell.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti un settore del campo di battaglia del gioco Battleship.
 *use Implementa i metodi offerti dall'interfaccia Cell, utilizzati per rappresentare lo stato di una cella del campo di battaglia del gioco Battleship.
 */
public class CellImpl implements Cell {

    public enum State {
        HIT,
        MISS,
        SUNK,
        UNKNOWN
    }

    private State _state = State.UNKNOWN;
    private static final String TAG = "Cell";

    public CellImpl(){
        _state = State.UNKNOWN;
    }

    /**
     * @name isHit
     * @desc Ritorna true se c'è una nave nella cella e quindi indica che è stata colpita, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public boolean isHit() {
     //play.Logger.info(TAG + ".isHit()");
     //play.Logger.info(TAG + ".isHit() return: " + (_state == State.HIT));
        return _state == State.HIT;
    }
    /**
     * @name isMiss
     * @desc Ritorna true se non c'è una nave nella cella e quindi indica che non è stato colpito niente, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public boolean isMiss() {
     //play.Logger.info(TAG + ".isMiss()");
     //play.Logger.info(TAG + ".isMiss() return: " + (_state == State.MISS));
        return _state == State.MISS;
    }
    /**
     * @name isUnknown
     * @desc Ritorna true se nessuno ha ancora sparato in quella cella, false altrimenti.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public boolean isUnknown() {
     //play.Logger.info(TAG + ".isUnknown()");
     //play.Logger.info(TAG + ".isUnknown() return: " + (_state == State.UNKNOWN));
        return _state == State.UNKNOWN;
    }
    /**
     * @name isSunk
     * @desc Dice se la cella rappresenta un pezzo della nave affondata.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public boolean isSunk() {
     //play.Logger.info(TAG + ".isSunk()");
     //play.Logger.info(TAG + ".isSunk() return: " + (_state == State.UNKNOWN));
        return _state == State.SUNK;
    }
    /**
     * @name setState
     * @desc Setta lo stato della cella.
     * @param {State} state - Rappresenta lo stato della cella.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public void setState(State state) {
     //play.Logger.info(TAG + ".setState(" + (new Gson()).toJson(state) + ")");
        _state = state;
     //play.Logger.info(TAG + ".setState(" + (new Gson()).toJson(state) + ") return: void");
    }
    /**
     * @name getState
     * @desc Ritorna lo stato della cella.
     * @returns {State}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public State getState() {
        return _state;
    }

    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {Cell} c - Rappresenta la cella da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.CellImpl
     */
    @Override
    public boolean equals(Cell c) {
        return this.getState()==c.getState();
    }
}
