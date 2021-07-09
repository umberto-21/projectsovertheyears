package microservices.profile.persistence.dao.sql;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import microservices.profile.persistence.dao.ProfileDao;
import microservices.profile.types.Error;
import microservices.profile.types.ErrorConstants;
import microservices.profile.types.ErrorImpl;
import microservices.profile.types.Profile;
import microservices.profile.types.ProfileImpl;
import microservices.profile.types.Response;
import microservices.profile.types.ResponseImpl;
import microservices.profile.types.Session;
import microservices.profile.types.SessionImpl;
import microservices.profile.types.Local;
import microservices.profile.types.LocalImpl;
import java.util.List;
import java.util.ArrayList;

/**
 *file SqlProfileDaoImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad un profilo mediante SQL.
 */
public class SqlProfileDaoImpl implements ProfileDao {

  @Override
  public Response<Profile> createProfile(String username, int sessionId, Integer beaconId){
    Connection connection = null;
    Statement statement = null;
    Profile profileToReturn = null;
    Error errorToReturn = null;
    String beaconIdToInsert = beaconId==null ? "NULL": "'"+beaconId.toString()+"'" ;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "INSERT INTO `Profiles` (`username`, `sessionId`, `beaconId`) VALUES ('" + username + "','" + sessionId + "'," + beaconIdToInsert + ");";
      statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      //idProfile of created profile
      Integer profileId = resultSet.getInt(1);
      profileToReturn = new ProfileImpl(username, profileId);
    }catch(MySQLIntegrityConstraintViolationException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DUPLICATE_ENTRY_OR_INVALID_SESSION);
      e.printStackTrace();
    }catch(SQLException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }//if
      }catch(SQLException e){
        errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
        e.printStackTrace();
      }//catch
    }//finally
    ResponseImpl<Profile> response = new ResponseImpl<Profile>(profileToReturn, errorToReturn);
    return response;
  }//createProfile

  @Override
  public Response<Profile> getProfile(int sessionId){
    Connection connection = null;
    Statement statement = null;
    Profile profileToReturn = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "SELECT `profileId`, `username`, `sessionId`, `beaconId` FROM `Profiles` WHERE `sessionId` = "+ sessionId +";";
      ResultSet resultSet = statement.executeQuery(query);

      if(resultSet.next()) {
        Integer queryProfileId = resultSet.getInt("profileId");
        String queryUsername = resultSet.getString("username");
        profileToReturn = new ProfileImpl(queryUsername, queryProfileId);
      }else{
        errorToReturn = new ErrorImpl(ErrorConstants.INVALID_SESSION);
      }
    }catch(SQLException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }
      }catch(SQLException e){
        errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
        e.printStackTrace();
      }
    }
    ResponseImpl<Profile> response = new ResponseImpl<Profile>(profileToReturn, errorToReturn);
    return response;
  }

  @Override
  public Error deleteProfile(int sessionId){
    Connection connection = null;
    Statement statement = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "DELETE FROM `Profiles` WHERE `sessionId` = "+ sessionId +";";
      int numberOfDeletedProfiles = statement.executeUpdate(query);

      if(numberOfDeletedProfiles == 0){
        errorToReturn = new ErrorImpl(ErrorConstants.INVALID_SESSION);
      }else{
        //It's ok: errorToReturn = null.
      }
    }catch(SQLException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }
      }catch(SQLException e){
        errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
        e.printStackTrace();
      }
    }
    return errorToReturn;
  }

  @Override
  public Error modifyProfile(String newUsername, int sessionId, String newBeaconId){
    Connection connection = null;
    Statement statement = null;
    Error errorToReturn = null;
    String usernameToInsert;
    if(newUsername == null){
      usernameToInsert = "";
    }else{
      usernameToInsert = "`username` = '"+ newUsername +"'";
    }
    String beaconIdToInsert;
    if(newBeaconId == null){
      beaconIdToInsert = "";
    }else{
      beaconIdToInsert = "`beaconId` = ";
      if(newBeaconId.equals("NULL")) {
        beaconIdToInsert += "NULL";
      }else{
        beaconIdToInsert += Integer.parseInt(newBeaconId);
      }
      if(newUsername != null){
        beaconIdToInsert += " ,";
      }
    }
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "UPDATE `Profiles` SET "+ beaconIdToInsert +""+ usernameToInsert +" WHERE `sessionId` = "+ sessionId +";";
      statement.executeUpdate(query);
    }catch(MySQLIntegrityConstraintViolationException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DUPLICATE_ENTRY_OR_INVALID_SESSION);
      e.printStackTrace();
    }catch(SQLException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }
      }catch(SQLException e){
        errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
        e.printStackTrace();
      }
    }
    return errorToReturn;
  }
  
  @Override
  public Response<Session> getSession(int profileId){
    Connection connection = null;
    Statement statement = null;
	Session sessionToReturn = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
	  
      String query = "SELECT `sessionId` "
	                +"FROM `Sessions` "
					+"WHERE `sessionId`=( "
						+"SELECT `sessionId` "
						+"FROM `Profiles` "
						+"WHERE `profileId`="+ profileId +" "
					+");";
      ResultSet resultSet = statement.executeQuery(query);

      if(resultSet.next()) {
        int querySessionId = resultSet.getInt("sessionId");
        sessionToReturn = new SessionImpl(querySessionId);
      }else{
        errorToReturn = new ErrorImpl(ErrorConstants.INVALID_PROFILE);
      }
    }catch(SQLException e){
      errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
      e.printStackTrace();
    }finally{
      try{
        if(connection!=null){
          connection.close();
        }
      }catch(SQLException e){
        errorToReturn = new ErrorImpl(ErrorConstants.DATABASE_ERROR);
        e.printStackTrace();
      }
    }
    ResponseImpl<Session> response = new ResponseImpl<Session>(sessionToReturn, errorToReturn);
    return response;
  }
  
  @Override
  public List<String> getNameList(int localId){
    ArrayList<String> list = new ArrayList<String>();
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"SELECT `username` "
        +"FROM `Profiles` "
        +"WHERE `beaconId` IN( "
          +"SELECT `beaconId` "
          +"FROM `Beacons` "
          +"WHERE `uuid` = ( "
            +"SELECT `uuid` "
            +"FROM `Locals` "
            +"WHERE `localId` = "+ localId +" ) );";
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
	      list.add(resultSet.getString("username"));
	    }
    }catch(SQLException e){
      list = null;
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
    return list;
  }
  
  @Override
  public int getBeaconId(String uuid, int major, int minor){
    Connection connection = null;
    Integer beaconId = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      
      String queryA = ""
        +"SELECT `beaconId` "
        +"FROM `Beacons` "
        +"WHERE `uuid` = '"+ uuid +"' "
        +"AND `major` = "+ major +" "
        +"AND `minor` = "+ minor +" ;";
      ResultSet resultSet = statement.executeQuery(queryA); 
      if(resultSet.next()){ //il beacon c'è già
        beaconId = resultSet.getInt(1);
      }else{ //il beacon non c'è
        
        String queryB = ""//creo il beacon
        +"INSERT INTO `Beacons` "
        +"(`uuid`,`major`,`minor`) "
        +"VALUES "
        +"('"+ uuid +"',"+ major +","+ minor +");";
        statement.executeUpdate(queryB, Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        resultSet.next();
        beaconId = resultSet.getInt(1);
        
        Integer localId = null;
        String queryC = ""//vedo se il locale esiste già
        +"SELECT `localId` "
        +"FROM `Locals` "
        +"WHERE `uuid` = '"+ uuid +"' ;";
        resultSet = statement.executeQuery(queryC);
        if(resultSet.next()){//il locale esise
          localId = resultSet.getInt(1);
        }else{//il locale non esiste
          String queryD = ""//creo il locale
          +"INSERT INTO `Locals` "
          +"(`uuid`,`description`) "
          +"VALUES "
          +"('"+ uuid +"','');";
          statement.executeUpdate(queryD, Statement.RETURN_GENERATED_KEYS);
          resultSet = statement.getGeneratedKeys();
          resultSet.next();
          localId = resultSet.getInt(1);
        }
        
        String queryE = ""//crea un tavolo associato al beacon e al locale appena inseriti
        +"INSERT INTO `Tables` "
        +"(`tableDescription`,`beaconId`,`localId`) "
        +"VALUES "
        +"('',"+ beaconId +","+ localId +");";
        statement.executeUpdate(queryE);
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
    return beaconId;
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
}