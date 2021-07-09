package digitalsloths.socialtables.games.battleship.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.SelectShipPositionModel;
import digitalsloths.socialtables.games.battleship.model.SelectShipPositionModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.SelectShipPositionCommunicationImpl;
import digitalsloths.socialtables.games.battleship.types.PositionImpl;
import digitalsloths.socialtables.games.battleship.types.Ship;
import digitalsloths.socialtables.games.battleship.types.ShipImpl;
import digitalsloths.socialtables.games.battleship.types.ShipTypeImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * name BattleshipManager.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfacce che gestisce quale presenter di Battleship deve entrare in esecuzione;
 * use Utilizzata da UtilityManager per gestire il gioco di battaglia navale.
 */
public class SelectShipPositionPresenterImpl extends AppCompatActivity implements SelectShipPositionPresenter {

    private static final String TAG = "SelectShipPresenter";

    private SelectShipPositionModel _selectShipPositionModel;

    private BattleshipModel battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());

    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();

    private int _x,_y;
    private boolean _rotation = false; //false horizontal | true vertical
    private int nShips = 0;

    /**
     * @name onCreate
     * @desc Metodo per far iniziare l'Activity per l'inserimento delle navi sul campo.
     * @param {Bundle} savedInstanceState - Insieme di informazioni aggiuntive passate all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       //Log.i(TAG, "SelectShipPositionPresenterImpl.onCreate(Bundle)");

        super.onCreate(savedInstanceState);
        battleshipManager.setShipPositioningActiviy(this);
        setContentView(R.layout.activity_select_ship_position_view);

        _selectShipPositionModel = new SelectShipPositionModelImpl(new SelectShipPositionCommunicationImpl());

        updateShipCounter();

        setOnClickCell(); //inserisco evento click sulle celle per posizionamento

        callBackShips();

        _selectShipPositionModel.addTeamShipsPositionObserver(new Callback<ArrayList<ShipImpl>>() {
            @Override
            public void onResponse(Call<ArrayList<ShipImpl>> call, Response<ArrayList<ShipImpl>> response) {
                if(response.isSuccessful()){
                   //Log.i(TAG, "addTeamShipsPositionObserver.onResponse....");
                    ArrayList<ShipImpl> teamShips = response.body();
                    if(teamShips != null) {
                        placeShips(teamShips);
                       //Log.i(TAG, "addTeamShipsPositionObserver.onResponse :" + (new Gson()).toJson(teamShips));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ShipImpl>> call, Throwable t) {

            }
        });



    }

    /**
     * @name setOnClickCell
     * @desc Per ogni cella del campo imposta un ascoltatore del tap dell'utente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenterImpl
     */
    private void setOnClickCell() {

       //Log.i(TAG, ".setOnClickCell()");

        final TableLayout field = (TableLayout)findViewById(R.id.fieldLayout);

        for(int i = 1; i < field.getChildCount(); i++) {
            final TableRow row = (TableRow)field.getChildAt(i);
            for(int j = 1; j < row.getChildCount(); j++) {
                final ImageView cell = (ImageView)row.getChildAt(j);
                cell.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        _x = row.indexOfChild(cell)-1;
                        _y = field.indexOfChild((TableRow)cell.getParent())-1;
                        Ship ship = new ShipImpl(_rotation,3,new PositionImpl(_x,_y));
                       //Log.i("CLICK", "X: " + _x + " Y: " + _y);
                        if(nShips > 0) {
                            nShips = nShips - 1;
                            setShipCounter(nShips);
                            final ImageView shipImmage = placeShip(ship);
                            final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fieldFrame);
                            _selectShipPositionModel.setShipPosition(ship, new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.isSuccessful()) {
                                        Boolean isShipSet = response.body();
                                        if (isShipSet != null && isShipSet == true) {
                                           //Log.i(TAG, ".nave accettata...");
                                            //callBackShips();
                                        } else {
                                           //Log.i(TAG, ".nave non accettata...");

                                            nShips = nShips + 1; //todo race condition

                                            setShipCounter(nShips);
                                            shipImmage.setImageResource(android.R.color.transparent);
                                            frameLayout.removeView(shipImmage);
                                        }
                                    } else {
                                       //Log.i(TAG, ".nave non accettata...");

                                        nShips = nShips + 1; //todo race condition

                                        setShipCounter(nShips);
                                        shipImmage.setImageResource(android.R.color.transparent);
                                        frameLayout.removeView(shipImmage);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    nShips = nShips + 1;
                                    setShipCounter(nShips);
                                    shipImmage.setImageResource(android.R.color.transparent);
                                    frameLayout.removeView(shipImmage);
                                }
                            });
                        }
                    }
                });
            }
        }

    }

    private void callBackShips(){
        _selectShipPositionModel.getTeamShip(new Callback<ArrayList<ShipImpl>>() {
            @Override
            public void onResponse(Call<ArrayList<ShipImpl>> call, Response<ArrayList<ShipImpl>> response) {
                if(response.isSuccessful()){
                    List<ShipImpl> teamShips = response.body();
                    if(teamShips != null) {
                        placeShips(teamShips);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ShipImpl>> call, Throwable t) {

            }
        });
    }

    private ImageView placeShip(Ship s){
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fieldFrame);

            ImageView newShip = new ImageView(this); //creo la nuova nave

            int cell_dimension = (int) getResources().getDimension(R.dimen.cell_side); //prelevo dimensioni cella griglia
            int ship_width = (int) getResources().getDimension(R.dimen.ship_width); //prelevo dimensione nave
            int header_left = cell_dimension / 3;

            FrameLayout.LayoutParams layoutParams; //variabile per settare valori posizione nave

            if (!s.isVertical()) {
                layoutParams = new FrameLayout.LayoutParams(ship_width, cell_dimension); //setto dimensione nave
                newShip.setImageResource(R.drawable.ship_h); //setto immagine da usare
            } else {
                layoutParams = new FrameLayout.LayoutParams(cell_dimension, ship_width);
                newShip.setImageResource(R.drawable.ship_v);
            }

            int x_coordinate = cell_dimension * (s.getPosition().getX() + 1) - header_left;
            int y_coordinate = cell_dimension * (s.getPosition().getY() + 1) - header_left/3;

            layoutParams.setMargins(x_coordinate,y_coordinate, 0, 0); //imposto posizione rispetto layout
            newShip.setLayoutParams(layoutParams); //aggiungo a layout

            frameLayout.addView(newShip);

        return newShip;

//            updateShipCounter();

    }
    /**
     * @name placeShips
     * @desc Rimuove il componente del team indicato.
     * @param {Profile} profile - Stringa contenente il nome del membro del team da rimuovere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenterImpl
     */
    @Override
    public void placeShips(List<ShipImpl> teamShips) {
       //Log.i(TAG, ".placeShips()");

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fieldFrame);

        for(Ship s:teamShips) {

             placeShip(s);
        }

    }

    private void setShipCounter(int shipCounter){
        TextView shipsPlacedView = (TextView) this.findViewById(R.id.numberShips);
        shipsPlacedView.setText(String.valueOf(shipCounter));
    }

    private void updateShipCounter(){
        _selectShipPositionModel.getShipsToPositioning(new Callback<ArrayList<ShipTypeImpl>>() {
            @Override
            public void onResponse(Call<ArrayList<ShipTypeImpl>> call, Response<ArrayList<ShipTypeImpl>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ShipTypeImpl> shipList = response.body();
                    if(shipList != null) {
                        nShips = shipList.size();
                    } else {
                        nShips = 0;
                    }
                    setShipCounter(shipList.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ShipTypeImpl>> call, Throwable t) {

            }
        });
    }

    /**
     * @name reverseShip
     * @desc Rimuove il componente del team indicato.
     * @param {Profile} profile - Stringa contenente il nome del membro del team da rimuovere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.SelectShipPositionPresenterImpl
     */
    @Override
    public void reverseShip(View view) {

       //Log.i(TAG, ".reverseShip(View)");

        //prendo riferimento alla view della nave
        ImageView ship = (ImageView) findViewById(R.id.shipView);

        //prendo riferimento al layout soprastante e tolgo la view
        LinearLayout layout = ((LinearLayout)ship.getParent());
        layout.removeView(ship);

        //modifico le dimensioni della view per rotarla
        ship.setLayoutParams(new LinearLayout.LayoutParams(ship.getHeight(),ship.getWidth()));

        //capisco la rotazione per inserire la giusta immagine
        if(!_rotation) {
            ship.setImageResource(R.drawable.ship_v);
            _rotation = true;
        }else{
            ship.setImageResource(R.drawable.ship_h);
            _rotation = false;
        }

        //aggiungo la nuova vista
        layout.addView(ship);

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
    public void onDestroy(){
        super.onDestroy();
        _selectShipPositionModel.removeTeamShipsPositionObserver();
    }
}
