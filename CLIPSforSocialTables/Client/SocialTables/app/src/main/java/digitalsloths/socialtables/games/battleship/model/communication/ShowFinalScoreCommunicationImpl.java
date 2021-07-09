package digitalsloths.socialtables.games.battleship.model.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.model.communication.CommunicationImpl;
import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.ScoreImpl;
import digitalsloths.socialtables.types.Profile;
import digitalsloths.socialtables.types.ProfileImpl;
import digitalsloths.socialtables.types.Session;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pablos on 6/9/16.
 */
public class ShowFinalScoreCommunicationImpl extends CommunicationImpl implements ShowFinalScoreCommunication {    private ApiBattleshipRest _apiBattleshipRest;
    private static final String TAG = "EnemyAttackPhaseComm";

    private Gson gson;

    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Score.class, new ScoreDeserializer());
        gsonBuilder.registerTypeAdapter(Score.class, new ScoreInstanceCreator());
        gson = gsonBuilder.create();
    }

    private class ScoreDeserializer implements JsonDeserializer<Score> {
        public Score deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return (new Gson()).fromJson(jobject, ScoreImpl.class);
        }
    }

    private class ScoreInstanceCreator implements InstanceCreator<Score> {
        public Score createInstance(Type type) {
            return new ScoreImpl("", 0);
        }
    }

    private interface ApiBattleshipRest{

        @GET("/battleship/getBattleFinalScore/{session}")
        Call<List<Score>> getBattleFinalScore(@Path("session") String session);
        //todo gson list desiarilezr ->arraylist
    }

    /**
     * @name ShowFinalScoreCommunicationImpl
     * @desc Costruttore di default;
     * @memberOf Client.Games.Battleship.Model.Communication.ShowFinalScoreCommunicationImpl
     */
    public ShowFinalScoreCommunicationImpl() {
        _apiBattleshipRest = getApiBattleshipRest();
    }

    /**
     * @name getApiBattleshipRest
     * @desc Restituisce le API REST per battleship.
     * @param {ApiBattleshipRest}
     * @memberOf Client.Games.Battleship.Model.Communication.ShowFinalScoreCommunicationImpl
     */
    private ApiBattleshipRest getApiBattleshipRest(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiBattleshipRest.class);
    }



    /**
     * @name getFinalScore
     * @desc Restituisce il punteggio finale totalizzato dalla squadra avversario;
     * @returns {Score}
     * @memberOf Client.Games.Battleship.Model.Communication.ShowFinalScoreCommunication
     */
    @Override
    public void getEnemyTeamScore (Callback<List<Score>> callback){
        String methodSignature = "getFinalScore(...)";
       //Log.v(TAG, methodSignature);
        Session session = getSession();
        String jsonSession = gson.toJson(session);
        Call<List<Score>> call = _apiBattleshipRest.getBattleFinalScore(jsonSession);
        call.enqueue(callback);
       //Log.v(TAG, methodSignature + " return : void ");
    }
}
