package digitalsloths.socialtables.basefunctions.presenter;

import com.google.gson.Gson;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.model.EnvironmentModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentModelImpl;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModelImpl;
import digitalsloths.socialtables.basefunctions.model.communication.EnvironmentCommunication;
import digitalsloths.socialtables.basefunctions.model.communication.EnvironmentCommunicationImpl;
import digitalsloths.socialtables.basefunctions.types.errors.BluetoothErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.basefunctions.types.errors.ErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.InternetErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.ServerErrorImpl;
//import digitalsloths.socialtables.games.battleship.presenter.StartGamePresenterImpl;
//import digitalsloths.socialtables.games.battleship.presenter.TeamManagementPresenterImpl;
import digitalsloths.socialtables.games.utility.presenter.GamesListPresenterImpl;
import digitalsloths.socialtables.profile.presenter.ProfileManager;
import digitalsloths.socialtables.profile.presenter.ProfileManagerImpl;

/**
 * name MainMenuPresenterImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce View::MainMenu implementando la application//Logic e la presentation//Logic per la selezione e
 * l'avvio di una delle funzionalità offerte (Gestione profilo, Chat, Bacheca, Battaglia Navale)
 * use Viene utilizzata per visualizzare il menù iniziale dell'applicazione
 */
public class MainMenuPresenterImpl extends AppCompatActivity implements MainMenuPresenter {


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private EnvironmentModel _environment;
    private static Context _context;

    /**
     * @name onCreate
     * @desc Costruttore di default dell'activity principale dell'applicazione;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazioni utili all'Activity;
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_view);

        _context = getApplicationContext();

        EnvironmentServiceModel environmentServiceModel = EnvironmentServiceModelImpl.getIstance();
        EnvironmentCommunication environmentCommunication = new EnvironmentCommunicationImpl();
        _environment = new EnvironmentModelImpl(environmentCommunication,environmentServiceModel);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    //@Override 
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
                builder.show();
            }
            }

    }

    /**
     * @name onResume
     * @desc Metodo chiamato alla ripresa dell'attività dopo che era stata messa in attesa;
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void onResume(){
        super.onResume();
        checkStatus();
    }

    /**
     * @name checkStatus
     * @desc Fa partire i controlli d'ambiente e visualizza un errore in caso manchino la connessione dati
     * o il server sia irraggiungibile o il bluetooth sia disattivato;
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void checkStatus() {

        // controlla finchè tutte le condizioni non sono verificate
        // segnala un errore alla volta

        if (!_environment.isBluetoothActive()) {
            Error error = new BluetoothErrorImpl();
            show(error);
        }
        else {
            if (!_environment.isInternetActive()) {
                Error error = new InternetErrorImpl();
                show(error);
            } else {
                if (!_environment.isServerActive()) {
                    Error error = new ServerErrorImpl();
                    show(error);
                }
            }
        }
    }

    /**
     * @name show
     * @desc Metodo per avviare l'Activity di visualizzazione dell'errore.
     * @param {Error} error - L'errore da visualizzare.
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    private void show(Error error){
        Intent errorIntent = new Intent(this.getApplicationContext(),ErrorPresenterImpl.class);
        Gson gson = new Gson();
        errorIntent.putExtra("error", gson.toJson(error));
        startActivity(errorIntent);
    }

    /**
     * @name loadProfileManager
     * @desc Chiama il manager della chat alla pressione del pulsante di avvio della chat;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void loadProfileManager(View view){

        //Chiamata al ProfileManager
        ProfileManager profileManager = new ProfileManagerImpl(getAppContext());
        profileManager.onStart();

    }

    /**
     * @name loadGameManager
     * @desc Chiama il manager dei giochi alla pressione del pulsante selettore dei giochi;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void loadGamesManager(View view){

        Intent gameManager = new Intent(getApplicationContext(), GamesListPresenterImpl.class);
        startActivity(gameManager);
    }

    /**
     * @name loadChatManager
     * @desc Chiama il manager della bacheca alla pressione del pulsante di accesso alla bacheca;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void loadChatManager(View view) {
        show(new ErrorImpl("Not available yet, sorry."));
    }

    /**
     * @name loadNoticeBoardManager
     * @desc Chiama il profile manager alla pressione del pulsante di gestione profilo;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void loadNoticeBoardManager(View view) {
        show(new ErrorImpl("Not available yet, sorry."));
    }

    /**
     * @name getAppContext
     * @desc Metodo statico per ottenere il contesto dell'Activity principale.
     * @return {Context} - Contesto dell'Activity principale.
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenterImpl
     */
    public static Context getAppContext() {
        return _context;
    }

    /**
     * @name onRequestPermissionsResult
     * @desc Visualizza informazioni riguardo i permessi che l'utente ha a disposizione;
     * @param {int} requestCode - Codice di richiesta;
     * @param {String} permissions[] - Array di stringhe contenenti i vari permessi;
     * @param {int} grantResults[] - Array dei codici dei permessi garantiti;
     * @returns {void}
     * @memberOf Client.BaseFunctions.Presenter.MainMenuPresenterImpl
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Log.v(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }

    }


}
