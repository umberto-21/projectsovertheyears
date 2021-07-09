package microservices.battleship.services;
import microservices.battleship.types.Profile;
import microservices.battleship.types.Team;
import microservices.battleship.types.Ship;
import microservices.battleship.types.Local;
import microservices.battleship.types.Shot;
import microservices.battleship.types.Field;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Beacon;
import microservices.battleship.types.Score;
import microservices.battleship.types.Game;
import microservices.battleship.types.Table;
import java.util.List;

/**
 *file BattleshipServices.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Interfaccia che gestisce i dati relativi al gioco Battleship;
 *use Viene utilizzata per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business;
 */
public interface BattleshipServices{

  //BATTLESHIP

  /**
  * @name addUserShips
  * @desc Inserisce una nuova nave associata all'utente. %Richiama addUserShips di Persistence::BattleshipPersistence
  * @param {Ship} ship - Rappresenta la nave da aggiungere.
  * @param {Profile} profile - Rappresenta il profilo dell'utente che ha aggiunto la nave.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  void addUserShips(Ship ship, Profile profile);
  
  /**
  * @name checkFinishShipPositioning
  * @desc Ritorna true se l'utente ha finito di posizionare le navi. %Richiama checkUserFinishShipPositioning(Profile)
  * @param {Team} team - Rappresenta il team su cui verificare la fine del posizionamento.
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  boolean checkFinishShipPositioning(Team team);
  
  /**
  * @name checkTeamFinishShots
  * @desc Ritorna true sse tutti i membri del team specificato hanno finito i loro colpi a disposizione; %Richiama checkUserFinishShoots(profile) su tutti i membri del team
  * @param {Team} team - Rappresenta il team su cui verificare la presenza di ulteriori colpi da sparare;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  boolean checkTeamFinishShots(Team team);
  
  /**
  * @name checkUserFinishShipPositioning
  * @desc Controlla che tutte le navi dell'utente siano state posizionate. %Richiama checkUserFinishShipPositioning di Battleship::Persistence::BattleshipPersistence
  * @param {Profile} profile - Rappresenta il profilo dell'utente.
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  boolean checkUserFinishShipPositioning(Profile profile);
  
  /**
  * @name checkUserFinishShoots
  * @desc Ritorna true se l'utente ha finito i colpi dal sparare. %Richiama checkUserFinishShoots di Battleship::Persistence::BattleshipPersistence
  * @param {Profile} profile - Rappresenta il profilo dell'utente.
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  boolean checkUserFinishShoots(Profile profile);

  /**
  * @name deleteBattle
  * @desc cancella i dati del database riguardanti la battaglia
  * @param {Team} team - il team del quale eliminare il match;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  void deleteBattle(Team team);
  
  /**
  * @name getOpponentTeam
  * @desc Ritorna la squadra avversaria al team indicato %[BattleshipPersistence::getOpponentTeam(teamId)]
  * @param {Team} team - Rappresenta la squadra di cui si vuole conoscere l'avversario;
  * @returns {Team}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  Team getOpponentTeam(Team team);
  
  /**
  * @name getReadyTeams
  * @desc Metodo che ritorna una lista con le squadre di un locale pronte al gioco;
  * @param {Local} local - Locale del quale ritornare una lista delle squadre pronte allo scontro. L'identificatore del locale deve essere inizializzato;
  * @returns {List}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  List<Team> getReadyTeams(Local local);
  
  /**
  * @name getShips
  * @desc Ritorna la lista di navi già posizionate dai membri del team. %Richiama getShips di Battleship::Persistence::BattleshipPersistence
  * @param {Team} team - Rappresenta il team dell'utente.
  * @returns {List}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  List<Ship> getShips(Team team);
  
  /**
  * @name getShotList
  * @desc Ritorna la lista di colpi sparati nell'ultimo turno dall'utente associato al profilo %[BattleshipPersistence::getShotList(profileId)]
  * @param {Profile} profile - Profilo associato all'utente di cui si vuole sapere la lista di colpi sparati durante l'ultimo turno;
  * @returns {list}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  List<Shot> getShotList(Profile profile);
  
  /**
  * @name getShotNumber
  * @desc Ritorna il numero di colpi restanti da sparare;
  * @param {Profile} profile - Profilo dell'utente che deve sparare i colpi;
  * @returns {int}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  int getShotNumber(Profile profile); 
  
  /**
  * @name getTeam
  * @desc ritorna il team associato al profilo; %chiama il metodo getTeam sull'oggetto BattleshipPersistence
  * @param {Profile} profile - profilo da cui si deve ricavare il Team
  * @returns {Team}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  Team getTeam(Profile profile);

  /**
  * @name getTeamField
  * @desc Ritorna il campo del team indicato; %[BattleshipPersistence::getTeamField(teamId)]
  * @param {Team} team - Indica il team di cui si vuole ottenere il campo;
  * @returns {Field}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  Field getTeamField(Team team);

  /**
  * @name getUserShips
  * @desc Ritorna la lista della navi posizionate dall'utente specificato. %Richiama getUserShips di Battleship::Persistence::BattleshipPersistence
  * @param {Profile} profile - Rappresenta il profilo dell'utente.
  * @returns {List}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  List<Ship> getUserShips(Profile profile);
  
  /**
  * @name insertSearchingOpponent
  * @desc Inserisce il team nella lista di team pronti per fare una partita. %Richiama insertSearchingOpponent di Persistence::BattleshipPersistence
  * @param {Team} team - Rappresenta il team da aggiungere alla lista di team pronti al gioco.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  void insertSearchingOpponent(Team team);
  
  /**
  * @name insertShoot
  * @desc Memorizza il colpo appena sparato. %Richiama insertShoot di Battleship::Persistence::BattleshipPersistence
  * @param {Shoot} shoot - Rappresenta il colpo sparato.
  * @param {Profile} profile - Rappresenta il profilo che ha sparato il colpo.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  void insertShot(Shot shot, Profile profile);
  
  /**
  * @name isBattleRunning
  * @desc Ritorna true sse il team dell'utente è impegnato in una partita contro un altro team.
  * @param {Team} team - Il team del quale si vuole sapere se è nella fase di battaglia;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  boolean isBattleRunning(Team team);
  
  /**
  * @name isTeamExists
  * @desc ritorna true sse esiste un team associato al beacon;
  * @param {Beacon} beacon - rappresenta il beacon associato al tavolo;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  boolean isTeamExists(Beacon beacon);
  
  /**
  * @name removeReadyTeam
  * @desc Rimuove il team selezionato dalla lista di team in attesta di iniziare una partita. %Richiama removeReadyTeam di Persistence::BattleshipPersistence
  * @param {Team} team - Rappresenta il team da rimuovere.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  void removeReadyTeam(Team team);
  
  /**
  * @name removeTeamMember
  * @desc Rimuove il membro (passato come paramentro) dal team dell'utente associato alla sessione. %Richiama removeTeamMember di Business::BattleshipPersistence
  * @param {Team} team - Rappresenta il team del membro da rimuovere.
  * @param {Profile} profile - Rappresenta il profilo dell'utente da rimuovere.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  void removeTeamMember(Team team, Profile profile);

  /**
  * @name setOpponent
  * @desc Imposta le due squadre sfidanti. %Richiama setOpponent di Persistence::BattleshipPersistence
  * @param {Team} teamOne - Rappresenta la prima squadra partecipante alla partita.
  * @param {Team} teamTwo - Rappresenta la seconda squadra partecipante alla partita.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */
  void setOpponent(Team teamOne, Team teamTwo);
  
  /**
  * @name setShipToPositioning
  * @desc Assegna ad ogni utente le navi che potrà posizionare. %Richiama setShipToPositioning di Persistence::BattleshipPersistence per ogni membro dei team
  * @param {ShipType} shipType - Rappresenta la nave assegnata all'utente.
  * @param {Profile} profile - Rappresenta il profilo dell'utente.
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  void setShipToPositioning(ShipType shipType, Profile profile);
  
  /**
  * @name updateField
  * @desc Aggiorna i dati memorizzati del campo del team specificato;
  * @param {Field} field - Rappresenta il campo aggiornato del team di cui si vuole aggiornare il capo;
  * @param {Team} team - Rappresenta il team di cui si vuole aggiornare il campo;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
  */ 
  void updateField(Field field, Team team);

  /**
   * @name setAttackTeam
   * @desc setta il team attacante del turno;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void setAttackTeam(Team team);

  /**
   * @name isAttackPhase
   * @desc ritorna true sse il team è in fase di attacco;
   * @param {Team} team - parametro di tipo Team;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  boolean isAttackPhase(Team team);

  /**
   * @name removeAttackTeam
   * @desc rimuove un team dalla fase di attacco;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void removeAttackTeam(Team team);

  /**
   * @name getLocal
   * @desc Richiede al database il locale associato al profilo utente;
   * @param {Profile} profile - parametro di tipo Profile;
   * @returns {Local}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  Local getLocal(Profile profile);

  /**
   * @name getShipsToPositioning
   * @desc ritorna la lista di navi che un utente deve posizionare;
   * @param {Profile} profile - parametro di tipo Profile;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  List<ShipType> getShipsToPositioning(Profile profile);

  /**
   * @name setMaxAvailableShots
   * @desc Imposta il numero di colpi totali che ha a disposizione un giocatore;
   * @param {int} numberOfShots - Il numero di colpi totali che può effettuare un giocatore in un turno;
   * @param {Profile} profile - Un oggetto di tipo Profile, l'identificatore del profilo deve essere inizializzato;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void setMaxAvailableShots(int numberOfShots, Profile profile);

  /**
   * @name addReadyTeam
   * @desc aggiunge un Team alla lista dei Team pronti per iniziare una partita;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void addReadyTeam(Team team);

  /**
   * @name clearTeamShots
   * @desc Rimuove tutti i colpi dei membri del team associati ad una squadra;
   * @param {Team} team - Team al quale si vogliono azzerare i colpi dei componenti;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void clearTeamShots(Team team);
  
  //SCORE
  
  /**
  * @name addScore
  * @desc Memorizza lo score passato associandolo al gioco indicato;
  * @param {Game} game - Il gioco a cui associare il punteggio;
  * @param {Score} score - Punteggio da inserire nella classifica del gioco indicato;
  * @param {Local} local - Locale al quale associare il punteggio;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.ScoreServices
  */ 
  void addScore(Game game, Score score, Local local);
  
  /**
  * @name getLeaderboard
  * @desc Ritorna la classifica associata al gioco indicato;
  * @param {Game} game - Il gioco del quale si vuole avere la leaderboard;
  * @param {Local} local - Il locale del quale si vuole avere la leaderbord;
  * @returns {list}
  * @memberOf Server.Microservices.Battleship.Services.ScoreServices
  */
  List<Score> getLeaderboard(Game game, Local local);

  /**
   * @name deleteScore
   * @desc Elimina lo score associato al parametro;
   * @param {Score} score - Score da eliminare. L'identificatore del punteggio deve essere inizializzato;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void deleteScore(Score score);
  
  //TEAM
  
  /**
  * @name createTeam
  * @desc Crea un team partendo dal prototipo passato;
  * @param {Team} team - Rappresenta il prototipo del team da memorizzare sul server. Ci deve essere un capitano nel team. Ogni membro del team, capitano incluso, deve avere inizializzato l'identificatore del profilo;
  * @returns {Team}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */ 
  Team createTeam(Team team);
  
  /**
  * @name deleteTeam
  * @desc Metodo che dato un oggetto di tipo Team, con l'identificatore del team inizializzato, ne richiede l'eliminazione al layer persistence;
  * @param {Team} team - oggetto di tipo Team del quale chiedere l'eliminazione al layer persistence. L'identificatore del team deve essere inizializzato;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */ 
  void deleteTeam(Team team);
  
  /**
  * @name isTeamExist
  * @desc Ritorna true se esiste un team associato al tavolo;
  * @param {Table} table - Tavolo sul quale si vuole verificare se esiste un team associato. l'Identificatore del tavolo deve essere inizializzato;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */
  boolean isTeamExist(Table table);
  
/**
* @name updateTeam
* @desc Aggiorna il team con i parametri di newTeam, sse l'operazione fallisce ritorna false;
* @param {Team} team - Rappresenta il team da modificare. L'identificatore del team deve essere inizializzato;
* @param {Team} newTeam - Rappresenta le nuove informazioni da associate al team. Se il nome del team è impostato a null allora il nome del team non verrà modificato. Gli identificatori dei profili di tutti i membri del team, capitano incluso, devono essere inizializzati. Se il profilo del capitano è impostato a null allora il capitano della vecchia squadra non verra cambiato;
* @returns {boolean}
* @memberOf Server.Microservices.Battleship.Services.TeamServices
*/
  boolean updateTeam(Team team, Team newTeam);
  
  /**
  * @name getTable
  * @desc Ritorna il tavolo associato al beacon;
  * @param {Beacon} beacon - Beacon del quale si vuole sapere qual tavolo è associato;
  * @returns {Table}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */
  Table getTable(Beacon beacon);
  
  /**
  * @name getTeamNameList
  * @desc Ritorna la lista di nomi di team del locale indicato;
  * @param {Local} local - Locale nel quale vogliamo controllare se il team esiste;
  * @returns {list}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */
  List<String> getTeamNameList(Local local);
  
  /**
  * @name joinTeam
  * @desc Ritorna true solo se il membro è stato aggiunto correttamente al team;
  * @param {Profile} profile - Profilo del membro da aggiungere al team;
  * @param {Table} table - Tavolo al quale aggiungere un membro al team;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Services.TeamServices
  */ 
  boolean joinTeam(Profile profile, Table table);

  /**
   * @name getTeam
   * @desc ritorna il team associato al tavolo passato come parametro;
   * @param {Table} table - parametro di tipo Table;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  Team getTeam(Table table);

  /**
   * @name setCaptain
   * @desc Setta il profilo utente passato come parametro capitano del team;
   * @param {Team} team - Rappresenta il team a cui assegnare un capitano;
   * @param {Profile} profile - Rappresenta il profilo utente a cui assegnare il ruolo di capitano;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  void setCaptain(Team team, Profile profile);

  boolean isTeamExists(Team team);
}
