package digitalsloths.socialtables.games.utility.presenter;

import android.content.Context;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;

/**
 * name GamesListPresenter.java
 * author Pinton Federico
 * date 15/05/2016
 * brief Interfaccia che gestisce la application//Logic e la presentation//Logic per fornire l'elenco dei giochi che l'applicazione mette a disposizione.
 * use Utilizzata da UtilityManager per gestire la selezione e l'avvio di un gioco.
 */
public interface GamesListPresenter {

    /**
     * @name showGamesList
     * @desc Visualizza una lista di giochi e permette di selezionarne uno;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    public void showGamesList();

    /**
     * @name loadGame
     * @desc richiama il metodo loadGame dall'utility manager;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    public void loadGame(Game game);

    /**
     * @name getContext
     * @desc Contesto dell'Activity di presentazione dei giochi
     * @returns {Context}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    public Context getContext();

    /**
     * @name getGamesList
     * @desc Lista di giochi disponibili
     * @returns {List<Game>}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    public List<Game> getGamesList();

}
