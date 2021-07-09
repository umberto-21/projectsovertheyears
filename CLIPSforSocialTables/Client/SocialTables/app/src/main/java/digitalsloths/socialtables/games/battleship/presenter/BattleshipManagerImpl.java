package digitalsloths.socialtables.games.battleship.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.locks.ReentrantLock;

import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModel;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModelImpl;
import digitalsloths.socialtables.games.battleship.model.ProfileModel;
import digitalsloths.socialtables.games.battleship.model.ProfileModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.ProfileCommunicationImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * name BattleshipManagerImpl.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfacce che gestisce quale presenter di Battleship deve entrare in esecuzione;
 * use Utilizzata da UtilityManager per gestire il gioco di battaglia navale.
 */
public class BattleshipManagerImpl extends Activity implements BattleshipManager{

    private static final String TAG = "BattleshipManager";
    //riferimento singleton
    private static BattleshipManagerImpl BATTLESHIP_MANAGER_ISTANCE;

    private BattleshipModel _battleshipModel;
    private JoinTeamModel jointeamModel = new JoinTeamModelImpl(new JoinTeamCommunicationImpl());
    private ProfileModel profileModel = new ProfileModelImpl(new ProfileCommunicationImpl());

    private Activity startGameActivity;



    private Activity createTeam;
    private Activity joinTeamActivity;
    private Activity teamManagementActivity;
    private Activity waitingOpponentActivity;

    private Activity shipPositioningActiviy;
    private Activity attackPhaseActivity;
    private Activity enemyAttackPhaseActivity;
    private Activity showFinalScoreActivity;
    private AttackPhasePresenter attackPhasePresenter;
    private EnemyAttackPhasePresenter enemyAttackPhasePresenter;
    private Boolean isBattleRunning = false;


    //private boolean attackPhaseActivityFirstInstance = true;
    //private boolean enemyAttackPhaseActivityFirstInstance = true;

    /**
     * @name BattleshipManagerImpl
     * @desc Costruttore privato per Singleton della classe
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManagerImpl
     */
    public BattleshipManagerImpl() {
       //Log.i(TAG, ".BattleshipManagerImpl()");
        _battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());
//        this = MainMenuPresenterImpl.getAppthis();
    profileModel.getProfile();


    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        BATTLESHIP_MANAGER_ISTANCE = this;
        start();
    }

    @Override
    public void setCreateTeam(Activity createTeam) {
        this.createTeam = createTeam;
    }

    @Override
    public void setShipPositioningActiviy(Activity shipPositioningActiviy) {
        this.shipPositioningActiviy = shipPositioningActiviy;
    }

    @Override
    public Activity getStartGameActivity() {
        return startGameActivity;
    }

    @Override
    public void setStartGameActivity(Activity startGameActivity) {
        this.startGameActivity = startGameActivity;
    }

    @Override
    public Activity getJoinTeamActivity() {
        return joinTeamActivity;
    }

    @Override
    public void setJoinTeamActivity(Activity joinTeamActivity) {
        this.joinTeamActivity = joinTeamActivity;
    }

    @Override
    public Activity getTeamManagementActivity() {
        return teamManagementActivity;
    }

    @Override
    public void setTeamManagementActivity(Activity teamManagementActivity) {
        this.teamManagementActivity = teamManagementActivity;
    }
    @Override
    public void setEnemyAttackPhasePresenter(EnemyAttackPhasePresenter enemyAttackPhasePresenter) {
        this.enemyAttackPhasePresenter = enemyAttackPhasePresenter;
    }

    @Override
    public void setAttackPhasePresenter(AttackPhasePresenter attackPhasePresenter) {
        this.attackPhasePresenter = attackPhasePresenter;
    }


    @Override
    public void setShowFinalScoreActivity(Activity showFinalScoreActivity) {
        this.showFinalScoreActivity = showFinalScoreActivity;
    }


    @Override
    public void setWaitingOpponentActivity(Activity waitingOpponentActivity) {
        this.waitingOpponentActivity = waitingOpponentActivity;
    }

    private void startReorderActivity(Class activity){
        Intent activityIntent = new Intent(this, activity);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(activityIntent);
    }
    /**
     * @name getBattleshipManagerIstance
     * @desc Metodo che ritorna un riferimento allaclasse singleton;
     * @returns {BattleshipManagerImpl}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManagerImpl
     */
    public static BattleshipManagerImpl getBattleshipManagerIstance() {
//       //Log.i(TAG, ".getBattleshipManagerIstance()");
//        if(BATTLESHIP_MANAGER_ISTANCE == null) {
//            BATTLESHIP_MANAGER_ISTANCE = new BattleshipManagerImpl();
//        }
        return BATTLESHIP_MANAGER_ISTANCE;
    }

    /**
     * @name start
     * @desc Mostra la prima schermata all'utente dove può scegliere di visualizzare la classifica o iniziare a giocare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void start() {
       //Log.i(TAG, ".start()");
//        Intent startIntent = new Intent(this,StartGamePresenterImpl.class);
//        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startActivity(startIntent);
        startReorderActivity(StartGamePresenterImpl.class);
    }

    /**
     * @name showLeaderboard
     * @desc Visualizza la classifica di battaglia navale associata al locale dell'utente;
     * @returns {void }
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void showLeaderboard() {
       //Log.i(TAG, ".showLeaderboard()");
//        Intent leaderIntent = new Intent(MainMenuPresenterImpl.getAppthis(),LeaderboardPresenterImpl.class);
//        leaderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        leaderIntent.putExtra("game","Battleship"); //extra per far conoscere di quale gioco caricare la classifica
//        MainMenuPresenterImpl.getAppthis().startActivity(leaderIntent);
    }

    /**
     * @name startPlay
     * @desc Se non esiste una squadra associata al beacon, visualizza la schermata per crearne una, altrimenti inserisce l'utente nella squadra esistente e visualizza i componenti della squadra;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void startPlay() {
       //Log.i(TAG, ".startPlay()");
        synchronized (isBattleRunning){
            isBattleRunning = true;
        }
        _battleshipModel.isTeamExists(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response == null) return;
                    if(response.body() == false){
                        startReorderActivity(CreateTeamPresenterImpl.class);
                    }

                    else {
                        showTeamManagement();
                    }

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    public void isCaptain(final Runnable runnable) {
        _battleshipModel.isCaptain(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()==true){
                        runnable.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    /**
     * @name showTeamManagement
     * @desc Controlla che l'utente sia capitano in caso positivo visualizza la gestione del team con possibilità di eliminare utente dal team, altrimenti visualizza un messaggio di errore e successivamente visualizza la lista dei membri del team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void showTeamManagement() {
       //Log.i(TAG, ".showTeamManagement()");
        isFirstTurn = true;
        _battleshipModel.isCaptain(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()==null) return;
                    if(response.body()==true){
                        startReorderActivity(TeamManagementPresenterImpl.class);
                        closeCreateTeam();
                    } else {
                        _battleshipModel.joinTeam(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body()==null) return;
                                if(response.body()==true) {
                                    startReorderActivity(JoinTeamPresenterImpl.class);
                                    closeCreateTeam();
                                } else {
                                    if(startGameActivity != null){
                                        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(startGameActivity);
                                        alertBuilder.setTitle("Team full");
                                        alertBuilder.setMessage("The team is full.");
                                        AlertDialog alertDialog = alertBuilder.create();
                                        alertDialog.show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


    }

    /**
     * @name changeToCaptainTeamManagment
     * @desc Chiude la vista del team da marinaio ed apre quella da capitano;
     * @param {Activity} activity - Rappresenta l'activity da chiudere in caso l'utente sia capitano;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void changeToCaptainTeamManagment() {
       //Log.i(TAG, ".changeToCaptainTeamManagment()");
        _battleshipModel.isCaptain(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()==true){
                        closeJoinTeam();
                        startReorderActivity(TeamManagementPresenterImpl.class);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    /**
     * @name kickOut
     * @desc Visualizza la schermata iniziale di battleship quando l'utente viene espulso dalla squadra, visualizza un messaggio informativo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void kickOut() {
      //Log.i(TAG, ".kickOut()");
        closeBattle();
//        startReorderActivity(StartGamePresenterImpl.class);
        if(startGameActivity != null){
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(startGameActivity);
            alertBuilder.setTitle("Kick out");
            alertBuilder.setMessage("You have been kick out off the team.");
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }
    }

    /**
     * @name leaveTeam
     * @desc Visualizza la schermata iniziale di battleship quando un utente lascia il team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void leaveTeam() {
      //Log.i(TAG, ".leaveTeam()");
        jointeamModel.leaveTeam(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
//        Intent backHome = new Intent(this, StartGamePresenterImpl.class);
//        this.startActivity(backHome);
        closeBattle();
//        startReorderActivity(StartGamePresenterImpl.class);
        _battleshipModel.removeBattleEndObserver();
       //Log.i(TAG, ".leaveTeam() return");
    }

    private void closeBattle(){
        closeWaitingOpponent();
        closeJoinTeam();
        closeTeamManagement();
        closeShipPositioning();
        closeAttachPhase();
        closeEnemyAttachPhase();
        closeFinalScore();
    }

    private void closeCreateTeam(){
        if(createTeam != null){
            createTeam.finish();
            createTeam = null;
        }
    }

    private void closeStartGame(){
        if(startGameActivity != null) {
            startGameActivity.finish();
            startGameActivity = null;
        }
    }

    private void closeJoinTeam(){
        if(joinTeamActivity != null) {
            joinTeamActivity.finish();
            joinTeamActivity = null;
        }
    }
    private void closeTeamManagement(){
        if(teamManagementActivity != null) {
            teamManagementActivity.finish();
            teamManagementActivity = null;
        }
    }

    private void closeShipPositioning(){
        if(shipPositioningActiviy != null) {
            shipPositioningActiviy.finish();
            shipPositioningActiviy = null;
        }
    }

    private void closeAttachPhase(){
        if(attackPhaseActivity != null) {
            attackPhaseActivity.finish();
            attackPhaseActivity = null;
        }
    }

    private void closeEnemyAttachPhase(){
        if(enemyAttackPhaseActivity != null) {
            enemyAttackPhaseActivity.finish();
            enemyAttackPhaseActivity = null;
        }
    }

    private void closeFinalScore(){
        if(showFinalScoreActivity != null) {
            showFinalScoreActivity.finish();
            showFinalScoreActivity = null;
        }
    }

    private void closeWaitingOpponent(){
        if(waitingOpponentActivity != null) {
            waitingOpponentActivity.finish();
            waitingOpponentActivity = null;
        }
    }



    /**
     * @name startSearching
     * @desc Visualizza la schermata di ricerca opponente in corso;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void startSearching() {
       //Log.i(TAG, ".startSearching()");
        _battleshipModel.removeStartSearchingOpponentObserver();
        startReorderActivity(WaitingOpponentPresenterImpl.class);
        _battleshipModel.addStartBattleObserver(new Runnable() {
            @Override
            public void run() {
                //Log.i(TAG, ".trovato opponente");
                startPositioning();
            }
        });
    }

    private final ReentrantLock isFirstTurnLock = new ReentrantLock();

    private boolean isFirstTurn = true;


    /**
     * @name startPositioning
     * @desc Visualizza la schermata per il posizionamento delle navi;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void startPositioning() {
        closeWaitingOpponent();
        _battleshipModel.removeStopSearchingOpponentObserver();
        startReorderActivity(SelectShipPositionPresenterImpl.class);

        _battleshipModel.addStartAttackPhaseObserver(new Runnable() {
            @Override
            public void run() {
                isFirstTurnLock.lock();
                try {
                    if (isFirstTurn) {
                        isFirstTurn = false;
                        setShowFinalScoreObserver();
                        startAttackPhase();
                        closeShipPositioning();
                    }
                } finally {
                    isFirstTurnLock.unlock();
                }
            }
        });

        _battleshipModel.addStartEnemyAttackPhaseObserver(new Runnable() {
            @Override
            public void run() {
                isFirstTurnLock.lock();
                try {
                    if (isFirstTurn) {
                        isFirstTurn = false;
                        setShowFinalScoreObserver();
                        startEnemyAttackPhase();
                        closeShipPositioning();
                    }
                } finally {
                    isFirstTurnLock.unlock();
                }
            }
        });


    }

    private void setShowFinalScoreObserver(){
        _battleshipModel.addBattleEndObserver(new Runnable() {
            @Override
            public void run() {
                showFinalScore();
                closeShipPositioning();
                closeAttachPhase();
                closeEnemyAttachPhase();
            }
        });
    }

    /**
     * @name exitSearchingOpponent
     * @desc Chiama l'activity che mi permette di gestire la squadra e a seconda della tipologia del chiamante se è capitano o marinaio;
     * @param {Activity} searchingView - Attività che si occupa di cercare l'avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void exitSearchingOpponent() {
       //Log.i(TAG, ".exitSearchingOpponent()");
        leaveTeam();
    }

    /**
     * @name startAttackPhase
     * @desc Visualizza la schermata di attacco dove l'utente potrà inserire le coordinate dei colpi da sparare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void startAttackPhase() {
      //Log.i(TAG, ".startAttackPhase()");
        if(isBattleRunning) {
            startReorderActivity(AttackPhasePresenterImpl.class);
            closeEnemyAttachPhase();
            _battleshipModel.addStartEnemyAttackPhaseObserver(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            attackPhasePresenter.showResult();
                        }
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startEnemyAttackPhase();
                }
            });
        }
    }

    /**
     * @name startEnemyAttackPhase
     * @desc Visualizza il campo del team finché il team avversario attacca;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void startEnemyAttackPhase() {
      //Log.i(TAG, ".startEnemyAttackPhase()");
        if(isBattleRunning) {
            startReorderActivity(EnemyAttackPhasePresenterImpl.class);
            closeAttachPhase();
            _battleshipModel.addStartAttackPhaseObserver(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enemyAttackPhasePresenter.updateAllayField();
                        }
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startAttackPhase();
                }
            });
        }

    }

    private void checkBattleFinish(){

    }

    /**
     * @name showFinalScore
     * @desc Visualizza i punteggi finali della partita;
     * @param {Activity} finishView - Activity che terminna la partita da chiudere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.BattleshipManager
     */
    @Override
    public void showFinalScore() {
      //Log.i(TAG, ".showFinalScore()");
        synchronized (isBattleRunning){
            isBattleRunning = false;
        }
        _battleshipModel.removeStartEnemyAttackPhaseObserver();
        _battleshipModel.removeStartAttackPhaseObserver();
        closeBattle();
        startReorderActivity(FinalScorePresenterImpl.class);


    }

    @Override
    public void setEnemyAttackPhaseActivity(Activity enemyAttackPhaseActivity) {
        this.enemyAttackPhaseActivity = enemyAttackPhaseActivity;
    }

    @Override
    public void setAttackPhaseActivity(Activity attackPhaseActivity) {
        this.attackPhaseActivity = attackPhaseActivity;
    }

    @Override
    public void exit(){
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        leaveTeam();
        closeBattle();
        closeStartGame();
    }
}