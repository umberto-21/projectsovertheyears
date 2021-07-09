package digitalsloths.socialtables.profile.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * name ProfileManagerImpl.java
 * author Pinton Federico
 * date 27/05/2016
 * brief La classe gestisce le funzionalità del profilo;
 * use Viene utilizzata per la gestione del profilo;
 */
public class ProfileManagerImpl implements  ProfileManager {
    private static final String TAG = "ProfileManagerImpl";
    private Context _context;

    /**
     * @name ProfileManagerImpl
     * @desc costruttore
     * @param {Context} context - Contesto con cui inizializzare il campo dati;
     * @memberOf Client.Profile.Presenter.ProfileManagerImpl
     */
    public ProfileManagerImpl(Context context){
       //Log.v(TAG, ".ProfileManagerImpl(Context)");
        _context=context;
    }

    /**
     * @name onStart
     * @desc Metodo che invoca l'activity per la gestione del profilo utente;
     * @returns {void}
     * @memberOf Client.Profile.Presenter.ProfileManagerImpl
     */
    @Override
    public void onStart() {
       //Log.v(TAG, ".onStart()");
        Intent profileManager = new Intent(_context, ProfilePresenterImpl.class);
        profileManager.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(profileManager);

    }
}
