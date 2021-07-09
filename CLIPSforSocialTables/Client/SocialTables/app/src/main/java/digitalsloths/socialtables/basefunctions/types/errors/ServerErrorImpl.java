package digitalsloths.socialtables.basefunctions.types.errors;

import android.util.Log;

/**
 * name ServerErrorImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce le informazioni riguardanti un errore della connessione con il server
 * use Viene utilizzata per gestire le informazioni di un errore della connessione con il server
 */
public class ServerErrorImpl extends ErrorImpl implements ServerError{
    private static final String TAG = "ServerErrorImpl";
    /**
     * @name ServerErrorImpl
     * @desc Costruttore dell'errore server irraggiungibile;
     * @memberOf Client.BaseFunctions.Types.Errors.ServerErrorImpl
     */
    public ServerErrorImpl(){
        super("Unreachable Server");
       //Log.v(TAG, ".ServerErrorImpl()");
    }

}
