package microservices.battleship.types;

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
     * @memberOf Server.Microservices.Battleship.Types.Beacon
     */
    public String getUuid();

    /**
     * @name getMajor
     * @desc Restitusce il major del Beacon.
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Beacon
     */
    public int getMajor();

    /**
     * @name getMinor
     * @desc Restituisce il minor del Beacon.	
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Beacon
     */
    public int getMinor();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {BeaconImpl} b - Rappresenta il beacon da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Beacon
     */
    public boolean equals(Beacon b);
}
