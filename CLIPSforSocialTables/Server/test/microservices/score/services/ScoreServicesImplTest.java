import microservices.battleship.services.BattleshipServices;
import microservices.battleship.services.BattleshipServicesImpl;
import microservices.battleship.types.Score;
import microservices.battleship.types.ScoreImpl;
import microservices.battleship.types.Game;
import microservices.battleship.types.GameImpl;
import microservices.battleship.types.Local;
import microservices.battleship.types.LocalImpl;
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
 *file ScoreServicesImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 1/06/2016
 *brief Classe che serve per testare il coretto funzionamento della classe ScoreServicesImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScoreServicesImplTest {
	
  private static Application _app;
  //GameImpl(int id, String name)
  Game _game = new GameImpl(1,"Battleship");
  //LocalImpl(int id, String name)
  Local _local = new LocalImpl(1,"Acquario");
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
	//ScoreImpl(int score, String teamName)
	Score score = new ScoreImpl(53,"Score_Services_Test_Team",0);
    BattleshipServices scoreServices = BattleshipServicesImpl.getInstance();
    scoreServices.addScore(_game, score, _local);
  }
  
  @Test
  public void T2_getLeaderboard() {
    BattleshipServices scoreServices = BattleshipServicesImpl.getInstance();
    List<Score> list = scoreServices.getLeaderboard(_game, _local);
	assertNotNull("La lista ritornata non è null.", list);
    assertFalse("La lista non è vuota.", list.isEmpty());
  }
}
