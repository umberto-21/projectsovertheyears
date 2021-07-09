package digitalsloths.socialtables.basefunctions.types;

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
     * @desc Costruttore di copia del Beacon	
	 * @param {Beacon} beacon - Il Beacon da copiare
     * @returns {int}
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    public BeaconImpl(org.altbeacon.beacon.Beacon beacon){
        _uuid = beacon.getId1().toHexString();
        _major = beacon.getId2().toInt();
        _minor = beacon.getId3().toInt();
    }

    /**
     * @name BeaconImpl
     * @desc 	Costruttore a tre parametri per creare il Beacon	
	 * @param {String} uuid - Uuid del Beacon
	 * @param {int} major - Il major del Beacon
	 * @param {int} minor - Il minor del Beacon
     * @returns {int}
     * @memberOf Client.Basefunctions.Types.Beacon
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
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    @Override
    public String getUuid() {
        return _uuid;
    }

    /**
     * @name getUuid
     * @desc Restituisce l'Uuid del Beacon ricevuto dal client.
     * @returns {String}
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    @Override
    public int getMajor() {
        return _major;
    }

    /**
     * @name getMinor
     * @desc Restituisce il minor del Beacon.	
     * @returns {int}
     * @memberOf Client.Basefunctions.Types.Beacon
     */
    @Override
    public int getMinor() {
        return _minor;
    }

    public boolean equals(Beacon b) {
        return (this.getMajor()==b.getMajor())&&(this.getMinor()==b.getMinor())&&(this.getUuid().equals(b.getUuid()));
    }

}
