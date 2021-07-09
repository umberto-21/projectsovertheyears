package microservices.profile.communication;

import com.google.gson.Gson;
import microservices.profile.business.ProfileBusinessImpl;
import microservices.profile.types.*;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 *file ProfileCommunicationImpl.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Gestisce tutta la comunicazione con il server riguardante la gestione del profilo dell'utente.
 *use Viene utilizzata per comunicare con il server.
 */
public class ProfileCommunicationImpl implements ProfileCommunication {
    private static final String TAG = "ProfileCommunicationImpl";
    private ProfileBusinessImpl _profileBusiness = ProfileBusinessImpl.getInstance();
    /**
     * @name changeProfile
     * @desc Cambia l'username al profilo, ritorna true (json) sse la modifica è andata a buon fine;
     * @param {Session} session - Rappresenta la sessione associata al utente;
     * @param {Profile} profile - Rappresenta il nuovo profilo associato all'utente;
     * @return {boolean}
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    @Override
    public Result changeProfile(String jsonSession, String jsonProfile) {
        //play.Logger.info(TAG + ".changeProfile(" + jsonSession + ", " + jsonProfile + ")");
        Gson gson = new Gson();
        Profile updateProfile = gson.fromJson(jsonProfile, ProfileImpl.class);
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userProfile = _profileBusiness.getProfile(session);
        //Profile userProfile = new ProfileImpl("Prova", 1); //rimuovere
        boolean isProfileChanged =  _profileBusiness.changeProfile ( userProfile, updateProfile);
        String json = gson.toJson(isProfileChanged);
        //play.Logger.info(TAG + ".changeProfile(" + jsonSession + ", " + jsonProfile + ") return: " + json);
        return ok(json);
    }

    /**
     * @param jsonSession
     * @param jsonBeacon
     * @returns {Profile}
     * @name getProfile
     * @desc Ritorna il profilo associato alla sessione;
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    @Override
    public Result getProfile(String jsonSession, String jsonBeacon) {
        //play.Logger.info(TAG + ".getProfile(" + jsonSession + ", " + jsonBeacon + ")");
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
        Profile profile = _profileBusiness.getProfile(session, beacon);
        String json = gson.toJson(profile);
        //play.Logger.info(TAG + ".getProfile(" + jsonSession + ", " + jsonBeacon + ") return: " + json);
        return ok(json);

    }

    /**
     * @param jsonSession
     * @param jsonBeacon  @returns {void}
     * @name updateLocation
     * @desc Aggiorna il Beacon associato all'utente;
     * @memberOf Server.Microservices.Profile.Communication.ProfileCommunication
     */
    @Override
    public Result updateLocation(String jsonSession, String jsonBeacon) {
        //play.Logger.info(TAG + ".updateLocation(" + jsonSession + ", " + jsonBeacon + ")");
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
        Profile profile = _profileBusiness.getProfile(session, beacon);
        _profileBusiness.updateLocation(profile, beacon);
        //play.Logger.info(TAG + ".updateLocation(" + jsonSession + ", " + jsonBeacon + ") return");
        return ok();
    }
}
