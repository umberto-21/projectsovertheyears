package microservices.battleship.business;

import java.util.List;

import microservices.battleship.types.*;
import microservices.battleship.types.Profile;
import microservices.battleship.types.Score;
import microservices.battleship.types.Team;

/**
 * file : BattleshipBusiness.java
 * author : Andria Umberto
 * date : 25/05/2016
 * brief : Interfaccia che gestisce la logica di funzionamento del servizio e risponde agli input e alle richieste
 *         che arrivano dall'esterno per quanto riguarda il gioco Battleship.
 * use : Viene utilizzata per gestire la logica del gioco Battleship e quindi la risposta alle
 *       richieste provenienti dall'esterno.
 */
public interface BattleshipBusiness {

    /**
     * @name checkStartBattle
     * @desc Controlla che tutte le navi delle squadre siano state posizionate. %Richima checkFinishShipPositioning di Battleship::Services::BattleshipServices sul team del profilo e sul team avversario
     * @param {Team} teamOne - Rappresenta il primo team della partita.
     * @param {Team} teamTwo - Rappresenta la seconda squadra della partita.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean checkStartBattle (Team teamOne, Team teamTwo);

    /**
     * @name createTeam
     * @desc Ritorna true sse la creazione del team è andata a buon fine;
     * @param {String} teamName - Rappresenta il nome da associare al team;
     * @param {Profile} profile - parametro di tipo Profile;
     * @param {Beacon} beacon - parametro di tipo Beacon;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean createTeam (String teamName, microservices.battleship.types.Profile profile,
    Beacon beacon);


    /**
     * @name getAllayField
     * @desc \begin{itemize} \item{chiama il metodo getTeam (profile : Profile) : Team sull'istanza di BattleshipServices} \item{chiama il metodo getTeamField (team : Team) : Field sull'istanza di BattleshipServices} \item{ritorna il risultato della precedente chiamata} \end{itemize}
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {Field}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public Field getAllayField (microservices.battleship.types.Profile profile);

    /**
     * @name getEnemyField
     * @desc begin{itemize} \item{chiama il metodo getTeam (profile : Profile) : Team} \item{chiama il metodo getTeamField (team : Team) : Field} \item{ritorna il risultato della precedente invocazione} \end{itemize}
     * @param {Profile} profile - profilo che ha richiesto il campo avversario;
     * @returns {Field}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public Field getEnemyField (microservices.battleship.types.Profile profile);


    /**
     * @name getShipsToPositioning
     * @desc chiama il metodo BattleshipServices.getShipsToPositioning (profile : Profile) : List;
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {List}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public List<ShipType> getShipsToPositioning (Profile profile);

    /**
     * @name getShotNumber
     * @desc Ritorna il numero di colpi da sparare;
     * @param {Profile} profile - Profilo del giocatore che deve sparare;
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public int getShotNumber (microservices.battleship.types.Profile profile);

    /**
     * @name getTeam
     * @desc \begin{itemize} \item{chiama il metodo getTeam (profile : Profile) Team sull'istanza di BattleshipServices} \item{torna il risultato della precedente chiamata} \end{itemize}
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {Team}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public microservices.battleship.types.Team getTeam (microservices.battleship.types.Profile profile);

    /**
     * @name getTeamFinalScore
     * @desc restituisce il punteggio del team;
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public Score getTeamFinalScore (Team team);

    /**
     * @name getTeamShips
     * @desc Ritorna la lista di navi già posizionate dai membri del team. %Richiama getShips di Battleship::Services::BattleshipServices
     * @param {Profile} profile - Rappresenta il profilo dell'utente del team.
     * @returns {List}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public List<Ship> getTeamShips (microservices.battleship.types.Profile profile);

    /**
     * @name isBattleEnd
     * @desc \begin{itemize} \item{chiama isBattleRunning (beacon : Beacon), se la chiamata ritorna false ritorna true altrimenti false} \end{itemize}
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isBattleEnd (Profile profile);

    /**
     * @name isBattleRunning
     * @desc Ritorna true sse il team dell'utente è impegnato in una partita contro un altro team.
     * @param {Beacon} beacon - Rappresenta il beacon più vicini all'utente.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isBattleRunning (Beacon beacon);

    /**
     * @name isBattleStart
     * @desc Controlla che il profile sia un membro dei team salvati nella lista isBattleStart; ritorna true nel caso lo sia;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isBattleStart (microservices.battleship.types.Profile profile);

    /**
     * @name isBattleWin
     * @desc Ritorna true sse il team ha affondato tutte le navi dell'avversario;
     * @param {Team} team - Team della squadra di cui si vuole sapere si ha vinto;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean allShipSunk(Team team);

    /**
     * @name isCaptain
     * @desc \begin{itemize} \item{chiama il metodo getTeam (profile : Profile) : Team} \item{controllo che il profilo ottenuto dal client sia il capitano, se così torno true; } \end{itemize}
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isCaptain (microservices.battleship.types.Profile profile);

    /**
     * @name isStartAttackPhase
     * @desc ritorna true sse il team associato al profilo è nella lista \_startAttackPhase;
     * @param {Profile} profile - profilo associato alla richiesta del client;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isStartAttackPhase (microservices.battleship.types.Profile profile);

    /**
     * @name isStartEnemyAttackPhase
     * @desc ritorna true sse il team associato al profilo è nella lista \_startEnemyAttackPhase
     * @param {Profile} profile - profilo associato alla richiesta del client;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isStartEnemyAttackPhase (microservices.battleship.types.Profile profile);

    /**
     * @name isStartSearchingOpponent
     * @desc \begin{itemize} \item{chiama il metodo BattleshipServices.getLocal (profile : Profile) : Local} \item{chiama il metodo BattleshipServices.getReadyTeams (local : Local) : List} \item{chiama il metodo BattleshipServices.getTeam (profile : Profile) : Team} \item{controlla che il team ottenuto dalla precedente chiamata sia nella lista di team pronti alla sfida} \item{ritorna true nel caso sia presente, false in caso contrario} \end{itemize}
     * @param {Profile} profile - profilo associato alla richiesta del client;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isStartSearchingOpponent (microservices.battleship.types.Profile profile);

    /**
     * @name isStopSearchingOpponent
     * @desc \begin{itemize} \item{chiama il metodo BattleshipServices.getLocal (profile : Profile) : Local} \item{chiama il metodo BattleshipServices.getReadyTeams (local : Local) : List} \item{chiama il metodo BattleshipServices.getTeam (profile : Profile) : Team} \item{controlla che il team ottenuto dalla precedente chiamata sia nella lista di team pronti alla sfida} \item{ritorna false nel caso sia presente, true in caso contrario} \end{itemize}
     * @param {Profile} profile - profilo associato alla richiesta del client;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isStopSearchingOpponent (microservices.battleship.types.Profile profile);

    /**
     * @name isTeamExists
     * @desc chiama il metodo isTeamExists (beacon : Beacon) : boolean sull'oggetto di tipo BattleshipServices
     * @param {Beacon} beacon - rappresenta il beacon associato al tavolo;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean isTeamExists (Beacon beacon);

    /**
     * @name joinTeam
     * @desc \begin{itemize} \item{Ottengo il profilo dal Client; } \item{Ottengo il Beacon dal Client; } \item{Ottengo il tavolo associato al Beacon;} \item{Ottengo il team associato al tavolo;} \item{Controllo che il team non sia completo altrimenti restituisco false;} \item{Aggiungo il membro al team e restituisco true;} \end{itemize}
     * @param {Profile} profile - parametro di tipo Profile;
     * @param {Beacon} beacon - parametro di tipo Beacon
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean joinTeam (microservices.battleship.types.Profile profile, Beacon beacon);

    /**
     * @name leaveTeam
     * @desc \begin{itemize} \item{chiama il metodo getTeam (profile : Profile) Team sull'istanza di BattleshipServices} \item{chiama il metodo removeTeamMember (team : Team, profile : Profile) : void sull'istanza di BattleshipServices} \end{itemize}
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public void leaveTeam (microservices.battleship.types.Profile profile);

    /**
     * @name removeTeamMember
     * @desc \begin{itemize} \item{Controlla che il profilo che ha inoltrato la richiesta sia il capitano altrimenti termina} \item{Controlla che il profilo da rimuovere sia il capitano, nel caso lo sia assegna il ruolo di capitano ad un nuovo utente e lo comunica al database} \item{Chiama il metodo BattleshipServices.removeTeamMember (team : Team, profile : Profile) : void; } \end{itemize}
     * @param {Profile} profile - Rappresenta il profilo dell'utente da rimuovere.
     * @param {Profile} profileToRemove - Rappresenta il profilo associato all'utente da rimuovere dal Team;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public void removeTeamMember (microservices.battleship.types.Profile profile, microservices.battleship.types.Profile
                                  profileToRemove);

    /**
     * @name sendShot
     * @desc \begin{itemize} \item{Controlla quanti colpi rimangono all'utente; } \item{Chiama il metodo getShotNumber (profile : Profile) : int, se il risultato è minore o uguale a 0 ritorna false; } \item{Chiama il metodo insertShot (shot : Shot, profile : Profile) : void e ritorna true; } \end{itemize}
     * @param {Shot} shot - indica il colpo da sparare;
     * @param {Profile} profile - indica l'utente che ha sparato il colpo;
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean sendShot (Shot shot, microservices.battleship.types.Profile profile);

    /**
     * @name setShipPosition
     * @desc \begin{itemize} \item{Chiama BattleshipServices.checkUserFinishShipPositioning (profile : Profile) : boolean, se false} begin {itemize} \item{Chiama BattleshipServices.getShips (team : Team) : List e controlla che non vi siano collisioni tra le navi già posizionate e la nave da posizionare; } \item{Controlla che le posizioni scelte non siano fuori dalla griglia di gioco; } \item{Se tutti i controlli hanno esito negativo chiama BattleshipServices.addUserShips(ship : Ship, profile : Profile) : void} \end{itemize} \item{Chiama BattleshipBusiness.checkStartBattle (team : Team, opponentTeam : Team ) : boolean} \end{itemize}
     * @param {Ship} ship - Rappresenta la nave posizionata dall'utente.
     * @param {Profile} profile - Rappresenta il profilo dell'utente che ha posizionato la nave.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public boolean setShipPosition (Ship ship, microservices.battleship.types.Profile profile);


    /**
     * @name startSearchingOpponent
     * @desc \begin{itemize} \item{Controlla che il profilo utente sia il capitano; } \item{chiama il metodo BattleshipServices.getLocal (profile : Profile) : Local; } \item{chiama il metodo BattleshipServices.getReadyTeams (local: Local) : List } \item{controlla che la lista non sia vuota} \item{se la lista non è vuota associa due team e chiama il metodo BattleshipServices.removeReadyTeam (team : Team) : void } \item{Crea il campo alleato e il campo avversario;} \item{Notifica i cambiamenti al database tramite il metodo BattleshipServices.updateField (field : Field, team: Team) : void } \item{se la lista è vuota chiama il metodo BattleshipServices.addReadyTeam (team : Team) : void } \end{itemize}
     * @param {Profile} profile - Parametro di tipo Profile;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public void startSearchingOpponent (microservices.battleship.types.Profile profile);

    /**
     * @name stopSearchingOpponet
     * @desc chiama il metodo removeReadyTeam (team : Team) : void sull'istanza di BattleshipServices;
     * @param {Profile} profile - parametro di tipo Profile;
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public void stopSearchingOpponent (microservices.battleship.types.Profile profile);

    /**
     * @name setShipToPositioning
     * @desc Assegna ad ogni utente il numero di navi che potrà posizionare; chiama BattleshipServices.setShipToPositioning (shipType : ShipType, profile : Profile) per ogni membro dei team;
     * @param {Team} teamOne - Rappresenta la prima squadra della partita, ai membri della quale vengono assegnate le navi.
     * @param {Team} teamTwo - Rappresenta la seconda squadra della partita, ai membri della quale vengono assegnate le navi.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Business.BattleshipBusiness
     */
    public void setShipToPositioning (Team teamOne, Team teamTwo);

    public void setRandomShipsPositions (Profile profile);

    public void setRandomShot (Profile profile);

    public List<Score> getBattleFinalScore (Profile profile);
}
