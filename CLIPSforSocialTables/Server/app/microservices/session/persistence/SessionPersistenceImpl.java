package microservices.session.persistence;
import microservices.session.persistence.dao.DaoFactory;
import microservices.session.persistence.dao.SessionDao;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;

/**
 *file SessionPersistenceImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Classe che gestisce i dati elementari relativi alla sessione, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato.
 *use Implementa i metodi offerti dall'interfaccia SessionPersistence, utilizzati per interfacciarsi e comunicare direttamente con il database utilizzato.
 */
public class SessionPersistenceImpl implements SessionPersistence{

  private DaoFactory _daoFactory;

  /**
   * Costruttore completo.
   */
  public SessionPersistenceImpl(){
    _daoFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
  }

  @Override
  public Response<Session> createSession(){
    SessionDao sessionDao = _daoFactory.getSessionDao();
    return sessionDao.createSession();
  }

  @Override
  public Error deleteSession(int sessionId){
    SessionDao sessionDao = _daoFactory.getSessionDao();
    return sessionDao.deleteSession(sessionId);
  }
  
  @Override
  public Error updateLastAccess(int sessionId){
	SessionDao sessionDao = _daoFactory.getSessionDao();
	return sessionDao.updateLastAccess(sessionId);
  }
}
