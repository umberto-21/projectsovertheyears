package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.presenter.ErrorPresenterImpl;
import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;
import digitalsloths.socialtables.basefunctions.types.errors.BluetoothErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.basefunctions.types.errors.ErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.InternetErrorImpl;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.CreateTeamModel;
import digitalsloths.socialtables.games.battleship.model.CreateTeamModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.CreateTeamCommunicationImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ivan Parise
 * Created date: 25/05/2016
 * Description: Implementa l'interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'utente di creare una squadra;
 * Use: Utilizzato da BattleshipManager per gestire la fase di creazione del team.
 */
public class CreateTeamPresenterImpl extends AppCompatActivity implements CreateTeamPresenter{

    private static final String TAG = "CreateTeamPresenterImpl";
    private CreateTeamModel _createTeamModel;
    private BattleshipManager _battleshipManager;
    private BattleshipModel _battleshipModel;

    /**
     * @name onCreate
     * @desc Costruttore di default dell'activity per la creazione della squadra;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazione utili all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();
       //Log.i(TAG, "CreateTeamPresenterImpl.onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team_view);
        _createTeamModel = new CreateTeamModelImpl(new CreateTeamCommunicationImpl());
        _battleshipManager =  BattleshipManagerImpl.getBattleshipManagerIstance();
        _battleshipManager.setCreateTeam(this);
        run();
    }

    /**
     * @name run
     * @desc Metodo che fa eseguire i comandi di creazione del nuovo team, altrimenti visualizza gli errori;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenterImpl
     */
    @Override
    public void run() {
       //Log.i(TAG, ".run()");
        final Button enterButton = (Button) findViewById(R.id.okButton);
        assert enterButton != null;
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText =(EditText) findViewById(R.id.inputTeamName);
                String name = nameText.getText().toString();
                if(!name.equals("")) {
                    createTeam(name);
                }else{
                    Error error = new ErrorImpl("You can't put empty team name");
                    show(error);
                }
            }
        });

    }

    /**
     * @name show
     * @desc Metodo che si occupa della visualizzazione degli errori;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenterImpl
     */
    private void show(Error error){
       //Log.i(TAG, ".show(Error)");
        Intent errorIntent = new Intent(this.getApplicationContext(),ErrorPresenterImpl.class);
        Gson gson = new Gson();
        errorIntent.putExtra("error", gson.toJson(error));
        startActivity(errorIntent);
    }

    /**
     * @name createTeam
     * @desc Metodo che si occupa della creazione della suadra interagendo col model;
     * @param {String} name - Nome della squadra;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenterImpl
     */
    @Override
    public void createTeam(String name) {
       //Log.i(TAG, ".createTeam(String)");
        _createTeamModel.createTeam(name, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    if (response.body() == true) {
                        _battleshipManager.showTeamManagement();
                    } else {
                        //_battleshipManager.startPlay();
                        Error error = new ErrorImpl("Errore nella creazione del team.");
                        show(error);
                        // TODO: 6/14/16 Probabilmenta no chiude l'actitiy, provare a fare indietro 
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
}
