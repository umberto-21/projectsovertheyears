package digitalsloths.socialtables.games.battleship.presenter;

import android.view.View;

import java.util.List;

import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;

/**
 * name SelectShipPositionPresenter.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{} per dare la possibilità all'utente di posizionare le sue navi nel campo di battaglia;
 * use Utilizzato da BattleshipManager per gestire il posizionamento delle navi da parte dell'utente;
 */
public interface SelectShipPositionPresenter {

    /**
     * @name placeShips
     * @desc Posiziona le navi del team sul terreno di gioco;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenter
     */
    public void placeShips (List<ShipImpl> teamShips);

    /**
     * @name reverseShip
     * @desc Cambia l'orintamento della nave da posizionare;
     * @param {View} view - Vista dell'oggetto cliccato a schermo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenter
     */
    public void reverseShip(View view);

}
