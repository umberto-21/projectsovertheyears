package digitalsloths.socialtables.games.utility.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;
import digitalsloths.socialtables.games.battleship.presenter.BattleshipManager;
import digitalsloths.socialtables.games.battleship.presenter.BattleshipManagerImpl;
//import digitalsloths.socialtables.games.battleship.presenter.StartGamePresenterImpl;
import digitalsloths.socialtables.games.utility.types.Game;

/**
 * name UtilityManagerImpl
 * author Pinton Federico
 * date 27/05/2016
 * brief Implementa l'interfaccia che gestisce quale presenter del gestore dei giochi deve essere in esecuzione.
 * use Utilizzata da BaseFunctionManager per gestire l'avvio e l'esecuzione dei giochi.
 */
public class UtilityManagerImpl implements UtilityManager {

    private static final String TAG = "UtilityManagerImpl";

    private Game _game;
    private Activity _activity;

    /**
     * @name UtilityManagerImpl
     * @desc Costruttore della classe che inizializza il gioco, e riceve l'activity su cui eseguire gli errori;
     * @memberOf Client.Games.Utility.Presenter.UtilityManagerImpl
     */
    public UtilityManagerImpl(Activity activity,Game game){
       //Log.v(TAG, ".UtilityManagerImpl(Activity,"+ game.getName()+" )");
        _activity = activity;
        _game=game;
    }


    /**
     * @name loadGame
     * @desc Metodo che si occupa di far partire il gioco;
     * @param {Game} game - Gioco che il metodo deve far partire;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.UtilityManagerImpl
     */
    @Override
    public void loadGame(Game game) {
       //Log.v(TAG, ".loadGame("+ game.getName() +")");
        switch(game.getName()) {
            case "Battleship":
               //Log.v(TAG, ".switch.case : Battleship");
//                BattleshipManagerImpl.getBattleshipManagerIstance().start();
                Intent battleshipManagerImplIntent = new Intent(MainMenuPresenterImpl.getAppContext(), BattleshipManagerImpl.class);
                battleshipManagerImplIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainMenuPresenterImpl.getAppContext().startActivity(battleshipManagerImplIntent);
                break;
            default:
               //Log.v(TAG, ".switch.default");
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_activity);
                alertBuilder.setTitle("Games Loading Error");

                alertBuilder.setMessage("No available yet, sorry.");
                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertBuilder.show();
                break;
        }

    }

    /**
     * @name run
     * @desc Metodo che si occupa dell'avvio del gioco caricato;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.UtilityManagerImpl
     */
    @Override
    public void run() {
       //Log.v(TAG, ".run()");
        loadGame(_game);
    }
}
