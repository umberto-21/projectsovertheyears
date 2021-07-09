package digitalsloths.socialtables.basefunctions.model.communication;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.types.Session;
import digitalsloths.socialtables.types.SessionImpl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * name CommunicationImpl.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Classe che implementa l'interfaccia che gestisce le funzionalità di comunicazione con il server;
 * Utilizzata dai package communication per avere le funzionalità di comunicazione di base;
 */
public class CommunicationImpl extends Observable implements Communication {
    private static final String TAG = "Communication";
    private static Session _session;
    private final String _baseUrl ;

    /**
     * @name CommunicationImpl
     * @desc Costruttore di default
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    public CommunicationImpl() {
        _baseUrl = "http://52.58.136.23:80/";
        //_baseUrl = "http://192.168.0.102:9000/";
    }

    /**
     * @name CommunicationImpl
     * @desc Costruttore con url di base definito;
     * @param {String} baseUrl - Url di base per comunicazione.
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    public CommunicationImpl(String baseUrl) {//http://10.192.80.176/
        _baseUrl = baseUrl;
    }

    /**
     * @name getSession
     * @desc Ritorna una sessione asincrona;
     * @returns {Session}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    @Override
    public Session getSession() {
       //Log.v(TAG, ".getSession()");
        if (_session == null){
            startSessionAsync();
        }
       //Log.v(TAG, ".getSession() return: " + (new Gson()).toJson(_session));
        return _session;
    }

    /**
     * @name getServerUrl
     * @desc Ritorna l'url del server;
     * @returns {String}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    @Override
    public String getServerUrl() {
        return _baseUrl;
    }

    private interface ApiProfile {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter
        //@GET("Session/createNewSession")
        @GET("session/startNewSession")
        Call<SessionImpl> createNewSession();
    }
    /**
     * @name startSessionAsync
     * @desc Avvia una session asincrona;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    private boolean startSessionAsync(){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Boolean> response = executor.submit(new Callable<Boolean>(){
                @Override
                public Boolean call() throws Exception {
                    return startSession();
                }
            }
        );
        Boolean _isSessionStart = false;
        try {
            _isSessionStart = response.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return _isSessionStart;
    }

    /**
     * @name startSession
     * @desc Avvia una sessione, se esegue correttamente restituisce true, false altrimenti;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    private boolean startSession() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ApiProfile apiSession = retrofit.create(ApiProfile.class);
        Call<SessionImpl> call = apiSession.createNewSession();
        try {
            Response<SessionImpl> response = call.execute();
            _session = response.body();
            //Log.v(TAG, "Nuova sessione creata ");
//Log.e(TAG, "Errore communicazione con il server [statusCode " + response.code() + "]");
            return _session != null;
        } catch (IOException e) {
            // handle error
           //Log.e(TAG, "Errore communicazione con il server [Eccezione "+ e.getMessage() + "]");
            return false;
        }







//        call.enqueue(new Callback<SessionImpl>() {
//            @Override
//            public void onResponse(Call<SessionImpl> call, Response<SessionImpl> response) {
//                if (response.isSuccessful()) {
//                    _session = response.body();
//                    if (_session != null) {
//                       //Log.v(TAG, "Nuova sessione creata ");
//                        return true;
//                    } else {
//                       //Log.e(TAG, "Errore communicazione con il server [statusCode " + response.code() + "]");
//                        return false;
//                    }
//                } else {
//                    // error response, no access to resource?
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SessionImpl> call, Throwable t) {
//                // something went completely south (like no internet connection)
//               //Log.v("Error", t.getMessage());
//            }
//        }
    }


}
