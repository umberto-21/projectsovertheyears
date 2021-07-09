import microservices.session.persistence.SessionPersistence;
import microservices.session.persistence.SessionPersistenceImpl;
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
 *file SessionPersistenceImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Classe che serve per testare il coretto funzionamento della classe SessionPersistenceImplTest.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessionPersistenceImplTest {

  private static Application _app;
  //id of the session that was created by createSessionTest() and should be deleted by deleteSessionTest()
  private static int _sessionId;
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
  public void T1_createSessionTest(){
    SessionPersistence sessionPersistence = new SessionPersistenceImpl();
    Response<Session> response = sessionPersistence.createSession();
    assertTrue("Response has an object inside.", response.hasObject());
    assertFalse("Object of type Response is without an object of type Error inside.", response.hasError());
    Session session = response.getObject();
    assertNotNull("Session is not null.", session);
    _sessionId = session.getId();
  }

  @Test
  public void T2_updateLastAccessTest(){
    SessionPersistence sessionPersistence = new SessionPersistenceImpl();
    Error error = sessionPersistence.updateLastAccess(_sessionId);
    assertNull("Error is null, session updated corectly.", error);
  }

  @Test
  public void T3_deleteSessionTest(){
    SessionPersistence sessionPersistence = new SessionPersistenceImpl();
    Error error = sessionPersistence.deleteSession(_sessionId);
    assertNull("Error is null, session deleted corectly. # session: "+ _sessionId +".", error);
    error = sessionPersistence.deleteSession(_sessionId);
    assertNotNull("There is no error, but it should be.", error);
  }
}