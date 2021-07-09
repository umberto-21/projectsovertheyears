package digitalsloths.socialtables.basefunctions.types.errors;

import android.os.Parcelable;
import android.util.Log;

/**
 * name BluetoothErrorImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce le informazioni riguardanti un errore della connessione Bluetooth
 * use Viene utilizzata per gestire le informazioni di un errore della connessione Bluetooth
 */
public class BluetoothErrorImpl extends ErrorImpl implements BluetoothError {
    private static final String TAG = "BluetoothErrorImpl";

    /**
     * @name BluetoothErrorImpl
     * @desc Costruttore
     * @memberOf Client.BaseFunctions.Types.Errors.BluetoothErrorImpl
     */
    public BluetoothErrorImpl(){
        super("Bluetooth is disabled, enable it before continue.");
       //Log.v(TAG, ".BluetoothErrorImpl()");
    }

}
