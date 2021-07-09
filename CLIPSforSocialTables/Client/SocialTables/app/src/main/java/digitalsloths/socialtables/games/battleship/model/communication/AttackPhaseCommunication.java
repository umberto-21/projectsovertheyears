package digitalsloths.socialtables.games.battleship.model.communication;

import java.util.Observable;

import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import digitalsloths.socialtables.games.battleship.types.Shot;
import retrofit2.Callback;

/*
 * name AttackPhaseCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia per gestire la comunicazione nella fase di attacco
 * use Utilizzata da AttackPhaseModel per gestire la comunicazione nella fase di attacco;
 */
public interface AttackPhaseCommunication {

    /**
     * @name getEnemyField
     * @desc richiede al server il Field;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     * @param callback
     */
    void getEnemyField (Callback<Field> callback);

    /**
     * @name getShotNumber
     * @desc Ritorna il numero di colpi da sparare;
     * @returns {int}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    void getShotNumber(Callback<Integer> callback);

    /**
     * @name sendShoot
     * @desc manda le coordinate sul colpo al server;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    void sendShot(Shot shot, Callback<Boolean> callback);

    /**
     * @name update
     * @desc chiama il metodo update della classe AttackPhaseModel;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.AttackPhaseCommunication
     */
    void addEndAttackPhaseObserver(Runnable runnable); // TODO: 6/13/16

    void removeEndAttackPhaseObserver(); // TODO: 6/13/16

}
