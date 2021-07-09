package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import digitalsloths.socialtables.games.battleship.model.communication.SelectShipPositionCommunication;
import digitalsloths.socialtables.games.battleship.types.PositionImpl;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import digitalsloths.socialtables.games.battleship.types.ShipType;
import digitalsloths.socialtables.games.battleship.types.ShipTypeImpl;
import retrofit2.Callback;

/**
 * name SelectShipPositionModelImpl.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic legata al posizionamento della nave;
 * use Usata da SelectShipPositionPresenter per gestire la business//Logic legata al posizionamento della nave;
 */
public class SelectShipPositionModelImpl extends Observable implements SelectShipPositionModel {

    private static final String TAG = "SelShiPosModelImpl";
    private SelectShipPositionCommunication _selectShipPositionCommunication;

    public SelectShipPositionModelImpl(SelectShipPositionCommunication selectShipPositionCommunication) {
       //Log.v(TAG, ".SelectShipPositionModelImpl()");
        _selectShipPositionCommunication = selectShipPositionCommunication;
       //Log.v(TAG, ".SelectShipPositionModelImpl()");
    }

    /**
     * @name getShipsToPositioning
     * @desc Ritorna la lista di navi da posizionare; chiama il metodo getShipsToPositioning () : List sull'oggetto di tipo SelectShipPositionCommunication
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    @Override
    public void getShipsToPositioning(Callback<ArrayList<ShipTypeImpl>> callback) {
       //Log.v(TAG, ".getShipsToPositioning()");
        _selectShipPositionCommunication.getShipsToPositioning (callback);
       //Log.v(TAG, ".getShipsToPositioning() return: void " );
    }

    /**
     * @name getTeamShip
     * @desc Ritorna la lista delle navi già posizionate dal team.
     * @returns {List}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    @Override
    public void getTeamShip(Callback<ArrayList<ShipImpl>> callback) {
       //Log.v(TAG, ".getTeamShip()");
        _selectShipPositionCommunication.getTeamShips(callback);
       //Log.v(TAG, ".getTeamShip() return: void" );
    }

    /**
     * @name setShipPosition
     * @desc chiama il metodo setShipPosition (ship : Ship) : boolean sull'oggetto di tipo SelectShipPositionCommunication;
     * @param {Client.Games.Battleship.Types.Ship} ship - rappresenta la nave da posizionare;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    @Override
    public void setShipPosition(Ship ship, Callback<Boolean> callback) {
       //Log.v(TAG, ".setShipPosition(" + (new Gson()).toJson (ship) + ")");
        _selectShipPositionCommunication.setShipPosition (ship,callback);
       //Log.v(TAG, ".getShipPosition(" + (new Gson()).toJson (ship) + ") return: void");
    }

    /**
     * @name addTeamShipsPositionObserver
     * @desc Aggiunge un ascoltatore per il posizionamento delle navi;
     * @param {Runnable} runnable - Oggetto che ha cambiato il suo stato;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModelImpl
     */
    @Override
    public void addTeamShipsPositionObserver(Callback<ArrayList<ShipImpl>> callback) {
       //Log.v(TAG, ".addTeamShipsPositionObserver()");
        _selectShipPositionCommunication.addTeamShipsPositionObserver(callback);
       //Log.v(TAG, ".addTeamShipsPositionObserver() return void ");
    }

    @Override
    public void removeTeamShipsPositionObserver(){
        _selectShipPositionCommunication.removeTeamShipsPositionObserver();
    }


}
