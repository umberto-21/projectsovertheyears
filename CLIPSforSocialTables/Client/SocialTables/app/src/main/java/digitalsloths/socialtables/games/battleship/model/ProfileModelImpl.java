package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModelImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.games.battleship.model.communication.ProfileCommunication;
import digitalsloths.socialtables.types.Profile;

/**
 * Created by Ivan Parise
 * Created_on 29/05/16.
 * Description: Gestisce tutta la business//Logic riguardante la gestione del profilo dell'utente;
 * Use: Viene utilizzata per gestire tutta la business//Logic\g{} riguardante la gestione del profilo utente;
 */
public class ProfileModelImpl implements  ProfileModel{
    private EnvironmentServiceModel _environmentServiceModel;
    private Profile _profile;
    private ProfileCommunication _profileCommunication;

    /**
     * @name ProfileModelImpl
     * @desc Costruttore della classe;
     * @memberOf Client.Profile.Model.ProfileModel
     */
    public ProfileModelImpl( ProfileCommunication profileCommunication){
        _environmentServiceModel = EnvironmentServiceModelImpl.getIstance();
        _profileCommunication = profileCommunication;
    }

    @Override
    public Profile getProfile() {
       //Log.v("ProfileModel", ".getProfile()");
        Beacon beacon=_environmentServiceModel.getNearestBeacon();
        if (beacon == null) return null;
        _profile = _profileCommunication.getProfile(beacon);
        return _profile;
    }
}
