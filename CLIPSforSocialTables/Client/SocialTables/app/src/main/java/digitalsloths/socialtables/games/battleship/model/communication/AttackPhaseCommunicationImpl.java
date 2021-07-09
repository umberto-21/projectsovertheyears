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
import digitalsloths.socialtables.games.battleship.types.Shot;
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
 * brief Interfaccia per gestire la comunicazione nella fase di attacco
 * use Utilizzata da AttackPhaseModel per gestire la comunicazione nella fase di attacco;
 */
public class AttackPhaseCommunicationImpl extends CommunicationImpl implements AttackPhaseCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "AttackPhaseComm";
    private static Boolean stopChekingEndAttackPhaseObserver = true;
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

        @GET("battleship/getEnemyField/{session}")
        Call<Field> getEnemyField(@Path("session") String session);

        @GET("battleship/sendShot/{session}/{shot}")
        Call<Boolean> sendShot(@Path("session") String session, @Path("shot") String shot);

        @GET("battleship/getShotNumber/{session}")
        Call<Integer> getShotNumber(@Path("session") String session);

        @GET("battleship/subscribeIsEndAttackPhase/{session}")
        Call<Boolean> subscribeIsEndAttackPhase(@Path("session") String session);
    }

    /**
     * @name AttackPhaseCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    public AttackPhaseCommunicationImpl() {
        _apiBattleshipRest = getApiAttackPhaseCommunication();
    }

    /**
     * @name getApiBattleshipCommunication
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunicationImpl
     */
    private ApiBattleshipRest getApiAttackPhaseCommunication(){
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
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     * @param callback
     */
    @Override
    public void getEnemyField (Callback<Field> callback){
        String methodSignature = "getEnemyField(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Field> call = _apiBattleshipRest.getEnemyField(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name getShotNumber
     * @desc Ritorna il numero di colpi da sparare;
     * @returns {int}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    @Override
    public void getShotNumber(Callback<Integer> callback){
        String methodSignature = "getShotNumber(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Integer> call = _apiBattleshipRest.getShotNumber(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name sendShoot
     * @desc manda le coordinate sul colpo al server;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    @Override
    public void sendShot(Shot shot, Callback<Boolean> callback) {
        String methodSignature = "sendShot(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        String jsonShot = gson.toJson(shot);
        Call<Boolean> call = _apiBattleshipRest.sendShot(jsonSession, jsonShot);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name update
     * @desc chiama il metodo update della classe AttackPhaseModel;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    //// TODO: 6/12/16
    @Override
    public void addEndAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, "addEndAttackPhaseObserver()");
        synchronized (stopChekingEndAttackPhaseObserver) {
            stopChekingEndAttackPhaseObserver = false;
        }

        Thread thread = new Thread(new EndAttackPhaseObserverRun(runnable));
        thread.start();

    }

    private class EndAttackPhaseObserverRun implements   Runnable{
        private Runnable runnable;
        public EndAttackPhaseObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingEndAttackPhaseObserver == false && !subscribeEndAttackPhaseHandle(runnable)) {

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void removeEndAttackPhaseObserver() {
        synchronized (stopChekingEndAttackPhaseObserver) {
            stopChekingEndAttackPhaseObserver = true;
        }
    }

    private boolean subscribeEndAttackPhaseHandle(Runnable runnable) {
        String methodSignature = "subscribeStartBattleHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<Boolean> call = _apiBattleshipRest.subscribeIsEndAttackPhase(jsonSession);
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
