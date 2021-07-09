package digitalsloths.socialtables.profile.model.communication;

import digitalsloths.socialtables.basefunctions.model.communication.Communication;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Profile;

/**
 * name ProfileCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Gestisce tutta la comunicazione con il server riguardante la gestione del profilo dell'utente;
 * use Viene utilizzata per comunicare con il server;
 */
public interface ProfileCommunication extends Communication{
    /**
     * @name getProfile
     * @desc Ritorna il profilo associato al utente;
     * @returns {Profile}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    Profile getProfile(Beacon beacon);

    /**
     * @name changeUsername
     * @desc Cambia l'username al profilo;
     * @param {Profile} profile - Profilo utente da modificare;
     * @returns {boolean}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    boolean changeProfile(Profile profile);
}
