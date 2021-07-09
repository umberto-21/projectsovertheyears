package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.AttackPhaseModel;
import digitalsloths.socialtables.games.battleship.model.AttackPhaseModelImpl;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.AttackPhaseCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.types.Cell;
import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.battleship.types.PositionImpl;
import digitalsloths.socialtables.games.battleship.types.ShotImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * name AttackPhasePresenterImpl.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} legata alla fase di attacco
 * use Utilizzato da BattleshipManager per gestire la fase di attacco.
 */
public class AttackPhasePresenterImpl extends AppCompatActivity implements AttackPhasePresenter {

    private static final String TAG = "AttackPresenterImpl";
    private AttackPhaseModel _attackPhaseModel;
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();

    private int shots = 0;
    private List<ImageView> sniperList = new LinkedList<>();

    /**
     * @name onCreate
     * @desc 	Metodo predefinito di creazione di una nuova fase di battaglia del giocatore.
     * @param {Bundle} savedInstanceState - Parametro formale contenente l'insieme di informazioni aggiuntive fornite all'avvio dell'Activity.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      //Log.i(TAG, "AttackPhasePresenterImpl.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_phase_view);

        _attackPhaseModel = new AttackPhaseModelImpl(new AttackPhaseCommunicationImpl());


        updateShotNumberView(shots);
        updateField();
        updateShotNumber();
        battleshipManager.setAttackPhaseActivity(this);
        battleshipManager.setAttackPhasePresenter(this);




    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        updateField();
        updateShotNumber();
    }

    @Override
    public void showResult(){
        shots = 0;
        updateShotNumberView(shots);
        updateField();
    }

    @Override
    public void updateField(){
        _attackPhaseModel.getEnemyField(new Callback<Field>() {
            @Override
            public void onResponse(Call<Field> call, Response<Field> response) {
                if(response.isSuccessful()){
                    Field field = response.body();
                    if(field != null){
                        deleteAllSniper();
                        showField(field.getGrid());
                    }
                }
            }

            @Override
            public void onFailure(Call<Field> call, Throwable t) {

            }
        });
    }

    private void addSniper(ImageView imageView){
        sniperList.add(imageView);
    }

    private void deleteAllSniper(){
        for(ImageView sniper: sniperList){
//            sniper.setImageResource(android.R.color.transparent);
        }
    }

    /**
     * @name showShootResult
     * @desc Notifica al server le coordinate del colpo sparato dall'utente
     * @param {Grid} grid - Il terreno di gioco avversario con lo stato delle celle in relazione ai colpi sparati dalla squadra.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    @Override
    public void showField(Grid grid) {

       //Log.i(TAG, ".showShootResult()");
        final TableLayout field = (TableLayout)findViewById(R.id.fieldLayout);

        for(int i = 1; i < field.getChildCount(); i++) {
            final TableRow row = (TableRow)field.getChildAt(i);
            for(int j = 1; j < row.getChildCount(); j++) {
                final ImageView cellView = (ImageView)row.getChildAt(j);
                cellView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int y = field.indexOfChild((TableRow)cellView.getParent()) - 1;
                        int x = row.indexOfChild(cellView) - 1;
                        if(shots > 0) {
                            cellView.setImageResource(R.drawable.sniper);
                            addSniper(cellView);
                            insertNextShoot(x, y); //spara solo se hai colpi a disposizione
                        }
                       //Log.i(TAG, "spara colpo (" + x + ", " + y + ")");
                    }
                });
                Cell cell = grid.getCell(j-1, i-1);
                if(cell.isMiss()) cellView.setImageResource(R.drawable.miss);
                if(cell.isHit()) cellView.setImageResource(R.drawable.hit);
                if(cell.isSunk()) cellView.setImageResource(R.drawable.sink_icon);
                if(cell.isUnknown()) cellView.setImageResource(android.R.color.transparent);

            }
        }

    }

    /**
     * @name insertNextShoot
     * @desc Presenta sul terreno di gioco i risultati dei colpi sparati dalla squadra dell'utente
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    @Override
    public void insertNextShoot(int x, int y) {

       //Log.i(TAG, ".insertNextShoot()");
        updateShotNumberView(shots);
        if(shots <= 0) return ;
        shots = shots -1;
        updateShotNumberView(shots);
        _attackPhaseModel.sendShot(new ShotImpl(new PositionImpl(x, y)), new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Boolean isSendShot = response.body();
                    if(isSendShot != null && isSendShot){
                       //Log.i(TAG, ".insertNextShoot() colpo accettato");
//                        updateShotNumber();
                    }else {

                       //Log.i(TAG, ".insertNextShoot() colpo non accettato");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                shots = shots + 1;
                                updateShotNumberView(shots);
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

               //Log.i(TAG, ".insertNextShoot() colpo non inviato");

//                shots = shots + 1;
//                updateShotNumberView(shots);
            }
        });

    }

    private void updateShotNumberView(final int nColpi){

        TextView shootsView = (TextView) findViewById(R.id.shootLeft);
        shootsView.setText(String.valueOf(nColpi));


    }

    private void updateShotNumber(){
       //Log.i(TAG, ".updateShotNumber");

        _attackPhaseModel.getShotNumber(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Integer nColpi = response.body();
                   //Log.i(TAG, ".numero colpi rimanenti " +(new Gson()).toJson(nColpi));
                    if(nColpi != null){
                        shots = nColpi;
                    }
                    updateShotNumberView(shots);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            battleshipManager.leaveTeam();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
