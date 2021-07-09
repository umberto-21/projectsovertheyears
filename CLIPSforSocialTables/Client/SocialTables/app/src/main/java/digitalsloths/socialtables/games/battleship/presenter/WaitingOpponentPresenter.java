package digitalsloths.socialtables.games.battleship.presenter;

import android.view.View;

/**
 * Created by umberto on 5/5/16.
 */
public interface WaitingOpponentPresenter {

    /**
     * @name exitSearchingOpponent
     * @desc chiama Client.Games.Battleship.Presenter.BattleshipManager.exitSearchingOpponent ();
     * @param {View} view - vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.WaitingOpponentPresenter
     */
    public void exitSearchingOpponent (View view);
}
