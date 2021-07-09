package microservices.profile.types;

/**
 * name BeaconImpl.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Interfaccia che gestisce le informazioni associate ad un beacon;
 * use Viene utilizzata per gestire le informazioni che rappresentano un beacon;
 */
public class BeaconImpl implements Beacon {


    private String _uuid;

    private int _major;

    private int _minor;

    /**
     * @name BeaconImpl
     * @desc Costruttore di default.
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    public BeaconImpl(){
        _uuid = null;
        _major = -1;
        _minor = -1;
    }

    /**
     * @name BeaconImpl
     * @desc Costruttore di copia del Beacon	
	 * @param {Beacon} beacon - Il Beacon da copiare
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    public BeaconImpl (Beacon beacon) {
        _uuid =beacon.getUuid();
        _major =beacon.getMajor();
        _minor =beacon.getMinor();
    }

    /**
     * @name BeaconImpl
     * @desc 	Costruttore a tre parametri per creare il Beacon	
	 * @param {String} uuid - Uuid del Beacon
	 * @param {int} major - Il major del Beacon
	 * @param {int} minor - Il minor del Beacon
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    public BeaconImpl(String uuid,int major,int minor){
        _uuid = uuid;
        _major = major;
        _minor = minor;
    }

    /**
     * @name getUuid
     * @desc Restituisce l'Uuid del Beacon ricevuto dal client.
     * @returns {String}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    @Override
    public String getUuid() {
        return _uuid;
    }

    /**
     * @name getMajor
     * @desc Restitusce il major del Beacon.
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    @Override
    public int getMajor() {
        return _major;
    }

    /**
     * @name getMinor
     * @desc Restituisce il minor del Beacon.	
     * @returns {int}
     * @memberOf Server.Microservices.Profile.Types.Beacon
     */
    @Override
    public int getMinor() {
        return _minor;
    }

}
