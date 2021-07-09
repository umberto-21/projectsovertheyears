package microservices.profile.types;

/**
 * name Beacon.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce le informazioni associate ad un beacon;
 * use Viene utilizzata per gestire le informazioni che rappresentano un beacon;
 */
public interface Beacon {

  /**
     * @name getUuid
     * @desc Restituisce l'Uuid del Beacon ricevuto dal client.
     * @returns {String}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
  public String getUuid();

  /**
     * @name getMajor
     * @desc Restitusce il major del Beacon.
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
  public int getMajor();

  /**
     * @name getMinor
     * @desc Restituisce il minor del Beacon.	
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
  public int getMinor();

}
