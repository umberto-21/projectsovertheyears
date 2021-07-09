package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file FieldImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti il campo di battaglia del gioco Battleship.
 *use Implementa i metodi offerti dall'interfaccia Field, utilizzati per gestire le informazioni riguardanti il campo di battaglia del gioco Battleship.
 */
public class FieldImpl implements Field {
    private Grid _shootGrid;
    private static final String TAG = "Field";

    /**
     * @name FieldImpl
     * @desc Costruttore della classe dai lunghezza e altezza.
     * @param {int} heigth - Rappresenta l'altezza del campo da gioco.
     * @param {int} length - Rappresenta la lunghezza del campo da gioco.
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    public FieldImpl (int h, int l) {
     //play.Logger.info(TAG + ".FieldImpl(" + (new Gson()).toJson(h) + (new Gson()).toJson(l) +")");
        _shootGrid =new GridImpl (h,l);
     //play.Logger.info(TAG + ".FieldImpl(" + (new Gson()).toJson(h) + (new Gson()).toJson(l) +") return: costruttore");
    }
    /**
     * @name FieldImpl
     * @desc Costruttore della classe data una griglia.
     * @param {Grid} grid - Rappresenta l'altezza del campo da gioco.
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    public FieldImpl (Grid g) {
     //play.Logger.info(TAG + ".FieldImpl(" + (new Gson()).toJson(g) +")");
        _shootGrid =g;
     //play.Logger.info(TAG + ".FieldImpl(" + (new Gson()).toJson(g) +") return: costruttore");
    }
    /**
     * @name set_shootGrid
     * @desc Setta la griglia del campo.
     * @param {Grid} shootGrid - Rappresenta la griglia del campo.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    @Override
    public void set_shootGrid(Grid shootGrid) {
     //play.Logger.info(TAG + ".set_shootGrid(" + (new Gson()).toJson(shootGrid) + ")");
        _shootGrid = shootGrid;
     //play.Logger.info(TAG + ".set_shootGrid(" + (new Gson()).toJson(shootGrid) + ") return: void");
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {FieldImpl} f - Rappresenta il Field da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    @Override
    public boolean equals(Field f) {
        return this.getShootGrid()==f.getShootGrid();
    }

    /**
     * @name getShootGrid
     * @desc Ritorna la griglia del campo con i colpi sparati.
     * @returns {Grid}
     * @memberOf Server.Microservices.Battleship.Types.FieldImpl
     */
    @Override
    public Grid getShootGrid() {
     //play.Logger.info(TAG + ".getShootGrid()");
     //play.Logger.info(TAG + ".getShootGrid() return: " + (new Gson()).toJson(_shootGrid));
        return _shootGrid;
    }
}
