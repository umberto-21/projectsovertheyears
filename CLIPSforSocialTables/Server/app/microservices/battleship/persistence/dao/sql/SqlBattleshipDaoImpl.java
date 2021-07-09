package microservices.battleship.persistence.dao.sql;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import microservices.battleship.persistence.dao.BattleshipDao;
import microservices.battleship.types.Team;
import microservices.battleship.types.TeamImpl;
import microservices.battleship.types.Profile;
import microservices.battleship.types.ProfileImpl;
import microservices.battleship.types.Ship;
import microservices.battleship.types.ShipImpl;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.ShipTypeImpl;
import microservices.battleship.types.Position;
import microservices.battleship.types.PositionImpl;
import microservices.battleship.types.Shot;
import microservices.battleship.types.ShotImpl;
import microservices.battleship.types.Field;
import microservices.battleship.types.FieldImpl;
import microservices.battleship.types.Grid;
import microservices.battleship.types.GridImpl;
import microservices.battleship.types.CellImpl;
import microservices.battleship.types.Local;
import microservices.battleship.types.LocalImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *file SqlBattleshipDaoImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative al gioco Battleship mediante SQL;
 *use Viene usata per salvare e recuperare le informazioni relative al gioco Battleship mediante SQL;
 */
public class SqlBattleshipDaoImpl implements BattleshipDao {

  @Override
  public void addUserShips(int x, int y, boolean vertical, int profileId){
    Connection connection = null;
    Integer battleshipShipId = getNotPosShipIdFromProfileId(profileId);
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//inserisce la posizione della nave
      +"INSERT INTO `BattleshipPositions` "
      +"(`x`,`y`) "
      +"VALUES "
      +"("+ x +","+ y +");";
      statement.executeUpdate(queryA, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      Integer battleshipShipPositionId = resultSet.getInt(1);
      
      String queryB = ""//associa la nave alla posizione
      +"UPDATE `BattleshipShips` "
      +"SET `battleshipPositionId` = "+ battleshipShipPositionId +" , "
      +"`vertical` = "+ vertical +" "
      +"WHERE `battleshipShipId` = "+ battleshipShipId +" ;";
      statement.executeUpdate(queryB);
      
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  //dato profileId trova battleshipShipId di una nave non posizionata
  private static Integer getNotPosShipIdFromProfileId(int profileId){
    Connection connection = null;
    Integer battleshipShipId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//trova la nave da posizionare
        +"SELECT `battleshipShipId`"
        +"FROM `BattleshipShips` "
        +"WHERE `profileId` = "+ profileId +" "
        +"AND `battleshipPositionId` IS NULL "
        +"LIMIT 1 ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      resultSet.next();
      battleshipShipId = resultSet.getInt("battleshipShipId");
      
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return battleshipShipId; 
  }
  
  @Override
  public boolean checkUserFinishShipPositioning(int profileId){
    Connection connection = null;
    boolean response = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = ""//trova una nave non posizionata
        +"SELECT `battleshipShipId` "
        +"FROM `BattleshipShips` "
        +"WHERE `profileId` = "+ profileId +" "
        +"AND `battleshipPositionId` IS NULL "
        +"LIMIT 1 ;";
      ResultSet resultSet = statement.executeQuery(query);
      response = !(resultSet.next());
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return response;
  }
  
  @Override
  public boolean checkUserFinishShoots(int profileId){
    Connection connection = null;
    Integer maxShots = null;
    Integer placedShots = null;
    boolean response = true;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//trova il numero dei colpi totali a disposizione
        +"SELECT `numberOfShots` "
        +"FROM `BattleshipNumberOfShots` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        maxShots = resultSet.getInt(1);
      }
      
      String queryB = ""//trova il numero dei colpi sparati
        +"SELECT COUNT(*) "
        +"FROM `BattleshipAttacks` "
        +"WHERE `profileId` = "+ profileId +" ;";
      resultSet = statement.executeQuery(queryB);
      if(resultSet.next()){
        placedShots = resultSet.getInt(1);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    if(maxShots!=null && placedShots!=null){
      response = (maxShots - placedShots) == 0;
    }
    return response;
  }

  @Override
  public void deleteBattle(int teamId){
    Connection connection = null;
    Integer matchId = null;
    Integer enemyTeamId = getOpponentTeamId(teamId);
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//trova il matchId associato al teamId
        +"SELECT `matchId`"
        +"FROM `Teams`"
        +"WHERE `teamId` = "+ teamId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        matchId = resultSet.getInt(1);
        if(resultSet.wasNull()){
          matchId = null;
        }
      }
      if(matchId != null){
        String queryB = ""//elimina le posizioni associate ai colpi del match
         +"DELETE FROM `BattleshipPositions` "
          +"WHERE `battleshipPositionId` IN ( "
            +"SELECT `battleshipPositionId`"
            +"FROM `BattleshipAttacks`"
            +"WHERE `battleshipMatchId` = "+ matchId +" ) ;";
        statement.executeUpdate(queryB);    
        String queryC = ""//elimina il match
          +"DELETE FROM `BattleshipMatches` "
          +"WHERE `battleshipMatchId` = "+ matchId +" ;";
        statement.executeUpdate(queryC);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    if(matchId != null){
      if(enemyTeamId!=null){
        clearBattleshipTeam(enemyTeamId);
      }
      clearBattleshipTeam(teamId);
    }
  }
  
  private static void clearBattleshipTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//elimina le posizioni associate alla griglia del team
        +"DELETE FROM `BattleshipPositions` "
        +"WHERE `battleshipPositionId` IN ( "
          +"SELECT `battleshipPositionId` "
          +"FROM `BattleshipCells` "
          +"WHERE `teamId` = "+ teamId +" );";
      statement.executeUpdate(queryA);
      
      String queryB = ""//elimina la griglia del team
        +"DELETE FROM `BattleshipCells` "
        +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(queryB);
      
      String queryC = ""//elimina le posizioni associate alle navi del team
      +"DELETE FROM `BattleshipPositions` "
      +"WHERE `battleshipPositionId` IN ( "
        +"SELECT `battleshipPositionId` "
        +"FROM `BattleshipShips` "
        +"WHERE `teamId` = "+ teamId +" );";
      statement.executeUpdate(queryC);
     
      String queryD = ""//elimina le navi del team
        +"DELETE FROM `BattleshipShips` "
        +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(queryD);
      
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public Team getOpponentTeam(int teamId){
    Team team = null;
    Integer enemyTeamId = getOpponentTeamId(teamId);
    if(enemyTeamId != null){
      team = getTeamFromTeamId(enemyTeamId);
    }
    return team;
  }
  
  private static Integer getOpponentTeamId(int teamId){
    Connection connection = null;
    Integer enemyTeamId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//trova il team avversario
        +"SELECT `teamId` "
        +"FROM `Teams` "
        +"WHERE `teamId` != "+ teamId +" "
        +"AND `matchId` = ("
          +"SELECT `matchId`"
          +"FROM `Teams`"
          +"WHERE `teamId` = "+ teamId +" ) ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        enemyTeamId = resultSet.getInt(1);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return enemyTeamId;
  }
  
  private static Team getTeamFromTeamId(int teamId){
    Connection connection = null;
    ArrayList<Profile> teammates = null;
    Team team = null;
    Profile captain = null;
    String teamName = null;
    
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//trova i teammates
        +"SELECT `profileId`, `username` "
        +"FROM `Profiles` "
        +"WHERE `teamId` = "+ teamId +" "
        +"AND `profileId` NOT IN( "
          +"SELECT `profileId` "
          +"FROM `TeamLeaders` "
          +"WHERE `teamId` = "+ teamId +" );";
      ResultSet resultSet = statement.executeQuery(queryA);
      teammates = new ArrayList<Profile>();
      while(resultSet.next()){
        String QUsername = resultSet.getString("username");
        Integer QProfileId = resultSet.getInt("profileId");
	      teammates.add(new ProfileImpl(QUsername, QProfileId));
	    }
      resultSet.close();
      String queryB = ""//trova il leader
        +"SELECT `profileId`, `username` "
        +"FROM `Profiles` "
        +"WHERE `teamId` = "+ teamId +" "
        +"AND `profileId` = ( "
          +"SELECT `profileId` "
          +"FROM `TeamLeaders` "
          +"WHERE `teamId` = "+ teamId +" );";
      resultSet = statement.executeQuery(queryB);
      if(resultSet.next()){
        String QUsername = resultSet.getString("username");
        Integer QProfileId = resultSet.getInt("profileId");
        captain = new ProfileImpl(QUsername, QProfileId);
      }
      resultSet.close();
      String queryC = ""//trova il nome del team
        +"SELECT `teamName` "
        +"FROM `Teams` "
        +"WHERE `teamId` = "+ teamId +" ;";
      resultSet = statement.executeQuery(queryC);
      if(resultSet.next()){
        teamName = resultSet.getString("teamName");
      }
      resultSet.close();
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    if(teamName!=null && captain!=null && teammates!=null){
      team = new TeamImpl(teamName, teamId, captain, teammates);
    }
    return team;
  }
  
  @Override
  public List<Team> getReadyTeams(int localId){
    Connection connection = null;
    ArrayList<Integer> readyTeamsId = null;
    ArrayList<Team> readyTeams = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = ""//trova id dei team pronti, nel locale localId
        +"SELECT `teamId` "
        +"FROM `Teams` "
        +"WHERE `ready` = TRUE "
        +"AND `tableId` IN ( "
          +"SELECT `tableId` "
          +"FROM `Tables` "
          +"WHERE `localId` = "+ localId +" ) ;";
      ResultSet resultSet = statement.executeQuery(query);
      readyTeamsId = new ArrayList<Integer>();
      while(resultSet.next()){
        Integer teamId = resultSet.getInt("teamId");
        readyTeamsId.add(teamId);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    readyTeams = new ArrayList<Team>();
    if(readyTeamsId != null){
      for(Integer readyTeamId : readyTeamsId){
        Team team = getTeamFromTeamId(readyTeamId);
        if(team != null){
          readyTeams.add(team);
        }else{
          //C'è stato un errore nel reperimento del team, il team non verrà inserito.
        }
      }
    }
    return readyTeams;
  }
  
  @Override
  public List<Shot> getShotList(int profileId){
    Connection connection = null;
    ArrayList<Integer> shotsPositionsId = new ArrayList<Integer>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//trova gli id delle posizioni dei colpi
        +"SELECT `battleshipPositionId` "
        +"FROM `BattleshipAttacks` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        Integer shotPositionId = resultSet.getInt(1);
        shotsPositionsId.add(shotPositionId);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    ArrayList<Shot> shots = new ArrayList<Shot>();
    for(Integer shotPositionId : shotsPositionsId){
      shots.add(new ShotImpl(getPosition(shotPositionId)));
    }
    return shots;
  }
  
  @Override
  public int getShotNumber(int profileId){
    Connection connection = null;
    Integer maxShots = null;
    Integer placedShots = null;
    Integer remainingShots = 0;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//trova il numero dei colpi totali a disposizione
        +"SELECT `numberOfShots` "
        +"FROM `BattleshipNumberOfShots` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        maxShots = resultSet.getInt(1);
      }
      
      String queryB = ""//trova il numero dei colpi sparati
        +"SELECT COUNT(*) "
        +"FROM `BattleshipAttacks` "
        +"WHERE `profileId` = "+ profileId +" ;";
      resultSet = statement.executeQuery(queryB);
      if(resultSet.next()){
        placedShots = resultSet.getInt(1);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    if(maxShots!=null && placedShots!=null){
      remainingShots = (maxShots - placedShots);
    }
    return remainingShots;
  }
  
  @Override
  public Team getTeamFormProfileId(int profileId){
    Connection connection = null;
    Integer teamId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//trova l'id del team del profilo
        +"SELECT `teamId` "
        +"FROM `Profiles` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      if(resultSet.next()){
        teamId = resultSet.getInt(1);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    Team team = null;
    if(teamId != null){
      team = getTeamFromTeamId(teamId);
    }
    return team;
  }
  
  @Override
  public Field getTeamField(int teamId){
    Connection connection = null;
    Integer larghezza = null;
    Integer altezza = null;
    Field teamField = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//trova la larghezza della griglia
        +"SELECT `x`"
        +"FROM `BattleshipPositions` "
        +"WHERE `battleshipPositionId` IN ( "
        +"  SELECT `battleshipPositionId` "
        +"  FROM `BattleshipCells` "
        +"  WHERE `teamId` = "+ teamId +" "
        +") "
        +"ORDER BY `x` DESC "
        +"LIMIT 1;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        larghezza = resultSet.getInt(1);
      }
      
      String queryB = ""//trova l'altezza della griglia
        +"SELECT `y`"
        +"FROM `BattleshipPositions` "
        +"WHERE `battleshipPositionId` IN ( "
        +"  SELECT `battleshipPositionId` "
        +"  FROM `BattleshipCells` "
        +"  WHERE `teamId` = "+ teamId +" "
        +") "
        +"ORDER BY `y` DESC "
        +"LIMIT 1;";
      resultSet = statement.executeQuery(queryB);
      if(resultSet.next()){
        altezza = resultSet.getInt(1);
      }
      
      if(larghezza !=null && larghezza !=null){
      	altezza++;
      	larghezza++;
        Grid grid = new GridImpl(altezza, larghezza);

        String queryC = ""//trova le celle di una griglia
          +"SELECT `x`,`y`,`state` "
          +"FROM `BattleshipCells` "
          +"NATURAL JOIN `BattleshipPositions` "
          +"WHERE `teamId` = "+ teamId +" "
          +"ORDER BY `x`, `y` ; ";
        resultSet = statement.executeQuery(queryC);
        while(resultSet.next()){
          Integer x = resultSet.getInt("x");
          Integer y = resultSet.getInt("y");
          String stateStr = resultSet.getString("state");
          CellImpl.State state;
          switch(stateStr) {
            case "HIT": state = CellImpl.State.HIT; break;
            case "MISS": state = CellImpl.State.MISS; break;
            case "SUNK": state = CellImpl.State.SUNK; break;
            case "UNKNOWN": state = CellImpl.State.UNKNOWN; break;
            default: state = CellImpl.State.UNKNOWN; 
          }
          grid.setCell(x,y,state);
        }
        teamField = new FieldImpl(grid);
      }//if
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return teamField;
  }
  
  @Override
  public List<Ship> getUserShips(int profileId){
    Connection connection = null;
    ArrayList<Integer> userShipsId = new ArrayList<Integer>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//trova id delle navi posizionate, del profileId
        +"SELECT `battleshipShipId` "
        +"FROM `BattleshipShips` "
        +"WHERE `battleshipPositionId` IS NOT NULL "
        +"AND `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        Integer shipId = resultSet.getInt(1);
        userShipsId.add(shipId);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    ArrayList<Ship> userShips = new ArrayList<Ship>();
    for(Integer userShipId : userShipsId){
      userShips.add(getShip(userShipId));
    }
    return userShips;
  }
  
  private static Ship getShip(int shipId){
    Connection connection = null;
    boolean isVertical = false;
    Integer positionId = null;
    Integer length = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""
        +"SELECT `vertical`, `battleshipPositionId` "
        +"FROM `BattleshipShips` "
        +"WHERE `battleshipShipId` = "+ shipId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      resultSet.next();
      isVertical = resultSet.getBoolean("vertical");
      positionId = resultSet.getInt("battleshipPositionId");
      
      String queryB = ""//ottiene numero di componenti della nave
        +"SELECT COUNT(*) "
        +"FROM `BattleshipShipComponents` "
        +"WHERE `battleshipShipId` = "+ shipId +" ;";
      resultSet = statement.executeQuery(queryB);
      resultSet.next();
      length = resultSet.getInt(1);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    Position position = getPosition(positionId);
    Ship ship = new ShipImpl(isVertical, length, position);
    return ship;
  }
  
    private static Position getPosition(int positionId){
    Connection connection = null;
    Position position = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//restituisce x,y della posizione
        +"SELECT `x`,`y` "
        +"FROM `BattleshipPositions` "
        +"WHERE `battleshipPositionId` = "+ positionId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      if(resultSet.next()){
        Integer x = resultSet.getInt("x");
        Integer y = resultSet.getInt("y");
        position = new PositionImpl(x, y);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return position;
  }
  
  @Override
  public void insertSearchingOpponent(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Teams` "
        +"SET `ready` = TRUE "
        +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public void insertShot(int x, int y, int profileId){
    Connection connection = null;
    Integer battleshipMatchId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = "" //da profileId a battleshipMatchId
      +"SELECT `battleshipMatchId` "
      +"FROM `BattleshipMatches` "
      +"WHERE `battleshipMatchId` = ( "
      +"  SELECT `matchId` "
      +"  FROM `Teams` "
      +"  WHERE `teamId` = ( "
      +"    SELECT `teamId` "
      +"    FROM `Profiles` "
      +"	  WHERE `profileId` = "+ profileId +" "
      +"  ) "
      +");";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){
        battleshipMatchId = resultSet.getInt(1);
      }
      
      if(battleshipMatchId != null){
        String queryB = ""//inserisce la posizione del colpo
        +"INSERT INTO `BattleshipPositions` "
        +"(`x`,`y`) "
        +"VALUES "
        +"("+ x +","+ y +");";
        statement.executeUpdate(queryB, Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer battleshipShotPositionId = resultSet.getInt(1);
        
        String queryC = ""//crea battleshipAttack
        +"INSERT INTO `BattleshipAttacks` "
        +"(`battleshipPositionId`, `profileId`,`battleshipMatchId`)"
        +"VALUES "
        +"("+ battleshipShotPositionId +","+ profileId +","+ battleshipMatchId +");";
        statement.executeUpdate(queryC);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public boolean isBattleRunning(int teamId){
    Connection connection = null;
    boolean response = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//torva matchId associato al team
        +"SELECT `matchId` "
        +"FROM `Teams` "
        +"WHERE `teamId` = "+ teamId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      if(resultSet.next()){
        Integer matchId = resultSet.getInt("matchId");
        response = !(resultSet.wasNull());
      }else{
        //viene ritornato false se il team non esiste.
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return response;
  }
  
  @Override
  public boolean isTeamExists(String uuid, int major, int minor){
    Connection connection = null;
    boolean response = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement(); 
      String query = ""//vede se esiste un team associato al beacon
        +"SELECT `teamId` "
        +"FROM `Teams` "
        +"WHERE `tableId` = ( "
          +"SELECT `tableId` "
          +"FROM `Tables` "
          +"WHERE `beaconId` = ( "
            +"SELECT `beaconId` "
            +"FROM `Beacons` "
            +"WHERE `uuid` = '"+ uuid +"' "
            +"AND `major` = "+ major +" "
            +"AND `minor` = "+ minor +" ) );";
      ResultSet resultSet = statement.executeQuery(query);
      response = resultSet.next();
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return response;
  }
  
  @Override
  public void removeReadyTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Teams` "
        +"SET `ready` = FALSE "
        +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public void removeTeamMember(int teamId, int profileId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Profiles` "
        +"SET `teamId` = NULL "
        +"WHERE `profileId` = "+ profileId +" "
        +"AND `teamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public void setOpponent(int teamOneId, int teamTwoId){
    Connection connection = null;
    Integer battleshipMatchId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = ""//crea un match
      +"INSERT INTO `BattleshipMatches` "
      +"(`attackingTeamId`) "
      +"VALUES "
      +"(NULL);";
      statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      battleshipMatchId = resultSet.getInt(1);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    setTeamMatchId(teamOneId, battleshipMatchId);
    setTeamMatchId(teamTwoId, battleshipMatchId);
  }
  
  private static void setTeamMatchId(int teamId, int matchId){
      Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
      +"UPDATE `Teams` "
      +"SET `matchId` = "+ matchId +" "
      +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public void setShipToPositioning(int length, int profileId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//crea la nave
      +"INSERT INTO `BattleshipShips` "
      +"(`vertical`,`teamId`,`battleshipPositionId`,`profileId`) "
      +"VALUES "
      +"( "
      +"  NULL "
      +", "
      +"  (SELECT `teamId` "
      +"  FROM `Profiles` "
      +"  WHERE `profileId` = "+ profileId +") "
      +", "
      +"  NULL "
      +", "
      +"  "+ profileId +" "
      +"); ";
      statement.executeUpdate(queryA, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      Integer battleshipShipId = resultSet.getInt(1);
      
      String queryB = ""//genera i componenti della nave
      +"INSERT INTO `BattleshipShipComponents` "
      +"(`battleshipShipId`,`componentNumber`) "
      +"VALUES "
      +getCreateShipComponents(battleshipShipId, length);
      statement.executeUpdate(queryB);
      
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  private static String getCreateShipComponents(int battleshipShipId, int length){
    String s = "";
    for(int i=1; i<length; i++){
      s += "("+ battleshipShipId +","+ i +"), "; 
    }
    s += "("+ battleshipShipId +","+ length +");";
    return s;
  }
  
  @Override
  public void updateField(String [][] grid, int teamId){
    Connection connection = null;
    Integer larghezza = grid[0].length;
    Integer altezza = grid.length;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//vede se c'è già la griglia
        +"SELECT COUNT(*) "
        +"FROM `BattleshipCells` "
        +"WHERE `teamId` = "+teamId+" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      resultSet.next();
      Integer numeroCelle = resultSet.getInt(1);
      if(numeroCelle.equals(0)){ //la crea
        for(int y=0; y<altezza; y++){
          for(int x=0; x<larghezza; x++){
            String queryB = ""
              +"INSERT INTO `BattleshipPositions` "
              +"(`x`,`y`) "
              +"VALUES "
              +"("+ x +","+ y +"); ";
            statement.executeUpdate(queryB);
            String queryC = ""
              +"INSERT INTO `BattleshipCells` "
              +"(`teamId`,`state`,`battleshipPositionId`) "
              +"VALUES "
              +"("+ teamId +",'"+ grid[y][x] +"',LAST_INSERT_ID()); ";
            statement.executeUpdate(queryC);
          }
        }
      }else{ //la modifica
        for(int y=0; y<altezza; y++){
          for(int x=0; x<larghezza; x++){
            String queryD = ""
              +"UPDATE `BattleshipCells` "
              +"SET `state` = '"+ grid[y][x] +"' "
              +"WHERE `teamId` = "+ teamId +" "
              +"AND `battleshipPositionId` IN ( "
              +"  SELECT `battleshipPositionId` "
              +"  FROM `BattleshipPositions` "
              +"  WHERE `x` = "+ x +" "
              +"  AND `y` = "+ y +" "
              +"); ";
            statement.executeUpdate(queryD);
          }
        }
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  public void setAttackTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = ""//imposta il team attaccante
      +"UPDATE `BattleshipMatches` "
      +"SET `attackingTeamId` = "+ teamId +" "
      +"WHERE `battleshipMatchId` = ( "
        +"SELECT `matchId` "
        +"FROM `Teams` "
        +"WHERE `teamId` = "+ teamId +" );";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public boolean isAttackPhase(int teamId){
    Connection connection = null;
    boolean response = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement(); 
      String query = ""//vede se il team è attaccante
        +"SELECT `attackingTeamId` "
        +"FROM `BattleshipMatches` "
        +"WHERE `attackingTeamId` = "+ teamId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      response = resultSet.next();
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return response;
  }
  
  @Override
  public void removeAttackTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = ""//rimuove il team dai team attaccanti
      +"UPDATE `BattleshipMatches` "
      +"SET `attackingTeamId` = NULL "
      +"WHERE `attackingTeamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public Local getLocal(int profileId){
    Connection connection = null;
    Local local = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String query = "" //trova il locale associato al profilo
        +"SELECT `localId`, `description` "
        +"FROM `Locals` "
        +"WHERE `uuid` = ( "
          +"SELECT `uuid` "
          +"FROM `Beacons` "
          +"WHERE `beaconId` = ( "
            +"SELECT `beaconId` "
            +"FROM `Profiles` "
            +"WHERE `profileId` = "+ profileId +" ) );";
      ResultSet resultSet = statement.executeQuery(query); 
      if(resultSet.next()){
        Integer localId = resultSet.getInt("localId");
        String description = resultSet.getString("description");
        local = new LocalImpl(localId, description);
      }
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    return local;
  }
  
  @Override
  public List<ShipType> getShipsToPositioning(int profileId){
    Connection connection = null;
    ArrayList<Integer> userShipsId = new ArrayList<Integer>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//trova id delle navi non posizionate
        +"SELECT `battleshipShipId` "
        +"FROM `BattleshipShips` "
        +"WHERE `battleshipPositionId` IS NULL "
        +"AND `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        Integer shipId = resultSet.getInt(1);
        userShipsId.add(shipId);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    ArrayList<ShipType> userShips = new ArrayList<ShipType>();
    for(Integer userShipId : userShipsId){
      userShips.add(getShipType(userShipId));
    }
    return userShips;
  }
  
  private static ShipType getShipType(int shipId){
    Connection connection = null;
    Integer length = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//ottiene numero di componenti della nave
        +"SELECT COUNT(*) "
        +"FROM `BattleshipShipComponents` "
        +"WHERE `battleshipShipId` = "+ shipId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      resultSet.next();
      length = resultSet.getInt(1);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    ShipType shipType = new ShipTypeImpl(length);
    return shipType;
  }
  
  @Override
  public boolean setMaxAvailableShots(int numberOfShots, int profileId){
    Connection connection = null;
    boolean noErrors = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""//controlla se esiste già il numero di colpi totali a disposizione.
        +"SELECT `profileId` "
        +"FROM `BattleshipNumberOfShots` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      if(resultSet.next()){//esiste già un record
      
        String queryB = ""//aggiorna il numero dei colpi totali a disposizione
        +"UPDATE `BattleshipNumberOfShots` "
        +"SET `numberOfShots` = "+ numberOfShots +" "
        +"WHERE `profileId` = "+ profileId +" ;";
        int updatedRows = statement.executeUpdate(queryB);
        noErrors = (updatedRows == 1);
      }else{//non esiste
      
        String queryC = ""//inserisce il numero dei colpi totali a disposizione
        +"INSERT INTO `BattleshipNumberOfShots` "
        +"(`profileId`,`numberOfShots`) "
        +"VALUES "
        +"("+ profileId +","+ numberOfShots +") ;";
        int insertedRows = statement.executeUpdate(queryC);
        noErrors = (insertedRows == 1);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return noErrors;
  }
  
  @Override
  public void addReadyTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Teams` "
        +"SET `ready` = TRUE "
        +"WHERE `teamId` = "+ teamId +" ;";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
  
  @Override
  public void clearTeamShots(int teamId){
    Connection connection = null;
    ArrayList<Integer> profilesId = new ArrayList<Integer>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//trova profili associati al team
        +"SELECT `profileId` "
        +"FROM `Profiles` "
        +"WHERE `teamId` = "+ teamId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        Integer profileId = resultSet.getInt(1);
        profilesId.add(profileId);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    
    for(Integer profileId : profilesId){
      clearProfileShots(profileId);
    }
  }
  
  private static void clearProfileShots(int profileId){
    Connection connection = null;
    ArrayList<Integer> positionsId = new ArrayList<Integer>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//trova gli id delle posizioni dei colpi
        +"SELECT `battleshipPositionId` "
        +"FROM `BattleshipAttacks` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(queryA);
      while(resultSet.next()){
        Integer positionId = resultSet.getInt(1);
        positionsId.add(positionId);
      }
      for(Integer positionId : positionsId){
        String queryB = ""//elimina la posizione associata ad un colpo
          +"DELETE FROM `BattleshipPositions` "
          +"WHERE `battleshipPositionId` = "+ positionId +" ;";
        statement.executeUpdate(queryB);
      }
      String queryC = ""//elimina i colpi associati ad un profilo
        +"DELETE FROM `BattleshipAttacks` "
        +"WHERE `profileId` = "+ profileId +" ;";
      statement.executeUpdate(queryC);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
  }
}
