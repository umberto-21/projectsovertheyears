package digitalsloths.socialtables.basefunctions.presenter;

import android.content.Context;
import android.view.View;

/**
 * name MainMenuPresenter.java
 * author Pinton Federico
 * date 28/04/2016
 * brief Gestisce View::MainMenu implementando la application//Logic e la presentation//Logic per la selezione e
 * l'avvio di una delle funzionalità offerte (Gestione profilo, Chat, Bacheca, Battaglia Navale)
 * use Viene utilizzata per visualizzare il menù iniziale dell'applicazione
 */
public interface MainMenuPresenter {

    /**
     * @name checkStatus
     * @desc Fa partire i controlli d'ambiente e visualizza un errore in caso manchino la connessione dati
     * o il server sia irraggiungibile o il bluetooth sia disattivato;
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenter
     */
    public void checkStatus();

    /**
     * @name loadProfileManager
     * @desc Chiama il manager della chat alla pressione del pulsante di avvio della chat;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenter
     */
    public void loadProfileManager(View view);

    /**
     * @name loadGameManager
     * @desc Chiama il manager dei giochi alla pressione del pulsante selettore dei giochi;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenter
     */
    public void loadGamesManager(View view);

    /**
     * @name loadChatManager
     * @desc Chiama il manager della bacheca alla pressione del pulsante di accesso alla bacheca;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenter
     */
    public void loadChatManager(View view);

    /**
     * @name loadNoticeBoardManager
     * @desc Chiama il profile manager alla pressione del pulsante di gestione profilo;
     * @param {View} view - Vista di riferimento al quale appartiene il pulsante assocciato al metodo chiamato dall'ascoltatore evento click
     * @memberOf Client.Basefunctions.Presenter.MainMenuPresenter
     */
    public void loadNoticeBoardManager(View view);

}
