package digitalsloths.socialtables.basefunctions.model.communication;

import digitalsloths.socialtables.types.Profile;

/**
 * name EnvironmentCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Gestisce la comunicazione tra client e server per le funzionalità di base;
 * use Viene utilizzata per comunicare con il server;
 */
public interface EnvironmentCommunication {
    /**
     * @name isServerActive
     * @desc Ritorna true se il server è attivo, false altrimenti;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    boolean isServerActive();
    /**
     * @name isInternetActive
     * @desc Ritorna true se esiste una connessione ad internet, false altrimenti.
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.EnvironmentCommunication
     */
    boolean isInternetActive();
}
