package microservices.battleship.persistence.dao;
import microservices.battleship.persistence.dao.sql.SqlDaoFactoryImpl;

/**
 *file DaoFactory.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Fornisce una interfaccia per l'accesso ai dati elementari legati al gioco Battleship, indipendentemente dal linguaggio usato per memorizzarli.
 *use Viene usata per creare la tipologia di factory richiesta e per fornire i dao relativi a tale tipologia; 
 */
public abstract class DaoFactory {

  // Tipo di dao supportato dalla factory;
  public static final int MYSQL = 0;

  /**
  * @name getBattleshipDao
  * @desc Metodo astratto usato per creare il dao per il gioco Battleship;
  * @returns {BattleshipDao}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.DaoFactory
  */ 
  public abstract BattleshipDao getBattleshipDao();
  
  /**
  * @name getScoreDao
  * @desc Metodo astratto usato per creare il dao del punteggio;
  * @returns {ScoreDao}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.DaoFactory
  */ 
  public abstract ScoreDao getScoreDao();
  
  /**
  * @name getTeamDao
  * @desc Metodo astratto usato per creare il dao per una squadra;
  * @returns {TeamDao}
  * @memberOf Server.Microservices.Team.Persistence.Dao.DaoFactory
  */ 
  public abstract TeamDao getTeamDao();

  /**
  * @name getDaoFactory
  * @desc Metodo statico che ritorna la dao factory del tipo richiesto o null se è stata richiesta una tipologia di dao non supportata;
  * @param {int} whichFactory - Tipologia di dao richiesta. Le tipologie di dao supportate sono presenti nella stessa classe come costanti pubbliche;
  * @returns {DaoFactory}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.DaoFactory
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
