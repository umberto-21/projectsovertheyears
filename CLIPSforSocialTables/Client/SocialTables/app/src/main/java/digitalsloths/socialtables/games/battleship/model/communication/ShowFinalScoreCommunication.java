package digitalsloths.socialtables.games.battleship.model.communication;

import java.util.List;

import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.ScoreImpl;
import retrofit2.Callback;

/**
 * name AttackPhaseCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che gestisce le funzionalità legate alla visualizzazione del punteggi finali della partita;
 * use Utilizzata da ShowFinalScoreModelImpl per visualizzazione del punteggi finali della partita;
 */
public interface ShowFinalScoreCommunication {

    /**
     * @name getTeamScore
     * @desc Restituisce il punteggio finale totalizzato dalla squadra dell'utente;
     * @returns {Score}
     * @memberOf Client.Games.Battleship.Model.Communication.ShowFinalScoreCommunication
     */
    void getEnemyTeamScore (Callback<List<Score>> callback);
}
