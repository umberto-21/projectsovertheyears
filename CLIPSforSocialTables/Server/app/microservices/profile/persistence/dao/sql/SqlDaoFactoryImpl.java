package microservices.profile.persistence.dao.sql;
import java.sql.Connection;
import microservices.profile.persistence.dao.DaoFactory;
import microservices.profile.persistence.dao.ProfileDao;
import play.db.*;

/**
 *file SqlDaoFactoryImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Gestisce le funzionalità per la creazione di specifici oggetti che manipolano i dati relativi al profilo.
 */
public class SqlDaoFactoryImpl extends DaoFactory {

  /**
  * @name createConnection
  * @desc Metodo statico che ritorna una connessione ad un database SQL.
  * @returns {Connection}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.Sql.SqlDaoFactoryImpl
  */ 
  public static Connection createConnection() {
    return DB.getConnection();
 }

  @Override
  public ProfileDao getProfileDao() {
    return new SqlProfileDaoImpl();
  }
}