package microservices.session.persistence;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;

/**
 *file SessionPersistence.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Interfaccia che gestisce i dati elementari relativi alla sessione, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato.
 *use Viene utilizzata per interfacciarsi e comunicare direttamente con il database utilizzato.
 */
public interface SessionPersistence{
	
  /**
  * @name createSession
  * @desc Ritorna un oggetto di tipo Response contenente un oggetto di tipo Session oppure un errore.
  * @returns {Response}
  * @memberOf Server.Microservices.Session.Persistence.SessionPersistence
  */ 
  public Response<Session> createSession();
  
  /**
  * @name deleteSession
  * @desc Elimina la sessione indicata, ritorna un errore se l'eliminazione è fallita;
  * @param {int} sessionId - Rappresenta l'id della sessione da distruggere;
  * @returns {Error}
  * @memberOf Server.Microservices.Session.Persistence.SessionPersistence
  */ 
  public Error deleteSession(int sessionId);
  
  /**
  * @name updateLastAccess
  * @desc Aggiorna l'ultimo accesso associato alla sessione, ritorna un errore se l'aggiornamento è fallito;
  * @param {int} sessionId - Rappresenta la sessione da aggiornare;
  * @returns {Error}
  * @memberOf Server.Microservices.Session.Persistence.SessionPersistence
  */ 
  public Error updateLastAccess(int sessionId);
}
