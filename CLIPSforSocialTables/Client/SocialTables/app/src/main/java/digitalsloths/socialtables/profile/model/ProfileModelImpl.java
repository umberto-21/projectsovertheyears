package digitalsloths.socialtables.profile.model;

import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModelImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.profile.model.communication.ProfileCommunication;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;

/**
 * File: ProfileImplModel.java
 * author: Ivan Parise
 * date 24/05/2016
 * brief: Gestisce tutta la business//Logic riguardante la gestione del profilo dell'utente;
 * use: Viene utilizzata per gestire tutta la business//Logic riguardante la gestione del profilo utente;
 */
public class ProfileModelImpl implements ProfileModel{
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

    /**
     * @name getProfile
     * @desc Restituisce il profilo associato all'utente;
     * @returns {Profile}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    @Override
    public Profile getProfile() {

        Beacon beacon=_environmentServiceModel.getNearestBeacon();
        if (beacon == null) return null;
        _profile = _profileCommunication.getProfile(beacon);
        return _profile;
}
    /**
     * @name setProfile
     * @desc Assegna un profilo all' oggetto
     * @param {Profile} profile - Il profilo da associare;
     * @returns {void}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    @Override
    public void setProfile(digitalsloths.socialtables.types.Profile profile) {
        _profile = profile;
    }

    /**
     * @name changeUsername
     * @desc 	Cambia l'username al profilo;
     * @param {String} username - Il nuovo nome utente;
     * @returns {boolean}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    @Override
    //attesa sistemazione metodo Ugo su communication
    public boolean changeUsername(String username){
        //attendo creazione classe Ugo
            _profile.setUsername(username);
            Profile profilo=new ProfileImpl(username);
            return _profileCommunication.changeProfile(profilo);

    }

}
