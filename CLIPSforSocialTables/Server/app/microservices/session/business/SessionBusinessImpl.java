package microservices.session.business;

import microservices.session.services.SessionServices;
import microservices.session.services.SessionServicesImpl;
import microservices.session.types.Response;
import microservices.session.types.Session;
import microservices.session.types.SessionImpl;

/**
 *file SessionBusiness.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Classe che gestisce la logica di funzionamento delle sessioni, quindi risponde agli input e alle richieste che arrivano dall'esterno per l'inizio o la distruzione delle sessioni.
 *use Viene utilizzata per gestire la logica delle sessioni, quindi risponde agli input e alle richieste che arrivano dall'esterno per l'inizio o la distruzione delle sessioni.
 */
public class SessionBusinessImpl implements SessionBusiness {
    private SessionServices _sessionServices = SessionServicesImpl.getIstance();
    private static SessionBusinessImpl ourInstance = new SessionBusinessImpl();

    public static SessionBusinessImpl getInstance() {
        return ourInstance;
    }

    private SessionBusinessImpl() {
    }


    /**
     * @name createNewSession
     * @desc Ritorna una nuova sessione associandogli un indirizzo IP;
     * @returns {Session}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    @Override
    public Session createNewSession() {
        Response<Session> sessionResponse = _sessionServices.createSession();
        return sessionResponse.getObject();

    }

    /**
     * @param session
     * @name destroySession
     * @desc Distrugge la sessione indicata;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    @Override
    public void destroySession(Session session) {
        _sessionServices.deleteSession(session);
    }

    /**
     * @param session
     * @name updateLastAccess
     * @desc Aggiorna l'ultimo accesso associato alla sessione;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Business.SessionBusiness
     */
    @Override
    public void updateLastAccess(Session session) {
        _sessionServices.updateLastAccess(session);
    }
}
