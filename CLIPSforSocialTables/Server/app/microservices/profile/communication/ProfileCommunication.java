package microservices.profile.communication;

import microservices.profile.types.Beacon;
import microservices.profile.types.Profile;
import microservices.profile.types.Session;
import play.mvc.Result;

/**
 *file ProfileCommunication.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Gestisce tutta la comunicazione con il server riguardante la gestione del profilo dell'utente.
 *use Viene utilizzata per comunicare con il server.
 */
public interface ProfileCommunication {
    /**
     * @name changeProfile
     * @desc Cambia l'username al profilo, ritorna true (json) sse la modifica è andata a buon fine;
     * @param {Session} session - Rappresenta la sessione associata al utente;
     * @param {Profile} profile - Rappresenta il nuovo profilo associato all'utente;
     * @return {boolean}
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    Result changeProfile(String session, String  profile);

    /**
     * @name getProfile
     * @desc Ritorna il profilo associato alla sessione;
     * @param {Session} session - Rappresenta la sessione associata al utente;
     * @param {Beacon} beacon - Rappresenta il beacon più vicino al'utente.
     * @returns {Profile}
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    Result getProfile(String session, String  beacon);

    /**
     * @name updateLocation
     * @desc Aggiorna il Beacon associato all'utente;
     * @param {Session} session - Rappresenta la sessione associata all'utente;
     * @param {Beacon} beacon - Rappresenta il beacon associato all'utente;
     * @returns {void}
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    Result updateLocation(String session, String beacon);
}
