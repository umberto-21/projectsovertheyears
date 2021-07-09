package digitalsloths.socialtables.games.battleship.model;

import java.util.Objects;
import java.util.Observable;

import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import digitalsloths.socialtables.games.battleship.types.Shot;
import retrofit2.Callback;

/**
 * name AttackPhaseCommunication.java
 * author Umberto Andria
 * date 28/04/2016
 * brief 	Interfaccia che gestisce la business//Logic associata alla fase di attacco;
 * use Viene utilizzata dal AttackPhasePresenter per gestisce la business//Logic associata alla fase di attacco;
 */
public interface AttackPhaseModel {

    /**
     * @name getEnemyField
     * @desc Ritorna lo stato del campo avversario;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.AttackPhaseModel
     * @param callback
     */
    public void getEnemyField (Callback<Field> callback);

    /**
     * @name getShotNumber
     * @desc Ritorna il numero di colpi da sparare;
     * @returns {int}
     * @memberOf Client.Games.Battleship.Model.AttackPhaseModel
     */
    public void getShotNumber (Callback<Integer> callback);

    /**
     * @name sendShot
     * @desc chiama il metodo sendShoot sull'oggetto di tipo Client.Games.Battleship.Model.Communication.AttackPhaseCommunication;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.AttackPhaseModel
     */
    public void sendShot (Shot shot, Callback<Boolean> callback);

    /**
     * @name update
     * @desc Aggiorna lo stato del campo visualizzando il risultato dei colpi;
     * @param {Observable} observable - Rappresenta l'oggetto che ha subito un cambiamento;
     * @param {Object} object - Parametro non utilizzato;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.AttackPhaseModelImpl
     */
    public void addEndAttackPhaseObserver (Runnable runnable);
}
