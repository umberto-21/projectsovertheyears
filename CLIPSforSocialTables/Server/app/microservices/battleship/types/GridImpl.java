package microservices.battleship.types;

import com.google.gson.Gson;
import scala.Array;
import sun.java2d.StateTrackable;

import java.util.Arrays;

/**
 *file GridImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti il campo di battaglia di una squadra.
 *use Implementa i metodi offerti dall'interfaccia Grid, utilizzati per gestire le informazioni riguardanti il campo di battaglia di una squadra.
 */
public class GridImpl implements Grid {
    private Cell [][] _cells;
    private int _heigth;
    private int _length;
    private static final String TAG = "Grid";

    /**
     * @name GridImpl
     * @desc Costruttore della classe.
     * @param {int} heigth - Rappresenta l'altezza della griglia.
     * @param {int} length - Rappresenta la lunghezza della griglia.
     * @memberOf Server.Microservices.Battleship.Types.GridImpl
     */
    public GridImpl (int h, int l) {
        String methodSignature = ".GridImpl(" + (new Gson()).toJson(h) + ", " + (new Gson()).toJson(l) +")";
     //play.Logger.info(TAG + methodSignature);
        _heigth =h;
        _length =l;
        _cells =new Cell [_heigth] [_length];
        for(int y=0; y<_heigth; y++){
            for(int x=0; x<_length; x++){
                _cells[y][x] = new CellImpl();
            }
        }
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getCell
     * @desc Ritorna una cella del campo del gioco Battleship.
     * @param {int} x - Rappresenta l'ascissa della cella del campo da gioco.
     * @param {int} y - Rappresenta l'ascissa della cella del campo da gioco.
     * @returns {Cell}
     * @memberOf Server.Microservices.Battleship.Types.GridImpl
     */
    @Override
    public Cell getCell(int x, int y) {
     //play.Logger.info(TAG + ".getCell()");
     //play.Logger.info(TAG + ".getCell() return: " + (new Gson()).toJson(_cells [x] [y]));
        return _cells [y] [x];
    }
    /**
     * @name getHeight
     * @desc Rappresenta la larghezza del campo da gioco.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.GridImpl
     */
    @Override
    public int getHeight() {
     //play.Logger.info(TAG + ".getHeight()");
     //play.Logger.info(TAG + ".getHeight() return: " + (new Gson()).toJson(_heigth));
        return _heigth;
    }
    /**
     * @name getLength
     * @desc Ritorna la lunghezza del campo da gioco.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Grid
     */
    @Override
    public int getLength() {
     //play.Logger.info(TAG + ".getLength()");
     //play.Logger.info(TAG + ".getLength() return: " + (new Gson()).toJson(_length));
        return _length;
    }
    /**
     * @name setCell
     * @desc Setta lo stato della cella.
     * @param {int} x - Rappresenta l'ascissa della cella.
     * @param {int} y - Rappresenta l'ordinata della cella.
     * @param {State} state - Rappresenta lo stato della cella.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.GridImpl
     */
    @Override
    public void setCell(int x, int y, CellImpl.State state) {
     //play.Logger.info(TAG + ".setCell(" + (new Gson()).toJson(x) + ","+ (new Gson()).toJson(y) +","+ state +")");
        _cells[y][x].setState(state);
     //play.Logger.info(TAG + ".setCell( " + (new Gson()).toJson(x) +  ", "+ (new Gson()).toJson(y) + ", "+ state + ") return: void");
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {GridImpl} g - Rappresenta la griglia da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.GridImpl
     */
    @Override
    public boolean equals(Grid g) {
        if (g.getHeight()!=this.getHeight()){
            return false;
        }else if(g.getLength()!=this.getLength()){
            return false;
        }else {
            boolean esito = true;
            for (int i = 0; i < _heigth && esito; i++) {
                for (int j = 0; j < _length && esito; j++) {
                    if (!(this.getCell(i,j).equals(g.getCell(i,j))))
                        esito=false;
                }
            }
            return esito;
        }
    }
}
