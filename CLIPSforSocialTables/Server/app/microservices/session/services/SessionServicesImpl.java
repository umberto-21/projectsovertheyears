package microservices.session.services;
import microservices.session.persistence.SessionPersistence;
import microservices.session.persistence.SessionPersistenceImpl;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;

/**
 *file SessionServicesImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Classe che gestisce i dati relativi la sessione di un utente.
 *use Implementa i metodi offerti dall'interfaccia SessionServices, utilizzati per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business.
 */
public class SessionServicesImpl implements SessionServices{

   private SessionPersistence _sessionPersistence;
   
  /**
   * Costruttore completo.
   */
  public SessionServicesImpl(){
    _sessionPersistence = new SessionPersistenceImpl();
  }

  @Override
  public Response<Session> createSession(){
    return _sessionPersistence.createSession();
  }
  
  @Override
  public Error deleteSession(Session session){
    return _sessionPersistence.deleteSession(session.getId());
  }
  
  @Override
  public Error updateLastAccess(Session session){
    return _sessionPersistence.updateLastAccess(session.getId());
  }


  public static SessionServices getIstance() {
    return new SessionServicesImpl();
  }
}