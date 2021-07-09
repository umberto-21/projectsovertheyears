package microservices.battleship.persistence.dao.sql;
import java.sql.Connection;
import microservices.battleship.persistence.dao.DaoFactory;
import microservices.battleship.persistence.dao.BattleshipDao;
import microservices.battleship.persistence.dao.ScoreDao;
import microservices.battleship.persistence.dao.TeamDao;
import play.db.*;

/**
 *file SqlDaoFactoryImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Gestisce le funzionalità per la creazione di specifici oggetti che manipolano i dati relativi al gioco Battleship;
 *use Viene usata per creare specifici oggetti che manipolano i dati, in un database di tipo sql, relativi al gioco Battleship;
 */
public class SqlDaoFactoryImpl extends DaoFactory {

  /**
  * @name createConnection
  * @desc Metodo statico che ritorna una connessione ad un database SQL;
  * @returns {Connection}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.Sql.SqlDaoFactoryImpl
  */ 
  public static Connection createConnection() {
    return DB.getConnection();
 }

  @Override
  public BattleshipDao getBattleshipDao() {
    return new SqlBattleshipDaoImpl();
  }
  
  @Override
  public ScoreDao getScoreDao() {
    return new SqlScoreDaoImpl();
  }
  
  @Override
  public TeamDao getTeamDao() {
    return new SqlTeamDaoImpl();
  }
}