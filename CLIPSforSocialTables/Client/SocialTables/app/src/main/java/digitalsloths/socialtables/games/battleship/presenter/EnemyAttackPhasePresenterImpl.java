package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.EnemyAttackPhaseModel;
import digitalsloths.socialtables.games.battleship.model.EnemyAttackPhaseModelImpl;
import digitalsloths.socialtables.games.battleship.model.SelectShipPositionModel;
import digitalsloths.socialtables.games.battleship.model.SelectShipPositionModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.EnemyAttackPhaseCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.SelectShipPositionCommunicationImpl;
import digitalsloths.socialtables.games.battleship.types.Cell;
import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import digitalsloths.socialtables.games.battleship.types.Grid;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * name EnemyAttackPhasePresenterImpl.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{} per dare la possibilità all'utente di visualizzare il risultato degli attacchi nemici
 * use Utilizzato da BattleshipManager per gestire la fase di attacco del team avversario.
 */
public class EnemyAttackPhasePresenterImpl extends AppCompatActivity implements EnemyAttackPhasePresenter{

    private static final String TAG = "EnemyAttackPresenter";

    private EnemyAttackPhaseModel enemyAttackPhaseModel;
    private SelectShipPositionModel _selectShipPositionModel = new SelectShipPositionModelImpl(new SelectShipPositionCommunicationImpl());
    private BattleshipModel battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();

    /**
     * @name onCreate
     * @desc 	Metodo predefinito di creazione di una nuova fase di battaglia del giocatore.
     * @param {Bundle} savedInstanceState - Parametro formale contenente l'insieme di informazioni aggiuntive fornite all'avvio dell'Activity.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.AttackPhasePresenter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

      //Log.i(TAG, "onCreate(...)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemy_attack_phase_view);

        enemyAttackPhaseModel = new EnemyAttackPhaseModelImpl(new EnemyAttackPhaseCommunicationImpl());

        updateTeamShips();

        updateAllayField();
        battleshipManager.setEnemyAttackPhaseActivity(this);
        battleshipManager.setEnemyAttackPhasePresenter(this);
       //Log.i(TAG, "onCreate(...) return");
    }

    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        updateAllayField();
    }

    @Override
    public void updateAllayField(){
        enemyAttackPhaseModel.getAllayField(new Callback<Field>() {
            @Override
            public void onResponse(Call<Field> call, Response<Field> response) {
               //Log.i(TAG, "getAllayField.onResponse");
                if(response.isSuccessful()){
                    Field allayField = response.body();
                   //Log.i(TAG, "getAllayField.response.body() = " + (new Gson().toJson(allayField)));
                    if(allayField != null){
                        showAllayField(allayField.getGrid());
                    }
                }
               //Log.i(TAG, "getAllayField.onResponse return");
            }

            @Override
            public void onFailure(Call<Field> call, Throwable t) {
               //Log.i(TAG, "getAllayField.onFailure");
               //Log.i(TAG, "getAllayField.onFailure return");
            }
        });

    }

    private void updateTeamShips(){
            _selectShipPositionModel.getTeamShip(new Callback<ArrayList<ShipImpl>>() {
                @Override
                public void onResponse(Call<ArrayList<ShipImpl>> call, Response<ArrayList<ShipImpl>> response) {
                    if(response.isSuccessful()){
                        List<ShipImpl> teamShips = response.body();
                        if(teamShips != null) {
                            showTeamShips(teamShips);
                            updateAllayField();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ShipImpl>> call, Throwable t) {

                }
            });
    }

    /**
     * @name showTeamShips
     * @desc Mostra sul terreno tutte le navi posizionate dalla squadra all'inizio della partita
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.EnemyAttackPhasePresenter
     */
    @Override
    public void showTeamShips(List<ShipImpl> ships) {

       //Log.i(TAG, ".showTeamShips()");


        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fieldFrame);

        for(Ship s:ships) {

            ImageView newShip = new ImageView(this); //creo la nuova nave

            int cell_dimension = (int) getResources().getDimension(R.dimen.cell_side); //prelevo dimensioni cella griglia
            int ship_width = (int) getResources().getDimension(R.dimen.ship_width); //prelevo dimensione nave
            int header_left = cell_dimension/3; //scostamento per togliere spazio header numeri

            FrameLayout.LayoutParams layoutParams; //variabile per settare valori posizione nave

            if (!s.isVertical()) {
                layoutParams = new FrameLayout.LayoutParams(ship_width, cell_dimension); //setto dimensione nave
                newShip.setImageResource(R.drawable.ship_h); //setto immagine da usare
            } else {
                layoutParams = new FrameLayout.LayoutParams(cell_dimension, ship_width);
                newShip.setImageResource(R.drawable.ship_v);
            }

            layoutParams.setMargins(cell_dimension * (s.getPosition().getX()+1) - header_left, cell_dimension * (s.getPosition().getY()+1) - header_left/3, 0, 0); //imposto posizione rispetto layout
            newShip.setLayoutParams(layoutParams); //aggiungo a layout

            frameLayout.addView(newShip);

        }

//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("It's your enemy turn!!");
//        alertDialogBuilder.show();

//        showAllayField(enemyAttackPhaseModel.getAllayField().getGrid());

    }

    /**
     * @name showAllayField
     * @desc Mostra sul terreno di gioco dell'utente i risultati dei colpi sparati dalla squadra avversaria
     * @param {Grid} grid - Il proprio terreno di gioco con lo stato delle celle in relazione ai colpi sparati dalla squadra avversaria.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.EnemyAttackPhasePresenter
     */
    @Override
    public void showAllayField(Grid grid) {
        String signature = "showAllayField(" + (new Gson().toJson(grid)) + ")";
      //Log.i(TAG, signature);

        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fieldFrame);
        final TableLayout field = (TableLayout)findViewById(R.id.fieldLayout);

        for(int i = 1; i < field.getChildCount(); i++) {
            final TableRow row = (TableRow)field.getChildAt(i);
            for(int j = 1; j < row.getChildCount(); j++) {
                final ImageView cellView = (ImageView)row.getChildAt(j);
                Cell cell = grid.getCell(j-1,i-1);

                //devo creare una nuova view per far veder i colpi sopra la nave
                ImageView shootView = new ImageView(this);

                int cell_dimension = (int) getResources().getDimension(R.dimen.cell_side); //prelevo dimensioni cella griglia
                int header_left = cell_dimension / 3; //scarto per sistemare immagini sopra caselle
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(cell_dimension,cell_dimension);

                if(cell.isMiss())
                    shootView.setImageResource(R.drawable.miss);

                if(cell.isHit())
                    shootView.setImageResource(R.drawable.hit);

                if(cell.isSunk())
                    shootView.setImageResource(R.drawable.explosion);

                layoutParams.setMargins(cell_dimension * j - header_left, cell_dimension * i, 0, 0); //imposto posizione rispetto layout
                shootView.setLayoutParams(layoutParams); //aggiungo a layout

                if(cell.isMiss() || cell.isHit() || cell.isSunk()) frameLayout.addView(shootView);

            }
        }
       //Log.i(TAG, signature + " return");
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
