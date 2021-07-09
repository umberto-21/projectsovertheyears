package digitalsloths.socialtables.games.battleship.model.communication;

import java.util.Observer;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import retrofit2.Callback;

/**
 * name BattleshipCommunication
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia per la gestione della comunicazione tra client e server per le funzionalità che riguardano il gioco Battleship;
 * use Utilizzata da BattleshipModel per la gestione della comunicazione tra client e server per le funzionalità che riguardano il gioco Battleship;
 */
public interface BattleshipCommunication {
    /**
     * @name isTeamExists
     * @desc richiede al server se il team associato al beacon, passato come parametro, è già
     * presente nel database;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    void isTeamExists(Beacon beacon, Callback<Boolean> callback);

    /**
     * @name isBattleRunning
     * @desc Ritorna true se c'è già una battaglia in corso, false altrimenti;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunication
     */

    void isBattleRunning(Beacon beacon, Callback<Boolean> callback);


    /**
     * @name startBattle
     * @desc Comunica al model che la partita è iniziata;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
//    public void startBattle(Callback<Boolean> callback);

    /**
     * @name joinTeam
     * @desc Ritorna true solo se l'utente è entrato nel team;
     * @param {Beacon} beacon - parametro di tipo Beacon;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    void joinTeam(Beacon beacon, Callback<Boolean> callback);

    /**
     * @name isStartSearchOpponent
     * @desc Ritorna true sse il team dell'utente è entrato nella fase di ricerca di un opponente;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    void addStartSearchingOpponentObserver(Runnable runnable) ;

    void removeStartSearchingOpponentObserver(); // TODO: 6/12/16

    /**
     * @name SumbscribeIsStopSearchingOpponent
     * @desc Ritorna true sse la ricerca dell'opponente è finita perché il capitano è uscito dalla ricerca di un opponente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    void addStopSearchingOpponentObserver(Runnable runnable);

    void removeStopSearchingOpponentObserver(); // TODO: 6/12/16

    /**
     * @name isCaptain
     * @desc Richiede al server se il profilo associato alla sessione è capitano di una squadra;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    void isCaptain(Callback<Boolean> callback);

    /**
     * @name addStartBattleCallBack
     * @desc Chiama il metodo run sull'oggetto passato quando il team dell'utente ha iniziato una partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    public void addStartBattleObserver(Runnable runnable);

    /**
     * @name stopSubscribeIsStartSearchingOpponent
     * @desc Blocca le callback legate all'evento di inizio della ricerca di un opponente del team dell'utente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    void removeStartBattleObserver();  // TODO: 6/12/16


    void addStartAttackPhaseObserver(Runnable runnable); // TODO: 6/12/16

    void removeStartAttackPhaseObserver(); // TODO: 6/12/16

    void addStartEnemyAttackPhaseObserver(Runnable runnable); // TODO: 6/12/16

    void removeStartEnemyAttackPhaseObserver(); // TODO: 6/12/16

    void addBattleEndObserver(Runnable runnable); // TODO: 6/13/16

    void removeBattleEndObserver(); // TODO: 6/13/16

    Boolean isBattleEnd();



}
