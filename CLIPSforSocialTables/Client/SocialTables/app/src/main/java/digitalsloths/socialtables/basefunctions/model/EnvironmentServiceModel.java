package digitalsloths.socialtables.basefunctions.model;

import digitalsloths.socialtables.basefunctions.types.Beacon;

/**
 * Created by Federico PC on 28/04/2016.
 */
public interface EnvironmentServiceModel {
    public boolean isBluetoothActive();
    public Beacon getNearestBeacon();
}
