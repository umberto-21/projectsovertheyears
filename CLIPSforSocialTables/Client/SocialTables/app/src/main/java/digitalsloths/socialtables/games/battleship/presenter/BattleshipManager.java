package digitalsloths.socialtables.games.battleship.presenter;

import android.app.Activity;

import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.Team;

/**
 * name BattleshipManager.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfacce che gestisce quale presenter di Battleship deve entrare in esecuzione;
 * use Utilizzata da UtilityManager per gestire il gioco di battaglia navale.
 */
public interface BattleshipManager {

    Activity getStartGameActivity();

    void setStartGameActivity(Activity startGameActivity);

    Activity getJoinTeamActivity();

    void setJoinTeamActivity(Activity joinTeamActivity);

    Activity getTeamManagementActivity();

    void setTeamManagementActivity(Activity teamManagementActivity);

    void setEnemyAttackPhasePresenter(EnemyAttackPhasePresenter enemyAttackPhasePresenter);

    void setAttackPhasePresenter(AttackPhasePresenter attackPhasePresenter);

    void setShowFinalScoreActivity(Activity showFinalScoreActivity);

    void setWaitingOpponentActivity(Activity waitingOpponentActivity);

    /**
     * @name start
     * @desc Mostra la prima schermata all'utente dove può scegliere di visualizzare la classifica o iniziare a giocare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void start();

    /**
     * @name startPlay
     * @desc Se non esiste una squadra associata al beacon, visualizza la schermata per crearne una, altrimenti inserisce l'utente nella squadra esistente e visualizza i componenti della squadra;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void startPlay();

    /**
     * @name showLeaderboard
     * @desc Visualizza la classifica di battaglia navale associata al locale dell'utente;
     * @returns {void }
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void showLeaderboard();

    /**
     * @name showTeamManagement
     * @desc Controlla che l'utente sia capitano in caso positivo visualizza la gestione del team con possibilità di eliminare utente dal team, altrimenti visualizza un messaggio di errore e successivamente visualizza la lista dei membri del team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void showTeamManagement();

    /**
     * @name changeToCaptainTeamManagment
     * @desc Chiude la vista del team da marinaio ed apre quella da capitano;
     * @param {Activity} activity - Rappresenta l'activity da chiudere in caso l'utente sia capitano;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void changeToCaptainTeamManagment();

    /**
     * @name kickOut
     * @desc Visualizza la schermata iniziale di battleship quando l'utente viene espulso dalla squadra, visualizza un messaggio informativo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void kickOut();

    /**
     * @name leaveTeam
     * @desc Visualizza la schermata iniziale di battleship quando un utente lascia il team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void leaveTeam();

    /**
     * @name startPositioning
     * @desc Visualizza la schermata per il posizionamento delle navi;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void startPositioning();

    /**
     * @name startSearching
     * @desc Visualizza la schermata di ricerca opponente in corso;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void startSearching();

    /**
     * @name exitSearchingOpponent
     * @desc Chiama l'activity che mi permette di gestire la squadra e a seconda della tipologia del chiamante se è capitano o marinaio;
     * @param {Activity} searchingView - Attività che si occupa di cercare l'avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void exitSearchingOpponent();

    /**
     * @name startAttackPhase
     * @desc Visualizza la schermata di attacco dove l'utente potrà inserire le coordinate dei colpi da sparare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void startAttackPhase();

    /**
     * @name startEnemyAttackPhase
     * @desc Visualizza il campo del team finché il team avversario attacca;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void startEnemyAttackPhase();

    /**
     * @name showFinalScore
     * @desc Visualizza i punteggi finali della partita.
     * @param {Activity} finishView - Activity che terminna la partita da chiudere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    public void showFinalScore();

    void isCaptain(final Runnable runnable);

    void setEnemyAttackPhaseActivity(Activity enemyAttackPhaseActivity);

    void setAttackPhaseActivity(Activity attackPhaseActivity);

    void setCreateTeam(Activity createTeam);

    void setShipPositioningActiviy(Activity shipPositioning);

    void exit();
}
