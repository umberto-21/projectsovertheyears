package digitalsloths.socialtables.basefunctions.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * name ErrorViewImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce la visualizzazione di errori legati al ambiente di esecuzione
 * use Viene utilizzata per visualizzare a schermo gli errori legati all'ambiente di esecuzione
 */
public class ErrorViewImpl implements ErrorView {
    private static final String TAG = "ErrorViewImpl";
    /*
     * metodo che stampa a video il messaggio di errore
     */
    @Override
    public void show(AlertDialog alertDialog, String error) {
       //Log.v(TAG, ".show(AlertDialog,"+ error +")");
        alertDialog.setMessage(error);

        alertDialog.setCancelable(false);
        alertDialog.show();

    }
}
