package digitalsloths.socialtables.games.battleship.model;

import java.util.Objects;
import java.util.Observable;

import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import retrofit2.Callback;

/**
 * name EnemyAttackPhaseModel.java
 * author Casotto Federico
 * date 27/05/2016
 * brief 	Interfaccia che gestisce le funzionalità associate all fase di attacco del team avversario;
 * use Utilizzata da EnemyAttackPhasePresenterImpl per gestire la fase di attacco del team avversario;s
 */
public interface EnemyAttackPhaseModel {

    /**
     * @name getAllayField
     * @desc Aggiorna lo stato del campo visualizzando il risultato dei colpi;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.EnemyAttackPhaseModel
     */
    public void getAllayField(Callback<Field> callback);

    /**
     * @name addEndEnemyAttackPhaseObserver
     * @desc Aggiorna lo stato del campo visualizzando il risultato dei colpi;
     * @param {Runnable} runnable - Rappresenta l'oggetto che ha subito un cambiamento;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.EnemyAttackPhaseModel
     */
    public void addEndEnemyAttackPhaseObserver (Runnable runnable);

}
