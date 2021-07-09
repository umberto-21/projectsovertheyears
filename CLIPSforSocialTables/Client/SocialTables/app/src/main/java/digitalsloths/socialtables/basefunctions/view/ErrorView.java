package digitalsloths.socialtables.basefunctions.view;

import android.app.AlertDialog;

import digitalsloths.socialtables.basefunctions.types.errors.Error;

/**
 * name ErrorView.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce la visualizzazione di errori legati al ambiente di esecuzione
 * use Viene utilizzata per visualizzare a schermo gli errori legati all'ambiente di esecuzione
 */
public interface ErrorView {
    public void show(AlertDialog alertDialog, String error);
}
