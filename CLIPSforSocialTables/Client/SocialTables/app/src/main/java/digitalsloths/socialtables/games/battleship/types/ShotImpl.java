package digitalsloths.socialtables.games.battleship.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file ShotImpl.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Implementa l’interfaccia che gestisce le informazioni riguardanti un colpo sparato da un giocatore.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti un colpo sparato da un giocatore.
 */
public class ShotImpl implements Shot{

    private Position _position;
    private static final String TAG = "Shot";

    /**
     * @name ShotImpl
     * @desc Costruttore della classe.
     * @param {Position} position - Rappresenta la posizione del colpo sparato.
     * @memberOf Client.Games.Battleship.Types.ShootImpl
     */
    public ShotImpl (Position position) {
       //Log.v(TAG, ".ShotImpl(" + (new Gson()).toJson(position) + ")");
        _position = position;
       //Log.v(TAG, ".ShotImpl(" + (new Gson()).toJson(position) + ") return : costruttore");
    }
    /**
     * @name getPosition
     * @desc Rappresenta la posizione del colpo sparato.
     * @returns {Position}
     * @memberOf Client.Games.Battleship.Types.ShootImpl
     */
    @Override
    public Position getPosition() {
       //Log.v(TAG, ".getPosition()");
       //Log.v(TAG, ".getPosition() return : " + (new Gson()).toJson(_position));
        return _position;
    }
}
