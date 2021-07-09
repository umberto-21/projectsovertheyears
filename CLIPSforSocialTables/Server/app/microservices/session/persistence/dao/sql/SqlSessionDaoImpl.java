package microservices.session.persistence.dao.sql;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import microservices.session.persistence.dao.SessionDao;
import microservices.session.types.Error;
import microservices.session.types.ErrorConstants;
import microservices.session.types.ErrorImpl;
import microservices.session.types.Response;
import microservices.session.types.ResponseImpl;
import microservices.session.types.Session;
import microservices.session.types.SessionImpl;

/**
 *file SqlSessionDaoImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad una sessione mediante SQL.
 */
public class SqlSessionDaoImpl implements SessionDao {
	
  /**
   * Method used to create a session.
   * @return object of type Response holding an object of type Session or an error.
   */
  public Response<Session> createSession(){
    Connection connection = null;
    Statement statement = null;
    Session sessionToReturn = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "INSERT INTO `Sessions` (`sessionId`, `lastAccess`) VALUES (DEFAULT, NOW());";
      statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      // idSession of created profile
      int sessionId = resultSet.getInt(1);
      sessionToReturn = new SessionImpl(sessionId);
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

  /**
   * Method used to delete a session.
   * @param sessionId identifier of a session.
   * @return null if deletion was successful or an object of type Error.
   */
  public Error deleteSession(int sessionId){
    Connection connection = null;
    Statement statement = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "DELETE FROM `Sessions` WHERE `sessionId` = "+ sessionId +";";
      int numberOfDeletedRows = statement.executeUpdate(query);

      if(numberOfDeletedRows == 0){
        errorToReturn = new ErrorImpl(ErrorConstants.INVALID_SESSION);
      }else{
        // It's ok: errorToReturn = null.
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
  public Error updateLastAccess(int sessionId){
	Connection connection = null;
    Statement statement = null;
    Error errorToReturn = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      statement = connection.createStatement();
      String query = "UPDATE `Sessions` SET `lastAccess` = NOW() WHERE `sessionId` = "+ sessionId +";";
      int numberOfModifiedRows = statement.executeUpdate(query);

      if(numberOfModifiedRows == 0){
        errorToReturn = new ErrorImpl(ErrorConstants.INVALID_SESSION);
      }else{
        // It's ok: errorToReturn = null.
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
}
