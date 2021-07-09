package digitalsloths.socialtables.basefunctions.model;

import android.util.Log;

import com.google.gson.Gson;

import digitalsloths.socialtables.basefunctions.model.communication.EnvironmentCommunication;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Profile;

/**
 * file: EnviromentModelImpl.java
 * author: Andria Umberto
 * date: 6/6/16
 * brief:
 * use:
 */
public class EnvironmentModelImpl implements EnvironmentModel {

    private static final String TAG = "EnvironmentModelImpl";

    private EnvironmentCommunication _environmentCommunication;

    private EnvironmentServiceModel _environmentServiceModel;

    public EnvironmentModelImpl( EnvironmentCommunication environmentCommunication,
                                EnvironmentServiceModel environmentServiceModel) {

       //Log.v(TAG, ".EnvironmentModelImpl(..)");
        _environmentCommunication = environmentCommunication;
        _environmentServiceModel = environmentServiceModel;
       //Log.v(TAG, ".EnvironmentModelImpl(..) return");
    }

    @Override
    public Beacon getNearestBeacon() {
       //Log.v(TAG, ".getNearestBeacon()");
        Beacon beacon =_environmentServiceModel.getNearestBeacon();
       //Log.v(TAG, ".getNearestBeacon() return: " + (new Gson()).toJson(beacon));
        return beacon;
    }

    @Override
    public boolean isBluetoothActive() {
       //Log.v(TAG, ".isBluetoothActive()");
        boolean result =_environmentServiceModel.isBluetoothActive();
       //Log.v(TAG, ".isBluetoothActive() return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean isInternetActive() {
       //Log.v(TAG, ".isInternetActive()");
        boolean result =_environmentCommunication.isInternetActive();
       //Log.v(TAG, ".isInternetActive() return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean isServerActive() {
       //Log.v(TAG, ".isServerActive()");
        boolean result =_environmentCommunication.isServerActive();
       //Log.v(TAG, ".isServerActive() return: " + (new Gson()).toJson(result));
        return result;
    }

}
