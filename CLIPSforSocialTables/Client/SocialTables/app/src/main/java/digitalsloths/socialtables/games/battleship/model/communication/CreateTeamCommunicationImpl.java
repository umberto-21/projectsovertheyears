package digitalsloths.socialtables.games.battleship.model.communication;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
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
 * name CreateTeamCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che espone metodi per la gestione della comunicazione con il server per le funzionalità di creazione della squadra;
 * Utilizzata Utilizzata da CreateTeamPresenter per gestire la comunicazione con il server per le funzionalità di creazione della squadra;
 */
public class CreateTeamCommunicationImpl extends CommunicationImpl implements CreateTeamCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "CreateTeamCommunication";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private interface ApiBattleshipRest {

        @GET("battleship/createTeam/{session}/{teamName}/{beacon}")
        Call<Boolean> createTeam(@Path("session") String session, @Path("teamName") String teamName, @Path("beacon") String beacon);


    }

    /**
     * @name CreateTeamCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunicationImpl
     */
    public CreateTeamCommunicationImpl() {
        _apiBattleshipRest = inizializzeApiBattleshipRest();
    }

    /**
     * @name inizializzeApiBattleshipRest
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunicationImpl
     */
    private ApiBattleshipRest inizializzeApiBattleshipRest(){
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
     * @param beacon
     * @returns {boolean}
     * @name createTeam
     * @desc Chiede al server di creare un team ritornando l'esito della creazione;
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunication
     */
    @Override
    public void createTeam(String teamName, Beacon beacon, Callback<Boolean> callback) {
        String methodSignature = "createTeamSync(" + (new Gson()).toJson(teamName) + ", " + (new Gson()).toJson(beacon) + ")";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        String jsonBeacon = gson.toJson(beacon);
        Call<Boolean> call = _apiBattleshipRest.createTeam(jsonSession, teamName, jsonBeacon);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void");

    }


}
