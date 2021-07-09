package digitalsloths.socialtables.games.battleship.model.communication;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import digitalsloths.socialtables.games.battleship.types.ShipType;
import digitalsloths.socialtables.games.battleship.types.ShipTypeImpl;
import retrofit2.Callback;

/**
 * file: SelectShipPositionCommunication.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief: Interfaccia che gestisce la comunicazione con il server per la funzionalità del posizionamento navi;
 * use: Utilizzata da SelectSchipPositionPresenter per gestire la comunicazione con il server per la funzionalità del posizionamento navi;
 */
public interface SelectShipPositionCommunication {
    /**
     * @name getShipsToPositioning
     * @desc Richiede al server la lista di navi da posizionare;
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    void getShipsToPositioning(Callback<ArrayList<ShipTypeImpl>> callback);

    /**
     * @name getTeamShips
     * @desc Ritorna la lista delle navi del team posizionate;
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPos
     * itionCommunication
     */
    void getTeamShips(Callback<ArrayList<ShipImpl>> callback);

    /**
     * @name setShipPosition
     * @desc manda al server la posizione della nave
     * @param {Client.Games.Battleship.Types.Ship} ship - la nave da posizionare
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    void setShipPosition(Ship ship, Callback<Boolean> callback);

    /**
     * @name update
     * @desc Notifica a SelectShipPosition che c'è stato un aggiornamento nelle variabili del server;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    void addTeamShipsPositionObserver(Callback<ArrayList<ShipImpl>> callback); // TODO: 6/12/16

    void removeTeamShipsPositionObserver(); // TODO: 6/12/16
}
