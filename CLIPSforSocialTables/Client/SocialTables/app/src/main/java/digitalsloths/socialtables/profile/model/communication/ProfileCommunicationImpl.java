package digitalsloths.socialtables.profile.model.communication;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * name ProfileCommunicationImpl.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Gestisce tutta la comunicazione con il server riguardante la gestione del profilo dell'utente;
 * use Viene utilizzata per comunicare con il server;
 */
public class ProfileCommunicationImpl extends CommunicationImpl implements ProfileCommunication {
    private ApiProfile _apiProfile;
    private static final String TAG = "ProfileCommunication";
    private interface ApiProfile {

        @GET("profile/getProfile/{session}/{beacon}")
        Call<ProfileImpl> getProfile(@Path("session") String session, @Path("beacon") String beacon);

        @GET("profile/changeProfile/{session}/{profile}")
        Call<Boolean> changeProfile(@Path("session") String session, @Path("profile") String profile);
    }

    /**
     * @name ProfileCommunicationImpl
     * @desc Costruttore di default
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    public ProfileCommunicationImpl() {
        _apiProfile = getApiProfile();
    }

    /**
     * @name getApiProfile
     * @desc Ritorna le ApiProdile associate.
     * @returns {ApiProfile}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    private ApiProfile getApiProfile(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiProfile.class);
    }

    /**
     * @name getProfile
     * @desc Ritorna il profilo associato al utente;
     * @returns {Profile}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    @Override
    public Profile getProfile(Beacon beacon){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Profile> response = executor.submit(new getProfileCall(beacon));
        Profile profile = null;

        try {
            profile =  response.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return profile;
    }

    private class getProfileCall implements Callable<Profile>{
        private Beacon _beacon;

        public getProfileCall(Beacon beacon){
            _beacon = beacon;
        }
        @Override
        public Profile call() throws Exception {
            return getProfileSync(_beacon);
        }
    }

    /**
     * @name getProfileSync
     * @desc Ritorna il profilo associato al utente tramite il Beacon;
     * @param {Beacon} beacon - il Beacon letto.
     * @returns {Profile}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    public Profile getProfileSync(Beacon beacon) {
       //Log.v(TAG, ".getProfile(" + (new Gson()).toJson(beacon) + ")");
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        String jsonBeacon = gson.toJson(beacon);


        Call<ProfileImpl> call = _apiProfile.getProfile(jsonSession, jsonBeacon);
        try {
            Response<ProfileImpl> response = call.execute();
            Profile profile = response.body();
           //Log.v(TAG, ".getProfile(" + (new Gson()).toJson(beacon) + ") return : " + (new Gson()).toJson(profile));
            return profile;
        } catch (IOException e) {
            // handle error
           //Log.e(TAG, "getProfile: Errore communicazione con il server [Eccezione "+ e.getMessage() + "]");
           //Log.v(TAG, ".getProfile(" + (new Gson()).toJson(beacon) + ") return : " + (new Gson()).toJson(null));
            return null;
        }
    }

    /**
     * @name changeProfile
     * @desc Cambia l'username al profilo;
     * @param {String} username - Nuovo username da associare al profilo;
     * @returns {boolean}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    @Override
    public boolean changeProfile(Profile profile){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Boolean> response = executor.submit(new ChangeProfileCall(profile));
        Boolean isChanged = false;

        try {
            isChanged =  response.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return isChanged;
    }

    private class ChangeProfileCall implements Callable<Boolean>{
        private Profile _profile;

        public ChangeProfileCall(Profile profile){
            _profile = profile;
        }
        @Override
        public Boolean call() throws Exception {
            return changeProfileSync(_profile);
        }
    }

    /**
     * @name changeProfileSync
     * @desc Cambia il profilo dell'utente;
     * @param {Profile} profile - Il profilo utente attuale;
     * @returns {boolean}
     * @memberOf Client.Profile.Model.Communication.ProfileCommunication
     */
    public boolean changeProfileSync(Profile profile) {
       //Log.v(TAG, ".changeProfile(" + (new Gson()).toJson(profile) + ")");
        Session session = getSession();
        Gson gson = new Gson();
        String jsonSession = gson.toJson(session);
        String jsonProfile = gson.toJson(profile);

        Call<Boolean> call = _apiProfile.changeProfile(jsonSession, jsonProfile);
        try {
            Response<Boolean> response = call.execute();
            Boolean result = response.body();
           //Log.v(TAG, ".changeProfile(" + (new Gson()).toJson(profile) + ") return: " + (new Gson()).toJson(result));
            return result;
        } catch (IOException e) {
            // handle error
           //Log.e(TAG, "getProfile: Errore communicazione con il server [Eccezione "+ e.getMessage() + "]");
           //Log.v(TAG, ".changeProfile(" + (new Gson()).toJson(profile) + ") return: " + (new Gson()).toJson(false));
            return false;
        }
    }
}
