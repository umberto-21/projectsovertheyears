package microservices.battleship.persistence;
import com.google.gson.Gson;
import microservices.battleship.persistence.dao.DaoFactory;
import microservices.battleship.persistence.dao.BattleshipDao;
import microservices.battleship.persistence.dao.ScoreDao;
import microservices.battleship.persistence.dao.TeamDao;
import microservices.battleship.types.Score;
import microservices.battleship.types.Team;
import microservices.battleship.types.Ship;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Shot;
import microservices.battleship.types.Field;
import microservices.battleship.types.Table;
import microservices.battleship.types.Local;

import java.util.List;

/**
 *file BattleshipPersistenceImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che gestisce i dati elementari relativi al gioco Battleship, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato;
 *use Implementa i metodi offerti dall'interfaccia BattleshipPersistence, utilizzati per interfacciarsi e comunicare direttamente con il database utilizzato;
 */
public class BattleshipPersistenceImpl implements BattleshipPersistence{
  private static final String TAG = "BattleshipPersImpl";
  private DaoFactory _daoFactory;

  /**
  * @name TeamPersistenceImpl
  * @desc Costruttore completo;
  * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistenceImpl
  */ 
  public BattleshipPersistenceImpl(){
    String methodSignature = ".BattleshipPersistenceImpl()";
 //play.Logger.info(TAG + methodSignature);
    _daoFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
 //play.Logger.info(TAG + methodSignature + " return ");
  }
  
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
  @Override
  public void addUserShips(int x, int y, boolean vertical, int profileId){
    String methodSignature = ".addUserShips("+x+","+y+","+vertical+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.addUserShips(x, y, vertical, profileId);
 //play.Logger.info(TAG + methodSignature + " return void");
  }

  /**
   * @name checkUserFinishShipPositioning
   * @desc Controlla che tutte le navi dell'utente siano state posizionate.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean checkUserFinishShipPositioning(int profileId){
    String methodSignature = ".checkUserFinishShipPositioning("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean check=battleshipDao.checkUserFinishShipPositioning(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ check+";");
    return check;
  }

  /**
   * @name checkUserFinishShoots
   * @desc Ritorna true se l'utente ha finito i colpi dal sparare.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean checkUserFinishShoots(int profileId){
    String methodSignature = ".checkUserFinishShoots("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean check=battleshipDao.checkUserFinishShoots(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ check+";");
    return check;
  }

  /**
   * @name deleteBattle
   * @desc Metodo che elimina la battaglia di un team;
   * @param {int} teamId - Identificativo della squadra che si vuole togliere dalla battaglia;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void deleteBattle(int teamId){
    String methodSignature = ".deleteBattle("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.deleteBattle(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getOpponentTeam
   * @desc Restituisce l'id della squadra avversaria a quella associata al id passato come parametro;
   * @param {int} teamId - Identificativo della squadra di cui si vuole conoscere l'avversario;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Team getOpponentTeam(int teamId){
    String methodSignature = ".getOpponentTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    Team team=battleshipDao.getOpponentTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(team) +";");
    return team;
  }

  /**
   * @name getReadyTeams
   * @desc Metodo che ritorna una lista di team pronti a giocare;
   * @param {int} localId - Identificativo del locale di cui si vogliono conoscere le squadre pronte a giocare;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public List<Team> getReadyTeams(int localId){
    String methodSignature = ".getReadyTeams("+localId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    List<Team> teamList=battleshipDao.getReadyTeams(localId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(teamList) +";");
    return teamList;
  }

  /**
   * @name getShotList
   * @desc Ritorna la lista di colpi sparati durante l'ultimo turno dall'utente associato all'id passato come parametro.
   * @param {int} profileId - Id associato al profilo di cui si vuole avere la lista di colpi sparati durante l'ultimo turno;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public List<Shot> getShotList(int profileId){
    String methodSignature = ".getShotList("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    List<Shot> shotList=battleshipDao.getShotList(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(shotList) +";");
    return shotList;
  }

  /**
   * @name getShotNumber
   * @desc Ritorna il numero dei colpi che ha a disposizione un giocatore;
   * @param {int} profileId - Identificativo dell'utente di cui si vogliono sapere il numero di colpi;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public int getShotNumber(int profileId){
    String methodSignature = ".getShotNumber("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    int shotNumber=battleshipDao.getShotNumber(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(shotNumber) +";");
    return  shotNumber;
  }

  /**
   * @name getTeamFormProfileId
   * @desc Ritorna il tipo team associato a un profilo dato;
   * @param {int} profileId - Identificativo del profilo di cui si vuole ottenere il team;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Team getTeamFormProfileId(int profileId){
    String methodSignature = ".getTeamFormProfileId("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    Team team=battleshipDao.getTeamFormProfileId(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(team) +";");
    return team;
  }

  /**
   * @name getTeamField
   * @desc Restituisce il campo associato alla squadra con id uguale a quello passato per parametro.
   * @param {int} teamId - Identificativo del team di cui si vuole ottenere il campo di battaglia;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Field getTeamField(int teamId){
    String methodSignature = ".getTeamField("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    Field field=battleshipDao.getTeamField(teamId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(field) +";");
    return field;
  }

  /**
   * @name getUserShips
   * @desc Ritorna le navi assegnate all'utente.
   * @param {int} idProfilo - Rappresenta l'identificativo del profilo dell'utente.
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public List<Ship> getUserShips(int profileId){
    String methodSignature = ".getUserShips("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    List<Ship> shipList=battleshipDao.getUserShips(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(shipList) +";");
    return shipList;
  }

  /**
   * @name insertSearchingOpponent
   * @desc Inserisce nel database la squadra che ha fatto richiesta di giocare.
   * @param {int} idTeam - Rappresenta il team da inserire nel database.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void insertSearchingOpponent(int teamId){
    String methodSignature = ".insertSearchingOpponent("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.insertSearchingOpponent(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name insertShoot
   * @desc Memorizza il colpo sparato dall'utente.
   * @param {int} x - Rappresenta l'ascissa del colpo sparato.
   * @param {int} y - Rappresenta l'ordinata del colpo sparato.
   * @param {int} idProfilo - Rappresenta l'identificatore del profilo che ha sparato il colpo.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void insertShot(int x, int y, int profileId){
    String methodSignature = ".insertShot("+x+","+y+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.insertShot(x,y,profileId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name isBattleRunning
   * @desc Ritorna true se una battaglia e' in corso con un determinato team;
   * @param {int} teamId - Team di cui si vuole sapere se e' ingaggiato in una battaglia;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean isBattleRunning(int teamId){
    String methodSignature = ".isBattleRunning("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean isBattleRunning=battleshipDao.isBattleRunning(teamId);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(isBattleRunning) +";");
    return isBattleRunning;
  }

  /**
   * @name isTeamExist
   * @desc Ritorna true se esiste gia' un team associato ad un beacon;
   * @param {String} uuid - Uuid associato al beacon di un tavolo;
   * @param {int} major - Major associato al beacon di un tavolo;
   * @param {int} minor - Minor associato al beacon di un tavolo;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean isTeamExists(String uuid, int major, int minor){
    String methodSignature = ".isTeamExists("+uuid+","+major+","+minor+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean isTeamExist=battleshipDao.isTeamExists(uuid, major, minor);
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(isTeamExist) +";");
    return isTeamExist;
  }

  /**
   * @name removeReadyTeam
   * @desc Rimuove un team che e' pronto a giocare;
   * @param {int} teamId - Identificativo di un team pronto a giocare che si vuole rimuovere;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void removeReadyTeam(int teamId){
    String methodSignature = ".removeReadyTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.removeReadyTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name removeTeamMember
   * @desc Rimuove il membro passato con il parametro idProfile dal team specificato dal parametro idTeam.
   * @param {int} idProfile - Rappresenta l'identificativo del profilo da rimuovere.
   * @param {int} idTeam - Rappresenta l'identificativo del team del l'utente.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void removeTeamMember(int teamId, int profileId){
    String methodSignature = ".removeTeamMember("+teamId+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.removeTeamMember(teamId, profileId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setOpponent
   * @desc Imposta le due squadre sfidanti della partita.
   * @param {int} idTeamOne - Rappresenta l'identificativo della prima squadra partecipante alla partita.
   * @param {int} idTeamTwo - Rappresenta l'identificativo della seconda squadra partecipante alla partita.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void setOpponent(int teamOneId, int teamTwoId){
    String methodSignature = ".setOpponent("+teamOneId+","+teamTwoId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.setOpponent(teamOneId, teamTwoId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setShipToPositioning
   * @desc Assegna una nave da posizionare ad un utente;
   * @param {int} length - Lunghezza della nave da posizionare;
   * @param {int} profileId - Identificativo dell'utente a cui si vuole assegnare una nave da posizionare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void setShipToPositioning(int length, int profileId){
    String methodSignature = ".setShipToPositioning("+length+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.setShipToPositioning(length, profileId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name updateField
   * @desc Aggiorna lo stato del capo indicato da fieldId; %Probabilmente da cambiare con un addShoot(fieldId, x, y) {dipende come implementiamo il campo}
   * @param {int} teamId - Rappresenta l'identificativo del team il cui campo e' da aggiornare;
   * @param {String[][]} grid - Griglia con gli esiti dei colpi con cui aggiornare il campo;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void updateField(String [][] grid, int teamId){
    String methodSignature = ".updateField("+(new Gson()).toJson(grid)+","+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.updateField(grid,teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setAttackTeam
   * @desc Imposta un team come attaccante;
   * @param {int} teamId - Identificativo del team da impostare come attaccante;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void setAttackTeam(int teamId){
    String methodSignature = ".setAttackTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.setAttackTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name isAttackPhase
   * @desc Ritorna true se un team e' in fase di attacco;
   * @param {int} teamId - Identificativo del team di cui si vuole sapere se e' in fase d'attacco;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean isAttackPhase(int teamId){
    String methodSignature = ".isAttackPhase("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean isAttackPhase=battleshipDao.isAttackPhase(teamId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(isAttackPhase)+";");
    return isAttackPhase;
  }

  /**
   * @name removeAttackTeam
   * @desc Rimuove dalla fase d'attacco un determinato team;
   * @param {int} teamId - Identificativo del team che si vuole rimuovere dalla fase d'attacco;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void removeAttackTeam(int teamId){
    String methodSignature = ".removeAttackTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.removeAttackTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getLocal
   * @desc Ritorna il locale il cui e' un determinato utente;
   * @param {int} profileId - Identificativo dell'utente di cui vogliamo sapere in che locale e'
   * @returns {Local}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Local getLocal(int profileId){
    String methodSignature = ".getLocal("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    Local local=battleshipDao.getLocal(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(local)+";");
    return local;
  }

  /**
   * @name getShipsToPositioning
   * @desc Ritorna una lista di navi da far posizionare ad un utente;
   * @param {int} profileId - Identificativo dell'utente di cui si vogliono sapere quali navi deve posizionare;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public List<ShipType> getShipsToPositioning(int profileId){
    String methodSignature = ".getShipsToPositioning("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    List<ShipType> shipTypeList=battleshipDao.getShipsToPositioning(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(shipTypeList)+";");
    return shipTypeList;
  }

  /**
   * @name setMaxAvailableShots
   * @desc Setta il numero massimo di colpi che puo' sparare un utente;
   * @param {int} numberOfShots - Numero massimo di colpi da assegnare a un utente;
   * @param {int} profileId - Utente a cui assegnare un numero massimo di colpi;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean setMaxAvailableShots(int numberOfShots, int profileId){
    String methodSignature = ".setMaxAvailableShots("+numberOfShots+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    boolean result=battleshipDao.setMaxAvailableShots(numberOfShots, profileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name addReadyTeam
   * @desc Aggiunge un team alla lista di quelli pronti a giocare;
   * @param {int} teamId - Team da aggiungere alla lista di quelli pronti a giocare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void addReadyTeam(int teamId){
    String methodSignature = ".addReadyTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.addReadyTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name clearTeamShots
   * @desc Rimuove tutti i colpi dei membri del team associati ad una squadra;
   * @param {int} teamId - Identificativo del team a cui si vogliono togliere i colpi dei rispettivi membri;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void clearTeamShots(int teamId){
    String methodSignature = ".clearTeamShots("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    BattleshipDao battleshipDao = _daoFactory.getBattleshipDao();
    battleshipDao.clearTeamShots(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }
  
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
  @Override
  public void addScore(int gemeId, int score, String teamName, int localId){
    String methodSignature = ".addScore("+gemeId+","+score+","+teamName+","+localId+")";
 //play.Logger.info(TAG + methodSignature);
    ScoreDao scoreDao = _daoFactory.getScoreDao();
    scoreDao.addScore(gemeId, score, teamName, localId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getLeaderboard
   * @desc Ritorna la classifica associata al gioco indicato;
   * @param {int} gameId - Identificatore del gioco del quale si vuole avere la leaderboard;
   * @param {int} localId - Identificatore del locale del quale si vuole avere la leaderbord;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistance
   */
  @Override
  public List<Score> getLeaderboard(int gameId, int localId){
    String methodSignature = ".getLeaderboard("+gameId+","+localId+")";
 //play.Logger.info(TAG + methodSignature);
    ScoreDao scoreDao = _daoFactory.getScoreDao();
    List<Score> scoreList=scoreDao.getLeaderboard(gameId, localId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(scoreList)+";");
    return scoreList;
  }

  /**
   * @name deleteScore
   * @desc Elimina un risultato dalla classifica;
   * @param {int} scoreId - Identificativo del risultato da eliminare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void deleteScore(int scoreId){
    String methodSignature = ".deleteScore("+scoreId+")";
 //play.Logger.info(TAG + methodSignature);
    ScoreDao scoreDao = _daoFactory.getScoreDao();
    scoreDao.deleteScore(scoreId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }
  
  //TEAM

  /**
   * @name addMember
   * @desc Aggiunge un profilo alla squadra;
   * @param {int} teamId - Identificatore del team;
   * @param {int} memberProfileId - Identificatore del profilo da aggiungere alla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean addMember(int teamId, int memberProfileId){
    String methodSignature = ".addMember("+teamId+","+memberProfileId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    boolean result=teamDao.addMember(teamId, memberProfileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name changeTeamName
   * @desc Rinomina la squadra;
   * @param {int} teamId - Identificatore della squadra;
   * @param {String} teamName - Il nuovo nome da associare alla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean changeTeamName(int teamId, String teamName){
    String methodSignature = ".changeTeamName("+teamId+","+teamName+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    boolean result=teamDao.changeTeamName(teamId, teamName);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name createTeam
   * @desc Crea la squadra con il nome e il caposquadra passati come paramentri;
   * @param {String} teamName - Rappresenta il nome della squadra;
   * @param {int} leaderProfileId - Rappresenta il profilo dell'utente creatore del team (il caposquadra);
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Team createTeam(String teamName, int leaderProfileId){
    String methodSignature = ".createTeam("+teamName+","+leaderProfileId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    Team team=teamDao.createTeam(teamName, leaderProfileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(team)+";");
    return team;
  }

  /**
   * @name deleteTeam
   * @desc Elimina la squadra indicata;
   * @param {int} teamId - Identificatore del team da eliminare;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public void deleteTeam(int teamId){
    String methodSignature = ".deleteTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    teamDao.deleteTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getTeam
   * @desc Restituisce il team indicato;
   * @param {int} teamId - l'identificatore del team da ritornare;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Team getTeam(int teamId){
    String methodSignature = ".getTeam("+teamId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    Team team=teamDao.getTeam(teamId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(team)+";");
    return team;
  }

  /**
   * @name isTeamExist
   * @desc Ritorna true se già esiste una squadra associata al tavolo indicato;
   * @param {int} tableId - Identificativo del tavolo sul quale verificare se c'è un team associato;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean isTeamExist(int tableId){
    String methodSignature = ".isTeamExist("+tableId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    boolean result=teamDao.isTeamExist(tableId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name removeMember
   * @desc Rimuove un profilo dalla squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
   * @param {int} teamId - Identificatore del team;
   * @param {int} memberProfileId - Identificatore del profilo da rimuovere dalla squadra;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean removeMember(int teamId, int memberProfileId){
    String methodSignature = ".removeMember("+teamId+","+memberProfileId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    boolean result=teamDao.removeMember(teamId, memberProfileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name setTeamLeader
   * @desc Imposta come leader il profilo passato come parametro. Il profilo deve già fare parte della squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
   * @param {int} teamId - Rappresenta l'identificativo del team su cui cambiare il caposquadra;
   * @param {int} profileId - Rappresenta l'identificativo del profilo del nuovo caposquadra del team. Il profilo deve appartenere al team;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public boolean setTeamLeader(int teamId, int profileId){
    String methodSignature = ".setTeamLeader("+teamId+","+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    boolean result=teamDao.setTeamLeader(teamId, profileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name getTable
   * @desc Ritorna il tavolo associato al beacon;
   * @param {String} uuid - Uuid del beacon;
   * @param {int} major - Major del beacon;
   * @param {int} minor - Minor del beacon;
   * @returns {Table}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Table getTable(String uuid, int major, int minor){
    String methodSignature = ".getTable("+uuid+","+major+","+minor+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    Table table=teamDao.getTable(uuid, major, minor);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(table)+";");
    return table;
  }

  /**
   * @name getTeamNameList
   * @desc Ritorna la lista di nomi di team del locale indicato;
   * @param {int} localId - L'identificatore del locale;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public List<String> getTeamNameList(int localId){
    String methodSignature = ".getTeamNameList("+localId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    List<String> stringList=teamDao.getTeamNameList(localId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(stringList)+";");
    return stringList;
  }

  /**
   * @name getTeamIdFromTableId
   * @desc Ritorna l'identificatore del team ricavato dall'identificatore del tavolo. Ritorna null se il reperimento fallisce;
   * @param {int} tableId - Identificatore del tavolo;
   * @returns {Integer}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Integer getTeamIdFromTableId(int tableId){
    String methodSignature = ".getTeamIdFromTableId("+tableId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    Integer id=teamDao.getTeamIdFromTableId(tableId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(id)+";");
    return id;
  }

  /**
   * @name getTeamIdFromProfileId
   * @desc Ritorna l'identificatore del team ricavato dall'identificatore di un utente. Ritorna null se il reperimento fallisce;
   * @param {int} profileId - Identificatore del profilo utente;
   * @returns {Integer}
   * @memberOf Server.Microservices.Battleship.Persistence.BattleshipPersistence
   */
  @Override
  public Integer getTeamIdFromProfileId(int profileId){
    String methodSignature = ".getTeamIdFromProfileId("+profileId+")";
 //play.Logger.info(TAG + methodSignature);
    TeamDao teamDao = _daoFactory.getTeamDao();
    Integer id = teamDao.getTeamIdFromProfileId(profileId);
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(id)+";");
    return id;
  }
}
