package microservices.battleship.types;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *file AdminFieldImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che rappresenta un campo da gioco sul quale si possono aggiungere nuovi colpi.
 *use Implementa i metodi offerti dall'interfaccia AdminField, utilizzati per rappresentare un campo da gioco sul quale si possono aggiungere nuovi colpi.
 */
public class AdminFieldImpl extends TeamFieldImpl implements AdminField {
    private static final String TAG = "AdminField";
    /**
     * @name AdminFieldImpl
     * @desc Costruttore della classe.
     * @param {Grid} grid - Rappresenta la griglia del campo.
     * @param {int} h - Rappresenta l'altezza del campo da gioco.
     * @param {int} l - Rappresenta la lunghezza del campo da gioco.
     * @memberOf Server.Microservices.Battleship.Types.AdminFieldImpl
     */
    public AdminFieldImpl (int h, int l, List<Ship> s) {
        super (h,l,s);
        String methodSignature = ".TeamImpl(" + (new Gson()).toJson(h) + "," + (new Gson()).toJson(l) + "," + (new Gson()).toJson(s) + ")";
     //play.Logger.info(TAG + methodSignature);
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name AdminFieldImpl
     * @desc Costruttore della classe.
     * @param {Grid} grid - Rappresenta la griglia del campo.
     * @param {List } s - Rappresenta la lista di navi.
     * @memberOf Server.Microservices.Battleship.Types.AdminFieldImpl
     */
    public AdminFieldImpl (Grid grid, List<Ship> s) {
        super (grid, s);
        String methodSignature = ".TeamImpl(" + (new Gson()).toJson(grid) + "," + (new Gson()).toJson(s) + ")";
     //play.Logger.info(TAG + methodSignature);
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name Shot
     * @desc Aggiunge un colpo singolo, aggiornando di conseguenza la stato del campo.
     * @param {Shoot} shoot - Rappresenta il colpo sparato dall'avversario.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.AdminFieldImpl
     */
    @Override
    public void Shot(Shot shot) {
     //play.Logger.info(TAG + ".Shot(" + (new Gson()).toJson(shot) + ")");
        List<Ship> shipList =getShipList();
        Iterator<Ship> itr =shipList.iterator();
        boolean hit =false;
        while (itr.hasNext() && !hit) {
            Ship s =itr.next();
            hit =s.isHit(shot);
        }
        CellImpl.State state = CellImpl.State.MISS;
        if (hit) {
            state =CellImpl.State.HIT;
        }
        getShootGrid().setCell(shot.getPosition().getX(), shot.getPosition().getY(),state);
     //play.Logger.info(TAG + ".Shot(" + (new Gson()).toJson(shot) + ") return: void");
    }
    /**
     * @name ShootList
     * @desc Aggiunge una lista di colpi aggiornando la situazione del campo.
     * @param {List} shootList - Rappresenta la lista di colpi sparati dalla squadra avversaria.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.AdminFieldImpl
     */
    @Override
    public void ShootList(List<Shot> shootList) {
     //play.Logger.info(TAG + ".ShootList(" + (new Gson()).toJson(shootList) + ")");
        Iterator<Shot> itr =shootList.iterator();
        while (itr.hasNext()) Shot(itr.next());
     //play.Logger.info(TAG + ".ShootList(" + (new Gson()).toJson(shootList) + ") return: void");
    }
}
