import microservices.battleship.persistence.BattleshipPersistence;
import microservices.battleship.persistence.BattleshipPersistenceImpl;
import org.junit.*;
import org.junit.runners.MethodSorters;
import play.*;
import play.test.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import microservices.battleship.types.Team;
import microservices.battleship.types.Profile;
import java.util.List;
import play.db.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *file TeamPersistenceImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che serve per testare il coretto funzionamento della classe TeamPersistenceImpl.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamPersistenceImplTest {
	
  private static Application _app;
  private static Integer _createdTeamId;
  private String _createdTeamName = "createTeamPersistenceTest";
  private String _teamName = "createTeamPersistenceTest";
  private static Integer _leaderProfileId = 5;
  private static Integer _teamId = 2;
  private static Integer _profileId = 5;
  private static Integer _tableId = 2;
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
  public void T1_createTeamTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    Team team = teamPersistence.createTeam(_createdTeamName, _leaderProfileId);
    assertNotNull("Il team ritornato non è nullo.",team);
    _createdTeamId = team.getId();
    assertEquals("Il nome richiesto e il nome creato sono uguali.",team.getNameTeam(),_createdTeamName);
  }
  
  @Test
  public void T2_addMemberTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    boolean ok = teamPersistence.addMember(_createdTeamId, _profileId);
    assertTrue("Il membro è stato aggiunto alla squadra.",ok);
  }
  
  @Test
  public void T3_setTeamLeaderTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    boolean ok = teamPersistence.setTeamLeader(_createdTeamId, _profileId);
    assertTrue("Il membro è diventato capitano.",ok);
    assertTrue("Il ridiventare capitano non causa errori.",ok);
  }
  
  @Test
  public void T4_changeTeamNameTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    boolean ok = teamPersistence.changeTeamName(_createdTeamId, _teamName);
    assertTrue("Il nome della squadra è stato modificato.",ok);
  }
  
  @Test
  public void T5_getTeamTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    Team team = teamPersistence.getTeam(_createdTeamId);
    assertNotNull(team);
    String retTeamName = team.getNameTeam();
    assertEquals("Il nome creato e il nome richiesto sono identici.",retTeamName,_teamName);
    List<Profile> teammates = team.getTeammates();
    assertNotNull("Non è nulla la lista dei membri del team.",teammates);
    Profile captain = team.getCaptain();
    assertNotNull("Il profilo del capitano è stato inizializzato.",captain);
    Integer captainProfileId = captain.getId();
    assertEquals(_leaderProfileId, captainProfileId);
  }
  
  @Test
  public void T6_removeMemberTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    boolean removed = teamPersistence.removeMember(_createdTeamId, _profileId);
    assertTrue("Il profilo è stato rimosso dal team", removed);
    removed = teamPersistence.removeMember(_createdTeamId, _profileId);
    assertFalse("Il profilo non è stato rimosso, in quanto non è associato al team.", removed);
  }

  @Test
  public void T7_deleteTeamTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    teamPersistence.deleteTeam(_createdTeamId);
  }

  @Test
  public void T8_isTeamExistTest(){
    BattleshipPersistence teamPersistence = new BattleshipPersistenceImpl();
    assertTrue("C'è una squadra associata al tavolo #"+_tableId+".",teamPersistence.isTeamExist(_tableId));
  }
 
}