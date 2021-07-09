package microservices.session.business;

import microservices.session.types.Session;

/**
 *file SessionBusiness.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Interfaccia che gestisce la logica di funzionamento delle sessioni, quindi risponde agli input e alle richieste che arrivano dall'esterno per l'inizio o la distruzione delle sessioni.
 *use Viene utilizzata per gestire la logica delle sessioni, quindi risponde agli input e alle richieste che arrivano dall'esterno per l'inizio o la distruzione delle sessioni.
 */
public interface SessionBusiness {
    /**
     * @name createNewSession
     * @desc Ritorna una nuova sessione associandogli un indirizzo IP;
     * @param {IP} ip - Rappresenta l'indirizzo ip associato all'utente;
     * @returns {Session}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    Session createNewSession();

    /**
     * @name destroySession
     * @desc Distrugge la sessione indicata;
     * @param {Session} session - Rappresenta la sessione da distruggere;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    void destroySession(Session session);
    /**
     * @name updateLastAccess
     * @desc Aggiorna l'ultimo accesso associato alla sessione;
     * @param {Session} session - Rappresenta la sessione a cui aggiornare l'ultimo accesso;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    void updateLastAccess(Session session);
}

