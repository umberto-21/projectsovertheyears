package microservices.battleship.services;
import com.google.gson.Gson;
import microservices.battleship.persistence.BattleshipPersistence;
import microservices.battleship.persistence.BattleshipPersistenceImpl;
import microservices.battleship.types.Profile;
import microservices.battleship.types.Team;
import microservices.battleship.types.Ship;
import microservices.battleship.types.Local;
import microservices.battleship.types.Shot;
import microservices.battleship.types.Field;
import microservices.battleship.types.Position;
import microservices.battleship.types.Grid;
import microservices.battleship.types.Cell;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Beacon;
import microservices.battleship.types.Game;
import microservices.battleship.types.Score;
import microservices.battleship.types.Table;
import java.util.List;
import java.util.ArrayList;

/**
 *file BattleshipServicesImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che gestisce i dati relativi al gioco Battleship;
 *use Implementa i metodi offerti dall'interfaccia BattleshipServices, utilizzati per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business;
 */
public enum BattleshipServicesImpl implements BattleshipServices{

  INSTANCE;
  private static final String TAG = "BattleshipServicesImpl";
  private BattleshipPersistence _battleshipPersistenceImpl;

  /**
   * @name BattleshipServicesImpl
   * @desc Costruttore della classe che gestisce i dati relativi al gioco battleship;
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServicesImpl
   */
  private BattleshipServicesImpl(){
    String methodSignature = ".BattleshipPersistenceImpl()";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl = new BattleshipPersistenceImpl();
 //play.Logger.info(TAG + methodSignature + " return ");
  }

  /**
   * @name getInstance
   * @desc Metodo che ritorna l'istanza dell'oggetto BattleshipServicesImpl;
   * @returns {BattleshipServicesImpl}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServicesImpl
   */
  public static BattleshipServicesImpl getInstance(){
    String methodSignature = ".getInstance()";
 //play.Logger.info(TAG + methodSignature);
 //play.Logger.info(TAG + methodSignature + " return INSTANCE ;");
    return INSTANCE;
  }


  /**
   * @name addUserShips
   * @desc Inserisce una nuova nave associata all'utente. %Richiama addUserShips di Persistence::BattleshipPersistence
   * @param {Ship} ship - Rappresenta la nave da aggiungere.
   * @param {Profile} profile - Rappresenta il profilo dell'utente che ha aggiunto la nave.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void addUserShips(Ship ship, Profile profile){
    String methodSignature = ".addUserShips("+(new Gson()).toJson(ship)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    Position position = ship.getPosition();
    _battleshipPersistenceImpl.addUserShips(position.getX(), position.getY(), ship.isVertical(), profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name checkFinishShipPositioning
   * @desc Ritorna true se l'utente ha finito di posizionare le navi. %Richiama checkUserFinishShipPositioning(Profile)
   * @param {Team} team - Rappresenta il team su cui verificare la fine del posizionamento.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override 
  public boolean checkFinishShipPositioning(Team team){
    String methodSignature = ".checkFinishShipPositioning("+(new Gson()).toJson(team)+")";
  //play.Logger.info(TAG + methodSignature);
    if(team == null) {
    //play.Logger.info(TAG + methodSignature + "return: false  (team null)");
      return false;
    }
    boolean response = checkUserFinishShipPositioning(team.getCaptain());
    if(response){
      List<Profile> teammates = team.getTeammates();
      for(int i=0; i<teammates.size() && response; i++){
        response = checkUserFinishShipPositioning(teammates.get(i));
      }
    }
  //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(response) +";");
    return response;
  }

  /**
   * @name checkTeamFinishShots
   * @desc Ritorna true sse tutti i membri del team specificato hanno finito i loro colpi a disposizione; %Richiama checkUserFinishShoots(profile) su tutti i membri del team
   * @param {Team} team - Rappresenta il team su cui verificare la presenza di ulteriori colpi da sparare;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override 
  public boolean checkTeamFinishShots(Team team){
    String methodSignature = ".checkTeamFinishShots("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean response = checkUserFinishShoots(team.getCaptain());
    if(response){
      List<Profile> teammates = team.getTeammates();
      for(int i=0; i<teammates.size() && response; i++){
        response = checkUserFinishShoots(teammates.get(i));
      }
    }
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(response) +";");
    return response;
  }

  /**
   * @name checkUserFinishShipPositioning
   * @desc Controlla che tutte le navi dell'utente siano state posizionate. %Richiama checkUserFinishShipPositioning di Battleship::Persistence::BattleshipPersistence
   * @param {Profile} profile - Rappresenta il profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public boolean checkUserFinishShipPositioning(Profile profile){
    String methodSignature = ".checkUserFinishShipPositioning("+(new Gson()).toJson(profile)+")";
  //play.Logger.info(TAG + methodSignature);
    boolean response=_battleshipPersistenceImpl.checkUserFinishShipPositioning(profile.getId());
  //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(response) +";");
    return response;
  }

  /**
   * @name checkUserFinishShoots
   * @desc Ritorna true se l'utente ha finito i colpi dal sparare. %Richiama checkUserFinishShoots di Battleship::Persistence::BattleshipPersistence
   * @param {Profile} profile - Rappresenta il profilo dell'utente.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public boolean checkUserFinishShoots(Profile profile){
    String methodSignature = ".checkUserFinishShoots("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean response=_battleshipPersistenceImpl.checkUserFinishShoots(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(response) +";");
    return response;
  }

  /**
   * @name deleteBattle
   * @desc cancella i dati del database riguardanti la battaglia
   * @param {Team} team - il team del quale eliminare il match;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void deleteBattle(Team team){
    String methodSignature = ".deleteBattle("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.deleteBattle(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getOpponentTeam
   * @desc Ritorna la squadra avversaria al team indicato %[BattleshipPersistence::getOpponentTeam(teamId)]
   * @param {Team} team - Rappresenta la squadra di cui si vuole conoscere l'avversario;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public Team getOpponentTeam(Team team){
    String methodSignature = ".getOpponentTeam("+(new Gson()).toJson(team)+")";
  //play.Logger.info(TAG + methodSignature);
    Team teamToReturn=_battleshipPersistenceImpl.getOpponentTeam(team.getId());
  //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(teamToReturn) +";");
    return teamToReturn;
  }

  /**
   * @name getReadyTeams
   * @desc Metodo che ritorna una lista con le squadre di un locale pronte al gioco;
   * @param {Local} local - Locale del quale ritornare una lista delle squadre pronte allo scontro. L'identificatore del locale deve essere inizializzato;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public List<Team> getReadyTeams(Local local){
    String methodSignature = ".getReadyTeams("+(new Gson()).toJson(local)+")";
 //play.Logger.info(TAG + methodSignature);
    List<Team> teamList=_battleshipPersistenceImpl.getReadyTeams(local.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(teamList) +";");
    return teamList;
  }

  /**
   * @name getShips
   * @desc Ritorna la lista di navi già posizionate dai membri del team. %Richiama getShips di Battleship::Persistence::BattleshipPersistence
   * @param {Team} team - Rappresenta il team dell'utente.
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public List<Ship> getShips(Team team){
    String methodSignature = ".getShips("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    List ships = getUserShips(team.getCaptain());
    List<Profile> teammates = team.getTeammates();
    for(Profile teammate : teammates){
      ships.addAll(getUserShips(teammate));
    }
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(ships) +";");
    return ships;
  }

  /**
   * @name getShotList
   * @desc Ritorna la lista di colpi sparati nell'ultimo turno dall'utente associato al profilo %[BattleshipPersistence::getShotList(profileId)]
   * @param {Profile} profile - Profilo associato all'utente di cui si vuole sapere la lista di colpi sparati durante l'ultimo turno;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public List<Shot> getShotList(Profile profile){
    String methodSignature = ".getShotList("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    List<Shot> shotList=_battleshipPersistenceImpl.getShotList(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(shotList) +";");
    return  shotList;
  }

  /**
   * @name getShotNumber
   * @desc Ritorna il numero di colpi restanti da sparare;
   * @param {Profile} profile - Profilo dell'utente che deve sparare i colpi;
   * @returns {int}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public int getShotNumber(Profile profile){
    String methodSignature = ".getShotNumber("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    int result=_battleshipPersistenceImpl.getShotNumber(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(result) +";");
    return result;
  }

  /**
   * @name getTeam
   * @desc ritorna il team associato al profilo; %chiama il metodo getTeam sull'oggetto BattleshipPersistence
   * @param {Profile} profile - profilo da cui si deve ricavare il Team
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public Team getTeam(Profile profile){
    String methodSignature = ".getTeam("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    Team team = null;
    Integer teamId = _battleshipPersistenceImpl.getTeamIdFromProfileId(profile.getId());
    if(teamId != null){
       team = _battleshipPersistenceImpl.getTeam(teamId);
    }
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(team) +";");
    return team;
  }

  /**
   * @name getTeamField
   * @desc Ritorna il campo del team indicato; %[BattleshipPersistence::getTeamField(teamId)]
   * @param {Team} team - Indica il team di cui si vuole ottenere il campo;
   * @returns {Field}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public Field getTeamField(Team team){
    String methodSignature = ".getTeamField("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    Field field=_battleshipPersistenceImpl.getTeamField(team.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(field) +";");
    return field;
  }

  /**
   * @name getUserShips
   * @desc Ritorna la lista della navi posizionate dall'utente specificato. %Richiama getUserShips di Battleship::Persistence::BattleshipPersistence
   * @param {Profile} profile - Rappresenta il profilo dell'utente.
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public List<Ship> getUserShips(Profile profile){
    String methodSignature = ".getUserShips("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    List<Ship> shipList=_battleshipPersistenceImpl.getUserShips(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(shipList) +";");
    return shipList;
  }

  /**
   * @name insertSearchingOpponent
   * @desc Inserisce il team nella lista di team pronti per fare una partita. %Richiama insertSearchingOpponent di Persistence::BattleshipPersistence
   * @param {Team} team - Rappresenta il team da aggiungere alla lista di team pronti al gioco.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void insertSearchingOpponent(Team team){
    String methodSignature = ".insertSearchingOpponent("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.insertSearchingOpponent(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name insertShoot
   * @desc Memorizza il colpo appena sparato. %Richiama insertShoot di Battleship::Persistence::BattleshipPersistence
   * @param {Shoot} shoot - Rappresenta il colpo sparato.
   * @param {Profile} profile - Rappresenta il profilo che ha sparato il colpo.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void insertShot(Shot shot, Profile profile){
    String methodSignature = ".insertShot("+(new Gson()).toJson(shot)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    Position position = shot.getPosition();
    _battleshipPersistenceImpl.insertShot(position.getX(), position.getY(), profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name isBattleRunning
   * @desc Ritorna true sse il team dell'utente è impegnato in una partita contro un altro team.
   * @param {Team} team - Il team del quale si vuole sapere se è nella fase di battaglia;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public boolean isBattleRunning(Team team){
    String methodSignature = ".isBattleRunning("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean result=_battleshipPersistenceImpl.isBattleRunning(team.getId());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(result) +";");
    return result;
  }

  /**
   * @name isTeamExists
   * @desc ritorna true sse esiste un team associato al beacon;
   * @param {Beacon} beacon - rappresenta il beacon associato al tavolo;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public boolean isTeamExists(Beacon beacon){
    String methodSignature = ".isTeamExists("+(new Gson()).toJson(beacon)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean result=_battleshipPersistenceImpl.isTeamExists(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
 //play.Logger.info(TAG + methodSignature + " return "+ (new Gson()).toJson(result) +";");
    return result;
  }
  /**
   * @name removeReadyTeam
   * @desc Rimuove il team selezionato dalla lista di team in attesta di iniziare una partita. %Richiama removeReadyTeam di Persistence::BattleshipPersistence
   * @param {Team} team - Rappresenta il team da rimuovere.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void removeReadyTeam(Team team){
    String methodSignature = ".removeReadyTeam("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.removeReadyTeam(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name removeTeamMember
   * @desc Rimuove il membro (passato come paramentro) dal team dell'utente associato alla sessione. %Richiama removeTeamMember di Business::BattleshipPersistence
   * @param {Team} team - Rappresenta il team del membro da rimuovere.
   * @param {Profile} profile - Rappresenta il profilo dell'utente da rimuovere.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void removeTeamMember(Team team, Profile profile){
    String methodSignature = ".removeTeamMember("+(new Gson()).toJson(team)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.removeTeamMember(team.getId(), profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setOpponent
   * @desc Imposta le due squadre sfidanti. %Richiama setOpponent di Persistence::BattleshipPersistence
   * @param {Team} teamOne - Rappresenta la prima squadra partecipante alla partita.
   * @param {Team} teamTwo - Rappresenta la seconda squadra partecipante alla partita.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void setOpponent(Team teamOne, Team teamTwo){
    String methodSignature = ".setOpponent("+(new Gson()).toJson(teamOne)+","+(new Gson()).toJson(teamTwo)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.setOpponent(teamOne.getId(), teamTwo.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setShipToPositioning
   * @desc Assegna ad ogni utente le navi che potrà posizionare. %Richiama setShipToPositioning di Persistence::BattleshipPersistence per ogni membro dei team
   * @param {ShipType} shipType - Rappresenta la nave assegnata all'utente.
   * @param {Profile} profile - Rappresenta il profilo dell'utente.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void setShipToPositioning(ShipType shipType, Profile profile){
    String methodSignature = ".setShipToPositioning("+(new Gson()).toJson(shipType)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.setShipToPositioning(shipType.getLength(), profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name updateField
   * @desc Aggiorna i dati memorizzati del campo del team specificato;
   * @param {Field} field - Rappresenta il campo aggiornato del team di cui si vuole aggiornare il capo;
   * @param {Team} team - Rappresenta il team di cui si vuole aggiornare il campo;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void updateField(Field field, Team team){
    String methodSignature = ".updateField("+(new Gson()).toJson(field)+","+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    Grid grid = field.getShootGrid();
    int heigth = grid.getHeight();
    int width = grid.getLength();
    String [][] stringGrid = new String [heigth][width];
    for(int y=0; y<heigth; y++){
      for(int x=0; x<width; x++){
        Cell cell = grid.getCell(x,y);
        String cellState;
        if(cell.isUnknown()){
          cellState = "UNKNOWN";
        }else
          if(cell.isMiss()){
            cellState = "MISS";
          }else 
            if(cell.isHit()){
              cellState = "HIT";
            }else
              if(cell.isSunk()){
                cellState = "SUNK";
              }else{
                cellState = "UNKNOWN";
              }
        stringGrid[y][x] = cellState;
      }
    }
    _battleshipPersistenceImpl.updateField(stringGrid, team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name setAttackTeam
   * @desc setta il team attacante del turno;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void setAttackTeam(Team team){
    String methodSignature = ".setAttackTeam("+(new Gson()).toJson(team)+")";
  //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.setAttackTeam(team.getId());
  //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name isAttackPhase
   * @desc ritorna true sse il team è in fase di attacco;
   * @param {Team} team - parametro di tipo Team;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public boolean isAttackPhase(Team team){
    String methodSignature = ".isAttackPhase("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean result=_battleshipPersistenceImpl.isAttackPhase(team.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name removeAttackTeam
   * @desc rimuove un team dalla fase di attacco;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void removeAttackTeam(Team team){
    String methodSignature = ".removeAttackTeam("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.removeAttackTeam(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getLocal
   * @desc Richiede al database il locale associato al profilo utente;
   * @param {Profile} profile - parametro di tipo Profile;
   * @returns {Local}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public Local getLocal(Profile profile){
    String methodSignature = ".getLocal("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    Local local=_battleshipPersistenceImpl.getLocal(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(local)+";");
    return local;
  }

  /**
   * @name getShipsToPositioning
   * @desc ritorna la lista di navi che un utente deve posizionare;
   * @param {Profile} profile - parametro di tipo Profile;
   * @returns {List}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public List<ShipType> getShipsToPositioning(Profile profile){
    String methodSignature = ".getShipsToPositioning("+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    List<ShipType> shipTypeList=_battleshipPersistenceImpl.getShipsToPositioning(profile.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(shipTypeList)+";");
    return shipTypeList;
  }

  /**
   * @name setMaxAvailableShots
   * @desc Imposta il numero di colpi totali che ha a disposizione un giocatore;
   * @param {int} numberOfShots - Il numero di colpi totali che può effettuare un giocatore in un turno;
   * @param {Profile} profile - Un oggetto di tipo Profile, l'identificatore del profilo deve essere inizializzato;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void setMaxAvailableShots(int numberOfShots, Profile profile){
    String methodSignature = ".setMaxAvailableShots("+(new Gson()).toJson(numberOfShots)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.setMaxAvailableShots(numberOfShots, profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name addReadyTeam
   * @desc aggiunge un Team alla lista dei Team pronti per iniziare una partita;
   * @param {Team} team - parametro di tipo Team;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void addReadyTeam(Team team){
    String methodSignature = ".addReadyTeam("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.addReadyTeam(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name clearTeamShots
   * @desc Rimuove tutti i colpi dei membri del team associati ad una squadra;
   * @param {Team} team - Team al quale si vogliono azzerare i colpi dei componenti;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void clearTeamShots(Team team){
    String methodSignature = ".clearTeamShots("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.clearTeamShots(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }
  
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
  @Override
  public void addScore(Game game, Score score, Local local){
    String methodSignature = ".addScore("+(new Gson()).toJson(game)+","+(new Gson()).toJson(score)+","+(new Gson()).toJson(local)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.addScore(game.getId(), score.getScore(), score.getTeamName(), local.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name getLeaderboard
   * @desc Ritorna la classifica associata al gioco indicato;
   * @param {Game} game - Il gioco del quale si vuole avere la leaderboard;
   * @param {Local} local - Il locale del quale si vuole avere la leaderbord;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Services.ScoreServices
   */
  @Override
  public List<Score> getLeaderboard(Game game, Local local){
    String methodSignature = ".getLeaderboard("+(new Gson()).toJson(game)+","+(new Gson()).toJson(local)+")";
 //play.Logger.info(TAG + methodSignature);
    List<Score> scoreList=_battleshipPersistenceImpl.getLeaderboard(game.getId(), local.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(scoreList)+";");
    return scoreList;
  }

  /**
   * @name deleteScore
   * @desc Elimina lo score associato al parametro;
   * @param {Score} score - Score da eliminare. L'identificatore del punteggio deve essere inizializzato;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void deleteScore(Score score){
    String methodSignature = ".deleteScore("+(new Gson()).toJson(score)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.deleteScore(score.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }
  
  //TEAM

  /**
   * @name createTeam
   * @desc Crea un team partendo dal prototipo passato;
   * @param {Team} team - Rappresenta il prototipo del team da memorizzare sul server. Ci deve essere un capitano nel team. Ogni membro del team, capitano incluso, deve avere inizializzato l'identificatore del profilo;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public Team createTeam(Team team){
    String methodSignature = ".createTeam("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    Team createdTeam = _battleshipPersistenceImpl.createTeam(team.getNameTeam(), team.getCaptain().getId());
    List<Profile> teammates = team.getTeammates();
    if(teammates != null){
      for (Profile profile : teammates){
        _battleshipPersistenceImpl.addMember(createdTeam.getId(), profile.getId());
      }
    }
    Team teamToReturn=_battleshipPersistenceImpl.getTeam(createdTeam.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(teamToReturn)+";");
    return teamToReturn;
  }

  /**
   * @name deleteTeam
   * @desc Metodo che dato un oggetto di tipo Team, con l'identificatore del team inizializzato, ne richiede l'eliminazione al layer persistence;
   * @param {Team} team - oggetto di tipo Team del quale chiedere l'eliminazione al layer persistence. L'identificatore del team deve essere inizializzato;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public void deleteTeam(Team team){
    String methodSignature = ".deleteTeam("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.deleteTeam(team.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  /**
   * @name isTeamExist
   * @desc Ritorna true se esiste un team associato al tavolo;
   * @param {Table} table - Tavolo sul quale si vuole verificare se esiste un team associato. l'Identificatore del tavolo deve essere inizializzato;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public boolean isTeamExist(Table table){
    String methodSignature = ".isTeamExist("+(new Gson()).toJson(table)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean result=_battleshipPersistenceImpl.isTeamExist(table.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
    return result;
  }

  /**
   * @name updateTeam
   * @desc Aggiorna il team con i parametri di newTeam, sse l'operazione fallisce ritorna false;
   * @param {Team} team - Rappresenta il team da modificare. L'identificatore del team deve essere inizializzato;
   * @param {Team} newTeam - Rappresenta le nuove informazioni da associate al team. Se il nome del team è impostato a null allora il nome del team non verrà modificato. Gli identificatori dei profili di tutti i membri del team, capitano incluso, devono essere inizializzati. Se il profilo del capitano è impostato a null allora il capitano della vecchia squadra non verra cambiato;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public boolean updateTeam(Team team, Team newTeam){
    String methodSignature = ".updateTeam("+(new Gson()).toJson(team)+","+(new Gson()).toJson(newTeam)+")";
 //play.Logger.info(TAG + methodSignature);
    boolean noErrors = true;
    int teamId = team.getId();
    Team oldTeam = _battleshipPersistenceImpl.getTeam(teamId);
    Profile newCaptain = newTeam.getCaptain();
    boolean setNewCaptain = false;
    Integer newCaptainId = newCaptain.getId();
    if(newCaptain != null){
      Integer oldCaptainId = oldTeam.getCaptain().getId();
      if(!oldCaptainId.equals(newCaptainId)){
        setNewCaptain = true;
        noErrors = noErrors & _battleshipPersistenceImpl.removeMember(teamId, oldCaptainId);
      }
    }
    String newTeamName = newTeam.getNameTeam();
    if(newTeamName != null){
      noErrors = noErrors & _battleshipPersistenceImpl.changeTeamName(teamId, newTeamName);
    }
    List<Profile> newTeammates = newTeam.getTeammates();
    ArrayList<Integer> newProfilesId = new ArrayList<Integer>();
    
    for(Profile profile : newTeammates){
      newProfilesId.add(profile.getId());
    }
    List<Profile> oldTeammates = oldTeam.getTeammates();
    ArrayList<Integer> oldProfilesId = new ArrayList<Integer>();
    for(Profile profile : oldTeammates){
      oldProfilesId.add(profile.getId());
    }
    for(int oldProfileId : oldProfilesId){
      if(!newProfilesId.contains(oldProfileId)){
        noErrors = noErrors & _battleshipPersistenceImpl.removeMember(teamId, oldProfileId);
      }
    }
    for(int newProfileId : newProfilesId){
      if(!oldProfilesId.contains(newProfileId)){
        noErrors = noErrors & _battleshipPersistenceImpl.addMember(teamId, newProfileId);
      }
    }
    if(setNewCaptain){
      noErrors = noErrors & _battleshipPersistenceImpl.setTeamLeader(teamId, newCaptainId);
      noErrors = noErrors & _battleshipPersistenceImpl.addMember(teamId, newCaptainId);
    }
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(noErrors)+";");
    return noErrors;
  }

  /**
   * @name getTable
   * @desc Ritorna il tavolo associato al beacon;
   * @param {Beacon} beacon - Beacon del quale si vuole sapere qual tavolo è associato;
   * @returns {Table}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public Table getTable(Beacon beacon){
    String methodSignature = ".getTable("+(new Gson()).toJson(beacon)+")";
 //play.Logger.info(TAG + methodSignature);
    Table table=_battleshipPersistenceImpl.getTable(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(table)+";");
    return _battleshipPersistenceImpl.getTable(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
  }

  /**
   * @name getTeamNameList
   * @desc Ritorna la lista di nomi di team del locale indicato;
   * @param {Local} local - Locale nel quale vogliamo controllare se il team esiste;
   * @returns {list}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public List<String> getTeamNameList(Local local){
    String methodSignature = ".getTeamNameList("+(new Gson()).toJson(local)+")";
 //play.Logger.info(TAG + methodSignature);
    List<String> stringList=_battleshipPersistenceImpl.getTeamNameList(local.getId());
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(stringList)+";");
    return stringList;
  }

  /**
   * @name joinTeam
   * @desc Ritorna true solo se il membro è stato aggiunto correttamente al team;
   * @param {Profile} profile - Profilo del membro da aggiungere al team;
   * @param {Table} table - Tavolo al quale aggiungere un membro al team;
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Services.TeamServices
   */
  @Override
  public boolean joinTeam(Profile profile, Table table){
    String methodSignature = ".joinTeam("+(new Gson()).toJson(profile)+","+(new Gson()).toJson(table)+")";
 //play.Logger.info(TAG + methodSignature);
    Integer teamId = _battleshipPersistenceImpl.getTeamIdFromTableId(table.getId());
    if(teamId == null){
   //play.Logger.info(TAG + methodSignature + " return false;");
      return false;
    }else{
      boolean result=_battleshipPersistenceImpl.addMember(teamId, profile.getId());
   //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(result)+";");
      return result;
    }
  }

  /**
   * @name getTeam
   * @desc ritorna il team associato al tavolo passato come parametro;
   * @param {Table} table - parametro di tipo Table;
   * @returns {Team}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public Team getTeam(Table table){
    String methodSignature = ".getTeam("+(new Gson()).toJson(table)+")";
 //play.Logger.info(TAG + methodSignature);
    Team team = null;
    Integer teamId = _battleshipPersistenceImpl.getTeamIdFromTableId(table.getId());
    if(teamId != null){
      team = _battleshipPersistenceImpl.getTeam(teamId);
    }
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(team)+";");
    return team;
  }

  /**
   * @name setCaptain
   * @desc Setta il profilo utente passato come parametro capitano del team;
   * @param {Team} team - Rappresenta il team a cui assegnare un capitano;
   * @param {Profile} profile - Rappresenta il profilo utente a cui assegnare il ruolo di capitano;
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Services.BattleshipServices
   */
  @Override
  public void setCaptain(Team team, Profile profile){
    String methodSignature = ".setCaptain("+(new Gson()).toJson(team)+","+(new Gson()).toJson(profile)+")";
 //play.Logger.info(TAG + methodSignature);
    _battleshipPersistenceImpl.setTeamLeader(team.getId(), profile.getId());
 //play.Logger.info(TAG + methodSignature + " return void;");
  }

  @Override
  public boolean isTeamExists(Team team){
    String methodSignature = ".isTeamExists("+(new Gson()).toJson(team)+")";
 //play.Logger.info(TAG + methodSignature);
    int teamId = team.getId();
    team = _battleshipPersistenceImpl.getTeam(teamId);
    boolean teamExists = team!=null;
 //play.Logger.info(TAG + methodSignature + " return "+(new Gson()).toJson(teamExists)+";");
    return teamExists;
  }
}
