package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Objects;
import java.util.Observable;

import digitalsloths.socialtables.games.battleship.model.communication.EnemyAttackPhaseCommunication;
import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import retrofit2.Callback;

/**
 * name EnemyAttackPhaseModelImpl.java
 * author Casotto Federico
 * date 27/05/2016
 * brief 	Interfaccia che gestisce le funzionalità associate all fase di attacco del team avversario;
 * use Utilizzata da EnemyAttackPhasePresenterImpl per gestire la fase di attacco del team avversario;
 */
public class EnemyAttackPhaseModelImpl extends Observable implements EnemyAttackPhaseModel {

    private static final String TAG = "AttackPhaseModelImpl";

    private EnemyAttackPhaseCommunication _enemyAttackPhaseCommunication;

    /**
     * @name EnemyAttackPhaseModelImpl
     * @desc costruttore
     * @param {AttackPhaseCommunication} com - attackPhaseCommunication
     * @memberOf Client.Games.Battleship.Model.EnemyAttackPhaseModelImpl
     */
    public EnemyAttackPhaseModelImpl (EnemyAttackPhaseCommunication com) {

       //Log.v(TAG, ".AttackPhaseModelImpl()");
        _enemyAttackPhaseCommunication = com;
       //Log.v(TAG, ".AttackPhaseModelImpl()");
    }

    /**
     * @name getAllayField
     * @desc Aggiorna lo stato del campo visualizzando il risultato dei colpi;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.EnemyAttackPhaseModel
     */
    @Override
    public void getAllayField(Callback<Field> callback) {
       //Log.v(TAG, ".getAllayField ()");
        _enemyAttackPhaseCommunication.getAllayField(callback);
       //Log.v(TAG, ".getAllayField () return: void" );
    }

    /**
     * @name addEndEnemyAttackPhaseObserver
     * @desc Aggiorna lo stato del campo visualizzando il risultato dei colpi;
     * @param {Runnable} runnable - Rappresenta l'oggetto che ha subito un cambiamento;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.EnemyAttackPhaseModel
     */
    @Override
    public void addEndEnemyAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, ".addEndEnemyAttackPhaseObserver ()");
        _enemyAttackPhaseCommunication.addEndEnemyAttackPhaseObserver (runnable);
       //Log.v(TAG, ".addEndEnemyAttackPhaseObserver() return: void");
    }
}
