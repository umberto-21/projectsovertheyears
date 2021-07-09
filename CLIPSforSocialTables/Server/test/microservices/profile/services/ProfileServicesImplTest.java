import microservices.profile.services.ProfileServices;
import microservices.profile.services.ProfileServicesImpl;
import microservices.profile.types.Error;
import microservices.profile.types.Response;
import microservices.profile.types.Session;
import microservices.profile.types.Local;
import microservices.profile.types.LocalImpl;
import org.junit.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import play.*;
import play.test.*;
import java.util.List;
import java.util.Iterator;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import play.db.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *file ProfileServicesImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 30/05/2016
 *brief Classe che serve per testare il coretto funzionamento della classe ProfileServicesImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileServicesImplTest {

  private static Application _app;
  private static Local _local = new LocalImpl(1,"Acquario");
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
  public void T1_getNameListTest(){
    ProfileServices profileServices = ProfileServicesImpl.getInstance();
	_local = new LocalImpl(1,"Acquario");
	List<String> list = profileServices.getNameList(_local);
	assertNotNull("Lista dei nomi non è null.", list);
	Iterator<String> iterator = list.iterator();	
  }
}