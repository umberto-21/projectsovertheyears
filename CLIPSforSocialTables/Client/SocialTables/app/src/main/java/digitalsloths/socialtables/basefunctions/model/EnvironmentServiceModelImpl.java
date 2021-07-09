package digitalsloths.socialtables.basefunctions.model;

import com.google.gson.Gson;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;

/**
 * name EnvironmentServiceModelImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce le interrogazioni legate all'ambiente di esecuzione; classe statica
 * use Viene utilizzata quando bisogna controllare lo stato del dispositivo
 */
public class EnvironmentServiceModelImpl implements EnvironmentServiceModel {
    private static final String TAG = "EnvServiceModelImpl";
    private static EnvironmentServiceModelImpl _istance;
    BeaconService mService;
    boolean mBound = false;

    public static EnvironmentServiceModelImpl getIstance() {
        if(_istance == null) {
            _istance = new EnvironmentServiceModelImpl();
        }
        return _istance;
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
           //Log.v(TAG, ".onServiceConnected(...)");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BeaconServiceImpl.BeaconServiceBinder binder = (BeaconServiceImpl.BeaconServiceBinder) service;
            mService = binder.getService();
            mBound = true;
           //Log.v(TAG, "mBound: " + (new Gson()).toJson(mBound));
           //Log.v(TAG, ".onServiceConnected(...) return");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
           //Log.v(TAG, ".onServiceDisconnected(...)");
            mBound = false;
           //Log.v(TAG, ".onServiceDisconnected(...) return");
        }
    };

    private Beacon _nearestBeacon;

    private Context _context;

    private EnvironmentServiceModelImpl() {
       //Log.v(TAG, ".EnvironmentServiceModelImpl()");
        _context = MainMenuPresenterImpl.getAppContext();
        Intent intent = new Intent(_context, BeaconServiceImpl.class);
        _context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
       //Log.v(TAG, ".EnvironmentServiceModelImpl() return");
    }
    /*
     * controlla se il bleutooth è attivo
     */
    @Override
    public boolean isBluetoothActive() {
        BluetoothAdapter myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (myBluetoothAdapter == null) {
            return false;
        } else {
            if (!myBluetoothAdapter.isEnabled()) {
                return false;
            } else {
                return true;
            }
        }
    }

    /*
     * restituisce il Beacon più vicino rilevato
     */
    @Override
    public Beacon getNearestBeacon() {
       //Log.v(TAG, ".getNearestBeacon()");
       //Log.v(TAG, ".getNearestBeacon().mBound: " + (new Gson()).toJson(mBound));
        if(mBound == true){
            _nearestBeacon = mService.getNearestBeacon();
        } else {
            _nearestBeacon = null;
        }
       //Log.v(TAG, ".getNearestBeacon() return: " + (new Gson()).toJson(_nearestBeacon));
        return _nearestBeacon;
    }
}
