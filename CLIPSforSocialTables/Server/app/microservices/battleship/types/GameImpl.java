package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file GameImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti un gioco.
 *use Implementa i metodi offerti dall'interfaccia Game, utilizzati per la gestione delle informazioni riguardanti un gioco.
 */
public class GameImpl implements Game {
    private final Integer _id;
    private String _name;
    private static final String TAG = "Game";
    /**
     * @name GameImpl
     * @desc Costruttore della classe.
     * @param {Integer} id - Rappresenta l'identificativo del gioco.
     * @param {String} name - Rappresenta il nome del gioco.
     * @memberOf Server.Microservices.Battleship.Types.GameImpl
     */
    public GameImpl(Integer id, String name){
        String methodSignature = ".GameImpl(" + (new Gson()).toJson(id) + ", " + (new Gson()).toJson(name) +")";
     //play.Logger.info(TAG + methodSignature);
        _id = id;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name GameImpl
     * @desc Costruttore della classe.
     * @param {String} name - Rappresenta il nome del gioco.
     * @memberOf Server.Microservices.Battleship.Types.GameImpl
     */
    public GameImpl(String name){
        String methodSignature = ".GameImpl(" + (new Gson()).toJson(name) +")";
     //play.Logger.info(TAG + methodSignature);
        _id = null;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getName
     * @desc Ritorna il nome del gioco.
     * @returns {String}
     * @memberOf Server.Microservices.Battleship.Types.GameImpl
     */
    @Override
    public String getName() {
     //play.Logger.info(TAG + ".getName()");
     //play.Logger.info(TAG + ".getName() return: " + (new Gson()).toJson(_name));
        return _name;
    }
    /**
     * @name getId
     * @desc Ritorna l'id del gioco.
     * @returns {Integer}
     * @memberOf Server.Microservices.Battleship.Types.GameImpl
     */
    @Override
    public Integer getId() {
     //play.Logger.info(TAG + ".getId()");
     //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {Game} g - Rappresenta il gioco da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.GameImpl
     */
    @Override
    public boolean equals(Game g) {
        return (this.getId().equals(g.getId()))&&(this.getName().equals(g.getName()));
    }
}