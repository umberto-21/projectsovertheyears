package digitalsloths.socialtables.games.utility.presenter;

import android.content.Context;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name LeaderboardPresenter.java
 * author Parise Ivan
 * date 08/05/2016
 * brief IInterfaccia che gestisce Model:Leaderboard e View::Leaderboard implementando la application//Logic\g{}
 * e la presentation//Logic\g{} per dare la possibilità all'utente di aprire la classifica del gioco desiderato.
 * use Utilizzata da UtilityManager per gestire la visualizzazione della classifica.
 */
public interface LeaderboardPresenter {

    /**
     * @name loadGamesListMenu
     * @desc Metodo che carica la lista dei giochi disponibili;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    public void loadGamesListMenu();

    /**
     * @name showLeaderBoard
     * @desc Metodo che visualizza la classifica;
     * @param {Game} game - Gioco di cui si vuole visualizzare la classifica;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    public void showLeaderBoard(Game game);

    /**
     * @name getContext
     * @desc Fornisce il contesto dell'Activity di visualizzazione classifica.
     * @returns {Context}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    public Context getContext();

    /**
     * @name getScoresList
     * @desc Fornisce la lista di punteggi migliori disponibili.
     * @returns {List<Score>}
     * @memberOf Client.Games.Battleship.Presenter.LeaderboardPresenter
     */
    public List<Score> getScoresList();

}
