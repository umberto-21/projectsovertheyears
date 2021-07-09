import microservices.session.services.SessionServices;
import microservices.session.services.SessionServicesImpl;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;
import org.junit.*;
import org.junit.FixMethodOrder;
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
 *file SessionServicesImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Classe che serve per testare il coretto funzionamento della classe SessionServicesImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessionServicesImplTest {

  private static Application _app;
  //id of the session that was created by createSessionTest() and should be deleted by deleteSessionTest()
  private static Session _session;
  private static Connection _connection = null;

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
  public void T1_createSessionTest(){
    SessionServices sessionServices = new SessionServicesImpl();
    Response<Session> response = sessionServices.createSession();
    assertTrue("Response has an object inside.", response.hasObject());
    assertFalse("Object of type Response is without an object of type Error inside.", response.hasError());
    Session session = response.getObject();
    assertNotNull("Session is not null.", session);
    _session = session;
  }
  
  @Test
  public void T2_updateLastAccessTest(){
    SessionServices sessionServices = new SessionServicesImpl();
    Error error = sessionServices.updateLastAccess(_session);
    assertNull("Error is null, session updated corectly.", error);
  }

  @Test
  public void T3_deleteSessionTest(){
    SessionServices sessionServices = new SessionServicesImpl();
    Error error = sessionServices.deleteSession(_session);
    assertNull("Error is null, session deleted corectly.", error);
    error = sessionServices.deleteSession(_session);
    assertNotNull("There is no error, but it should be.", error);
  }
}