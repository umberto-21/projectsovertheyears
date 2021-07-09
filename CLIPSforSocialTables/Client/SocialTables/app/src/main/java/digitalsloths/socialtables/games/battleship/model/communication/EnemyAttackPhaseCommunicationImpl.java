package digitalsloths.socialtables.games.battleship.model.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.games.battleship.types.Cell;
import digitalsloths.socialtables.games.battleship.types.CellImpl;
import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.battleship.types.GridImpl;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * name AttackPhaseCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che gestisce la comunicazione con il server per le funzionalità associate all fase di attacco del team avversario;
 * use Utilizzata da EnemyAttackPhaseModelImpl per gestire la fase di attacco del team avversario;
 */
public class EnemyAttackPhaseCommunicationImpl extends CommunicationImpl implements EnemyAttackPhaseCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "EnemyAttackPhaseComm";
    private Boolean stopChekingEndEnemyAttackPhaseObserver = true;
    private int sleepTime = 1000;

    private Gson gson;

    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Field.class, new FieldDeserializer());
        gsonBuilder.registerTypeAdapter(Field.class, new FieldInstanceCreator());
        gsonBuilder.registerTypeAdapter(Grid.class, new GridDeserializer());
        gsonBuilder.registerTypeAdapter(Grid.class, new GridInstanceCreator());
        gsonBuilder.registerTypeAdapter(Cell.class, new CellDeserializer());
        gsonBuilder.registerTypeAdapter(Cell.class, new CellInstanceCreator());
        gson = gsonBuilder.create();
    }

    private class FieldDeserializer implements JsonDeserializer<Field> {
        public Field deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return gson.fromJson(jobject, FieldImpl.class);
        }
    }

    private class FieldInstanceCreator implements InstanceCreator<Field> {
        private Grid grid = new GridImpl(10, 10);
        public Field createInstance(Type type) {
            return new FieldImpl(grid);
        }
    }

    private class GridDeserializer implements JsonDeserializer<Grid> {
        public Grid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return gson.fromJson(jobject, GridImpl.class);
        }
    }

    private class GridInstanceCreator implements InstanceCreator<Grid> {
        public Grid createInstance(Type type) {
            return new GridImpl(10, 10);
        }
    }

    private class CellDeserializer implements JsonDeserializer<Cell> {
        public Cell deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return gson.fromJson(jobject, CellImpl.class);
        }
    }

    private class CellInstanceCreator implements InstanceCreator<Cell> {
        public Cell createInstance(Type type) {
            return new CellImpl();
        }
    }

    private interface ApiBattleshipRest{
        @GET("battleship/getAllayField/{session}")
        Call<Field> getAllayField(@Path("session") String session);

        @GET("battleship/subscribeIsEndEnemyAttackPhase/{session}")
        Call<Boolean> subscribeIsEndEnemyAttackPhase(@Path("session") String session);
    }

    /**
     * @name EnemyAttackPhaseCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.EnemyAttackPhaseCommunicationImpl
     */
    public EnemyAttackPhaseCommunicationImpl() {
        _apiBattleshipRest = getApiEnemyAttackPhaseCommunication();
    }

    /**
     * @name getApiEnemyAttackPhaseCommunication
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.EnemyAttackPhaseCommunicationImpl
     */
    private ApiBattleshipRest getApiEnemyAttackPhaseCommunication(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiBattleshipRest.class);
    }


    /**
     * @name getField
     * @desc richiede al server la Field;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.Communication.EnemyAttackPhaseCommunication
     */
    @Override
    public void getAllayField (Callback<Field> callback){
        String methodSignature = "getAllayField(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Field> call = _apiBattleshipRest.getAllayField(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }


    /**
     * @name update
     * @desc Ritorna true sse c'è un aggiornamento nel server;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.AllayAttackPhaseCommunication
     */
    //// TODO: 6/12/16
    @Override
    public void addEndEnemyAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, "addEndEnemyAttackPhaseObserver()");
        synchronized (stopChekingEndEnemyAttackPhaseObserver) {
            stopChekingEndEnemyAttackPhaseObserver = false;
        }

        Thread thread = new Thread(new EndEnemyAttackPhaseObserverRun(runnable));
        thread.start();

    }

    private class EndEnemyAttackPhaseObserverRun implements   Runnable{
        private Runnable runnable;
        public EndEnemyAttackPhaseObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingEndEnemyAttackPhaseObserver == false && !subscribeEndEnemyAttackPhaseHandle(runnable)) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void removeEndEnemyAttackPhaseObserver() {
        synchronized (stopChekingEndEnemyAttackPhaseObserver) {
            stopChekingEndEnemyAttackPhaseObserver = true;
        }
    }

    private boolean subscribeEndEnemyAttackPhaseHandle(Runnable runnable) {
        String methodSignature = "subscribeStartBattleHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsEndEnemyAttackPhase(jsonSession);
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
}
