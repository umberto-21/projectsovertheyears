package digitalsloths.socialtables.profile.model;

/**
 * File: ProfileModel.java
 * author: Ivan Parise
 * date 24/05/2016
 * brief: Gestisce tutta la business//Logic riguardante la gestione del profilo dell'utente;
 * use: Viene utilizzata per gestire tutta la business//Logic riguardante la gestione del profilo utente;
 */
public interface ProfileModel {
    /**
     * @name getProfile
     * @desc Restituisce il profilo associato all'utente;
     * @returns {Profile}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    public digitalsloths.socialtables.types.Profile getProfile();
    /**
     * @name setProfile
     * @desc Assegna un profilo all' oggetto
     * @param {Profile} profile - Il profilo da associare;
     * @returns {void}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    public void setProfile(digitalsloths.socialtables.types.Profile profile);
    /**
     * @name changeUsername
     * @desc 	Cambia l'username al profilo;
     * @param {String} username - Il nuovo nome utente;
     * @returns {boolean}
     * @memberOf Client.Profile.Model.ProfileModel
     */
    public boolean changeUsername(String username);
}
