package digitalsloths.socialtables.profile.presenter;

import android.view.View;

/**
 * name ProfilePresenterImpl.java
 * author Casotto Federico
 * date 16/05/2016
 * brief Gestisce l'application//Logic di tutte le funzionalità legate al profilo
 * use Viene utilizzata per gestire l'application//Logic di tutte le funzionalità legate al profilo
 */
public interface ProfilePresenter {

    /**
     * @name changeUsername
     * @desc Cambia l'username al profilo;
     * @param {View} view - Vista di riferimento sulla quale visualizzare gli errori;
     * @returns {void}
     * @memberOf Client.Profile.Presenter.ProfilePresenter
     */
    public void changeUsername(View view);

}
