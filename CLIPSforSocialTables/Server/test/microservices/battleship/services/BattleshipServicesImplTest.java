package microservices.battleship.services;

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
 *file BattleshipServicesImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che serve per testare il coretto funzionamento della classe BattleshipServicesImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BattleshipServicesImplTest {
	
  private static Application _app;
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

}
