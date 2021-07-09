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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * file: JoinTeamCommunicationImpl.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief:Interfaccia che gestisce la comunicazione con il per le funzionalità di inserimento nella squadra;
 * use:Utilizzata da JoinTeamPresenter per gestire la comunicazione con il per le funzionalità di inserimento nella squadra;
 */
public class JoinTeamCommunicationImpl extends CommunicationImpl implements JoinTeamCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "JoinTeamCommunication";
    private static volatile Boolean stopChekingTeamObserver = true;
    private Gson gson;
    private int sleepTime = 1000;

    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Profile.class, new ProfileDeserializer());
        gsonBuilder.registerTypeAdapter(Profile.class, new ProfileInstanceCreator());
//        gsonBuilder.registerTypeAdapter(Profile.class, new TeamDeserializer());
        gson = gsonBuilder.create();
    }

    private class ProfileDeserializer implements JsonDeserializer<Profile> {
        public Profile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return (new Gson()).fromJson(jobject, ProfileImpl.class);
        }
    }

    private class ProfileInstanceCreator implements InstanceCreator<Profile> {
        public Profile createInstance(Type type) {
            return new ProfileImpl("");
        }
    }


    private interface ApiBattleshipRest {


//        Call<TeamComImpl> getTeam(@Path("session") String session);
        @GET("battleship/getTeam/{session}")
        Call<TeamImpl> getTeam(@Path("session") String session);

        @GET("battleship/subscribeIsTeamChange/{session}/{milliSeconds}")
        Call<Boolean> subscribeIsTeamChange(@Path("session") String session, @Path("milliSeconds") Integer milliSeconds);

        @GET("battleship/leaveTeam/{session}")
        Call<Void> leaveTeam(@Path("session") String session);

    }

    /**
     * @name JoinTeamCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunicationImpl
     */
    public JoinTeamCommunicationImpl() {
        _apiBattleshipRest = inizializzeApiBattleshipRest();
    }

    /**
     * @name inizializzeApiBattleshipRest
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunicationImpl
     */
    private ApiBattleshipRest inizializzeApiBattleshipRest(){
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
     * @name getTeam
     * @desc Ritorna il team di cui l'utente fa parte;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    

    @Override
    public void getTeam(final Callback<TeamImpl> callback) {
        String methodSignature = "getTeam(...)";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<TeamImpl> call = _apiBattleshipRest.getTeam(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    @Override
    public Future<Team> getTeamFuture() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Team> response = executor.submit(new GetTeamCallable());
        return response;
    }

    private class GetTeamCallable implements Callable<Team> {
        @Override
        public Team call() throws Exception {
            return getTeamSync();
        }
    }

    public Team getTeamSync() {
        String methodSignature = "getTeamSync()";
       //Log.v(TAG, methodSignature);
        Session session = getSession();

        String jsonSession = gson.toJson(session);

        Call<TeamImpl> call = _apiBattleshipRest.getTeam(jsonSession);
        try {
            Response<TeamImpl> response = call.execute();
            Team getTeamValue = response.body();
           //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(getTeamValue));
            return getTeamValue;
        } catch (IOException e) {
            // handle error
           //Log.e(TAG, "createTeamSync: Errore communicazione con il server [Eccezione "+ e.getMessage() + "]");
           //Log.v(TAG, methodSignature + " return : " + (new Gson()).toJson(null));
            return null;
        }
    }

    /**
     * @name leaveTeam
     * @desc Togli l'utente dal team;
     * @returns {void }
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    @Override
    public void leaveTeam(Callback<Void> callback) {
        String methodSignature = "leaveTeamSync()";
       //Log.v(TAG, methodSignature);
        Session session = getSession();

        String jsonSession = gson.toJson(session);
        Call<Void> call = _apiBattleshipRest.leaveTeam(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

    /**
     * @name update
     * @desc Notifica gli oggetti Observer che seguono i suoi cambiamenti, che qualcosa è cambiato;
     * @returns {voiid}
     * @memberOf Client.Games.Battleship.Model.Communication.JoinTeamCommunication
     */
    //// TODO: 6/12/16
    @Override
    public void addTeamObserver(Runnable runnable) {

        String methodSignature = "addTeamObserver()";
       //Log.v(TAG, methodSignature);
        synchronized (stopChekingTeamObserver) {
            stopChekingTeamObserver = false;
        }
        Thread thread = new Thread(new TeamObserverRun(runnable));
        thread.start();

    }

    private class TeamObserverRun implements   Runnable{
        private Runnable runnable;
        public TeamObserverRun(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run(){
            while (stopChekingTeamObserver == false ){
                subscribeTeamHandle(runnable);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void removeTeamObserver() {
        String methodSignature = "removeTeamObserver()";
       //Log.v(TAG, methodSignature);
        synchronized (stopChekingTeamObserver) {
            stopChekingTeamObserver = true;
        }
    }

    private void subscribeTeamHandle(Runnable runnable) {
        String methodSignature = "subscribeTeamHandle()";
      //Log.v(TAG, methodSignature);
        TeamImpl oldTeam = null;
        TeamImpl newTeam = null;
        Session session = getSession();
        String jsonSession = gson.toJson(session);
            try {
                Call<TeamImpl> call = _apiBattleshipRest.getTeam(jsonSession);
                newTeam = call.execute().body();
               //Log.v(TAG, methodSignature + " newTeam : " + (new Gson()).toJson(newTeam) + " ");
               //Log.v(TAG, methodSignature + " oldTeam : " + (new Gson()).toJson(oldTeam) + " ");
                runnable.run();
//                if (newTeam != null && newTeam.equals(oldTeam) ) {
//                    runnable.run();
                   //Log.v(TAG, methodSignature + " run() {aggiornamento team !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!}");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (newTeam != null) oldTeam = newTeam;
       //Log.v(TAG, methodSignature + " return : void ");
    }



}
