package digitalsloths.socialtables.games.battleship.model.communication;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * name BattleshipCommunicationImpl
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia per la gestione della comunicazione tra client e server per le funzionalità che riguardano il gioco Battleship;
 * use Utilizzata da BattleshipModel per la gestione della comunicazione tra client e server per le funzionalità che riguardano il gioco Battleship;
 */

public class BattleshipCommunicationImpl extends CommunicationImpl implements BattleshipCommunication {
    private static ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "BattleshipCommunication";
    private static final Integer minSecondSubscribe = 30;
    private static volatile Boolean stopChekingStartSearchingOpponent = true;
    private static volatile Boolean stopChekingStopSearchingOpponent = true;
    private static volatile Boolean stopChekingStartBattle = true;
    private static volatile Boolean stopChekingStartAttackPhaseObserver = true;
    private static volatile Boolean stopChekingStartEnemyAttackPhaseObserver = true;
    private static volatile Boolean stopChekingBattleEndObserver = true;
    private int sleepTime  = 500;
    private interface ApiBattleshipRest {

        @GET("battleship/isTeamExist/{beacon}")
        Call<Boolean> isTeamExists(@Path("beacon") String beacon);

        @GET("battleship/isBattleRunning/{beacon}")
        Call<Boolean> isBattleRunning(@Path("beacon") String beacon);

        @GET("battleship/joinTeam/{session}/{beacon}")
        Call<Boolean> joinTeam(@Path("session") String profile, @Path("beacon") String beacon);

        @GET("battleship/isStartSearchingOpponent/{session}")
        Call<Boolean> isStartSearchingOpponentHandle(@Path("session") String session);

        @GET("battleship/isStopSearchingOpponent/{session}")
        Call<Boolean> subscribeIsStopSearchingOpponentHandle(@Path("session") String session);

        @GET("battleship/isCaptain/{session}")
        Call<Boolean> isCaptain(@Path("session") String session);

//        @GET("battleship/startBattle/{session}")
//        Call<Boolean> startBattle(@Path("session") String session);

        @GET("battleship/isStartBattle/{session}")
        Call<Boolean> subscribeIsStartBattleHandle(@Path("session") String session);

        @GET("battleship/isStartAttackPhase/{session}")
        Call<Boolean> subscribeIsStartAttackPhaseHandle(@Path("session") String session);

        @GET("battleship/isStartEnemyAttackPhase/{session}")
        Call<Boolean> subscribeIsStartEnemyAttackPhaseHandle(@Path("session") String session);

        @GET("battleship/isBattleEnd/{session}")
        Call<Boolean> subscribeIsBattleEndHandle(@Path("session") String session);

    }

    public BattleshipCommunicationImpl() {
        _apiBattleshipRest = getApiBattleshipCommunication();
    }

    private ApiBattleshipRest getApiBattleshipCommunication(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiBattleshipRest.class);
    }

    /**
     * @name isTeamExists
     * @desc richiede al server se il team associato al beacon, passato come parametro, è già
     * presente nel database;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    @Override
    public void isTeamExists(Beacon beacon, Callback<Boolean> callback) {
        String methodSignature = ".isTeamExists(" + (new Gson()).toJson(beacon) + ", ...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        String jsonBeacon = gson.toJson(beacon);
        Call<Boolean> call = _apiBattleshipRest.isTeamExists(jsonBeacon);
        //Call<Boolean> call = _apiBattleshipRest.isTeamExists(beacon);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name isBattleRunning
     * @desc Ritorna true se c'è già una battaglia in corso, false altrimenti;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunication
     */
    @Override
    public void isBattleRunning(Beacon beacon, Callback<Boolean> callback) {
        String methodSignature = ".isBattleRunning(" + (new Gson()).toJson(beacon) + ", ...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        String jsonBeacon = gson.toJson(beacon);
        Call<Boolean> call = _apiBattleshipRest.isBattleRunning(jsonBeacon);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");

    }


    /**
     * @name startBattle
     * @desc Comunica al model che la partita è iniziata;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
//    @Override
//    public void startBattle(Callback<Boolean> callback) {
//        String methodSignature = ".startBattle(...)";
//       //Log.v(TAG, methodSignature);
//        Gson gson = new Gson();
//        Session session = getSession();
//        String jsonSession = gson.toJson(session);
//        Call<Boolean> call = _apiBattleshipRest.startBattle(jsonSession);
//        call.enqueue(callback);
//       //Log.v(TAG, methodSignature + " return : void ");
//    }


    /**
     * @name joinTeam
     * @desc Ritorna true solo se l'utente è entrato nel team;
     * @param {Beacon} beacon - parametro di tipo Beacon;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    @Override
    public void joinTeam(Beacon beacon, Callback<Boolean> callback) {
        String methodSignature = ".joinTeam(" + (new Gson()).toJson(beacon) + ", ...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        String jsonBeacon = gson.toJson(beacon);
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.joinTeam(jsonSession, jsonBeacon);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name isStartSearchOpponent
     * @desc Ritorna true sse il team dell'utente è entrato nella fase di ricerca di un opponente;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */
    @Override
    public void addStartSearchingOpponentObserver(Runnable runnable) {
        String methodSignature = "addStartSearchingOpponentObserver(...)";
       //Log.i(TAG, methodSignature);
        synchronized (stopChekingStartSearchingOpponent) {
                stopChekingStartSearchingOpponent = false;
        }
        Thread thread = new Thread(new ChekingStartSearchingOpponent(runnable));
        thread.start();

    }

    private class ChekingStartSearchingOpponent implements   Runnable{
        private Runnable runnable;
        public ChekingStartSearchingOpponent(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingStartSearchingOpponent == false && !subscribeStartSearchingOpponentHandle(runnable)) {
               //Log.i(TAG, "stopChekingStartSearchingOpponent == false ?" + (new Gson()).toJson(stopChekingStartSearchingOpponent == false));
                try {

                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }

        }

    }

    @Override
    public void removeStartSearchingOpponentObserver() {
        synchronized (stopChekingStartSearchingOpponent) {
            stopChekingStartSearchingOpponent = true;
           //Log.i(TAG, "stopChekingStartSearchingOpponent = true");
        }
    }

    private boolean subscribeStartSearchingOpponentHandle(Runnable runnable) {
        String methodSignature = "subscribeStartSearchingOpponentHandle()";
      //Log.i(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        try {
            Call<Boolean> call = _apiBattleshipRest.isStartSearchingOpponentHandle(jsonSession);
            Boolean isStartSearchingOpponent;
                isStartSearchingOpponent = call.execute().body();
           //Log.v(TAG, methodSignature + " call.execute().body() : " + isStartSearchingOpponent + " ");

            if (isStartSearchingOpponent != null && isStartSearchingOpponent == true) {
                runnable.run();
                return true;
            }
           //Log.v(TAG, methodSignature + " return : void ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @name SumbscribeIsStopSearchingOpponent
     * @desc Ritorna true sse la ricerca dell'opponente è finita perché il capitano è uscito dalla ricerca di un opponente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    @Override
    public void addStopSearchingOpponentObserver(Runnable runnable) {
        synchronized (stopChekingStopSearchingOpponent) {
                stopChekingStopSearchingOpponent = false;
        }
        Thread thread = new Thread(new ChekingStopSearchingOpponent(runnable));
        thread.start();
    }

    private class ChekingStopSearchingOpponent implements   Runnable{
        private Runnable runnable;
        public ChekingStopSearchingOpponent(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingStopSearchingOpponent == false && !subscribeStopSearchingOpponentHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeStopSearchingOpponentObserver() {
        synchronized (stopChekingStopSearchingOpponent) {
            stopChekingStopSearchingOpponent = true;
        }
    }

    private boolean subscribeStopSearchingOpponentHandle(Runnable runnable) {
        String methodSignature = "subscribeStopSearchingOpponentHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        try {
            Call<Boolean> call = _apiBattleshipRest.subscribeIsStopSearchingOpponentHandle(jsonSession);
            boolean isStopSearchingOpponent = call.execute().body();
            if(isStopSearchingOpponent == true) {
                runnable.run();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name isCaptain
     * @desc Richiede al server se il profilo associato alla sessione è capitano di una squadra;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.BattleshipCommunication
     */

    @Override
    public void isCaptain(Callback<Boolean> callback) {
        String methodSignature = "isCaptainSync(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.isCaptain(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name addBattleStartCallBack
     * @desc Notifica gli oggetti Observer che seguono i suoi cambiamenti, che qualcosa è cambiato;
     * @returns {voiid}
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    @Override
    public void addStartBattleObserver(Runnable runnable) {
       //Log.v(TAG, "addStartBattleObserver()");
        synchronized (stopChekingStartBattle) {
                stopChekingStartBattle = false;
        }
        Thread thread = new Thread(new StartBattleObserverRun(runnable));
        thread.start();
    }

    private class StartBattleObserverRun implements   Runnable{
        private Runnable runnable;
        public StartBattleObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingStartBattle == false && !subscribeStartBattleHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeStartBattleObserver() {
        synchronized (stopChekingStartBattle) {
            stopChekingStartBattle = true;
        }
    }

    private boolean subscribeStartBattleHandle(Runnable runnable) {
        String methodSignature = "subscribeStartBattleHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsStartBattleHandle(jsonSession);
        try {
            Boolean isStartBattle = call.execute().body();
           //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(isStartBattle) + " ");
            if(isStartBattle != null && isStartBattle == true) {
                runnable.run();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
       //Log.v(TAG, methodSignature + " return : void ");
    }

//// TODO: 6/12/16
    @Override
    public void addStartAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, "addStartAttackPhaseObserver()");
        synchronized (stopChekingStartAttackPhaseObserver) {
                stopChekingStartAttackPhaseObserver = false;
        }

        Thread thread = new Thread(new StartAttackPhaseObserverRun(runnable));
        thread.start();
    }

    private class StartAttackPhaseObserverRun implements   Runnable{
        private Runnable runnable;
        public StartAttackPhaseObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingStartAttackPhaseObserver == false && !subscribeStartAttackPhaseHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeStartAttackPhaseObserver() {
        synchronized (stopChekingStartAttackPhaseObserver) {
            stopChekingStartAttackPhaseObserver = true;
        }
    }

    private boolean subscribeStartAttackPhaseHandle(Runnable runnable) {
        String methodSignature = "subscribeStartBattleHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsStartAttackPhaseHandle(jsonSession);
        try {
            Boolean isStartBattle = call.execute().body();
           //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(isStartBattle) + " ");
            if(isStartBattle != null && isStartBattle == true) {
                runnable.run();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
       //Log.v(TAG, methodSignature + " return : void ");
    }



    //// TODO: 6/12/16
    @Override
    public void addStartEnemyAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, "addStartEnemyAttackPhaseObserver()");
        synchronized (stopChekingStartEnemyAttackPhaseObserver) {
                stopChekingStartEnemyAttackPhaseObserver = false;
        }
        Thread thread = new Thread(new StartEnemyAttackPhaseObserverRun(runnable));
        thread.start();
    }

    private class StartEnemyAttackPhaseObserverRun implements   Runnable{
        private Runnable runnable;
        public StartEnemyAttackPhaseObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingStartEnemyAttackPhaseObserver == false && !subscribeStartEnemyAttackPhaseHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeStartEnemyAttackPhaseObserver() {
        synchronized (stopChekingStartEnemyAttackPhaseObserver) {
            stopChekingStartEnemyAttackPhaseObserver = true;
        }
    }

    private boolean subscribeStartEnemyAttackPhaseHandle(Runnable runnable) {
        String methodSignature = "subscribeStartEnemyAttackPhaseHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsStartEnemyAttackPhaseHandle(jsonSession);
        try {
            Boolean isStartBattle = call.execute().body();
          //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(isStartBattle) + " ");
            if(isStartBattle != null && isStartBattle == true) {
                runnable.run();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
      //Log.v(TAG, methodSignature + " return : void ");
    }

    //// TODO: 6/12/16
    @Override
    public void addBattleEndObserver(Runnable runnable) {
       //Log.v(TAG, "addBattleEndObserver()");
        synchronized (stopChekingBattleEndObserver) {
            stopChekingBattleEndObserver = false;
        }
        Thread thread = new Thread(new BattleEndObserverRun(runnable));
        thread.start();
    }

    private class BattleEndObserverRun implements   Runnable{
        private Runnable runnable;
        public BattleEndObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingBattleEndObserver == false && !subscribeBattleEndHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeBattleEndObserver() {
        synchronized (stopChekingBattleEndObserver) {
            stopChekingBattleEndObserver = true;
        }
    }

    private boolean subscribeBattleEndHandle(Runnable runnable) {
        String methodSignature = "subscribeBattleEndHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsBattleEndHandle(jsonSession);
        try {
            Boolean isStartBattle = call.execute().body();
           //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(isStartBattle) + " ");
            if(isStartBattle != null && isStartBattle == true) {
                runnable.run();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
       //Log.v(TAG, methodSignature + " return : void ");
    }

    @Override
    public Boolean isBattleEnd() {
        String methodSignature = "subscribeBattleEndHandle()";
        //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        final String jsonSession = gson.toJson(session);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        FutureTask<Boolean> futureIsBattleEnd = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Call<Boolean> call = _apiBattleshipRest.subscribeIsBattleEndHandle(jsonSession);
                return call.execute().body();
            }
        });
        executor.execute(futureIsBattleEnd);
        try {
            return futureIsBattleEnd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
        //Log.v(TAG, methodSignature + " return : void ");
    }


}
