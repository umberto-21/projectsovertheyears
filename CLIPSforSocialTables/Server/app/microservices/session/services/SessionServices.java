package microservices.session.services;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;
/**
 *file SessionServices.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Interfaccia che gestisce i dati relativi la sessione di un utente.
 *use Viene utilizzata per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business.
 */
public interface SessionServices{

  /**
  * @name createSession
  * @desc Ritorna un oggetto di tipo Response che contiene: la sessione appena creata, oppure un oggetto di tipo Error se la creazione è fallita.
  * @returns {Response}
  * @memberOf Server.Microservices.Session.Services.SessionServices
  */ 
  public Response<Session> createSession();
  
  /**
  * @name deleteSession
  * @desc Elimina la sessione indicata, ritorna un errore se l'eliminazione è fallita;
  * @param {Session} session - Rappresenta la sessione da distruggere;
  * @returns {Error}
  * @memberOf Server.Microservices.Session.Services.SessionServices
  */ 
  public Error deleteSession(Session session);
  
  /**
  * @name updateLastAccess
  * @desc Aggiorna l'ultimo accesso associato alla sessione, ritorna un errore se l'aggiornamento è fallito;
  * @param {Session} session - Rappresenta la sessione da aggiornare;
  * @returns {Error}
  * @memberOf Server.Microservices.Session.Services.SessionServices
  */ 
  public Error updateLastAccess(Session session);
}
