package microservices.profile.persistence.dao;
import microservices.profile.persistence.dao.sql.SqlDaoFactoryImpl;
import microservices.profile.types.Profile;

/**
 *file DaoFactory.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Fornisce una interfaccia per l'accesso ai dati elementari legati al profilo, indipendente dal linguaggio usato per memorizzarli.
 */
public abstract class DaoFactory {
	
  // Tipo di dao supportato dalla factory.
  public static final int MYSQL = 0;

  /**
  * @name getProfileDao
  * @desc Metodo astratto usato per creare il dao del profilo.
  * @returns {ProfileDao}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.DaoFactory
  */ 
  public abstract ProfileDao getProfileDao();

  /**
  * @name getDaoFactory
  * @desc Metodo che ritorna la dao factory del tipo richiesto o null se è stata richiesta una tipologia di dao non supportata.
  * @param {int} whichFactory - Tipologia di dao richiesta. Le tipologie di dao supportate sono presenti nella stessa classe come costanti pubbliche);
  * @returns {DaoFactory}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.DaoFactory
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