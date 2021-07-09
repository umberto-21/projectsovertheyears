package microservices.battleship.types;

import com.google.gson.Gson;
import microservices.battleship.types.TeamImpl;

/**
 * Created by Ivan Parise
 * Created on 01/06/16
 * Description: Interfaccia che gestisce le informazioni associate ad un tavolo;
 * Use: Utilizzato da BattleshipBusiness per controllare se ci sono team presenti nel tavolo;
 */
public class TableImpl implements Table {
    private Integer _id;
    private String _name;
    private static final String TAG = "Table";

    /**
     * @name TableImpl
     * @desc Costruttore della classe.
     * @param {Integer} Id - Rappresenta l'identificativo del tavolo
     * @param {String} name - Rappresenta il nome o numero del tavolo
     * @memberOf Server.Microservices.Battleship.Types.TableImpl
     */
    public TableImpl(Integer Id, String name){
        String methodSignature = ".TableImpl(" + (new Gson()).toJson(Id) + "," + (new Gson()).toJson(name) + ")";
     //play.Logger.info(TAG + methodSignature);
        _id = Id;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name TableImpl
     * @desc Costruttore della classe senza identificativo.
     * @param {String} name - Rappresenta il nome o il numero del tavolo.
     * @memberOf Server.Microservices.Battleship.Types.TableImpl
     */
    public TableImpl(String name){
        String methodSignature = ".TableImpl(" + "," + (new Gson()).toJson(name) + ")";
     //play.Logger.info(TAG + methodSignature);
        _id = null;
        _name = name;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }

    /**
     * @name getId
     * @desc Ritorna l'ID associato al tavolo;
     * @returns {Integer}
     * @memberOf Server.Microservices.Battleship.Types.TableImpl
     */
    @Override
    public Integer getId(){
     //play.Logger.info(TAG + ".getId()");
     //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }
    /**
     * @name getName
     * @desc Ritorna il nome associato al tavolo;
     * @returns {String}
     * @memberOf Server.Microservices.Battleship.Types.TableImpl
     */
    @Override
    public String getName(){
     //play.Logger.info(TAG + ".getName()");
     //play.Logger.info(TAG + ".getName() return: " + (new Gson()).toJson(_name));
        return _name;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TableImpl} t - Rappresenta il tavolo da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Table
     */
    @Override
    public boolean equals(Table t) {
        return (this.getId().equals(t.getId()))&&(this.getName().equals(t.getName()));
    }
}
