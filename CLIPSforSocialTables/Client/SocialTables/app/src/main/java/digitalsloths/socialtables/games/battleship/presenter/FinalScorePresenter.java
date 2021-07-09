package digitalsloths.socialtables.games.battleship.presenter;

import android.view.View;

import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name FinalScorePresenter.java
 * author Andria Umberto
 * date 10/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{}
 * per dare la possibilità all'utente di visualizzare il punteggio finale;
 * use Utilizzato da BattleshipManager per gestire la visualizzazione del punteggio finale.
 */
public interface FinalScorePresenter {

    /**
     * @name back
     * @desc chiama il metodo finishBattle sul BattleshipManager
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.FinalScorePresenter
     */
    public void back (View view);

    /**
     * @name showFinalScore
     * @desc chiama il metodo showFinalScore sull'oggetto Client.Games.Battleship.View.FinalScoreView
     * @param {Client.Games.Battleship.Types.Score} teamScore - rappresenta il punteggio della squadra
     * @param {Client.Games.Battleship.Types.Score} enemyScore - rappresenta il punteggio della squadra avversaria
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.FinalScorePresenter
     */
    public void showFinalScore (Score teamScore, Score enemyScore);
}
