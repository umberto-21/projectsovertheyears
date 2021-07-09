package microservices.battleship.persistence;
import microservices.battleship.types.Team;
import microservices.battleship.types.Ship;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Shot;
import microservices.battleship.types.Field;
import microservices.battleship.types.Score;
import microservices.battleship.types.Table;
import microservices.battleship.types.Local;
import java.util.List;

/**
 *file BattleshipPersistence.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Interfaccia che gestisce i dati elementari relativi al gioco Battleship, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato;
 *use Viene utilizzata per interfacciarsi e comunicare direttamente con il database utilizzato;
 */
public interface BattleshipPersistence{

  //BATTLESHIP

  /**
   * @name addUserShips
   * @desc Inserisce nel campo una nuova nave associata all'utente.
   * @param {boolean} isVertical - Dice se la nave è posizionata in verticale.
   * @param {int} x - Rappresenta l'ascissa del posizionamento della nave.
   * @param {int} y - Rappresenta l'ordinata del posizionamento della nave.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void addUserShips(int x, int y, boolean vertical, int profileId);

  /**
   * @name checkUserFinishShipPositioning
   * @desc Controlla che tutte le navi dell'utente siano state posizionate.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean checkUserFinishShipPositioning(int profileId);

  /**
   * @name checkUserFinishShoots
   * @desc Ritorna true se l'utente ha finito i colpi dal sparare.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean checkUserFinishShoots(int profileId);

  /**
   * @name deleteBattle
   * @desc Metodo che elimina la battaglia di un team;
   * @param {int} teamId - Identificativo della squadra che si vuole togliere dalla battaglia;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void deleteBattle(int teamId);

  /**
   * @name getOpponentTeam
   * @desc Restituisce l'id della squadra avversaria a quella associata al id passato come parametro;
   * @param {int} teamId - Identificativo della squadra di cui si vuole conoscere l'avversario;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Team getOpponentTeam(int teamId);

  /**
   * @name getReadyTeams
   * @desc Metodo che ritorna una lista di team pronti a giocare;
   * @param {int} localId - Identificativo del locale di cui si vogliono conoscere le squadre pronte a giocare;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  List<Team> getReadyTeams(int localId);

  /**
   * @name getShotList
   * @desc Ritorna la lista di colpi sparati durante l'ultimo turno dall'utente associato all'id passato come parametro.
   * @param {int} profileId - Id associato al profilo di cui si vuole avere la lista di colpi sparati durante l'ultimo turno;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  List<Shot> getShotList(int profileId);

  /**
   * @name getShotNumber
   * @desc Ritorna il numero dei colpi che ha a disposizione un giocatore;
   * @param {int} profileId - Identificativo dell'utente di cui si vogliono sapere il numero di colpi;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  int getShotNumber(int profileId);

  /**
   * @name getTeamFormProfileId
   * @desc Ritorna il tipo team associato a un profilo dato;
   * @param {int} profileId - Identificativo del profilo di cui si vuole ottenere il team;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Team getTeamFormProfileId(int profileId);

  /**
   * @name getTeamField
   * @desc Restituisce il campo associato alla squadra con id uguale a quello passato per parametro.
   * @param {int} teamId - Identificativo del team di cui si vuole ottenere il campo di battaglia;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Field getTeamField(int teamId);

  /**
   * @name getUserShips
   * @desc Ritorna le navi assegnate all'utente.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  List<Ship> getUserShips(int profileId);

  /**
   * @name insertSearchingOpponent
   * @desc Inserisce nel database la squadra che ha fatto richiesta di giocare.
   * @param {int} idTeam - Rappresenta il team da inserire nel database.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void insertSearchingOpponent(int teamId);

  /**
   * @name insertShoot
   * @desc Memorizza il colpo sparato dall'utente.
   * @param {int} x - Rappresenta l'ascissa del colpo sparato.
   * @param {int} y - Rappresenta l'ordinata del colpo sparato.
   * @param {int} idProfilo - Rappresenta l'identificatore del profilo che ha sparato il colpo.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void insertShot(int x, int y, int profileId);

  /**
   * @name isBattleRunning
   * @desc Ritorna true se una battaglia e' in corso con un determinato team;
   * @param {int} teamId - Team di cui si vuole sapere se e' ingaggiato in una battaglia;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean isBattleRunning(int teamId);

  /**
   * @name isTeamExist
   * @desc Ritorna true se esiste gia' un team associato ad un beacon;
   * @param {String} uuid - Uuid associato al beacon di un tavolo;
   * @param {int} major - Major associato al beacon di un tavolo;
   * @param {int} minor - Minor associato al beacon di un tavolo;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean isTeamExists(String uuid, int major, int minor);

  /**
   * @name removeReadyTeam
   * @desc Rimuove un team che e' pronto a giocare;
   * @param {int} teamId - Identificativo di un team pronto a giocare che si vuole rimuovere;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void removeReadyTeam(int teamId);

  /**
   * @name removeTeamMember
   * @desc Rimuove il membro passato con il parametro idProfile dal team specificato dal parametro idTeam.
   * @param {int} idProfile - Rappresenta l'identificativo del profilo da rimuovere.
   * @param {int} idTeam - Rappresenta l'identificativo del team del l'utente.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void removeTeamMember(int teamId, int profileId);

  /**
   * @name setOpponent
   * @desc Imposta le due squadre sfidanti della partita.
   * @param {int} idTeamOne - Rappresenta l'identificativo della prima squadra partecipante alla partita.
   * @param {int} idTeamTwo - Rappresenta l'identificativo della seconda squadra partecipante alla partita.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void setOpponent(int teamOneId, int teamTwoId);

  /**
   * @name setShipToPositioning
   * @desc Assegna una nave da posizionare ad un utente;
   * @param {int} length - Lunghezza della nave da posizionare;
   * @param {int} profileId - Identificativo dell'utente a cui si vuole assegnare una nave da posizionare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void setShipToPositioning(int length, int profileId);

  /**
   * @name updateField
   * @desc Aggiorna lo stato del capo indicato da fieldId; %Probabilmente da cambiare con un addShoot(fieldId, x, y) {dipende come implementiamo il campo}
   * @param {int} teamId - Rappresenta l'identificativo del team il cui campo e' da aggiornare;
   * @param {String[][]} grid - Griglia con gli esiti dei colpi con cui aggiornare il campo;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void updateField(String [][] grid, int teamId);

  /**
   * @name setAttackTeam
   * @desc Imposta un team come attaccante;
   * @param {int} teamId - Identificativo del team da impostare come attaccante;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void setAttackTeam(int teamId);

  /**
   * @name isAttackPhase
   * @desc Ritorna true se un team e' in fase di attacco;
   * @param {int} teamId - Identificativo del team di cui si vuole sapere se e' in fase d'attacco;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean isAttackPhase(int teamId);

  /**
   * @name removeAttackTeam
   * @desc Rimuove dalla fase d'attacco un determinato team;
   * @param {int} teamId - Identificativo del team che si vuole rimuovere dalla fase d'attacco;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void removeAttackTeam(int teamId);

  /**
   * @name getLocal
   * @desc Ritorna il locale il cui e' un determinato utente;
   * @param {int} profileId - Identificativo dell'utente di cui vogliamo sapere in che locale e'
   * @returns {Local}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Local getLocal(int profileId);

  /**
   * @name getShipsToPositioning
   * @desc Ritorna una lista di navi da far posizionare ad un utente;
   * @param {int} profileId - Identificativo dell'utente di cui si vogliono sapere quali navi deve posizionare;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  List<ShipType> getShipsToPositioning(int profileId);

  /**
   * @name setMaxAvailableShots
   * @desc Setta il numero massimo di colpi che puo' sparare un utente;
   * @param {int} numberOfShots - Numero massimo di colpi da assegnare a un utente;
   * @param {int} profileId - Utente a cui assegnare un numero massimo di colpi;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean setMaxAvailableShots(int numberOfShots, int profileId);

  /**
   * @name addReadyTeam
   * @desc Aggiunge un team alla lista di quelli pronti a giocare;
   * @param {int} teamId - Team da aggiungere alla lista di quelli pronti a giocare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void addReadyTeam(int teamId);

  /**
   * @name clearTeamShots
   * @desc Rimuove tutti i colpi dei membri del team associati ad una squadra;
   * @param {int} teamId - Identificativo del team a cui si vogliono togliere i colpi dei rispettivi membri;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void clearTeamShots(int teamId);
  
  //SCORE
  
  /**
  * @name addScore
  * @desc Memorizza lo score passato associandolo al gioco e alla squadra identificati dai parametri;
  * @param {int} gemeId - Identificatore del gioco al quale associare il punteggio;
  * @param {int} score - Punteggio da inserire nella classifica del gioco indicato;
  * @param {String} teamName - Nome del team al quale associare il punteggio;
  * @param {int} localId - Identificatore del locale al quale associare il punteggio;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistance
  */ 
  void addScore(int gemeId, int score, String teamName, int localId);
  
  /**
  * @name getLeaderboard
  * @desc Ritorna la classifica associata al gioco indicato;
  * @param {int} gameId - Identificatore del gioco del quale si vuole avere la leaderboard;
  * @param {int} localId - Identificatore del locale del quale si vuole avere la leaderbord;
  * @returns {List}
  * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistance
  */ 
  List<Score> getLeaderboard(int gameId, int localId);

  /**
   * @name deleteScore
   * @desc Elimina un risultato dalla classifica;
   * @param {int} scoreId - Identificativo del risultato da eliminare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void deleteScore(int scoreId);
  
  //TEAM

  /**
   * @name addMember
   * @desc Aggiunge un profilo alla squadra;
   * @param {int} teamId - Identificatore del team;
   * @param {int} memberProfileId - Identificatore del profilo da aggiungere alla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean addMember(int teamId, int memberProfileId);

  /**
   * @name changeTeamName
   * @desc Rinomina la squadra;
   * @param {int} teamId - Identificatore della squadra;
   * @param {String} teamName - Il nuovo nome da associare alla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean changeTeamName(int teamId, String teamName);

  /**
   * @name createTeam
   * @desc Crea la squadra con il nome e il caposquadra passati come paramentri;
   * @param {String} teamName - Rappresenta il nome della squadra;
   * @param {int} leaderProfileId - Rappresenta il profilo dell'utente creatore del team (il caposquadra);
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Team createTeam(String teamName, int leaderProfileId);

  /**
   * @name deleteTeam
   * @desc Elimina la squadra indicata;
   * @param {int} teamId - Identificatore del team da eliminare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  void deleteTeam(int teamId);

  /**
   * @name getTeam
   * @desc Restituisce il team indicato;
   * @param {int} teamId - l'identificatore del team da ritornare;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Team getTeam(int teamId);

  /**
   * @name isTeamExist
   * @desc Ritorna true se già esiste una squadra associata al tavolo indicato;
   * @param {int} tableId - Identificativo del tavolo sul quale verificare se c'è un team associato;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean isTeamExist(int tableId);

  /**
   * @name removeMember
   * @desc Rimuove un profilo dalla squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
   * @param {int} teamId - Identificatore del team;
   * @param {int} memberProfileId - Identificatore del profilo da rimuovere dalla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean removeMember(int teamId, int memberProfileId);

  /**
   * @name setTeamLeader
   * @desc Imposta come leader il profilo passato come parametro. Il profilo deve già fare parte della squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
   * @param {int} teamId - Rappresenta l'identificativo del team su cui cambiare il caposquadra;
   * @param {int} profileId - Rappresenta l'identificativo del profilo del nuovo caposquadra del team. Il profilo deve appartenere al team;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  boolean setTeamLeader(int teamId, int profileId);

  /**
   * @name getTable
   * @desc Ritorna il tavolo associato al beacon;
   * @param {String} uuid - Uuid del beacon;
   * @param {int} major - Major del beacon;
   * @param {int} minor - Minor del beacon;
   * @returns {Table}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Table getTable(String uuid, int major, int minor);

  /**
   * @name getTeamNameList
   * @desc Ritorna la lista di nomi di team del locale indicato;
   * @param {int} localId - L'identificatore del locale;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  List<String> getTeamNameList(int localId);

  /**
   * @name getTeamIdFromTableId
   * @desc Ritorna l'identificatore del team ricavato dall'identificatore del tavolo. Ritorna null se il reperimento fallisce;
   * @param {int} tableId - Identificatore del tavolo;
   * @returns {Integer}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Integer getTeamIdFromTableId(int tableId);

  /**
   * @name getTeamIdFromProfileId
   * @desc Ritorna l'identificatore del team ricavato dall'identificatore di un utente. Ritorna null se il reperimento fallisce;
   * @param {int} profileId - Identificatore del profilo utente;
   * @returns {Integer}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  Integer getTeamIdFromProfileId(int profileId);
  
}
