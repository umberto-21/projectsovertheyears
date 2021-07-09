package digitalsloths.socialtables.games.battleship.model;

import com.google.gson.Gson;

import android.util.Log;

import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModelImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunication;
import retrofit2.Callback;

/**
 * name BattleshipModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la bussines//Logic\g{} legata al gioco di Battleship;
 * use Viene utilizzata dai presenter per gestire la bussines//Logic\g{} legata al gioco di Battleship;
 */
public class BattleshipModelImpl implements BattleshipModel {

    private static final String TAG = "BattleshipModelImpl";
    private BattleshipCommunication _battleshipCommunication;
    private static EnvironmentServiceModel _serviceModel = EnvironmentServiceModelImpl.getIstance();

    /**
     * @name BattleshipModelImpl
     * @desc costruttore
     * @param {BattleshipCommunication} battleshipCommunication - rappresenta l'oggetto tramite cui fare chiamate al server
     * @param {Profile} profile - profilo utente;
     * @param {Team} team - team associato al profilo;
     * @memberOf Client.Games.Battleship.Model.BattleshipModelImpl
     */
    public BattleshipModelImpl (BattleshipCommunication battleshipCommunication) {
       //Log.v(TAG, ".BattleshipModelImpl(...)");
        _battleshipCommunication =battleshipCommunication;
       //Log.v(TAG, ".BattleshipModelImpl(...)");
    }

    /**
     * @name addBattleEndObserver
     * @desc Aggiunge ascoltatore conclusione partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addBattleEndObserver(Runnable runnable) {
       //Log.v(TAG, ".addBattleEndObserver ()");
        _battleshipCommunication.addBattleEndObserver(runnable);
       //Log.v(TAG, ".addBattleEndObserver () return: void" );

    }

    /**
     * @name isBattleRunning
     * @desc Ritorna true se c'è una battaglia in corso, false altrimenti.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void isBattleRunning(Callback<Boolean> callback) {
       //Log.v(TAG, ".isBattleRunning()");
        Beacon beacon =_serviceModel.getNearestBeacon();
        _battleshipCommunication.isBattleRunning (beacon, callback);
       //Log.v(TAG, ".isBattleRunning() return: void");
    }

    /**
     * @name addStartBattleObserver
     * @desc Aggiunge ascoltatore inizio partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addStartBattleObserver(Runnable runnable) {
       //Log.v(TAG, ".addStartBattleObserver ()");
        _battleshipCommunication.addStartBattleObserver(runnable); //--> no implemented method
       //Log.v(TAG, ".addStartBattleObserver () return: void ");

    }

    /**
     * @name isCaptain
     * @desc ritorna true sse il parametro profile è il capitano della squadra;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void isCaptain(Callback<Boolean> callback) {
       //Log.v(TAG, ".isCaptain( ... )");
        _battleshipCommunication.isCaptain (callback);
       //Log.v(TAG, ".isCaptain( ... ) return: void " );
    }

    /**
     * @name addStartAttackPhaseObserver
     * @desc Aggiunge ascoltatore inzio fase di attacco utente;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addStartAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, ".addStartAttackPhaseObserver ()");
        _battleshipCommunication.addStartAttackPhaseObserver (runnable);
       //Log.v(TAG, ".addStartAttackPhaseObserver () return: void" );
    }

    /**
     * @name addStartEnemyAttackPhaseObserver
     * @desc Aggiunge ascoltatore fase di attaco avversaria;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addStartEnemyAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, ".addStartEnemyAttackPhaseObserver ()");
        _battleshipCommunication.addStartEnemyAttackPhaseObserver (runnable);
       //Log.v(TAG, ".addStartEnemyAttackPhaseObserver () return: void" );

    }

    /**
     * @name addStartSearchingOpponentObserver
     * @desc Aggiunge ascoltatore inzio ricerca avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addStartSearchingOpponentObserver(Runnable runnable) {
       //Log.i(TAG, ".addStartSearchingOpponentObserver ()");
        _battleshipCommunication.addStartSearchingOpponentObserver(runnable);
       //Log.v(TAG, ".addStartSearchingOpponentObserver () return void ");
        //return result;
    }

    /**
     * @name addStopSearchingOpponentObserver
     * @desc Aggiunge ascoltatore fine ricerca avversario;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void addStopSearchingOpponentObserver(Runnable runnable) {
       //Log.v(TAG, ".addStopSearchingOpponentObserver ()");
        _battleshipCommunication.addStopSearchingOpponentObserver(runnable);
       //Log.v(TAG, ".addStopSearchingOpponentObserver () return: void" );
    }

    /**
     * @name isTeamExists
     * @desc Ritorna true sse il team associato al beacon è già stato creato.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void isTeamExists(Callback<Boolean> callback) {
       //Log.v(TAG, ".isTeamExists()");
        Beacon beacon =_serviceModel.getNearestBeacon();
        _battleshipCommunication.isTeamExists (beacon, callback);
       //Log.v(TAG, ".isTeamExists() return: void");
    }

    /**
     * @name joinTeam
     * @desc Ritorna true se l'inserimento dell'utente è andato a buon fine.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.BattleshipModel
     */
    @Override
    public void joinTeam(Callback<Boolean> callback) {
       //Log.v(TAG, ".joinTeam()");
        Beacon beacon =_serviceModel.getNearestBeacon();
        _battleshipCommunication.joinTeam (beacon, callback);
       //Log.v(TAG, ".joinTeam() return:void " );
    }

    public void removeStartSearchingOpponentObserver(){
        _battleshipCommunication.removeStartSearchingOpponentObserver();
    }

    public void removeStopSearchingOpponentObserver(){
        _battleshipCommunication.removeStopSearchingOpponentObserver();
    }

    public void removeStartEnemyAttackPhaseObserver(){
        _battleshipCommunication.removeStartEnemyAttackPhaseObserver();
    }

    public void removeBattleEndObserver(){
        _battleshipCommunication.removeBattleEndObserver();
    }

    public void removeStartBattleObserver(){
        _battleshipCommunication.removeStartBattleObserver();
    }

    @Override
    public void removeStartAttackPhaseObserver() {
        _battleshipCommunication.removeStartAttackPhaseObserver();
    }

    @Override
    public  Boolean isBattleEnd() {
        return _battleshipCommunication.isBattleEnd();
    }
}
