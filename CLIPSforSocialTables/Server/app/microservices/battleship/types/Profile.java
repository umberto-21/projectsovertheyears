package microservices.battleship.types;

/**
 *file Profile.java
 *author Filinesi Skrypnyk Oleksandr
 *date 7/06/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un profilo.
 *use Viene utilizzata per gestire le informazioni che rappresentano un profilo.
 */
public interface Profile{

  /**
  * @name getId
  * @desc Ritorna l'identificatore del profilo, null se nessun identificatore è associato al profilo;
  * @returns {Integer}
  * @memberOf Server.Microservices.Battleship.Types.Profile
  */ 
  public Integer getId();
  
  /**
  * @name getUsername
  * @desc Ritorna il nome associato al profilo;
  * @returns {String}
  * @memberOf Server.Microservices.Battleship.Types.Profile
  */ 
  public String getUsername();
  
  /**
  * @name setUsernames
  * @desc Setta l'username dell'utente.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Types.Profile
  */
  public void setUsername(String username);
  /**
   * @name equals
   * @desc Dice se i due oggetti sono uguali.
   * @param {ProfileImpl} p - Rappresenta il profilo da confrontare.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Types.Profile
   */
  public boolean equals(Profile p);
}
