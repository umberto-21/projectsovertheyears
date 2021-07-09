package digitalsloths.socialtables.basefunctions.types.errors;

import android.util.Log;

/**
 * name InternetError.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce le informazioni riguardanti la connessione Internet
 * use Viene utilizzata per gestire le informazioni riguardanti la connessione Internet
 */
public class InternetErrorImpl extends ErrorImpl implements InternetError{
    private static final String TAG = "InternetErrorImpl";
    /**
     * @name InternetErrorImpl
     * @desc Costruttore dell'errore sulla connessione internet;
     * @memberOf Client.BaseFunctions.Types.Errors.InternetErrorImpl
     */
    public InternetErrorImpl(){
        super("No internet connection");
       //Log.v(TAG, ".InternetErrorImpl()");
    }

}
