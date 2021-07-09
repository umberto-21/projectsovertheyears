package microservices.battleship.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import microservices.battleship.business.BattleshipBusinessImpl;
import microservices.battleship.types.Beacon;
import microservices.battleship.types.BeaconImpl;
import microservices.battleship.types.Field;
import microservices.battleship.types.Position;
import microservices.battleship.types.PositionImpl;
import microservices.battleship.types.Profile;
import microservices.battleship.types.ProfileImpl;
import microservices.battleship.types.Score;
import microservices.battleship.types.ScoreImpl;
import microservices.battleship.types.Ship;
import microservices.battleship.types.ShipImpl;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Shot;
import microservices.battleship.types.ShotImpl;
import microservices.battleship.types.Team;
import microservices.profile.business.ProfileBusiness;
import microservices.profile.business.ProfileBusinessImpl;
import microservices.profile.communication.ProfileCommunication;
import microservices.profile.communication.ProfileCommunicationImpl;
import microservices.profile.types.Session;
import microservices.profile.types.SessionImpl;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * file BattleshipCommunication.java author Ugo Padoan date 26/05/2016 brief Implementa
 * l'interfaccia per la gestione della comunicazione tra client e server per le funzionalità che
 * riguardano il gioco Battleship; use Viene utilizzata per gestire la comunicazione tra server e
 * client riguardante le funzionalità legate al gioco Battleship;
 */
public class BattleshipCommunicationImpl implements BattleshipCommunication {
    private static final String TAG = "BattleshipCommunicationImpl";
    private ProfileBusiness _profileBusiness = ProfileBusinessImpl.getInstance();
    private BattleshipBusinessImpl _BattleshipBusiness = BattleshipBusinessImpl.getInstance();
    private ProfileCommunication profileCommunication = new ProfileCommunicationImpl();
    private int sleepTimeUpdate = 1000;

    /**
     * @name isTeamExists
     * @desc Ritorna true sse il team associato al beacon è già stato creato;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    @Override
    public Result isTeamExists(String jsonBeacon) {
       //play.Logger.info(TAG + ".isTeamExists(" + jsonBeacon + ")");
        Gson gson = new Gson();
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
        Boolean isTeamExistsValue = _BattleshipBusiness.isTeamExists(beacon);
        String jsonIsTeamExistsValue = gson.toJson(isTeamExistsValue);
       //play.Logger.info(TAG + ".isTeamExists(" + jsonBeacon + ") return: " + jsonIsTeamExistsValue);
        return ok(jsonIsTeamExistsValue);
    }

    private Profile castProfile(microservices.profile.types.Profile profile) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(profile), microservices.battleship.types.ProfileImpl.class);
    }

    /**
     * @name isBattleRunning
     * @desc Ritorna true sse il team dell'utente è impegnato in una partita contro un altro team.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    @Override
    public Result isBattleRunning(String jsonBeacon) {
       //play.Logger.info(TAG + ".isBattleRunning(" + jsonBeacon + ")");
        Gson gson = new Gson();
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
//        Boolean isBattleRunning = false;
        Boolean isBattleRunning = _BattleshipBusiness.isTeamExists(beacon);
        String jsonisBattleRunningValue = gson.toJson(isBattleRunning);
       //play.Logger.info(TAG + ".isTeamExists(" + jsonBeacon + ") return: " + jsonisBattleRunningValue);
        return ok(jsonisBattleRunningValue);
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @param {String} teamName - Rappresenta il nome che l'utente vuole associare alla nuova
     *                 squadra;
     * @param {String} jsonBeacon - Rappresenta il beacon associato all'utente;
     * @name createTeam
     * @desc Ritorna true sse la creazione del team è andata a buon fine;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    @Override
    public Result createTeam(String jsonSession, String teamName, String jsonBeacon) {
        String methodSignature = ".createTeam(" + jsonSession + ", " + teamName + ", " + jsonBeacon + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
        profileCommunication.updateLocation(jsonSession, jsonBeacon);
        microservices.profile.types.Profile profileProfile = _profileBusiness.getProfile(session);
        Profile userPorifle = castProfile(profileProfile);
        Boolean isTeamCreate = _BattleshipBusiness.createTeam(teamName, userPorifle, beacon);
        String jsonIsTeamCreate = gson.toJson(isTeamCreate);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsTeamCreate);
        return ok(jsonIsTeamCreate);
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @name getTeam
     * @desc Ritorna il team dell'utente associato alla squadra.
     * @returns {Team}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    @Override
    public Result getTeam(String jsonSession) {
        String methodSignature = ".getTeam(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        Team getTeamValue = _BattleshipBusiness.getTeam(userPorifle);
        String jsonIsTeamCreate = gson.toJson(getTeamValue);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsTeamCreate);
        return ok(jsonIsTeamCreate);
    }

    /**
     * @name isTeamUpdateSumbribe
     * @desc Ritorna true sse c'è statto un aggiornamento nel team dell'utente;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */

    /**
     * @param {String} jsonProfile - Rappresenta il profilo dell'utente da rimuove.
     * @param {String} jsonSession - Rappresenta la sessione dell'utente da rimuovere.
     * @name removeTeamMember
     * @desc Rimuove il membro (passato come parametro) del team dell'utente associato alla
     * sessione.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/removeTeamMember/{session}/{profile}")
    @Override
    public Result removeTeamMember(String jsonSession, String jsonProfile) {
        String methodSignature = ".removeTeamMember(" + jsonSession + ", " + jsonProfile + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userProfile = castProfile(_profileBusiness.getProfile(session));
        Profile profile = gson.fromJson(jsonProfile, ProfileImpl.class);
        _BattleshipBusiness.removeTeamMember(userProfile, profile);
       //play.Logger.info(TAG + methodSignature + " return ");
        return ok();
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @name leaveTeam
     * @desc Rimuove l'utente dalla squadra;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/leaveTeam/{session}")
    @Override
    public Result leaveTeam(String jsonSession) {
        String methodSignature = ".leaveTeam(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile profile = castProfile(_profileBusiness.getProfile(session));
        _BattleshipBusiness.leaveTeam(profile);
       //play.Logger.info(TAG + methodSignature + " return ");
        return ok();
    }

    /**
     * @param {String} jsonBeacon - Rappresenta il beacon del tavolo dell'utente.
     * @param {String} jsonSession - Rappresenta la sessione dell'utente.
     * @name joinTeam
     * @desc Ritorna true se l'inserimento dell'utente è andato a buon fine.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/joinTeam/{session}/{beacon}")
    @Override
    public Result joinTeam(String jsonSession, String jsonBeacon) {
        String methodSignature = ".joinTeam(" + jsonSession + ", " + jsonBeacon + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Beacon beacon = gson.fromJson(jsonBeacon, BeaconImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isJoinTeam = _BattleshipBusiness.joinTeam(userPorifle, beacon);
        String jsonIsJoinTeam = gson.toJson(isJoinTeam);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsJoinTeam);
        return ok(jsonIsJoinTeam);
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @name startSearchOpponent
     * @desc Ricerca un'avversario per il team. %Richiama startSearchOpponent di
     * Business::BattleshipBusiness
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/startSearchingOpponent/{session}")
    @Override
    public Result startSearchingOpponent(String jsonSession) {
        String methodSignature = ".startSearchingOpponent(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile profile = castProfile(_profileBusiness.getProfile(session));
        _BattleshipBusiness.startSearchingOpponent(profile);
       //play.Logger.info(TAG + methodSignature + " return ");
        return ok();
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @name isStartSearchingOpponent
     * @desc Ritorna true sse il team dell'utente ha iniziato la ricerca di un avversario;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/isStartSearchingOpponent/{session}")
    @Override
    public Result isStartSearchingOpponent(String jsonSession) {
        String methodSignature = ".isStartSearchingOpponent(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isStartSearchingOpponentUpdate = _BattleshipBusiness.isStartSearchingOpponent(userPorifle);
        String jsonIsStartSearchingOpponent = gson.toJson(isStartSearchingOpponentUpdate);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStartSearchingOpponent);
        return ok(jsonIsStartSearchingOpponent);
    }


    /**
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @name stopSearchingOpponent
     * @desc Se l'utente è il capitano del suo team, interrompe la ricerca di un opponente del team
     * e fa ritornare tutti i componenti alla fase di formazione del team, altrimenti esce da team;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/stopSearchingOpponent/{session}")
    @Override
    public Result stopSearchingOpponent(String jsonSession) {
        String methodSignature = ".stopSearchingOpponent(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile profile = castProfile(_profileBusiness.getProfile(session));
        _BattleshipBusiness.stopSearchingOpponent(profile);
       //play.Logger.info(TAG + methodSignature + " return ");
        return ok();
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @name isStopSearchingOpponent
     * @desc Ritorna true sse il team dell'utente è uscito dalla ricerca di un avversario;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/isStopSearchingOpponent/{session}")
    public Result isStopSearchingOpponent(String jsonSession) {
        String methodSignature = ".isStopSearchingOpponent(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isStopSearchingOpponent = _BattleshipBusiness.isStopSearchingOpponent(userPorifle);
        String jsonIsStartSearchingOpponent = gson.toJson(isStopSearchingOpponent);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStartSearchingOpponent);
        return ok(jsonIsStartSearchingOpponent);
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @name isCaptain
     * @desc Ritorna true sse l'utente è il capitano della sua squadra;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/isCaptain/{session}")
    public Result isCaptain(String jsonSession) {
        String methodSignature = ".isCaptain(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isCaptain = _BattleshipBusiness.isCaptain(userPorifle);
        String jsonIsCaptain = gson.toJson(isCaptain);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsCaptain);
        return ok(jsonIsCaptain);
    }

    /**
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @name isStartBattle
     * @desc Ritorna true sse la partita è iniziata;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //@GET("battleship/isStartBattle/{session}")
    @Override
    public Result isStartBattle(String jsonSession) {
        String methodSignature = ".isStartBattle(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isStartBattle = _BattleshipBusiness.isBattleStart(userPorifle);
        String jsonIsStartSearchingOpponent = gson.toJson(isStartBattle);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStartSearchingOpponent);
        return ok(jsonIsStartSearchingOpponent);
    }

//    @Override
//    public Result startBattle(String jsonSession){
//        String methodSignature = ".startBattle(" + jsonSession + ")";
//    //play.Logger.info(TAG + methodSignature);
//        Gson gson = new Gson();
//        Session session = gson.fromJson(jsonSession, SessionImpl.class);
//        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
//        //isJoinTeam = _BattleshipBusiness.startBattle(userPorifle);
//    //play.Logger.info(TAG + methodSignature + " return: void");
//        return ok();
//    }

//    @Override
//    public Result isStopSearchingOpponent(String jsonSession){
//        String methodSignature = ".subscribeIsStopSearchingOpponent(" + jsonSession + ")";
//    //play.Logger.info(TAG + methodSignature);
//        Gson gson = new Gson();
//        Session session = gson.fromJson(jsonSession, SessionImpl.class);
//        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
//        boolean isStopSearchingOpponent = _BattleshipBusiness.isStopSearchingOpponent(userPorifle);
//        String jsonIsStopSearchingOpponent = gson.toJson(isStopSearchingOpponent);
//    //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStopSearchingOpponent);
//        return ok(jsonIsStopSearchingOpponent);
//    }

    @Override
    public Result getShipsToPositioning(String jsonSession) {
        String methodSignature = ".getShipsToPositioning(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        List<ShipType> shipsToPositioning = _BattleshipBusiness.getShipsToPositioning(userPorifle);
        String jsonShipsToPositioning = gson.toJson(shipsToPositioning);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonShipsToPositioning);
        return ok(jsonShipsToPositioning);
    }

    @Override
    public Result getTeamShips(String jsonSession) {
        String methodSignature = ".getTeamShips(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        List<Ship> teamShips = _BattleshipBusiness.getTeamShips(userPorifle);
        String jsonTeamShips = gson.toJson(teamShips);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonTeamShips);
        return ok(jsonTeamShips);
    }

    @Override
    public Result setShipPosition(String jsonSession, String jsonShip) {
        String methodSignature = ".setShipPosition(" + jsonSession + ", " + jsonShip + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson;// = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Position.class, new PositionDeserializer());
//        gsonBuilder.registerTypeAdapter(Position.class, new PositionInstanceCreator());
        gson = gsonBuilder.create();


        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Ship ship = gson.fromJson(jsonShip, ShipImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        Boolean isBattleshipSetted = _BattleshipBusiness.setShipPosition(ship, userPorifle);
        String jsonIsBattleshipSetted = gson.toJson(isBattleshipSetted);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsBattleshipSetted);
        return ok(jsonIsBattleshipSetted);
    }

    //battleship/isTeamShipsPosition/:session
    @Override
    public Result isTeamShipsPosition(String jsonSession) {
        String methodSignature = ".isTeamShipsPosition(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isTeamShipsUpdate = _BattleshipBusiness.isStartSearchingOpponent(userPorifle);
        String jsonIsTeamShipsUpdate = gson.toJson(isTeamShipsUpdate);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsTeamShipsUpdate);
        return ok(jsonIsTeamShipsUpdate);
    }

    @Override
    public Result isStartAttackPhase(String jsonSession) {
        String methodSignature = ".isStartAttackPhase(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isStartAttackPhase = _BattleshipBusiness.isStartAttackPhase(userPorifle);
        String jsonIsStartAttackPhase = gson.toJson(isStartAttackPhase);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStartAttackPhase);
        return ok(jsonIsStartAttackPhase);
    }

    @Override
    public Result isStartEnemyAttackPhase(String jsonSession) {
        String methodSignature = ".subscribeIsStartEnemyAttackPhase(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isStartEnemyAttackPhase = _BattleshipBusiness.isStartEnemyAttackPhase(userPorifle);
        String jsonIsStartEnemyAttackPhase = gson.toJson(isStartEnemyAttackPhase);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsStartEnemyAttackPhase);
        return ok(jsonIsStartEnemyAttackPhase);
    }

    @Override
    public Result getAllayField(String jsonSession) {
        String methodSignature = ".getAllayField(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        Field allayField = _BattleshipBusiness.getAllayField(userPorifle);
        //isJoinTeam = _BattleshipBusiness.getEnemyField(userPorifle);
        String jsonAllayField = gson.toJson(allayField);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonAllayField);
        return ok(jsonAllayField);
    }

    @Override
    public Result getEnemyField(String jsonSession) {
        String methodSignature = ".getAllayField(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        Field allayField = _BattleshipBusiness.getEnemyField(userPorifle);
        String jsonAllayField = gson.toJson(allayField);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonAllayField);
        return ok(jsonAllayField);
    }

    private class PositionDeserializer implements JsonDeserializer<Position> {
        public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = (JsonObject) json;
            return (new Gson()).fromJson(jobject, PositionImpl.class);
        }
    }
//
//    private class PositionInstanceCreator implements InstanceCreator<Position> {
//        public Position createInstance(Type type) {
//            return new PositionImpl(1, 0);
//        }
//    }

//    @Override
    public Result sendShot(String jsonSession, String jsonShot) {
        String methodSignature = ".sendShot(" + jsonSession + ", " + jsonShot + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson;// = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Position.class, new PositionDeserializer());
//        gsonBuilder.registerTypeAdapter(Position.class, new PositionInstanceCreator());
        gson = gsonBuilder.create();

        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Shot shot = gson.fromJson(jsonShot, ShotImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isSendShot = _BattleshipBusiness.sendShot(shot, userPorifle);
        String jsonIsSendShot = gson.toJson(isSendShot);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsSendShot);
        return ok(jsonIsSendShot);
    }

    @Override
    public Result isBattleEnd(String jsonSession) {
        String methodSignature = ".isBattleEnd(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        boolean isBattleEndValue = _BattleshipBusiness.isBattleEnd(userPorifle);
        String jsonIsBattleEndValue = gson.toJson(isBattleEndValue);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonIsBattleEndValue);
        return ok(jsonIsBattleEndValue);
    }

    @Override
    public Result getShotNumber(String jsonSession) {
        String methodSignature = ".isBattleEnd(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
        int shotNumber = _BattleshipBusiness.getShotNumber(userPorifle);
        String shotNumberValue = gson.toJson(shotNumber);
       //play.Logger.info(TAG + methodSignature + " return: " + shotNumberValue);
        return ok(shotNumberValue);
    }

    @Override
    public Result getBattleFinalScore(String jsonSession) {
        String methodSignature = ".getBattleFinalScore(" + jsonSession + ")";
       //play.Logger.info(TAG + methodSignature);
        Gson gson = new Gson();
        Session session = gson.fromJson(jsonSession, SessionImpl.class);
        Profile userPorifle = castProfile(_profileBusiness.getProfile(session));
//        List<Score> battleFinalScore = new ArrayList<>(2);
//        battleFinalScore.add(new ScoreImpl(1, "Team 1"));
//        battleFinalScore.add(new ScoreImpl(2, "Team 2"));
        List<Score> battleFinalScore= _BattleshipBusiness.getBattleFinalScore(userPorifle);
        String jsonBattleFinalScore = gson.toJson(battleFinalScore);
       //play.Logger.info(TAG + methodSignature + " return: " + jsonBattleFinalScore);
        return ok(jsonBattleFinalScore);
    }
}
