package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file CellImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Implementa l'interfaccia che gestisce le informazioni riguardanti un settore del campo di battaglia.
 *use Utilizzato da Grid per gestire lo stato del campo da gioco.
 */
public class CellImpl  implements Cell {

    public enum State {
        HIT,
        MISS,
        SUNK,
        UNKNOWN
    }
    private State _state = State.UNKNOWN;
    private static final String TAG = "Cell";
    public CellImpl() {
        //Log.v(TAG, ".CellImpl() costruttore (entro ed esco)");
    }
    /**
     * @name CellImpl
     * @desc Costruttore della classe
     * @param {enum} state - Rappresenta lo stato di una cella del campo.
     * @memberOf Client.Games.Battleship.Types.CellImpl
     */
    public void setState(State state) {
       //Log.v(TAG, ".setState(" + (new Gson()).toJson(state) + ")");
        _state = state;
       //Log.v(TAG, ".setState(" + (new Gson()).toJson(state) + ") return : void");
    }

   /* @Override
    public State getState(){
        return _state;
    }*/

    /**
     * @name isHit
     * @desc Ritorna true sse la cella è colpita.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.Cell
     */
    @Override
    public boolean isHit() {
       //Log.v(TAG, ".isHit()");
        if (_state == State.HIT) {
           //Log.v(TAG, ".isHit() return : " + (new Gson()).toJson(true));
            return true;
        }else {
           //Log.v(TAG, ".isHit() return : " + (new Gson()).toJson(false));
            return false;
        }
    }
    /**
     * @name isMiss
     * @desc Ritorna true sse la cella non contiene una nave è una volta sparato risulta acqua.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.CellImpl
     */
    @Override
    public boolean isMiss() {
       //Log.v(TAG, ".isMiss()");
        if (_state == State.MISS) {
           //Log.v(TAG, ".isMiss() return : " + (new Gson()).toJson(true));
            return true;
        }else{
            //Log.v(TAG, ".isMiss() return : " + (new Gson()).toJson(false));
             return false;
        }
    }
    /**
     * @name isUnknow
     * @desc Ritorna true sse è sconosciuto lo stato della cella.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.CellImpl
     */
    @Override
    public boolean isUnknown() {
       //Log.v(TAG, ".isUnknown()");
        if (_state == State.UNKNOWN) {
           //Log.v(TAG, ".isUnknown() return : " + (new Gson()).toJson(true));
            return true;
        }else{
           //Log.v(TAG, ".isUnknown() return : " + (new Gson()).toJson(false));
            return false;
        }
    }
    /**
     * @name isSunk
     * @desc Dice se la cella rappresenta un pezzo della nave affondata.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Types.CellImpl
     */
    @Override
    public boolean isSunk() {
       //Log.v(TAG, ".isSunk()");
        if (_state == State.SUNK) {
           //Log.v(TAG, ".isSunk() return : " + (new Gson()).toJson(true));
            return true;
        }else{
           //Log.v(TAG, ".isSunk() return : " + (new Gson()).toJson(false));
            return false;
        }
    }

    /*public boolean Equal(Cell cell){
        return this._state == cell.getState();
    }*/
}
