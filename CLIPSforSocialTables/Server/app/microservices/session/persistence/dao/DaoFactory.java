package microservices.session.persistence.dao;
import microservices.session.persistence.dao.sql.SqlDaoFactoryImpl;
import microservices.session.types.Session;

/**
 *file DaoFactory.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Fornisce una interfaccia per l'accesso ai dati elementari legati alla sessione, indipendentemente dal linguaggio usato per memorizzarli.
 */
public abstract class DaoFactory {

  // DAO types supported by the factory.
  public static final int MYSQL = 0;

  /** 
   * Method to create session dao.
   * @return object of type SessionDao.
   */
  public abstract SessionDao getSessionDao();

  /**
   * Method that returns a dao factory.
   * @param whichFactory identifier of the factory type.
   * @return object of type DaoFactory or null if @whichFactory is an incorect type of factory.
   */
  public static DaoFactory getDaoFactory(int whichFactory) {
    switch (whichFactory) {
      case MYSQL :
          return new SqlDaoFactoryImpl();
      default :
          return null;
    }
  }
}
