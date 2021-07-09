package digitalsloths.socialtables.games.battleship.presenter;

import android.view.View;

/**
 * name StartGamePresenter.java
 * author Pinton Federico
 * date 13/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} legata alla fase iniziale dove si può
 * visualizzare la classifica del locale o iniziare a giocare;
 * use     Utilizzato da BattleshipManager per gestire la fase di scelta se iniziare il gioco o visualizzare la classifica del locale;
 */
public interface StartGamePresenter {

    /**
     * @name startGame
     * @desc Comunica al manager di iniziare il gioco.
     * @param {View} view - Rappresenta la view che chiama il metodo.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.StartGamePresenter
     */
    public void startGame(View view);
    /**
     * @name showLeaderboard
     * @desc Comunica al manager di visualizzare la leaderboard.
     * @param {View} view - Rappresenta la view che chiama il metodo.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.StartGamePresenter
     */
    public void showLeaderboard(View view);

}
