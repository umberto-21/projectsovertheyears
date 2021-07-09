package digitalsloths.socialtables.games.utility.model;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;

/**
 * name GamesListModelImpl.java
 * author Casotto Federico
 * date 15/05/2016
 * brief Implementa l'interfaccia che gestisce la bussines//Logic legata alla gestione dei giochi avviabili
 * use Utilizzata da GamesListPresenter per gestire la bussines//Logic legata alla gestione dei giochi avviabili.
 */
public interface GamesListModel {
    public List<Game> getGameList();
    public void addGame(Game game);
}
