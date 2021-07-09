package microservices.battleship.communication;

import microservices.battleship.types.Beacon;
import play.mvc.Result;

/**
 *file BattleshipCommunication.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Interfaccia che gestisce la comunicazione tra server e client riguardante le funzionalità legate al gioco Battleship, quindi rimane in ascolto delle richieste che arrivano dal esterno del sistema server;
 *use Viene utilizzata per gestire la comunicazione tra server e client riguardante le funzionalità legate al gioco Battleship;
 */
public interface BattleshipCommunication {
    /**
     * @name isTeamExists
     * @desc Ritorna true sse il team associato al beacon è già stato creato;
     * @param {Beacon} beacon - Rappresenta il beacon più vicini all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result isTeamExists(String jsonBeacon);

    /**
     * @name isBattleRunning
     * @desc Ritorna true sse il team dell'utente è impegnato in una partita contro un altro team.
     * @param {Beacon} beacon - Rappresenta il beacon più vicini all'utente.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result isBattleRunning(String jsonBeacon);

    /**
     * @name createTeam
     * @desc Ritorna true sse la creazione del team è andata a buon fine;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @param {String} teamName - Rappresenta il nome che l'utente vuole associare alla nuova squadra;
     * @param {String} jsonBeacon - Rappresenta il beacon associato all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result createTeam(String jsonSession, String teamName, String jsonBeacon);

    /**
     * @name getTeam
     * @desc Ritorna il team dell'utente associato alla squadra.
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @returns {Team}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result getTeam(String jsonSession);

    /**
     * @name isTeamUpdateSumbribe
     * @desc Ritorna true sse c'è statto un aggiornamento nel team dell'utente;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //Result subscribeIsTeamChange(String jsonSession, String minSeconds);

    /**
     * @name removeTeamMember
     * @desc Rimuove il membro (passato come parametro) del team dell'utente associato alla sessione.
     * @param {String} jsonProfile - Rappresenta il profilo dell'utente da rimuove.
     * @param {String} jsonSession - Rappresenta la sessione dell'utente da rimuovere.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result removeTeamMember(String jsonSession, String jsonProfile);

    /**
     * @name leaveTeam
     * @desc Rimuove l'utente dalla squadra;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result leaveTeam(String jsonSession);

    /**
     * @name joinTeam
     * @desc Ritorna true se l'inserimento dell'utente è andato a buon fine.
     * @param {String} jsonBeacon - Rappresenta il beacon del tavolo dell'utente.
     * @param {String} jsonSession - Rappresenta la sessione dell'utente.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result joinTeam(String jsonSession, String jsonBeacon);

    /**
     * @name startSearchOpponent
     * @desc Ricerca un'avversario per il team. %Richiama startSearchOpponent di Business::BattleshipBusiness
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result startSearchingOpponent(String jsonSession);

    /**
     * @name isStartSearchingOpponent
     * @desc Ritorna true sse il team dell'utente ha iniziato la ricerca di un avversario;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */

    Result isStartSearchingOpponent(String jsonSession);

    /**
     * @name stopSearchingOpponent
     * @desc Se l'utente è il capitano del suo team, interrompe la ricerca di un opponente del team e fa ritornare tutti i componenti alla fase di formazione del team, altrimenti esce da team;
     * @param {String} jsonSession - Rappresenta la sessione associata al profilo.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result stopSearchingOpponent(String jsonSession);


    /**
     * @name isStopSearchingOpponent
     * @desc Ritorna true sse il team dell'utente è uscito dalla ricerca di un avversario;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result isStopSearchingOpponent(String jsonSession);

    /**
     * @name isCaptain
     * @desc Ritorna true sse l'utente è il capitano della sua squadra;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    Result isCaptain(String jsonSession);

    /**
     * @name subscribeStartBattleUpdate
     * @desc Ritorna true sse la partita è iniziata;
     * @param {String} jsonSession - Rappresenta la sessione associata all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Communication.BattleshipCommunication
     */
    //Result subscribeStartBattleUpdate(String jsonSession, String minSeconds);

//    Result startBattle(String jsonSession);

    //Result subscribeIsStopSearchingOpponent(String jsonSession, String jsonMinSeconds);

    Result getShipsToPositioning(String jsonSession);

    Result getTeamShips(String jsonSession);

    Result setShipPosition(String jsonSession, String jsonShip);

    //Result subscribeTeamShipsPosition(String jsonSession, String minSeconds);

    //Result subscribeIsStartAttackPhase(String jsonSession, String minSeconds);

    //Result subscribeIsStartEnemyAttackPhase(String jsonSession, String minSeconds);

    Result getAllayField(String jsonSession);

    Result getEnemyField(String jsonSession);

    Result isStartBattle(String jsonSession);

    Result isStartAttackPhase(String jsonSession);

    Result isTeamShipsPosition(String jsonSession);

    Result isStartEnemyAttackPhase(String jsonSession);

    Result sendShot(String jsonSession, String jsonShot);

    Result isBattleEnd(String jsonSession);

    Result getShotNumber(String jsonSession);


    Result getBattleFinalScore(String jsonSession);
}
