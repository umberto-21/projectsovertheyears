package microservices.profile.business;

import com.google.gson.Gson;
import microservices.profile.services.ProfileServices;
import microservices.profile.services.ProfileServicesImpl;
import microservices.profile.types.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by umberto on 5/24/16.
 */
public class ProfileBusinessImpl implements ProfileBusiness {

    private static final String TAG = "ProfileBusinessImpl";
    private static ProfileBusinessImpl instance;
    private static ProfileServices _services;

    private ProfileBusinessImpl () {
        //play.Logger.info(TAG + ".ProfileBusinessImpl()");
        _services = ProfileServicesImpl.getInstance();
        //play.Logger.info(TAG + ".ProfileBusinessImpl()");
    }

    public static ProfileBusinessImpl getInstance () {
        //play.Logger.info(TAG + ".getInstance()");
        if (instance == null) instance =new ProfileBusinessImpl();
        //play.Logger.info(TAG + ".getInstance() return: " + instance);
        return instance;
    }

    @Override
    public boolean changeProfile(Profile userProfile, Profile updateProfile) {

        //play.Logger.info(TAG + ".changeProfile(" + (new Gson()).toJson(userProfile) + ", " + (new Gson()).toJson(updateProfile) + ")");
        //--> è necessario controllare che il nuovo nome utente sia univoco

        Local local =_services.getLocal (userProfile);

        List<String> nameList =_services.getNameList(local);
        Iterator<String> itr =nameList.iterator();
        while (itr.hasNext()) {
            String name =itr.next();
            if (name == updateProfile.getUsername()) return false;
        }

        //chiamo il metodo changeProfile e ritorno il risultato
        boolean result = _services.changeProfile(userProfile, updateProfile);
        //play.Logger.info(TAG + "_services.changeProfile (" + (new Gson()).toJson(userProfile) + "," + (new Gson()).toJson(updateProfile) + ") return: " + (new Gson()).toJson(result));
        //play.Logger.info(TAG + ".changeProfile(" + userProfile + ", " + updateProfile + ") return: " + result);
        return result;
    }

    @Override
    public Profile createNewProfile(Session session, Beacon beacon) {
        //play.Logger.info(TAG + ".createNewProfile(" + session + ", " + beacon + ")");
        //Chiamo il metodo getNewProfile su profileServices
        String userName ="User_" +session.getId();
        Profile result =_services.getNewProfile (session, beacon, new ProfileImpl(userName, null));
        //play.Logger.info(TAG + ".createNewProfile(" + session + ", " + beacon + ") return: " + result);
        return result;
    }

    @Override
    public Profile getProfile(Session session, Beacon beacon) {
        //play.Logger.info(TAG + ".getProfile(" + session + ", " + beacon + ")");
        //Chiamo il metodo getProfile
        Profile profile =_services.getProfile (session);
        //play.Logger.info(TAG + "profile: " + (new Gson()).toJson(profile));
        //Controllo che il profilo esista se non esiste lo creo
        if (profile == null) profile =createNewProfile(session,beacon);
        //play.Logger.info(TAG + ".getProfile(" + session + ", " + beacon + ") return: " + profile );
        return profile;
    }

    @Override
    public Profile getProfile(Session session) {
        //play.Logger.info(TAG + ".getProfile(" + session + ")");
        Profile profile =_services.getProfile(session);
        //play.Logger.info(TAG + ".getProfile(" + session + ") return: " + profile );
        return profile;
    }

    @Override
    public void updateLocation(Profile profile, Beacon beacon) {
        //play.Logger.info(TAG + ".updateLocation(" + profile + ", " + beacon + ")");
        //chiamo il metodo updateLocation
        _services.updateLocation(profile, beacon);
        //play.Logger.info(TAG + ".updateLocation(" + profile + ", " + beacon + ")");
    }
}
