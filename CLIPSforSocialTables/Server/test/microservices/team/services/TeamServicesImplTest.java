import org.junit.*;
import org.junit.runners.MethodSorters;
import play.*;
import play.test.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import microservices.battleship.services.BattleshipServices;
import microservices.battleship.services.BattleshipServicesImpl;
import microservices.battleship.types.Team;
import microservices.battleship.types.TeamImpl;
import microservices.battleship.types.Profile;
import microservices.battleship.types.ProfileImpl;
import microservices.battleship.types.Table;
import microservices.battleship.types.TableImpl;
import java.util.List;
import java.util.ArrayList;
import play.db.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *file TeamServicesImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che serve per testare il coretto funzionamento delle funzionalità riguardanti un team.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamServicesImplTest {
	
  private static Application _app;
  private static String _captainName = "7_TestUsername";
  private static Integer _captainId = 7; 
  private static String _teamName = "TeamServicesTestTeam";
  private static String _updatedTeamName = "TeamServicesTestTeamNew";
  private static String _memberName = "8_TestUsername";
  private static Integer _memberId = 8;
  private static String _tableName = "4_TestTable";
  private static Integer _tableId = 4;
  private static Profile _teammate= new ProfileImpl(_memberName, _memberId);
  
  private static Profile _captain = new ProfileImpl(_captainName, _captainId);
  private static ArrayList<Profile> _teammates = new ArrayList<Profile>(){{
    add(_teammate);
  }};
  private static Team _createTeam = new TeamImpl(_teamName, _captain, _teammates);
  private static Integer _createdTeamId;
  private static Team _createdTeam;
  private static Table _table = new TableImpl(_tableId, _tableName);
  private static ArrayList<Profile> _newTeammates = new ArrayList<Profile>(){{
    add(_captain);
  }};
  
  
  private static Team _updateTeam = new TeamImpl(_updatedTeamName, _teammate, _newTeammates);
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
  public void T1_createTeamTest() {
    BattleshipServices teamServices = BattleshipServicesImpl.getInstance();
    _createdTeam = teamServices.createTeam(_createTeam);
    assertNotNull("Il team restituito non è nullo.",_createdTeam);
    Integer _createdTeamId = _createdTeam.getId();
    assertNotNull("l'identificatore del team creato non è null", _createdTeamId);
    String teamName = _createdTeam.getNameTeam();
    assertEquals("Il nome del team creato è giusto.",teamName, _teamName);
    Profile captain = _createdTeam.getCaptain();
    assertNotNull("Il profilo del capitano non è nullo.",_createdTeam);
    String captainName = captain.getUsername();
    assertEquals("Il nome del capitano è giusto.",captainName, _captainName);
    Integer captainId = captain.getId();
    assertEquals("L'id del capitano è giusto.",captainId, _captainId);
    List<Profile> teammates = _createdTeam.getTeammates();
    assertNotNull("la lista dei membri non è nulla.",teammates);
    assertEquals("La lista con richiesta ha 1 membro.",_teammates.size(),1);
    assertEquals("C'è solo un membro oltre al capitano.",teammates.size(),1);
    Profile member = teammates.get(0);
    assertNotNull("Il profilo del membro non è null.", member);
    String memberName = member.getUsername();
    assertEquals("il nome del membro è giusto.", memberName,_memberName);
    Integer memberId = member.getId();
    assertEquals("L'id del membro è giusto.",memberId, _memberId);
  }
  
  @Test
  public void T2_isTeamExistTest() {
    BattleshipServices teamServices = BattleshipServicesImpl.getInstance();
    boolean exist = teamServices.isTeamExist(_table);
    assertTrue("Il tavolo relativo al team creato è occupato.",exist);
  }
  
  @Test
  public void T3_updateTeamTest() {
    BattleshipServices teamServices = BattleshipServicesImpl.getInstance();
    boolean noErrors = teamServices.updateTeam(_createdTeam, _updateTeam);
    assertTrue("Aggiornamento del profilo avvenuto senza errori.",noErrors);
  }
  
  @Test
  public void T4_deleteTeamTest() {
    BattleshipServices teamServices = BattleshipServicesImpl.getInstance();
    teamServices.deleteTeam(_createdTeam);
  }
  
  @Test
  public void T5_isTeamExistTest() {
    BattleshipServices teamServices = BattleshipServicesImpl.getInstance();
    boolean exist = teamServices.isTeamExist(_table);
    assertFalse("Il tavolo relativo al team creato è libero.",exist);
  }
}
