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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.games.battleship.types.Position;
import digitalsloths.socialtables.games.battleship.types.PositionImpl;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import digitalsloths.socialtables.games.battleship.types.ShipType;
import digitalsloths.socialtables.games.battleship.types.ShipTypeImpl;
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
 * file: SelectShipPositionCommunication.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief: Interfaccia che gestisce la comunicazione con il server per la funzionalità del posizionamento navi;
 * use: Utilizzata da SelectSchipPositionPresenter per gestire la comunicazione con il server per la funzionalità del posizionamento navi;
 */
public class SelectShipPositionCommunicationImpl extends CommunicationImpl implements SelectShipPositionCommunication {
    private static final String TAG = "SelectShipPositionComm";
    private int sleepTime = 1000;
    private ApiBattleshipRest _apiBattleshipRest;

    private Boolean stopChekingTeamShipsPositionObserver = true;

    private interface ApiBattleshipRest {

        @GET("battleship/getShipsToPositioning/{session}")
        Call<ArrayList<ShipTypeImpl>> getShipsToPositioning(@Path("session") String session);

        @GET("battleship/getTeamShips/{session}")
        Call<ArrayList<ShipImpl>> getTeamShips(@Path("session") String session);

        @GET("battleship/setShipPosition/{session}/{ship}")
        Call<Boolean> setShipPosition(@Path("session") String beacon, @Path("ship") String ship);

        @GET(" ")
        Call<Boolean> subscribeTeamShipsPosition(@Path("session") String beacon);

    }

    private Gson gson;

    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Position.class, new PositionDeserializer());
        gsonBuilder.registerTypeAdapter(Position.class, new PositionInstanceCreator());
            gson = gsonBuilder.create();
    }

    private class PositionDeserializer implements JsonDeserializer<Position> {
        public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return (new Gson()).fromJson(jobject, PositionImpl.class);
        }
    }

    private class PositionInstanceCreator implements InstanceCreator<Position> {
        public Position createInstance(Type type) {
            return new PositionImpl(0,0);
        }
    }

    /**
     * @name SelectShipPositionCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunicationImpl
     */
    public SelectShipPositionCommunicationImpl() {
        _apiBattleshipRest = getApiBattleshipCommunication();
    }

    /**
     * @name getApiBattleshipCommunication
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunicationImpl
     */
    private ApiBattleshipRest getApiBattleshipCommunication(){
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
     * @name getShipsToPositioning
     * @desc Richiede al server la lista di navi da posizionare;
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    @Override
    public void getShipsToPositioning(Callback<ArrayList<ShipTypeImpl>> callback) {
        String methodSignature = "getShipsToPositioning(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<ArrayList<ShipTypeImpl>> call = _apiBattleshipRest.getShipsToPositioning(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name getTeamShips
     * @desc Ritorna la lista delle navi del team posizionate;
     * @returns {list}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    @Override
    public void getTeamShips(Callback<ArrayList<ShipImpl>> callback) {
        String methodSignature = "getTeamShips(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<ArrayList<ShipImpl>> call = _apiBattleshipRest.getTeamShips(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name setShipPosition
     * @desc manda al server la posizione della nave
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    @Override
    public void setShipPosition(Ship ship, Callback<Boolean> callback) {
        String methodSignature = "setShipPosition(...)";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        String jsonShip = gson.toJson(ship);
        Call<Boolean> call = _apiBattleshipRest.setShipPosition(jsonSession, jsonShip);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name update
     * @desc Notifica a SelectShipPosition che c'è stato un aggiornamento nelle variabili del
     * server;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.SelectShipPositionCommunication
     */
    //// TODO: 6/12/16
    @Override
    public void addTeamShipsPositionObserver(Callback<ArrayList<ShipImpl>> callback) {
       //Log.v(TAG, "addTeamShipsPositionObserver()");
        synchronized (stopChekingTeamShipsPositionObserver) {
            stopChekingTeamShipsPositionObserver = false;
        }

        Thread thread = new Thread(new TeamShipsPositionObserverRun(callback));
        thread.start();

    }

    private class TeamShipsPositionObserverRun implements   Runnable{
        private Callback<ArrayList<ShipImpl>> callback;
        public TeamShipsPositionObserverRun(Callback<ArrayList<ShipImpl>> callback){
            this.callback = callback;
        }
        @Override
        public void run(){
            while (stopChekingTeamShipsPositionObserver == false) {
                subscribeTeahShipnHandle(callback);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public void removeTeamShipsPositionObserver() {
        synchronized (stopChekingTeamShipsPositionObserver) {
            stopChekingTeamShipsPositionObserver = true;
        }
    }

    private void subscribeTeahShipnHandle(Callback<ArrayList<ShipImpl>> callback) {
        String methodSignature = "subscribeTeahShipnHandle()";
       //Log.v(TAG, methodSignature);
        Gson gson = new Gson();
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<ArrayList<ShipImpl>> call = _apiBattleshipRest.getTeamShips(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

}
