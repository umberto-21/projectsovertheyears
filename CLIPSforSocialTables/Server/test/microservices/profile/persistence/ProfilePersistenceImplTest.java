import microservices.profile.persistence.ProfilePersistence;
import microservices.profile.persistence.ProfilePersistenceImpl;
import microservices.profile.types.Profile;
import microservices.session.persistence.SessionPersistence;
import microservices.session.persistence.SessionPersistenceImpl;
import microservices.session.types.Error;
import microservices.session.types.Response;
import microservices.session.types.Session;
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
 *file ProfilePersistenceImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Classe che serve per testare il coretto funzionamento della classe ProfilePersistenceImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfilePersistenceImplTest {
	
  private static Application _app;
  // identificatore della sessione creata da createSessionTest() e che dovra essere eliminata da deleteSessionTest()
  private static int _sessionId;
  private static String _username = "utenteTestDatabase  1";
  private static String _newUsername = "new utenteTestDatabase  1";
  private static Integer _beaconId = null;
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

    SessionPersistence sessionPersistence = new SessionPersistenceImpl();
    Response<Session> response = sessionPersistence.createSession();
    assertTrue("T1 - Response has an object inside.", response.hasObject());
    assertFalse("T2 - Object of type Response is without an object of type Error inside.", response.hasError());
    Session session = response.getObject();
    assertNotNull("T3 - Session is not null.", session);
    _sessionId = session.getId();
  }


  @AfterClass
  public static void stopApp() {
    SessionPersistence sessionPersistence = new SessionPersistenceImpl();
    Error error = sessionPersistence.deleteSession(_sessionId);
    assertNull("T4 - Error is null, session deleted corectly. # session: "+ _sessionId +".", error);
    error = sessionPersistence.deleteSession(_sessionId);
    assertNotNull("T5 - There is no error, but it should be.", error);
    
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
  public void T1_createProfileTest(){
    String username = _username;
    int sessionId = _sessionId;
    Integer beaconId = _beaconId;

    ProfilePersistence profilePersistence = new ProfilePersistenceImpl();
    microservices.profile.types.Response<Profile> response = profilePersistence.createProfile(username, sessionId, beaconId);

    assertFalse("T5.1", response.hasError());
    assertTrue("T6", response.hasObject());
    Profile profile = response.getObject();
    assertEquals("T7", profile.getUsername(), username);
  }
  
  
  @Test
  public void T2_getProfileTest() {
    String username = _username;
    int sessionId = _sessionId;

    ProfilePersistence profilePersistence = new ProfilePersistenceImpl();
    microservices.profile.types.Response<Profile> response = profilePersistence.getProfile(sessionId);
    assertFalse("T10.1", response.hasError());
    assertTrue("T11", response.hasObject());
    Profile profile = response.getObject();
    assertEquals("T12", profile.getUsername(), username);
  }


  @Test
  public void T3_modifyProfileTest(){
    String newUsername = _newUsername;
    int sessionId = _sessionId;
    Integer beaconId = _beaconId;

    ProfilePersistence profilePersistence = new ProfilePersistenceImpl();
    microservices.profile.types.Error error = profilePersistence.modifyProfile(newUsername, sessionId, null);

    assertNull("T16", error);
  }

  
  @Test
  public void T4_deleteProfile() {
    int sessionId = _sessionId;
    ProfilePersistence profilePersistence = new ProfilePersistenceImpl();
    microservices.profile.types.Error error = profilePersistence.deleteProfile(sessionId);
    assertNull("T15", error);
    }
}