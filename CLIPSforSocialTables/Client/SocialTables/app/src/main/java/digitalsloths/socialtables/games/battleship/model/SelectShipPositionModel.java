package digitalsloths.socialtables.games.battleship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import digitalsloths.socialtables.games.battleship.types.ShipType;
import digitalsloths.socialtables.games.battleship.types.ShipTypeImpl;
import retrofit2.Callback;

/**
 * name SelectShipPositionModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic legata al posizionamento della nave;
 * use Usata da SelectShipPositionPresenter per gestire la business//Logic legata al posizionamento della nave;
 */
public interface SelectShipPositionModel {


    /**
     * @name getShipsToPositioning
     * @desc Ritorna la lista di navi da posizionare; chiama il metodo getShipsToPositioning () : List sull'oggetto di tipo SelectShipPositionCommunication
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    public void getShipsToPositioning (Callback<ArrayList<ShipTypeImpl>> callback);


    /**
     * @name getTeamShip
     * @desc Ritorna la lista delle navi già posizionate dal team.
     * @returns {List}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    public void getTeamShip ( Callback<ArrayList<ShipImpl>> callback );

    /**
     * @name setShipPosition
     * @desc chiama il metodo setShipPosition (ship : Ship) : boolean sull'oggetto di tipo SelectShipPositionCommunication;
     * @param {Client.Games.Battleship.Types.Ship} ship - rappresenta la nave da posizionare;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModel
     */
    public void setShipPosition (Ship ship, Callback<Boolean> callback);

    /**
     * @name addTeamShipsPositionObserver
     * @desc Aggiunge un ascoltatore per il posizionamento delle navi;
     * @param {Runnable} runnable - Oggetto che ha cambiato il suo stato;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.SelectShipPositionModelImpl
     */
    public void addTeamShipsPositionObserver (Callback<ArrayList<ShipImpl>> callback);

    void removeTeamShipsPositionObserver();
}
