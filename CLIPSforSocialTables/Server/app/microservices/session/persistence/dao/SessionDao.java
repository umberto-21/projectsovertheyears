package microservices.session.persistence.dao;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;

/**
 *file SessionPersistenceImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Gestisce tutte le funzionalità per il salvataggio e il reperimento di informazioni riguardanti una sessione.
 */
public interface SessionDao {
	
  /**
   * Method used to create a session.
   * @return object of type Response holding an object of type Session or an error.
   */
  public Response<Session> createSession();
  
  /**
   * Method used to delete a session.
   * @param sessionId identifier of a session.
   * @return null if deletion was successful or an object of type Error.
   */
  public Error deleteSession(int sessionId);  
  
  /**
  * @name updateLastAccess
  * @desc Aggiorna l'ultimo accesso associato alla sessione, ritorna un errore se l'aggiornamento è fallito;
  * @param {int} sessionId - Rappresenta la sessione da aggiornare;
  * @returns {Error}
  * @memberOf Server.Microservices.Session.Persistence.SessionPersistence.Dao
  */ 
  public Error updateLastAccess(int sessionId);
}
