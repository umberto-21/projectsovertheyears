package digitalsloths.socialtables.basefunctions.model.communication;

import digitalsloths.socialtables.types.Session;

/**
 * name Communication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che gestisce le funzionalità di comunicazione con il server;
 * Utilizzata dai package communication per avere le funzionalità di comunicazione di base;
 */
public interface Communication {

    /**
     * @name StartSession
     * @desc Ritorna true sse la creazione di una sessione nel server è riuscità;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    Session getSession();

    /**
     * @name getServerUrl
     * @desc Ritorna l'url del server;
     * @returns {String}
     * @memberOf Client.BaseFunctions.Model.Communication.Communication
     */
    String getServerUrl();
}
