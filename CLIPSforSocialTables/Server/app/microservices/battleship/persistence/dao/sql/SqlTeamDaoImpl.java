package microservices.battleship.persistence.dao.sql;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import microservices.battleship.persistence.dao.TeamDao;
import microservices.battleship.types.Team;
import microservices.battleship.types.TeamImpl;
import microservices.battleship.types.Profile;
import microservices.battleship.types.ProfileImpl;
import microservices.battleship.types.Table;
import microservices.battleship.types.TableImpl;
import java.util.List;
import java.util.ArrayList;

/**
 *file SqlTeamDaoImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad una squadra mediante SQL;
 *use Viene usata per salvare e recuperare le informazioni relative ad una squadra mediante SQL;
 */
public class SqlTeamDaoImpl implements TeamDao {

  @Override
  public boolean addMember(int teamId, int memberProfileId){
    Connection connection = null;
    boolean noErrors = true;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Profiles` "
        +"SET `teamId` = "+ teamId +" "
        +"WHERE `profileId` = "+ memberProfileId +";";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
      noErrors = false;
    }catch(SQLException e){
      e.printStackTrace();
      noErrors = false;
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
  public boolean changeTeamName(int teamId, String teamName){
    Connection connection = null;
    boolean noErrors = true;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Teams` "
        +"SET `teamName` = '"+ teamName +"' "
        +"WHERE `teamId` = "+ teamId +";";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
      noErrors = false;
    }catch(SQLException e){
      e.printStackTrace();
      noErrors = false;
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
  public Team createTeam(String teamName, int leaderProfileId){
    Connection connection = null;
    boolean noErrors = true;
    Integer teamId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""//crea un team
        +"INSERT INTO `Teams` "
        +"(`teamName`, `tableId`) "
        +"VALUES( "
        +"  '"+ teamName +"' "
        +", "
        +"  (SELECT `tableId` "
        +"  FROM `Tables` "
        +"  WHERE `beaconId` = ( "
        +"    SELECT `beaconId` "
        +"	  FROM `Profiles` "
        +"	  WHERE `profileId` = "+ leaderProfileId +" "
        +"  )) "
        +");";
      statement.executeUpdate(queryA, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      //teamId of created team
      teamId = resultSet.getInt(1);
      String queryB = ""//imposta il team leader
        +"INSERT INTO `TeamLeaders` "
        +"(`teamId`, `profileId`) "
        +"VALUES("+ teamId +","+ leaderProfileId +");";
      statement.executeUpdate(queryB);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
      noErrors = false;
    }catch(SQLException e){
      e.printStackTrace();
      noErrors = false;
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    if(teamId != null){
      addMember(teamId, leaderProfileId);
    }
    return getTeam(teamId);
  }

  @Override
  public void deleteTeam(int teamId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"DELETE FROM `Teams` "
        +"WHERE `teamId` = "+ teamId +";";
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
  public Team getTeam(int teamId){
    Connection connection = null;
    ArrayList<Profile> teammates = null;
    Profile captain = null;
    String teamName = null;
    Team team = null;
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
      String queryB = ""//trova il team leader
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
    if((captain!=null) && (teamName!=null) && (teammates!=null)){
      team = new TeamImpl(teamName, teamId, captain, teammates);
    }
    return team;
  }
  
  @Override
  public boolean isTeamExist(int tableId){
    Connection connection = null;
    boolean answer = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"SELECT `teamId` "
        +"FROM `Teams` "
        +"WHERE `tableId` = "+ tableId +";";
      ResultSet resultset = statement.executeQuery(query);
      answer = resultset.next();
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
    return answer;
  }
  
  @Override
  public boolean removeMember(int teamId, int memberProfileId){
    Connection connection = null;
    boolean successful = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"UPDATE `Profiles` "
        +"SET `teamId` = NULL "
        +"WHERE `profileId` = "+ memberProfileId +" "
        +"AND `teamId` = "+ teamId +" ;";
      int numberOfUpdatedRows = statement.executeUpdate(query);
      successful = (numberOfUpdatedRows != 0);
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
      successful = false;
    }catch(SQLException e){
      e.printStackTrace();
      successful = false;
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return successful;
  }
  
  @Override
  public boolean setTeamLeader(int teamId, int profileId){
    Connection connection = null;
    boolean successful = false;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String queryA = ""
        +"SELECT `profileId`"
        +"FROM `TeamLeaders` "
        +"WHERE `teamId` = "+ teamId +" ;";
      ResultSet resultset = statement.executeQuery(queryA);
      if(resultset.next()){//c'è un leader
        Integer oldLeaderId = resultset.getInt("profileId");
        if(oldLeaderId.equals(profileId)){//è uguale a quello vecchio
          successful = true;
        }else{//è uno nuovo
          String queryB = ""
            +"UPDATE `TeamLeaders` "
            +"SET `profileId` = "+ profileId +" "
            +"WHERE `teamId` = "+ teamId +" ;";
          int numberOfUpdatedRows = statement.executeUpdate(queryB);
          successful = (numberOfUpdatedRows != 0);
        }
      }else{//non c'è un leader
        String queryC = ""
          +"INSERT INTO `TeamLeaders` "
          +"(`teamId`,`profileId`)"
          +"VALUES ( "+ teamId +" , "+ profileId +" );";
        int numberOfInsertedRows = statement.executeUpdate(queryC);
        successful = (numberOfInsertedRows != 0);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      e.printStackTrace();
      successful = false;
    }catch(SQLException e){
      e.printStackTrace();
      successful = false;
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        e.printStackTrace();
      }//catch
    }//finally
    return successful;
  }
  
  @Override
  public Table getTable(String uuid, int major, int minor){
    Connection connection = null;
    Table table = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = "" //ottiene l'identificatore e la descrizione del tavolo legato al beacon
        +"SELECT `tableId`,`tableDescription` "
        +"FROM `Tables` "
        +"WHERE `beaconId` = ( "
          +"SELECT `beaconId` "
          +"FROM `Beacons` "
          +"WHERE `uuid` = '"+ uuid +"' "
          +"AND `major` = "+ major +" "
          +"AND `minor` = "+ minor +" );";
      ResultSet resultSet = statement.executeQuery(query);
      resultSet.next();
      Integer tableId = resultSet.getInt("tableId");
      String tableDescription = resultSet.getString("tableDescription");
      table = new TableImpl(tableId, tableDescription);
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
    return table;
  }
  
  @Override
  public List<String> getTeamNameList(int localId){
    Connection connection = null;
    List<String> list = new ArrayList<String>();
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = "" //nomi dei team di un locale
        +"SELECT `teamName` "
        +"FROM `Teams` "
        +"WHERE `tableId` IN ( "
          +"SELECT `tableId` "
          +"FROM `Tables` "
          +"WHERE `localId` = "+ localId +" );";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        String teamName = resultSet.getString("teamName");
        list.add(teamName);
      }
    }catch(MySQLIntegrityConstraintViolationException e){
      list = null;
      e.printStackTrace();
    }catch(SQLException e){
      list = null;
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
    return list;
  }
  
  @Override
  public Integer getTeamIdFromTableId(int tableId){
    Connection connection = null;
    Integer teamId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = "" //teamId dato un tableId
        +"SELECT `teamId` "
        +"FROM `Teams` "
        +"WHERE `tableId` = "+ tableId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      if(resultSet.next()){
        teamId = resultSet.getInt("teamId");
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
    return teamId;
  }
  
  @Override
  public Integer getTeamIdFromProfileId(int profileId){
    Connection connection = null;
    Integer teamId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = "" //teamId dato un profileId
        +"SELECT `teamId` "
        +"FROM `Profiles` "
        +"WHERE `profileId` = "+ profileId +" ;";
      ResultSet resultSet = statement.executeQuery(query);
      if(resultSet.next()){
        teamId = resultSet.getInt("teamId");
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
    return teamId;
  }
}
