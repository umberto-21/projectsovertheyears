package digitalsloths.socialtables.games.battleship.model;

import java.util.Observable;

import digitalsloths.socialtables.games.battleship.model.communication.AttackPhaseCommunication;
import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.Shot;
import retrofit2.Callback;

/**
 * name AttackPhaseCommunication.java
 * author Umberto Andria
 * date 28/04/2016
 * brief 	Interfaccia che gestisce la business//Logic associata alla fase di attacco;
 * use Viene utilizzata dal AttackPhasePresenter per gestisce la business//Logic associata alla fase di attacco;
 */
public class AttackPhaseModelImpl extends Observable implements AttackPhaseModel{

    private static final String TAG = "AttackPhaseModelImpl";
    AttackPhaseCommunication _attackPhaseCommunication;

    /**
     * @name AttackPhaseModelImpl
     * @desc costruttore
     * @param {AttackPhaseCommunication} com - attackPhaseCommunication
     * @memberOf Client.Games.Battleship.Model.AttackPhaseModelImpl
     */
    public AttackPhaseModelImpl (AttackPhaseCommunication com) {

       //Log.v(TAG, ".AttackPhaseModelImpl(" + (new Gson()).toJson(com) + ")");
        _attackPhaseCommunication =com;
       //Log.v(TAG, ".AttackPhaseModelImpl(" + (new Gson()).toJson(com) + ")");
    }

    @Override
    public void getEnemyField(Callback<Field> callback) {
       //Log.v(TAG, ".getEnemyField ()");
        _attackPhaseCommunication.getEnemyField(callback);
       //Log.v(TAG, ".getEnemyField () return: void");

    }

    @Override
    public void getShotNumber(Callback<Integer> callback) {
       //Log.v(TAG, ".getShotNumber ()");
        _attackPhaseCommunication.getShotNumber (callback);
       //Log.v(TAG, ".getShotNumber () return: void" );
    }

    @Override
    public void sendShot(Shot shot, Callback<Boolean> callback) {
       //Log.v(TAG, ".sendShoot() " );
        _attackPhaseCommunication.sendShot(shot, callback);
       //Log.v(TAG, ".sendShoot() " );
    }

    @Override
    public void addEndAttackPhaseObserver(Runnable runnable) {
       //Log.v(TAG, ".addEndAttackPhaseObserver ( ... )");
        _attackPhaseCommunication.addEndAttackPhaseObserver(runnable);
       //Log.v(TAG, ".addEndAttackPhaseObserver () return : void");
    }
}
