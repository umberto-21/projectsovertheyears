package digitalsloths.socialtables.games.battleship.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.WaitingOpponentModel;
import digitalsloths.socialtables.games.battleship.model.WaitingOpponentModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.WaitingOpponentCommunication;
import digitalsloths.socialtables.games.battleship.model.communication.WaitingOpponentCommunicationImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * name WaitingOpponentPresenterImpl
 * author Pinton Federico
 * date 30/05/2016
 * brief Implementa l'interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{}
 * per dare la possibilità ricercare ed aspettare un opponente.
 * use Utilizzato da BattleshipManager per gestire la ricerca e l'attesa di un opponente.
 */
public class WaitingOpponentPresenterImpl extends AppCompatActivity implements WaitingOpponentPresenter {
    private static final String TAG = "WaitOpponPresenter";
    private WaitingOpponentModel _waitingOpponentModel;
    private BattleshipModel battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();


    /**
     * @name onCreate
     * @desc Costruttore di default dell'activity per l'attesa dell'avversario;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazione utili all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.WaitingOpponentPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       //Log.i(TAG, ".onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_opponent);
        _waitingOpponentModel= new WaitingOpponentModelImpl(new WaitingOpponentCommunicationImpl());
//        battleshipModel.removeStartSearchingOpponentObserver();
        battleshipManager.setWaitingOpponentActivity(this);
    }


    /**
     * @name exitSearchingOpponent
     * @desc Metodo che stoppa la ricerca di un avversario;
     * @param {View} view - vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.WaitingOpponentPresenterImpl
     */
    @Override
    public void exitSearchingOpponent(View view) {
       //Log.i(TAG, ".exitSearchingOpponent(View)");
        _waitingOpponentModel.stopSearchingOpponent(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        BattleshipManagerImpl.getBattleshipManagerIstance().exitSearchingOpponent();
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        battleshipModel.removeStartBattleObserver();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            battleshipManager.leaveTeam();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
