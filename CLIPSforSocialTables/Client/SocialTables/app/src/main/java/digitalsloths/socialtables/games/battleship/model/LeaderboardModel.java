package digitalsloths.socialtables.games.battleship.model;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name LeaderboardModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la bussines//Logic\g{} legata alla gestione della classifica;
 * use Utilizzata da LeaderboardPresenter per gestire la bussines//Logic\g{} legata alla gestione della classifica;
 */
public interface LeaderboardModel {

    /**
     * @name getLeaderBoard
     * @desc Ritorna una lista di Score che rappresenta la classifica del gioco Battleship nel locale dove si trova l'utente; chiama il metodo getLeaderBoard () : List sull'oggetto di tipo LeaderboardCommunication
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.LeaderboardModel
     */
    public List<Score> getLeaderBoard ();
}
