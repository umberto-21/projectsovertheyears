package microservices.battleship.persistence.dao.sql;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import microservices.battleship.persistence.dao.ScoreDao;
import java.util.List;
import java.util.ArrayList;
import microservices.battleship.types.Score;
import microservices.battleship.types.ScoreImpl;

/**
 *file SqlProfileDaoImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 30/05/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad un punteggio mediante SQL.
 */
public class SqlScoreDaoImpl implements ScoreDao {

  @Override
  public void addScore(int gemeId, int score, String teamName, int localId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"INSERT INTO `GameScores`"
        +"(`teamName`, `score`, `gameId`, `localId`)"
        +"VALUES( '"+ teamName +"', "+ score +", "+ gemeId +", "+ localId +");";
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
  public List<Score> getLeaderboard(int gameId, int localId){
    ArrayList<Score> list = new ArrayList<Score>();
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""
        +"SELECT `gameScoreId`, `teamName`, `score` "
        +"FROM `GameScores` "
        +"WHERE `localId` = "+ localId +" "
        +"AND `gameId` = "+ gameId +";";
	ResultSet resultSet = statement.executeQuery(query);
	while(resultSet.next()){
	  list.add(new ScoreImpl(resultSet.getInt("score"), resultSet.getString("teamName"), resultSet.getInt("gameScoreId")));
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
    return list;
  }
  
  @Override
  public void deleteScore(int scoreId){
    Connection connection = null;
    try{
      connection = SqlDaoFactoryImpl.createConnection();
      Statement statement = connection.createStatement();
      String query = ""//elimina un punteggio
        +"DELETE FROM `GameScores` "
        +"WHERE `gameScoreId` = "+ scoreId +" ;";
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
  
}
