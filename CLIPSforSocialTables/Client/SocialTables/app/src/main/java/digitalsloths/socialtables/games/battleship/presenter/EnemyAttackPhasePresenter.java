package digitalsloths.socialtables.games.battleship.presenter;

import java.util.List;

import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;

/**
 * name EnemyAttackPhasePresenter.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{} per dare la possibilità all'utente di visualizzare il risultato degli attacchi nemici
 * use Utilizzato da BattleshipManager per gestire la fase di attacco del team avversario.
 */
public interface EnemyAttackPhasePresenter {

    /**
     * @name showAllayField
     * @desc Mostra sul terreno di gioco dell'utente i risultati dei colpi sparati dalla squadra avversaria
     * @param {Grid} grid - Il proprio terreno di gioco con lo stato delle celle in relazione ai colpi sparati dalla squadra avversaria.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.EnemyAttackPhasePresenter
     */
    public void showAllayField(Grid grid);

    void updateAllayField();

    /**
     * @name showTeamShips
     * @desc Mostra sul terreno tutte le navi posizionate dalla squadra all'inizio della partita
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.EnemyAttackPhasePresenter
     */
    public void showTeamShips (List<ShipImpl> ships);
}
