package digitalsloths.socialtables.games.battleship.model.communication;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * file: TeamManagementCommunicationImpl.java
 * author: Ugo Padoan
 * date: 6/7/16
 * brief:Interfaccia che gestisce la comunicazione con il per le funzionalità di inserimento nella squadra;
 * use:Utilizzata da JoinTeamPresenter per gestire la comunicazione con il per le funzionalità di inserimento nella squadra;
 */
public class TeamManagementCommunicationImpl extends  JoinTeamCommunicationImpl implements TeamManagementCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "TeamManagementComm";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private interface ApiBattleshipRest {

        @GET("battleship/removeTeamMember/{session}/{profile}")
        Call<Void> removeTeamMember(@Path("session") String session, @Path("profile") String profile);

    }

    /**
     * @name TeamManagementCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.TeamManagementCommunicationImpl
     */
    public TeamManagementCommunicationImpl() {
        _apiBattleshipRest = inizializeApiBattleshipRest();
    }

    /**
     * @name inizializeApiBattleshipRest
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.TeamManagementCommunicationImpl
     */
    private ApiBattleshipRest inizializeApiBattleshipRest(){
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
     * @name removeTeamMember
     * @desc rimuove il profilo indicato dal parametro
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.TeamManagementCommunication
     */
    @Override
    public void removeTeamMember(Profile profile, Callback<Void> callback) {
        String methodSignature = "removeTeamMemberSync(" + (new Gson()).toJson(profile) + ", ...)";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        String jsonProfile = gson.toJson(profile);
        Call<Void> call = _apiBattleshipRest.removeTeamMember(jsonSession, jsonProfile);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }

}
