package digitalsloths.socialtables.basefunctions.model.communication;

import com.google.gson.Gson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;

/**
 * name EnvironmentCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Gestisce la comunicazione tra client e server per le funzionalità di base;
 * use Viene utilizzata per comunicare con il server;
 */
public class EnvironmentCommunicationImpl implements EnvironmentCommunication {
    private static final String TAG = "EnvCommunicationImpl";
    private static EnvironmentCommunicationImpl ourInstance = new EnvironmentCommunicationImpl();
    private static Context _context;

    /**
     * @name getInstance
     * @desc 	Riferimento Singleton all'istanza di classe;
     * @param {Context} context - Contesto dell'Activity da utilizzare.
     * @returns {EnvironmentCommunicationImpl}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    public static EnvironmentCommunicationImpl getInstance(Context context) {
        if (_context == null) {
            _context = context;
        }
        return ourInstance;
    }

    /**
     * @name EnvironmentCommunicationImpl
     * @desc 	Costruttore di default.
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    public EnvironmentCommunicationImpl() {
        _context= MainMenuPresenterImpl.getAppContext();
    }

    /**
     * @name isServerActive
     * @desc Ritorna true se il server è attivo, false altrimenti;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    @Override
    public boolean isServerActive() {
        String server_url = "http://52.58.136.23:80/";
        int _serverTimeout = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Boolean> response = executor.submit(new IsServerActiveAsinc(server_url, _serverTimeout));
        Boolean _isServerActive = false;
        try {
             _isServerActive = response.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return _isServerActive;
    }

    public class IsServerActiveAsinc implements Callable<Boolean> {
        private String _url;
        private int _serverTimeout;

        public IsServerActiveAsinc(String url, int serverTimeout) {
            _url = url;
            _serverTimeout = serverTimeout;
        }

        @Override
        public Boolean call() throws Exception {
            return isServerActive(_url, _serverTimeout);
    }
}
    /**
     * @name isServerActive
     * @desc Ritorna true se il server è attivo, false altrimenti;
     * @param {String} url - Url di riferimento server.
     * @param {int} timeout - Timeout attesa connessione.
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    public boolean isServerActive(String url, int timeout) {
       //Log.v(TAG, "isServerActive(" + (new Gson()).toJson(url) + ", " + (new Gson()).toJson(timeout) + ")");
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
           //Log.v(TAG, "isServerActive(" + (new Gson()).toJson(url) + ", " + (new Gson()).toJson(timeout) + ") return: true");
            return true;
        } catch (IOException e) {
           //Log.e(TAG, "isServerActive(" + (new Gson()).toJson(url) + ", " + (new Gson()).toJson(timeout) + ") return: false [exception: " + (new Gson()).toJson(e) + "]");
            return false;
        }
    }

    /**
     * @name isInternetActive
     * @desc Ritorna true se esiste una connessione ad internet, false altrimenti.
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    @Override
    public boolean isInternetActive() {
        ConnectivityManager cm = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
