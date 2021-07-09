package digitalsloths.socialtables.games.battleship.model.communication;

import digitalsloths.socialtables.games.battleship.types.Field;
import digitalsloths.socialtables.games.battleship.types.FieldImpl;
import retrofit2.Callback;

/**
 * name AttackPhaseCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che gestisce la comunicazione con il server per le funzionalità associate all fase di attacco del team avversario;
 * use Utilizzata da EnemyAttackPhaseModelImpl per gestire la fase di attacco del team avversario;
 */
public interface EnemyAttackPhaseCommunication {
    /**
     * @name getAllayField
     * @desc Ritorna lo stato del campo del team dell'utente;
     * @returns {Field}
     * @memberOf Client.Games.Battleship.Model.Communication.EnemyAttackPhaseCommunication
     * @param callback
     */
    void getAllayField (Callback<Field> callback);

    /**
     * @name update
     * @desc Ritorna true sse c'è un aggiornamento nel server;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.EnemyAttackPhaseCommunication
     */
    void addEndEnemyAttackPhaseObserver(Runnable runnable); // TODO: 6/13/16  

    void removeEndEnemyAttackPhaseObserver(); // TODO: 6/13/16  
}
