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
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * file: WaitingOpponentCommunicationImpl.java
 * author: Ugo Padoan
 * date: 6/6/16
 * brief: Interfaccia per la gestione della comunicazione con il server per le funzionalità di attesa avversario;
 * use: Utilizzata da WaitingOpponentPresenter per la gestione della comunicazione con il server per le funzionalità di attesa avversario;
 */
public class WaitingOpponentCommunicationImpl extends CommunicationImpl implements WaitingOpponentCommunication {
    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "WaitingOpponentComm";
    private ExecutorService executor = Executors.newCachedThreadPool();
    private interface ApiBattleshipRest {

        @GET("battleship/startSearchingOpponent/{session}")
        Call<Void> startSearchingOpponent(@Path("session") String session);

        @GET("battleship/stopSearchingOpponent/{session}")
        Call<Void> stopSearchingOpponent(@Path("session") String session);

    }

    /**
     * @name WaitingOpponentCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunicationImpl
     */
    public WaitingOpponentCommunicationImpl() {
        _apiBattleshipRest = inizializzeApiBattleshipRest();
    }

    /**
     * @name inizializeApiBattleshipRest
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunicationImpl
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
     * @name startSearchingOpponent
     * @desc Comunica al server la disponibilità di iniziare la partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    @Override
    public void startSearchingOpponent(Callback<Void> callback) {
        String methodSignature = "startSearchingOpponentSync()";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        Call<Void> call = _apiBattleshipRest.startSearchingOpponent(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }


    /**
     * @name stopSearchingOpponent
     * @desc comunica al server che l'utente é uscito dalla ricerca;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    @Override
    public void stopSearchingOpponent(Callback<Void> callback) {
        String methodSignature = "stopSearchingOpponentSync(...)";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        Call<Void> call = _apiBattleshipRest.stopSearchingOpponent(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }
}
