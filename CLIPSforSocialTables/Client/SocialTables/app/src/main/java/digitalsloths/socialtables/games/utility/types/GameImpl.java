package digitalsloths.socialtables.games.utility.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file GamaImpl.java
 *author Saccon Daniele
 *date 03/05/2016
 *brief Implementa l’interfaccia che gestisce l’identicazione di un particolare gioco.
 *use Utilizzato da UtilityManager per avviare un particolare gioco.
 */
public class GameImpl implements Game{
    private final Integer _id;
    private String _name;
    private static final String TAG = "Game";

    /**
     * @name GameImpl
     * @desc Costruttore della classe.
     * @param {int} id - Rappresenta l'id del gioco.
     * @param {String} nome - Rappresenta il nome del gioco
     * @memberOf Client.Games.Utility.Types.GameImpl
     */
    public GameImpl(Integer id, String name){
       //Log.v(TAG, ".GameImpl(" + (new Gson()).toJson(id) + ", "+ (new Gson()).toJson(name) + ")");
        _id = id;
        _name = name;
       //Log.v(TAG, ".GameImpl(" + (new Gson()).toJson(id) + ", "+ (new Gson()).toJson(name) + ") return : costruttore");
    }
    /**
     * @name GameImpl
     * @desc Costruttore della classe.
     * @param {String} nome - Rappresenta il nome del gioco
     * @memberOf Client.Games.Utility.Types.GameImpl
     */
    public GameImpl(String name){
       //Log.v(TAG, ".GameImpl(" + (new Gson()).toJson(name) + ")");
        _name = name;
        _id = null;
       //Log.v(TAG, ".GameImpl(" + (new Gson()).toJson(name) + ") return : costruttore");
    }
    /**
     * @name getId
     * @desc Ritorna l'id del gioco.
     * @returns {int}
     * @memberOf Client.Games.Utility.Types.GameImpl
     */
    @Override
    public Integer getId() {
       //Log.v(TAG, ".getId()");
       //Log.v(TAG, ".getId() return : " + (new Gson()).toJson(_id));
        return _id;
    }
    /**
     * @name getNome
     * @desc Ritorna il nome del gioco.
     * @returns {String}
     * @memberOf Client.Games.Utility.Types.GameImpl
     */
    @Override
    public String getName() {
       //Log.v(TAG, ".getName()");
       //Log.v(TAG, ".getName() return : " + (new Gson()).toJson(_name));
        return _name;
    }
    /**
     * @name Equals
     * @desc Ritorna true se i 2 giochi sono uguali.
     * @returns {Boolean}
     * @memberOf Client.Games.Utility.Types.GameImpl
     */
    public boolean equals(Game game){
        return this._id == game.getId() && this._name == game.getName();
    }
}
