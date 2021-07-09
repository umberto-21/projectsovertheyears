package digitalsloths.socialtables.games.utility.presenter;

import digitalsloths.socialtables.games.utility.types.Game;

/**
 * name UtilityManager
 * author Pinton Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce quale presenter del gestore dei giochi deve essere in esecuzione.
 * use Utilizzata da BaseFunctionManager per gestire l'avvio e l'esecuzione dei giochi.
 */
public interface UtilityManager {
    /**
     * @name LoadGame
     * @desc costruisce il manager relativo al gioco indicato dal parametro game; chiama il metodo run sull'oggetto costruito.
     * @param {Client.Games.Utility.Types.Game} game - identifica il gioco da caricare
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.UtilityManager
     */
    public void loadGame(Game game);
    /**
     * @name run
     * @desc Chiama il metodo run sull'oggetto di tipo Client::Games::Utility::Presenter::GamesListPresenter;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.UtilityManager
     */
    public void run();
}
