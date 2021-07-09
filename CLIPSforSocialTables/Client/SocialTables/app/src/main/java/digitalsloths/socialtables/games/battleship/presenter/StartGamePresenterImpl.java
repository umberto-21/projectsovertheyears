package digitalsloths.socialtables.games.battleship.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.ProfileModel;
import digitalsloths.socialtables.games.battleship.model.ProfileModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.ProfileCommunicationImpl;

/**
 * name StartGamePresenterImpl.java
 * author Pinton Federico
 * date 13/05/2016
 * brief Implementa l'interfaccia che gestisce l'application//Logic
 * legata alla fase iniziale dove si può visualizzare la classifica del locale o iniziare a giocare;
 * use Utilizzato da BattleshipManager per gestire la fase di scelta se iniziare il gioco o visualizzare la classifica del locale;
 */
public class StartGamePresenterImpl extends AppCompatActivity implements StartGamePresenter {
    private static final String TAG = "StartGamePresenterImpl";
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();
    /**
     * @name onCreate
     * @desc Costruttore dell'activity che fa partire il gioco;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazioni utili all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.StartGamePresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
       //Log.i(TAG, ".onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_view);
        battleshipManager.setStartGameActivity(this);

    }

    /**
     * @name startGame
     * @desc Metodo che fa partire il gioco selezionato;
     * @param {View} view - Vista di riferimento che fa partire il metodo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.StartGamePresenterImpl
     */
    @Override
    public void startGame(View view) {
       //Log.i(TAG, ".startGame(View)");
        battleshipManager.startPlay();
    }

    /**
     * @name showLeaderboard
     * @desc Metodo che fa partire l'activity di visualizzazione della classifica;
     * @param {View} view - Vista di riferimento che fa partire il metodo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.StartGamePresenterImpl
     */
    @Override
    public void showLeaderboard(View view) {
       //Log.i(TAG, ".showLeaderboard(View)");
        battleshipManager.showLeaderboard();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        battleshipManager.exit();
    }
    
}
