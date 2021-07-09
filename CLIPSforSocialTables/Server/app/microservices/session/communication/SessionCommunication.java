package microservices.session.communication;

import microservices.session.types.Session;

import play.mvc.*;


/**
 *file SessionCommunication.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Interfaccia che gestisce la comunicazione tra server e client riguardante le funzionalità legate alle sessioni, quindi rimane in ascolto delle richieste che arrivano dal esterno del sistema server.
 *use Viene utilizzata per gestire la comunicazione tra server e client riguardante le funzionalità legate alle sessioni.
 */
public interface SessionCommunication {
    /**
     * @name createNewSession
     * @desc Crea una nuova sessione;
     * @returns {Session}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    Result createNewSession();
    /**
     * @name destroySession
     * @desc Distrugge la sessione indicata;
     * @param {Session} session - Rappresenta la sessione da dsitruggere;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    Result destroySession(String session);
    /**
     * @name updateLastAccess
     * @desc Aggiorna l'ultimo accesso associato alla sessione;
     * @param {Session} session - Rappresenta la sessione da aggiornare;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    Result updateLastAccess(String session);
}
