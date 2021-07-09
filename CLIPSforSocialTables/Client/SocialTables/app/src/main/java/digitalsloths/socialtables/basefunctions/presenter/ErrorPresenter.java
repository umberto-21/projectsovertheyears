package digitalsloths.socialtables.basefunctions.presenter;

import android.content.Context;
import android.view.View;

import digitalsloths.socialtables.basefunctions.types.errors.Error;

/**
 * name ErrorPresenter.java
 * author Pinton Federico
 * date 28/04/2016
 * brief La classe permette di visualizzare gli errori
 * use Viene utilittata per notificare all'utente gli errori riscontrati
 */
public interface ErrorPresenter {
    /**
     * @name close
     * @desc Chiude la schermata d'errore;
     * @param {View} view - Vista di riferimento nel quale far chiudere la schermata d'errore;
     * @returns {void}
     * @memberOf Client.BaseFunctions.Presenter.ErrorPresenter
     */
    public void close(View view);
}
