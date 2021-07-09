package microservices.session.communication;

import com.google.gson.Gson;
import microservices.session.business.SessionBusiness;
import microservices.session.business.SessionBusinessImpl;
import microservices.session.types.Session;
import microservices.session.types.SessionImpl;

import play.libs.Json;
import play.mvc.*;

import static play.mvc.Results.ok;


/**
 *file SessionCommunicationImpl.java
 *date 26/05/2016
 *brief Classe che gestisce la comunicazione tra server e client riguardante le funzionalità legate alle sessioni, quindi rimane in ascolto delle richieste che arrivano dal esterno del sistema server.
 *use Viene utilizzata per gestire la comunicazione tra server e client riguardante le funzionalità legate alle sessioni.
 */
public class SessionCommunicationImpl implements SessionCommunication {
    private static final String TAG = "SessionCommunicationImpl";
    private SessionBusiness _sessionBusiness = SessionBusinessImpl.getInstance();
    /**
     * @name createNewSession
     * @desc Crea una nuova sessione;
     * @returns {Session}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    @Override
    public Result createNewSession() {
        //play.Logger.info(TAG + ".createNewSession");
        Session session = _sessionBusiness.createNewSession();
        Gson gson = new Gson();
        String json = gson.toJson(session);
        //play.Logger.info(TAG + ".createNewSession return: " + json);
        return ok(json);
    }

    /**
     * @param jsonSession
     * @name destroySession
     * @desc Distrugge la sessione indicata;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    @Override
    public Result destroySession(String jsonSession) {
        //play.Logger.info(TAG + ".destroySession");
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        _sessionBusiness.destroySession(session);
        //play.Logger.info(TAG + ".destroySession return");
        return ok();
    }

    /**
     * @param jsonSession
     * @name updateLastAccess
     * @desc Aggiorna l'ultimo accesso associato alla sessione;
     * @returns {void}
     * @memberOf Server.Microservices.Session.Communication.SessionCommunication
     */
    @Override
    public Result updateLastAccess(String jsonSession) {
        //play.Logger.info(TAG + ".updateLastAccess(" + jsonSession + ")");
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        _sessionBusiness.updateLastAccess(session);
        //play.Logger.info(TAG + ".updateLastAccess(" + jsonSession + ") return");
        return ok();
    }
}
