import microservices.battleship.persistence.BattleshipPersistence;
import microservices.battleship.persistence.BattleshipPersistenceImpl;
import microservices.battleship.types.Score;
import java.util.List;
import org.junit.*;
import org.junit.runners.MethodSorters;
import play.*;
import play.test.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import play.db.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *file ScorePersistenceImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 31/05/2016
 *brief Classe che serve per testare il coretto funzionamento della classe ScorePersistenceImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScorePersistenceImplTest {
	
  private static Application _app;
  private static int _gemeId = 1;
  private static int _localId = 1;
  private static Connection _connection;

  @BeforeClass
  public static void startApp() {
    _app = Helpers.fakeApplication();
    Helpers.start(_app);
    _connection = DB.getConnection();
    try{
      Statement statement = _connection.createStatement();
      String queryA = ""//azzera il popolamento del database
        +"CALL PopulateReset;";
      statement.executeUpdate(queryA);
      String queryB = ""//popola il database
        +"CALL PopulateTest1;";
      statement.executeUpdate(queryB);
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  @AfterClass
  public static void stopApp() {
    try{
      Statement statement = _connection.createStatement();
      String queryC = ""//azzera il popolamento del database
        +"CALL PopulateReset;";
      statement.executeUpdate(queryC);
      if(_connection!=null){
        _connection.close();
      }//if
    }catch(SQLException e){
      e.printStackTrace();
    }//catch
    Helpers.stop(_app);
  }

  @Test
  public void T1_addScoreTest(){ 
	int score = 735;
	int teamId = 1;
	String teamName = "Score_Persistence_Test_Team";
    BattleshipPersistence scorePersistence = new BattleshipPersistenceImpl();
    scorePersistence.addScore(_gemeId, score, teamName, _localId);
  }
  
  @Test
  public void T2_getLeaderboard() {
    BattleshipPersistence scorePersistence = new BattleshipPersistenceImpl();
    List<Score> list = scorePersistence.getLeaderboard(_gemeId, _localId);
	assertNotNull("La lista ritornata non è null.", list);
    assertFalse("La lista non è vuota.", list.isEmpty());
  }
}