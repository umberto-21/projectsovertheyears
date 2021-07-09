package microservices.session.persistence.dao.sql;
import java.sql.Connection;
import microservices.session.persistence.dao.DaoFactory;
import microservices.session.persistence.dao.SessionDao;
import play.db.*;

/**
 *file SqlDaoFactoryImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Gestisce le funzionalità per l'accesso ai dati elementari legati alla sessione, manipolati tramite SQL.
 */
public class SqlDaoFactoryImpl extends DaoFactory {
	
  /**
   * Method used to get connection to database.
   * @return object of type Connection.
   */
  public static Connection createConnection(){
    return DB.getConnection("default", true);
  }

  /**
   * Method used to get session dao.
   * @return object implementing SessionDao interface.
   */
  public SessionDao getSessionDao(){
    return new SqlSessionDaoImpl();
  }
}
