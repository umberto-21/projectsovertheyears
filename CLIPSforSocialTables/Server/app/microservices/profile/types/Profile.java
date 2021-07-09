package microservices.profile.types;

/**
 *file Profile.java
 *author Filinesi Skrypnyk Oleksandr
 *date 29/05/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un profilo.
 *use Viene utilizzata per gestire le informazioni che rappresentano un profilo.
 */
public interface Profile{

  /**
  * @name getId
  * @desc Ritorna l'identificatore del profilo, null se nessun identificatore è associato al profilo;
  * @returns {Integer}
  * @memberOf Server.Microservices.Profile.Types.Profile
  */ 
  public Integer getId();
  
  /**
  * @name getUsername
  * @desc Ritorna il nome associato al profilo;
  * @returns {String}
  * @memberOf Server.Microservices.Profile.Types.Profile
  */ 
  public String getUsername();
  
  /**
  * @name setUsernames
  * @desc Setta l'username dell'utente.
  * @returns {void}
  * @memberOf Server.Microservices.Profile.Types.ProfileImpl
  */
  public void setUsername(String username);
}
