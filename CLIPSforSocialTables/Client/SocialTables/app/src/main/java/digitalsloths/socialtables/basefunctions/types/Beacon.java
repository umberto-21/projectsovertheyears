package digitalsloths.socialtables.basefunctions.types;

/**
 * name BeaconImpl.java
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
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    public String getUuid();

    /**
     * @name getMajor
     * @desc Restitusce il major del Beacon.
     * @returns {int}
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    public int getMajor();

    /**
     * @name getMinor
     * @desc Restituisce il minor del Beacon.	
     * @returns {int}
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    public int getMinor();

    public boolean equals(Beacon b);

}
