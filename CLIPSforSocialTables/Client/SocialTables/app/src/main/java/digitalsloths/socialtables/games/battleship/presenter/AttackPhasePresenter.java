package digitalsloths.socialtables.games.battleship.presenter;

import digitalsloths.socialtables.games.battleship.types.Grid;

/**
 * name AttackPhasePresenter.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} legata alla fase di attacco
 * use Utilizzato da BattleshipManager per gestire la fase di attacco.
 */
public interface AttackPhasePresenter {

    void showResult();

    void updateField();

    /**
     * @name showShootResult
     * @desc Notifica al server le coordinate del colpo sparato dall'utente
     * @param {Grid} grid - Il terreno di gioco avversario con lo stato delle celle in relazione ai colpi sparati dalla squadra.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    public void showField(Grid grid);

    /**
     * @name insertNextShoot
     * @desc Presenta sul terreno di gioco i risultati dei colpi sparati dalla squadra dell'utente
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    public void insertNextShoot (int x, int y);

}
