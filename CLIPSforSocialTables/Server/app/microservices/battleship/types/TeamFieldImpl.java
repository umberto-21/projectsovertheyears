package microservices.battleship.types;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

/**
 *file TeamFieldImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che rappresenta il campo avversario del team di cui si fa parte.
 *use Implementa i metodi offerti dall'interfaccia TeamField, utilizzati per rappresentare il campo avversario del team di cui si fa parte.
 */
public class TeamFieldImpl extends FieldImpl implements TeamField {
    private List<Ship> _shipList;
    private static final String TAG = "TeamField";

    /**
     * @name TeamFieldImpl
     * @desc Costruttore della classe.
     * @param {List} shipList - Rappresenta la lista delle navi.
     * @param {int} h - Rappresenta l'altezza del campo da gioco.
     * @param {int} l - Rappresenta la lunghezza del campo da gioco.
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    public TeamFieldImpl (int h, int l, List<Ship> s) {
        super (h,l);
        String methodSignature = ".TeamFieldImpl(" + (new Gson()).toJson(h) + ", " + (new Gson()).toJson(l) + ", " + (new Gson()).toJson(s) +")";
     //play.Logger.info(TAG + methodSignature);
        _shipList =s;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name TeamFieldImpl
     * @desc Costruttore della classe.
     * @param {List} shipList - Rappresenta la lista delle navi.
     * @param {Grid} grid - Rappresenta la griglia della squadra.
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    public TeamFieldImpl (Grid g, List<Ship> s) {
        super (g);
        String methodSignature = ".TeamFieldImpl(" + (new Gson()).toJson(g) + ", " + (new Gson()).toJson(s) +")";
        _shipList =s;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name set_shipList
     * @desc Setta la lista di navi.
     * @param {List } shipList - Rappresenta la lista di navi della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    @Override
    public void set_shipList(List<Ship> shipList) {
     //play.Logger.info(TAG + ".set_shipList(" + (new Gson()).toJson(shipList) + ")");
        _shipList = shipList;
     //play.Logger.info(TAG + ".set_shipList(" + (new Gson()).toJson(shipList) + ") return: void");
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TeamFieldImpl} t - Rappresenta il teamField da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    @Override
    public boolean equals(TeamField t) {
        boolean ok = true;
        Iterator<Ship> iterator_1 = t.getShipList().iterator();
        Iterator<Ship> iterator = this._shipList.iterator();
        for (; iterator.hasNext(); iterator.next()) {
            if (iterator != iterator_1)
                ok = false;
        }
        return ok;
    }

    /**
     * @name getShipList
     * @desc Ritorna la lista delle navi presenti nel campo.
     * @returns {List}
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    @Override
    public List<Ship> getShipList() {
     //play.Logger.info(TAG + ".getShipList()");
     //play.Logger.info(TAG + ".getShipList() return: " + (new Gson()).toJson(_shipList));
        return _shipList;
    }
    /**
     * @name isBattleLost
     * @desc Ritorna true se e solo se tutte le navi sono state affondate.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamFieldImpl
     */
    @Override
    public boolean isBattleLost() {
     //play.Logger.info(TAG + ".isBattleLost()");
        boolean lost =true;
        Iterator<Ship> itr =_shipList.iterator();
        while (itr.hasNext() && lost) {
            Ship s =itr.next();
            lost =s.isSink(getShootGrid());
        }
     //play.Logger.info(TAG + ".isBattleLost() return: " + (new Gson()).toJson(lost));
        return lost;
    }
}
