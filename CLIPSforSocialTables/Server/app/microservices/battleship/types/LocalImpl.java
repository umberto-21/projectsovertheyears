package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file LocalImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti un locale specifico.
 *use Implementa i metodi offerti dall'interfaccia Profile, utilizzati per per gestire le informazioni riguardanti un locale specifico.
 */
public class LocalImpl implements Local {
    private final Integer _id;
    private String _name;
    private static final String TAG = "Local";
    /**
     * @name LocalImlp
     * @desc Costruttore della classe.
     * @param {String} name - Rappresenta il nome del locale.
     * @param {int} id - Rappresenta l'identificativo del locale.
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    public LocalImpl(int id, String name){
        String methodSignature = ".TeamImpl(" + (new Gson()).toJson(id) + ", " + (new Gson()).toJson(name) +")";
     //play.Logger.info(TAG + methodSignature);
        _id = id;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name LocalImlp
     * @desc Costruttore della classe.
     * @param {String} name - Rappresenta il nome del locale.
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    public LocalImpl(String name){
        String methodSignature = ".LocalImpl("+ (new Gson()).toJson(name) +")";
     //play.Logger.info(TAG + methodSignature);
        _id = null;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getId
     * @desc Ritorna l'id del locale.
     * @returns {int}
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    @Override
    public int getId() {
     //play.Logger.info(TAG + ".getId()");
     //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }
    /**
     * @name getNome
     * @desc Ritorna il nome del locale.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    @Override
    public String getName() {
     //play.Logger.info(TAG + ".getName()");
     //play.Logger.info(TAG + ".getName() return: " + (new Gson()).toJson(_name));
        return _name;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {LocalImpl} l - Rappresenta il local da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.LocalImpl
     */
    @Override
    public boolean equals(Local l) {
        //return (this.getId()==l.getId())&&(this.getName()==l.getName());
        return (this.getId()==l.getId())&&(this.getName().equals(l.getName()));
    }
}
