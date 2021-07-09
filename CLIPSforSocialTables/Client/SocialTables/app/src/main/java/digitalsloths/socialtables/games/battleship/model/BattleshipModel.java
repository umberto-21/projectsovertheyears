package digitalsloths.socialtables.games.battleship.model;

import java.util.List;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.types.Profile;
import retrofit2.Callback;

/**
 * name BattleshipModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la bussines//Logic\g{} legata al gioco di Battleship;
 * use Viene utilizzata dai presenter per gestire la bussines//Logic\g{} legata al gioco di Battleship;
 */
public interface BattleshipModel {

    /**
     * @name addBattleEndObserver
     * @desc Aggiunge ascoltatore conclusione partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addBattleEndObserver (Runnable runnable);

    /**
     * @name isBattleRunning
     * @desc Ritorna true se c'è una battaglia in corso, false altrimenti.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void isBattleRunning (Callback<Boolean> callback);

    /**
     * @name addStartBattleObserver
     * @desc Aggiunge ascoltatore inizio partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addStartBattleObserver (Runnable runnable);

    /**
     * @name isCaptain
     * @desc ritorna true sse il parametro profile è il capitano della squadra;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void isCaptain (Callback<Boolean> callback);

    /**
     * @name addStartAttackPhaseObserver
     * @desc Aggiunge ascoltatore inzio fase di attacco utente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addStartAttackPhaseObserver (Runnable runnable);

    /**
     * @name addStartEnemyAttackPhaseObserver
     * @desc Aggiunge ascoltatore fase di attaco avversaria;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addStartEnemyAttackPhaseObserver (Runnable runnable);

    /**
     * @name addStartSearchingOpponentObserver
     * @desc Aggiunge ascoltatore inzio ricerca avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addStartSearchingOpponentObserver (Runnable runnable);

    /**
     * @name addStopSearchingOpponentObserver
     * @desc Aggiunge ascoltatore fine ricerca avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void addStopSearchingOpponentObserver (Runnable runnable);

    /**
     * @name isTeamExists
     * @desc Ritorna true sse il team associato al beacon è già stato creato.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void isTeamExists (Callback<Boolean> callback);

    /**
     * @name joinTeam
     * @desc Ritorna true se l'inserimento dell'utente è andato a buon fine.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    public void joinTeam (Callback<Boolean> callback);

    void removeStartSearchingOpponentObserver();

    void removeStopSearchingOpponentObserver();

    void removeBattleEndObserver();

    void removeStartBattleObserver();

    void removeStartAttackPhaseObserver();

    void removeStartEnemyAttackPhaseObserver();

    Boolean isBattleEnd();
}
