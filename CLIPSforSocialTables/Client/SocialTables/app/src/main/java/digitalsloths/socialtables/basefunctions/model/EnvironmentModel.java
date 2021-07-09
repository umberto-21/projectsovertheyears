package digitalsloths.socialtables.basefunctions.model;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Profile;

/**
 * file: EnviromentModel.java
 * author: Andria Umberto
 * date: 6/6/16
 * brief:
 * use:
 */
public interface EnvironmentModel {

    /**
     * @name getNearestBeacon
     * @desc Ritorna il Beacon più vicino; ritorna il risultato dell'invocazione del metodo getNearestBeacon () : void sul riferimento alla classe EnviromentServiceModel;
     * @returns {Beacon}
     * @memberOf Client.BaseFunctions.Model.EnviromentModel
     */
    public Beacon getNearestBeacon ();


    /**
     * @name isBluetoothActive
     * @desc ritorna il risultato dell'invocazione del metodo isBluetoothActive () : boolean sul riferimento all'oggetto di tipo EnviromentServiceModel;
     * @returns {boolean}
     * @memberOf Client.BaseFunctions.Model.EnviromentModel
     */
    public boolean isBluetoothActive ();

    /**
     * @name isInternetActive
     * @desc chiama EnviromentCommunication.isInternetActive ();
     * @returns {void}
     * @memberOf Client.BaseFunctions.Model.EnviromentModel
     */
    public boolean isInternetActive ();

    /**
     * @name isServerActive
     * @desc chiama EnviromentCommunication.isServerActive ();
     * @returns {void}
     * @memberOf Client.BaseFunctions.Model.EnviromentModel
     */
    public boolean isServerActive ();

}
