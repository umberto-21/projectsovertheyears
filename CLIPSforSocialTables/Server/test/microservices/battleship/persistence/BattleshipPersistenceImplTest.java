package microservices.battleship.persistence;

import microservices.battleship.persistence.BattleshipPersistence;
import microservices.battleship.persistence.BattleshipPersistenceImpl;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Local;
import microservices.battleship.types.Team;
import microservices.battleship.types.Profile;
import microservices.battleship.types.Ship;
import microservices.battleship.types.Position;
import microservices.battleship.types.Field;
import microservices.battleship.types.Grid;
import microservices.battleship.types.GridImpl;
import microservices.battleship.types.Shot;
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
import java.util.Arrays;

/**
 *file BattleshipPersistenceImplTest.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Classe che serve per testare il coretto funzionamento del layer persistence del gioco Battleship.
 *use Usata dal framework play per automatizzare i test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BattleshipPersistenceImplTest {

  private int _profile1Id = 1;//Team1 captain
  private int _profile2Id = 2;//Team1 /verrà rimosso dal team
  private int _profile3Id = 3;//Team2 captain
  private int _profile4Id = 4;//Team2 
  private int _profile5Id = 5;//Team2 
  private int _profile6Id = 6;//Team3 captain
  private int _profile7Id = 7;//Team4 captain
  private int _profile8Id = 8;//Senza un team 
  private int _profile9Id = 9;//Senza un team 
  private int _notExistingProfileId = 44;//profilo inesistente
  private String _profile1Username = "1_TestUsername";
  private String _profile2Username = "2_TestUsername";
  private String _profile3Username = "3_TestUsername";
  private String _profile4Username = "4_TestUsername";
  private String _profile5Username = "5_TestUsername";
  private String _profile6Username = "6_TestUsername";
  private String _profile7Username = "7_TestUsername";
  private String _profile8Username = "8_TestUsername";
  private String _profile9Username = "9_TestUsername";
  private int _profile1MaxShots = 3;
  private int _profile3MaxShots = 1;
  private int _profile4MaxShots = 1;
  private int _profile5MaxShots = 1;
  private int _profile1ShipsToPosition = 3;
  private int _profile3ShipsToPosition = 1;
  private int _profile4ShipsToPosition = 1;
  private int _profile5ShipsToPosition = 1;
  private int _profile1MaxAvailableShots = 3;
  private int _profile3MaxAvailableShots = 1;
  private int _profile4MaxAvailableShots = 1;
  private int _profile5MaxAvailableShots = 1;
  private int _profile1Shot1x = 0;
  private int _profile1Shot1y = 9;
  private int _profile1Shot2x = 5;
  private int _profile1Shot2y = 3;
  private int _profile1Shot3x = 2;
  private int _profile1Shot3y = 2;
  private int _profile3Shot1x = 1;
  private int _profile3Shot1y = 2;
  private int _profile4Shot1x = 3;
  private int _profile4Shot1y = 4;
  private int _profile5Shot1x = 5;
  private int _profile5Shot1y = 6;

  
  private int _team1Id = 1;//Giocherà contro il team2
  private int _team2Id = 2;//Giocherà contro il team1
  private int _team3Id = 3;//E' ready
  private int _team4Id = 4;
  private int _notExistingTeamId = 43;//team non esistente
  private String _team1Name = "1_TestTeam";
  private String _team2Name = "2_TestTeam";
  private String _team3Name = "3_TestTeam";
  private String _team4Name = "4_TestTeam";

  private int _local1Id = 1;
  private int _notExistinglocalId = 42;//locale non esistente
  private String _local1Name = "1_TestLocal";

  private String _beacon1Uuid = "1b826da6-4fa2-4e98-8024-bc5b71e0893e";
  private int _beacon1Major = 1;
  private int _beacon1Minor = 1;
  private String _beacon5Uuid = "1b826da6-4fa2-4e98-8024-bc5b71e0893e";
  private int _beacon5Major = 2;
  private int _beacon5Minor = 2; 
  
  private int _shipsLength = 3;

  private int _ship1x = 1;//team1 profile1
  private int _ship1y = 1;
  private boolean _ship1vertical = false;
  private int _ship2x = 1;//team1 profile1
  private int _ship2y = 3;
  private boolean _ship2vertical = true;
  private int _ship3x = 4;//team1 profile1
  private int _ship3y = 3;
  private boolean _ship3vertical = true;
  private int _ship4x = 1;//team2 profile3
  private int _ship4y = 0;
  private boolean _ship4vertical = true;
  private int _ship5x = 9;//team2 profile4
  private int _ship5y = 0;
  private boolean _ship5vertical = true;
  private int _ship6x = 4;//team2 profile5
  private int _ship6y = 9;
  private boolean _ship6vertical = false;
  
  private static int _gridHeigth = 10;
  private static int _gridWidth = 10;
  private static String _defaultCellStateString = "UNKNOWN";
  private static String [][] _defaultGridString = new String [_gridHeigth][_gridWidth];
  private static Grid _defaultGrid = new GridImpl(_gridHeigth, _gridWidth);
	
  private static Application _app;
  /*private int _shipLength = 2;
  private int _profileId = 2; //al quale associare le navi. T1; T3; di cui scoprire il locale T4
  private int _maxAvaliabeShots = 3;
  private int _maxAvaliabeShotsUpdate = 2;*/
  private static Connection _connection;
  private BattleshipPersistence _battleshipPersistence = new BattleshipPersistenceImpl();

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
        +"CALL PopulateTest2;";
      statement.executeUpdate(queryB);
    }catch(SQLException e){
      e.printStackTrace();
    }
    for (String[] riga: _defaultGridString){
       Arrays.fill(riga, _defaultCellStateString);
    }
  }

  @AfterClass
  public static void stopApp() {
    /*try{
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
    */
    Helpers.stop(_app);
  }
  
  @Test
  public void T01_getLocalTest(){
    Local local= _battleshipPersistence.getLocal(_profile1Id);
    assertNotNull("Il locale restituito non è null.", local);
    String localName = local.getName();
    assertEquals("Il locale ha l'id guisto.", local.getId(), _local1Id);
    assertEquals("Il locale ha il nome guisto.", local.getName(), _local1Name);
  }
  
  @Test
  public void T02_getTeamFormProfileIdTest(){
    Team team = _battleshipPersistence.getTeamFormProfileId(_profile2Id);
    assertNotNull("Il team restituito non è null.", team);
    assertEquals("Il team ha l'id guisto.", (int)team.getId(), _team1Id);
    assertEquals("Il team ha il nome guisto.", team.getNameTeam(), _team1Name);
    Profile captainProfile = team.getCaptain();
    assertNotNull("Il profilo del capitano restituito non è null.", captainProfile);
    assertEquals("Il capitano ha l'id giusto", (int)captainProfile.getId(), _profile1Id);
    assertEquals("Il capitano ha il nome giusto", captainProfile.getUsername(), _profile1Username);
    List<Profile> teammatesList = team.getTeammates();
    assertNotNull("La lista dei teammates restituita non è null.", teammatesList);
    assertEquals("La lista dei teammates ha esattamente 1 profilo.", teammatesList.size(), 1);
    Profile teammate1Profile = teammatesList.get(0);
    assertNotNull("Il profilo del primo teammate restituito non è null.", teammate1Profile);
    assertEquals("Il primo teammate ha l'id giusto", (int)teammate1Profile.getId(), _profile2Id);
    assertEquals("Il primo teammate ha il nome giusto", teammate1Profile.getUsername(), _profile2Username);
  }
  
  @Test
  public void T03_isTeamExistsTest(){
    boolean exists1 = _battleshipPersistence.isTeamExists(_beacon1Uuid, _beacon1Major, _beacon1Minor);
    assertTrue("Esiste un team associato al beacon 1.", exists1);
    boolean exists2 = _battleshipPersistence.isTeamExists(_beacon5Uuid, _beacon5Major, _beacon5Minor);
    assertFalse("Non esiste un team associato al beacon 5.", exists2);
  }
  
  @Test
  public void T04_removeTeamMemberTest(){
    _battleshipPersistence.removeTeamMember(_team1Id, _profile2Id);
    Team teamNull = _battleshipPersistence.getTeamFormProfileId(_profile2Id);
    assertNull("Il team associato al profilo 2 è null.", teamNull);
    Team team = _battleshipPersistence.getTeamFormProfileId(_profile1Id);
    assertNotNull("Il team restituito non è null.", team);
    assertEquals("Il team ha l'id guisto.", (int)team.getId(), _team1Id);
    assertEquals("Il team ha il nome guisto.", team.getNameTeam(), _team1Name);
    Profile captainProfile = team.getCaptain();
    assertNotNull("Il profilo del capitano restituito non è null.", captainProfile);
    assertEquals("Il capitano ha l'id giusto", (int)captainProfile.getId(), _profile1Id);
    assertEquals("Il capitano ha il nome giusto", captainProfile.getUsername(), _profile1Username);
    List<Profile> teammatesList = team.getTeammates();
    assertNotNull("La lista dei teammates restituita non è null.", teammatesList);
    assertEquals("La lista dei teammates ha esattamente 0 profili.", teammatesList.size(), 0);
  }
  
  @Test
  public void T05_getReadyTeamsTest(){
    List<Team> readyTeamsListA = _battleshipPersistence.getReadyTeams(_notExistinglocalId);
    assertNotNull("La lista dei team pronti (in un locale non esistente) restituita non è null.", readyTeamsListA);
    assertEquals("La lista dei team pronti (in un locale non esistente) ha esattamente 0 elementi.", readyTeamsListA.size(), 0);
    List<Team> readyTeamsListB = _battleshipPersistence.getReadyTeams(_local1Id);
    assertNotNull("La lista dei team pronti restituita non è null.", readyTeamsListB);
    assertEquals("La lista dei team pronti ha esattamente 1 elemento.", readyTeamsListB.size(), 1);
    Team team = readyTeamsListB.get(0);
    assertNotNull("Il team pronto restituito non è null.", team);
    assertEquals("Il team ha l'id guisto.", (int)team.getId(), _team3Id);
    assertEquals("Il team ha il nome guisto.", team.getNameTeam(), _team3Name);
    Profile captainProfile = team.getCaptain();
    assertNotNull("Il profilo del capitano restituito non è null.", captainProfile);
    assertEquals("Il capitano ha l'id giusto", (int)captainProfile.getId(), _profile6Id);
    assertEquals("Il capitano ha il nome giusto", captainProfile.getUsername(), _profile6Username);
    List<Profile> teammatesList = team.getTeammates();
    assertNotNull("La lista dei teammates restituita non è null.", teammatesList);
    assertEquals("La lista dei teammates ha esattamente 0 profili.", teammatesList.size(), 0);
  }
  
  @Test
  public void T06_removeReadyTeamTest(){
    _battleshipPersistence.removeReadyTeam(_team3Id);
    List<Team> readyTeamsList = _battleshipPersistence.getReadyTeams(_local1Id);
    assertNotNull("La lista dei team pronti restituita non è null.", readyTeamsList);
    assertEquals("La lista dei team pronti ha esattamente 0 elementi.", readyTeamsList.size(), 0);
  }
  
  @Test
  public void T07_addReadyTeamTest(){
    _battleshipPersistence.addReadyTeam(_team1Id);
    List<Team> readyTeamsList = _battleshipPersistence.getReadyTeams(_local1Id);
    assertNotNull("La lista dei team pronti restituita non è null.", readyTeamsList);
    assertEquals("La lista dei team pronti ha esattamente 1 elemento.", readyTeamsList.size(), 1);
    Team team = readyTeamsList.get(0);
    assertNotNull("Il team pronto restituito non è null.", team);
    assertEquals("Il team ha l'id guisto.", (int)team.getId(), _team1Id);
    assertEquals("Il team ha il nome guisto.", team.getNameTeam(), _team1Name);
    Profile captainProfile = team.getCaptain();
    assertNotNull("Il profilo del capitano restituito non è null.", captainProfile);
    assertEquals("Il capitano ha l'id giusto", (int)captainProfile.getId(), _profile1Id);
    assertEquals("Il capitano ha il nome giusto", captainProfile.getUsername(), _profile1Username);
    List<Profile> teammatesList = team.getTeammates();
    assertNotNull("La lista dei teammates restituita non è null.", teammatesList);
    assertEquals("La lista dei teammates ha esattamente 0 profili.", teammatesList.size(), 0);
  }
  
  @Test
  public void T08_insertSearchingOpponentTest(){
    _battleshipPersistence.insertSearchingOpponent(_team2Id);
    List<Team> readyTeamsList = _battleshipPersistence.getReadyTeams(_local1Id);
    assertNotNull("La lista dei team pronti restituita non è null.", readyTeamsList);
    assertEquals("La lista dei team pronti ha esattamente 2 elementi.", readyTeamsList.size(), 2);
    Team teamA = readyTeamsList.get(0);
    assertNotNull("Il teamA pronto restituito non è null.", teamA);
    assertEquals("Il teamA ha l'id guisto.", (int)teamA.getId(), _team1Id);
    assertEquals("Il teamA ha il nome guisto.", teamA.getNameTeam(), _team1Name);
    Profile teamACaptainProfile = teamA.getCaptain();
    assertNotNull("Il profilo del capitano (del TeamA) restituito non è null.", teamACaptainProfile);
    assertEquals("Il capitano (del TeamA) ha l'id giusto", (int)teamACaptainProfile.getId(), _profile1Id);
    assertEquals("Il capitano (del TeamA) ha il nome giusto", teamACaptainProfile.getUsername(), _profile1Username);
    List<Profile> teamATeammatesList = teamA.getTeammates();
    assertNotNull("La lista dei teammates (del TeamA) restituita non è null.", teamATeammatesList);
    assertEquals("La lista dei teammates (del TeamA) ha esattamente 0 profili.", teamATeammatesList.size(), 0);
    Team teamB = readyTeamsList.get(1);
    assertNotNull("Il teamB pronto restituito non è null.", teamB);
    assertEquals("Il teamB ha l'id guisto.", (int)teamB.getId(), _team2Id);
    assertEquals("Il teamB ha il nome guisto.", teamB.getNameTeam(), _team2Name);
    Profile teamBCaptainProfile = teamB.getCaptain();
    assertNotNull("Il profilo del capitano (del teamB) restituito non è null.", teamBCaptainProfile);
    assertEquals("Il capitano (del teamB) ha l'id giusto", (int)teamBCaptainProfile.getId(), _profile3Id);
    assertEquals("Il capitano (del teamB) ha il nome giusto", teamBCaptainProfile.getUsername(), _profile3Username);
    List<Profile> teamBTeammatesList = teamB.getTeammates();
    assertNotNull("La lista dei teammates (del teamB) restituita non è null.", teamBTeammatesList);
    assertEquals("La lista dei teammates (del teamB) ha esattamente 2 profili.", teamBTeammatesList.size(), 2);
    Profile teammate1Profile = teamBTeammatesList.get(0);
    assertNotNull("Il profilo del primo teammate (del teamB) restituito non è null.", teammate1Profile);
    assertEquals("Il primo teammate (del teamB) ha l'id giusto", (int)teammate1Profile.getId(), _profile4Id);
    assertEquals("Il primo teammate (del teamB) ha il nome giusto", teammate1Profile.getUsername(), _profile4Username);
    Profile teammate2Profile = teamBTeammatesList.get(1);
    assertNotNull("Il profilo del secondo teammate (del teamB) restituito non è null.", teammate2Profile);
    assertEquals("Il secondo teammate (del teamB) ha l'id giusto", (int)teammate2Profile.getId(), _profile5Id);
    assertEquals("Il secondo teammate (del teamB) ha il nome giusto", teammate2Profile.getUsername(), _profile5Username);
  }
  
  @Test
  public void T09_setOpponentTest(){
    _battleshipPersistence.setOpponent(_team1Id, _team2Id);
    _battleshipPersistence.setAttackTeam(_team1Id);
  }
  
  @Test
  public void T10_isBattleRunningTest(){
    boolean inBattleA = _battleshipPersistence.isBattleRunning(_team1Id);
    assertTrue("Il team 1 è in battaglia.", inBattleA);
    boolean inBattleB = _battleshipPersistence.isBattleRunning(_team2Id);
    assertTrue("Il team 2 è in battaglia.", inBattleB);
    boolean inBattleC = _battleshipPersistence.isBattleRunning(_team3Id);
    assertFalse("Il team 3 non è in battaglia.", inBattleC);
    boolean inBattleD = _battleshipPersistence.isBattleRunning(_notExistingTeamId);
    assertFalse("Un team inesistente non è in battaglia.", inBattleD);
  }
  
  @Test
  public void T11_getOpponentTeamTest(){
    Team teamA = _battleshipPersistence.getOpponentTeam(_team2Id);
    assertNotNull("Il teamA opponente restituito non è null.", teamA);
    assertEquals("Il teamA ha l'id guisto.", (int)teamA.getId(), _team1Id);
    assertEquals("Il teamA ha il nome guisto.", teamA.getNameTeam(), _team1Name);
    Profile teamACaptainProfile = teamA.getCaptain();
    assertNotNull("Il profilo del capitano (del TeamA) restituito non è null.", teamACaptainProfile);
    assertEquals("Il capitano (del TeamA) ha l'id giusto", (int)teamACaptainProfile.getId(), _profile1Id);
    assertEquals("Il capitano (del TeamA) ha il nome giusto", teamACaptainProfile.getUsername(), _profile1Username);
    List<Profile> teamATeammatesList = teamA.getTeammates();
    assertNotNull("La lista dei teammates (del TeamA) restituita non è null.", teamATeammatesList);
    assertEquals("La lista dei teammates (del TeamA) ha esattamente 0 profili.", teamATeammatesList.size(), 0);
    Team teamB = _battleshipPersistence.getOpponentTeam(_team1Id);
    assertNotNull("Il teamB pronto restituito non è null.", teamB);
    assertEquals("Il teamB ha l'id guisto.", (int)teamB.getId(), _team2Id);
    assertEquals("Il teamB ha il nome guisto.", teamB.getNameTeam(), _team2Name);
    Profile teamBCaptainProfile = teamB.getCaptain();
    assertNotNull("Il profilo del capitano (del teamB) restituito non è null.", teamBCaptainProfile);
    assertEquals("Il capitano (del teamB) ha l'id giusto", (int)teamBCaptainProfile.getId(), _profile3Id);
    assertEquals("Il capitano (del teamB) ha il nome giusto", teamBCaptainProfile.getUsername(), _profile3Username);
    List<Profile> teamBTeammatesList = teamB.getTeammates();
    assertNotNull("La lista dei teammates (del teamB) restituita non è null.", teamBTeammatesList);
    assertEquals("La lista dei teammates (del teamB) ha esattamente 2 profili.", teamBTeammatesList.size(), 2);
    Profile teammate1Profile = teamBTeammatesList.get(0);
    assertNotNull("Il profilo del primo teammate (del teamB) restituito non è null.", teammate1Profile);
    assertEquals("Il primo teammate (del teamB) ha l'id giusto", (int)teammate1Profile.getId(), _profile4Id);
    assertEquals("Il primo teammate (del teamB) ha il nome giusto", teammate1Profile.getUsername(), _profile4Username);
    Profile teammate2Profile = teamBTeammatesList.get(1);
    assertNotNull("Il profilo del secondo teammate (del teamB) restituito non è null.", teammate2Profile);
    assertEquals("Il secondo teammate (del teamB) ha l'id giusto", (int)teammate2Profile.getId(), _profile5Id);
    assertEquals("Il secondo teammate (del teamB) ha il nome giusto", teammate2Profile.getUsername(), _profile5Username);
  }
  
  @Test
  public void T12_setShipToPositioningTest(){
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile1Id);
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile1Id);
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile1Id);
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile3Id);
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile4Id);
    _battleshipPersistence.setShipToPositioning(_shipsLength, _profile5Id);
  }

  @Test
  public void T13_checkUserFinishShipPositioningTest(){
    boolean finishA = _battleshipPersistence.checkUserFinishShipPositioning(_profile1Id);
    assertFalse("Profilo 1 ha delle navi non posizionate.", finishA);
    boolean finishB = _battleshipPersistence.checkUserFinishShipPositioning(_profile3Id);
    assertFalse("Profilo 3 ha delle navi non posizionate.", finishB);
    boolean finishC = _battleshipPersistence.checkUserFinishShipPositioning(_profile4Id);
    assertFalse("Profilo 4 ha delle navi non posizionate.", finishC);
    boolean finishD = _battleshipPersistence.checkUserFinishShipPositioning(_profile5Id);
    assertFalse("Profilo 5 ha delle navi non posizionate.", finishD);
    boolean finishE = _battleshipPersistence.checkUserFinishShipPositioning(_profile9Id);
    assertTrue("Un profilo che non ha delle navi da posizionare, ha finito il posizionamento.", finishE);
    boolean finishF = _battleshipPersistence.checkUserFinishShipPositioning(_notExistingProfileId);
    assertTrue("Un profilo inesistente non ha delle navi non posizionate.", finishF);
  }
  
  @Test
  public void T15_getShipsToPositioningTest(){
    List<ShipType> shipsTypeListA = _battleshipPersistence.getShipsToPositioning(_profile1Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 1) non è null.", shipsTypeListA);
    assertEquals("La lista delle navi da posizionare (del profilo 1) ha esattamente 3 elementi.", shipsTypeListA.size(), 3);
    ShipType shipType1A = shipsTypeListA.get(0);
    assertNotNull("La prima nave da posizionare (del profilo 1) non è null.", shipType1A);
    assertEquals("La prima nave da posizionare (del profilo 1) ha la lunghezza giusta.", shipType1A.getLength(), _shipsLength);
    ShipType shipType2A = shipsTypeListA.get(0);
    assertNotNull("La seconda nave da posizionare (del profilo 1) non è null.", shipType2A);
    assertEquals("La seconda nave da posizionare (del profilo 1) ha la lunghezza giusta.", shipType2A.getLength(), _shipsLength);
    ShipType shipType3A = shipsTypeListA.get(0);
    assertNotNull("La terza nave da posizionare (del profilo 1) non è null.", shipType3A);
    assertEquals("La terza nave da posizionare (del profilo 1) ha la lunghezza giusta.", shipType3A.getLength(), _shipsLength);
    List<ShipType> shipsTypeListB = _battleshipPersistence.getShipsToPositioning(_profile3Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 3) non è null.", shipsTypeListB);
    assertEquals("La lista delle navi da posizionare (del profilo 3) ha esattamente 1 elemento.", shipsTypeListB.size(), 1);
    ShipType shipType1B = shipsTypeListB.get(0);
    assertNotNull("La prima nave da posizionare (del profilo 3) non è null.", shipType1B);
    assertEquals("La prima nave da posizionare (del profilo 3) ha la lunghezza giusta.", shipType1B.getLength(), _shipsLength);
    List<ShipType> shipsTypeListC = _battleshipPersistence.getShipsToPositioning(_profile4Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 4) non è null.", shipsTypeListC);
    assertEquals("La lista delle navi da posizionare (del profilo 4) ha esattamente 1 elemento.", shipsTypeListC.size(), 1);
    ShipType shipType1C = shipsTypeListC.get(0);
    assertNotNull("La prima nave da posizionare (del profilo 4) non è null.", shipType1C);
    assertEquals("La prima nave da posizionare (del profilo 4) ha la lunghezza giusta.", shipType1C.getLength(), _shipsLength);
    List<ShipType> shipsTypeListD = _battleshipPersistence.getShipsToPositioning(_profile5Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 5) non è null.", shipsTypeListD);
    assertEquals("La lista delle navi da posizionare (del profilo 5) ha esattamente 1 elemento.", shipsTypeListD.size(), 1);
    ShipType shipType1D = shipsTypeListD.get(0);
    assertNotNull("La prima nave da posizionare (del profilo 5) non è null.", shipType1D);
    assertEquals("La prima nave da posizionare (del profilo 5) ha la lunghezza giusta.", shipType1D.getLength(), _shipsLength);
    List<ShipType> shipsTypeListE = _battleshipPersistence.getShipsToPositioning(_profile9Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 9) non è null.", shipsTypeListE);
    assertEquals("La lista delle navi da posizionare (del profilo 9) ha esattamente 0 elementi.", shipsTypeListE.size(), 0);
    List<ShipType> shipsTypeListF = _battleshipPersistence.getShipsToPositioning(_notExistingProfileId);
    assertNotNull("La lista delle navi da posizionare (di un profilo inesistente) non è null.", shipsTypeListF);
    assertEquals("La lista delle navi da posizionare (di un profilo inesistente) ha esattamente 0 elementi.", shipsTypeListF.size(), 0);
  }
  
  @Test
  public void T16_addUserShipsTest(){
    _battleshipPersistence.addUserShips(_ship1x, _ship1y, _ship1vertical, _profile1Id);
    
    List<ShipType> shipsTypeListA = _battleshipPersistence.getShipsToPositioning(_profile1Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 1) non è null.", shipsTypeListA);
    assertEquals("La lista delle navi da posizionare (del profilo 1) ha esattamente 2 elementi.", shipsTypeListA.size(), 2);
    
    List<Ship> ShipsListA = _battleshipPersistence.getUserShips(_profile1Id);
    assertNotNull("La lista delle navi (del profilo 1) non è null.", ShipsListA);
    assertEquals("La lista delle navi (del profilo 1) ha esattamente 1 elementi.", ShipsListA.size(), 1);
    
    _battleshipPersistence.addUserShips(_ship2x, _ship2y, _ship2vertical, _profile1Id);
    _battleshipPersistence.addUserShips(_ship3x, _ship3y, _ship3vertical, _profile1Id);
    _battleshipPersistence.addUserShips(_ship4x, _ship4y, _ship4vertical, _profile3Id);
    _battleshipPersistence.addUserShips(_ship5x, _ship5y, _ship5vertical, _profile4Id);
    _battleshipPersistence.addUserShips(_ship6x, _ship6y, _ship6vertical, _profile5Id);
    boolean finishA = _battleshipPersistence.checkUserFinishShipPositioning(_profile1Id);
    assertTrue("Profilo 1 ha posizionato tutte le sue navi.", finishA);
    boolean finishB = _battleshipPersistence.checkUserFinishShipPositioning(_profile3Id);
    assertTrue("Profilo 3 ha posizionato tutte le sue navi.", finishB);
    boolean finishC = _battleshipPersistence.checkUserFinishShipPositioning(_profile4Id);
    assertTrue("Profilo 4 ha posizionato tutte le sue navi.", finishC);
    boolean finishD = _battleshipPersistence.checkUserFinishShipPositioning(_profile5Id);
    assertTrue("Profilo 5 posizionato tutte le sue navi.", finishD);
  }
  
  @Test
  public void T17_getUserShipsTest(){
    List<Ship> ShipsListA = _battleshipPersistence.getUserShips(_profile1Id);
    assertNotNull("La lista delle navi (del profilo 1) non è null.", ShipsListA);
    assertEquals("La lista delle navi (del profilo 1) ha esattamente 3 elementi.", ShipsListA.size(), 3);
    Ship ShipA1 = ShipsListA.get(0);
    assertNotNull("La prima nave (del profilo 1) non è null.", ShipA1);
    assertEquals("La prima nave (del profilo 1) ha l'orientamento giusto.", ShipA1.isVertical(), _ship1vertical);
    Position ShipA1Position = ShipA1.getPosition();
    assertNotNull("La posizione della prima nave (del profilo 1) non è null.", ShipA1Position);
    assertEquals("La prima nave (del profilo 1) ha la coordinata x giusta.", ShipA1Position.getX(), _ship1x);
    assertEquals("La prima nave (del profilo 1) ha la coordinata y giusta.", ShipA1Position.getY(), _ship1y);
    Ship ShipA2 = ShipsListA.get(1);
    assertNotNull("La seconda nave (del profilo 1) non è null.", ShipA2);
    assertEquals("La seconda nave (del profilo 1) ha l'orientamento giusto.", ShipA2.isVertical(), _ship2vertical);
    Position ShipA2Position = ShipA2.getPosition();
    assertNotNull("La posizione della seconda nave (del profilo 1) non è null.", ShipA2Position);
    assertEquals("La seconda nave (del profilo 1) ha la coordinata x giusta.", ShipA2Position.getX(), _ship2x);
    assertEquals("La seconda nave (del profilo 1) ha la coordinata y giusta.", ShipA2Position.getY(), _ship2y);
    Ship ShipA3 = ShipsListA.get(2);
    assertNotNull("La terza nave (del profilo 1) non è null.", ShipA3);
    assertEquals("La terza nave (del profilo 1) ha l'orientamento giusto.", ShipA3.isVertical(), _ship3vertical);
    Position ShipA3Position = ShipA3.getPosition();
    assertNotNull("La posizione della terza nave (del profilo 1) non è null.", ShipA3Position);
    assertEquals("La terza nave (del profilo 1) ha la coordinata x giusta.", ShipA3Position.getX(), _ship3x);
    assertEquals("La terza nave (del profilo 1) ha la coordinata y giusta.", ShipA3Position.getY(), _ship3y);
    List<Ship> ShipsListB = _battleshipPersistence.getUserShips(_profile3Id);
    assertNotNull("La lista delle navi (del profilo 3) non è null.", ShipsListB);
    assertEquals("La lista delle navi (del profilo 3) ha esattamente 1 elemento.", ShipsListB.size(), 1);
    Ship ShipB1 = ShipsListB.get(0);
    assertNotNull("La nave (del profilo 3) non è null.", ShipB1);
    assertEquals("La nave (del profilo 3) ha l'orientamento giusto.", ShipB1.isVertical(), _ship4vertical);
    Position ShipB1Position = ShipB1.getPosition();
    assertNotNull("La posizione della nave (del profilo 3) non è null.", ShipB1Position);
    assertEquals("La nave (del profilo 3) ha la coordinata x giusta.", ShipB1Position.getX(), _ship4x);
    assertEquals("La nave (del profilo 3) ha la coordinata y giusta.", ShipB1Position.getY(), _ship4y);
    List<Ship> ShipsListC = _battleshipPersistence.getUserShips(_profile4Id);
    assertNotNull("La lista delle navi (del profilo 4) non è null.", ShipsListC);
    assertEquals("La lista delle navi (del profilo 4) ha esattamente 1 elemento.", ShipsListC.size(), 1);
    Ship ShipC1 = ShipsListC.get(0);
    assertNotNull("La nave (del profilo 4) non è null.", ShipC1);
    assertEquals("La nave (del profilo 4) ha l'orientamento giusto.", ShipC1.isVertical(), _ship5vertical);
    Position ShipC1Position = ShipC1.getPosition();
    assertNotNull("La posizione della nave (del profilo 4) non è null.", ShipC1Position);
    assertEquals("La nave (del profilo 4) ha la coordinata x giusta.", ShipC1Position.getX(), _ship5x);
    assertEquals("La nave (del profilo 4) ha la coordinata y giusta.", ShipC1Position.getY(), _ship5y);
    List<Ship> ShipsListD = _battleshipPersistence.getUserShips(_profile5Id);
    assertNotNull("La lista delle navi (del profilo 5) non è null.", ShipsListD);
    assertEquals("La lista delle navi (del profilo 5) ha esattamente 1 elemento.", ShipsListD.size(), 1);
    Ship ShipD1 = ShipsListD.get(0);
    assertNotNull("La nave (del profilo 5) non è null.", ShipD1);
    assertEquals("La nave (del profilo 5) ha l'orientamento giusto.", ShipD1.isVertical(), _ship6vertical);
    Position ShipD1Position = ShipD1.getPosition();
    assertNotNull("La posizione della nave (del profilo 5) non è null.", ShipD1Position);
    assertEquals("La nave (del profilo 5) ha la coordinata x giusta.", ShipD1Position.getX(), _ship6x);
    assertEquals("La nave (del profilo 5) ha la coordinata y giusta.", ShipD1Position.getY(), _ship6y);
    
    List<Ship> ShipsListE = _battleshipPersistence.getUserShips(_profile9Id);
    assertNotNull("La lista delle navi (di un profilo senza navi) non è null.", ShipsListE);
    assertEquals("La lista delle navi (di un profilo senza navi) ha esattamente 0 elementi.", ShipsListE.size(), 0);
    
    List<Ship> ShipsListF = _battleshipPersistence.getUserShips(_notExistingProfileId);
    assertNotNull("La lista delle navi (di un profilo inesistente) non è null.", ShipsListF);
    assertEquals("La lista delle navi (di un profilo inesistente) ha esattamente 0 elementi.", ShipsListF.size(), 0);
  }
  
  @Test
  public void T18_updateFieldTest(){
    _battleshipPersistence.updateField(_defaultGridString, _team1Id);
    _battleshipPersistence.updateField(_defaultGridString, _team2Id);
  }
  
  @Test
  public void T19_getTeamFieldTest(){
    Field fieldTeamA = _battleshipPersistence.getTeamField(_team1Id);
    assertNotNull("Il Field (del team 1) restituito non è null.", fieldTeamA);
    Grid gridTeamA = fieldTeamA.getShootGrid();
    assertNotNull("La Grid (del team 1) restituita non è null.", gridTeamA);
    assertTrue("La Grid (del team 1) restituita è uguale alla grid richiesta.", gridTeamA.equals(_defaultGrid));
    Field fieldTeamB = _battleshipPersistence.getTeamField(_team2Id);
    assertNotNull("Il Field (del team 2) restituito non è null.", fieldTeamB);
    Grid gridTeamB = fieldTeamB.getShootGrid();
    assertNotNull("La Grid (del team 2) restituita non è null.", gridTeamB);
    assertTrue("La Grid (del team 2) restituita è uguale alla grid richiesta.", gridTeamB.equals(_defaultGrid));
    Field fieldTeamC = _battleshipPersistence.getTeamField(_team3Id);
    assertNull("Il Field (di un team con il field non inizializzato) restituito è null.", fieldTeamC);
    Field fieldTeamD = _battleshipPersistence.getTeamField(_notExistingTeamId);
    assertNull("Il Field (di un team inesistente) restituito è null.", fieldTeamD);
  }
  
  @Test
  public void T20_setMaxAvailableShotsTest(){
    boolean noErrorsA =_battleshipPersistence.setMaxAvailableShots(_profile1MaxAvailableShots, _profile1Id);
    assertTrue("Il massimo numero di colpi a disposizione per il profilo 1 e stato impostato senza errori.", noErrorsA);
    boolean noErrorsB =_battleshipPersistence.setMaxAvailableShots(_profile3MaxAvailableShots, _profile3Id);
    assertTrue("Il massimo numero di colpi a disposizione per il profilo 3 e stato impostato senza errori.", noErrorsB);
    boolean noErrorsC =_battleshipPersistence.setMaxAvailableShots(_profile4MaxAvailableShots, _profile4Id);
    assertTrue("Il massimo numero di colpi a disposizione per il profilo 4 e stato impostato senza errori.", noErrorsC);
    boolean noErrorsD =_battleshipPersistence.setMaxAvailableShots(_profile5MaxAvailableShots, _profile5Id);
    assertTrue("Il massimo numero di colpi a disposizione per il profilo 5 e stato impostato senza errori.", noErrorsD);
  }
  
  @Test
  public void T21_getShotNumberTest(){
    int shotNumberA =_battleshipPersistence.getShotNumber(_profile1Id);
    assertEquals("Il profilo 1 ha il giusto numero di colpi a disposizione.", shotNumberA, _profile1MaxAvailableShots);
    int shotNumberB =_battleshipPersistence.getShotNumber(_profile3Id);
    assertEquals("Il profilo 3 ha il giusto numero di colpi a disposizione.", shotNumberB, _profile3MaxAvailableShots);
    int shotNumberC =_battleshipPersistence.getShotNumber(_profile9Id);
    assertEquals("Un profilo a cui non è stato associato il massimo numero di colpi a disposizione ha 0 colpi a disposizione.", shotNumberC, 0);
    int shotNumberD =_battleshipPersistence.getShotNumber(_notExistingProfileId);
    assertEquals("Un profilo inesistente ha 0 colpi a disposizione.", shotNumberD, 0);
  }
  
  @Test
  public void T22_checkUserFinishShootsTest(){
    boolean finishShootsA =_battleshipPersistence.checkUserFinishShoots(_profile1Id);
    assertFalse("Il profilo 1 ha ancora dei colpi a disposizione.", finishShootsA);
    boolean finishShootsB =_battleshipPersistence.checkUserFinishShoots(_profile3Id);
    assertFalse("Il profilo 3 ha ancora dei colpi a disposizione.", finishShootsB);
    boolean finishShootsC =_battleshipPersistence.checkUserFinishShoots(_profile9Id);
    assertTrue("Un profilo a cui non è stato associato il massimo numero di colpi a disposizione ha finito i colpi a disposizione.", finishShootsC);
    boolean finishShootsD =_battleshipPersistence.checkUserFinishShoots(_notExistingProfileId);
    assertTrue("Un profilo inesistente ha finito i colpi a disposizione.", finishShootsD);
  }
  
  @Test
  public void T23_insertShotTest(){
    _battleshipPersistence.insertShot(_profile1Shot1x, _profile1Shot1y, _profile1Id);
    int shotNumberA =_battleshipPersistence.getShotNumber(_profile1Id);
    assertEquals("Il profilo 1 ha il giusto numero di colpi a disposizione.", shotNumberA, _profile1MaxAvailableShots - 1);
    boolean finishShootsA =_battleshipPersistence.checkUserFinishShoots(_profile1Id);
    assertFalse("Il profilo 1 ha ancora dei colpi a disposizione.", finishShootsA);
    _battleshipPersistence.insertShot(_profile1Shot2x, _profile1Shot2y, _profile1Id);
    int shotNumberB =_battleshipPersistence.getShotNumber(_profile1Id);
    assertEquals("Il profilo 1 ha il giusto numero di colpi a disposizione(1).", shotNumberB, _profile1MaxAvailableShots - 2);
    boolean finishShootsB =_battleshipPersistence.checkUserFinishShoots(_profile1Id);
    assertFalse("Il profilo 1 ha tuttora dei colpi a disposizione.", finishShootsB);
    _battleshipPersistence.insertShot(_profile1Shot3x, _profile1Shot3y, _profile1Id);
    int shotNumberC =_battleshipPersistence.getShotNumber(_profile1Id);
    assertEquals("Il profilo 1 ha il giusto numero di colpi a disposizione(0).", shotNumberC, _profile1MaxAvailableShots - 3);
    boolean finishShootsC =_battleshipPersistence.checkUserFinishShoots(_profile1Id);
    assertTrue("Il profilo 1 ha esaurito i colpi a disposizione.", finishShootsC);
  }
  
  @Test
  public void T24_getShotListTest(){
    List<Shot> ShotListA = _battleshipPersistence.getShotList(_profile1Id);
    assertNotNull("La lista dei colpi (del profilo 1) non è null.", ShotListA);
    assertEquals("La lista dei colpi (del profilo 1) ha esattamente 3 elementi.", ShotListA.size(), 3);
    Position positionA1 = ShotListA.get(0).getPosition();
    assertEquals("La coordinata x del primo colpo (del profilo 1) è giusta.", positionA1.getX(), _profile1Shot1x);
    assertEquals("La coordinata y del primo colpo (del profilo 1) è giusta.", positionA1.getY(), _profile1Shot1y);
    Position positionA2 = ShotListA.get(1).getPosition();
    assertEquals("La coordinata x del secondo colpo (del profilo 1) è giusta.", positionA2.getX(), _profile1Shot2x);
    assertEquals("La coordinata y del secondo colpo (del profilo 1) è giusta.", positionA2.getY(), _profile1Shot2y);
    Position positionA3 = ShotListA.get(2).getPosition();
    assertEquals("La coordinata x del terzo colpo (del profilo 1) è giusta.", positionA3.getX(), _profile1Shot3x);
    assertEquals("La coordinata y del terzo colpo (del profilo 1) è giusta.", positionA3.getY(), _profile1Shot3y);
    List<Shot> ShotListB = _battleshipPersistence.getShotList(_profile9Id);
    assertNotNull("La lista dei colpi (del profilo 9) non è null.", ShotListB);
    assertEquals("La lista dei colpi (del profilo 9) ha esattamente 0 elementi.", ShotListB.size(), 0);
    List<Shot> ShotListC = _battleshipPersistence.getShotList(_notExistingProfileId);
    assertNotNull("La lista dei colpi (di un profilo inesistente) non è null.", ShotListC);
    assertEquals("La lista dei colpi (di un profilo inesistente) ha esattamente 0 elementi.", ShotListC.size(), 0);
  }
  
  @Test
  public void T25_clearTeamShotsTest(){
    _battleshipPersistence.clearTeamShots(_team1Id);
    List<Shot> ShotListA = _battleshipPersistence.getShotList(_profile1Id);
    assertNotNull("La lista dei colpi (del profilo 1) non è null.", ShotListA);
    assertEquals("La lista dei colpi (del profilo 1) ha esattamente 0 elementi.", ShotListA.size(), 0);
    boolean finishShootsA =_battleshipPersistence.checkUserFinishShoots(_profile1Id);
    assertFalse("Il profilo 1 ha finito i colpi.", finishShootsA);
  }
  
  @Test
  public void T26_isAttackPhaseTest(){
    boolean attackA = _battleshipPersistence.isAttackPhase(_team1Id);
    assertTrue("Il team 1 è attaccante.", attackA);
    boolean attackB = _battleshipPersistence.isAttackPhase(_team2Id);
    assertFalse("Il team 2 non è attaccante.", attackB);
    boolean attackC = _battleshipPersistence.isAttackPhase(_notExistingTeamId);
    assertFalse("un team inesistente non è attaccante.", attackC);
  }
  
  @Test
  public void T27_removeAttackTeamTest(){
    _battleshipPersistence.removeAttackTeam(_team1Id);
    boolean attackA = _battleshipPersistence.isAttackPhase(_team1Id);
    assertFalse("Il team 1 non è attaccante.", attackA);
    boolean attackB = _battleshipPersistence.isAttackPhase(_team2Id);
    assertFalse("Il team 2 non è attaccante.", attackB);
  } 
  
  @Test
  public void T28_setAttackTeamTest(){
    _battleshipPersistence.setAttackTeam(_team2Id);
    boolean attackA = _battleshipPersistence.isAttackPhase(_team1Id);
    assertFalse("Il team 1 non è attaccante.", attackA);
    boolean attackB = _battleshipPersistence.isAttackPhase(_team2Id);
    assertTrue("Il team 2 è attaccante.", attackB);
  }
    
  @Test
  public void T29_deleteBattleTest(){
    _battleshipPersistence.deleteBattle(_team2Id);
    boolean inBattleA = _battleshipPersistence.isBattleRunning(_team1Id);
    assertFalse("Il team 1 non è in battaglia.", inBattleA);
    boolean inBattleB = _battleshipPersistence.isBattleRunning(_team2Id);
    assertFalse("Il team 2 non è in battaglia.", inBattleB);
    Team teamA = _battleshipPersistence.getOpponentTeam(_team1Id);
    assertNull("Il team opponente (del team 1) restituito è null.", teamA);
    Team teamB = _battleshipPersistence.getOpponentTeam(_team2Id);
    assertNull("Il team opponente (del team 2) restituito è null.", teamB);
    boolean attackA = _battleshipPersistence.isAttackPhase(_team1Id);
    assertFalse("Il team 1 non è attaccante.", attackA);
    boolean attackB = _battleshipPersistence.isAttackPhase(_team2Id);
    assertFalse("Il team 2 non è attaccante.", attackB);
    
    List<Ship> ShipsListA = _battleshipPersistence.getUserShips(_profile1Id);
    assertNotNull("La lista delle navi (del profilo 1) non è null.", ShipsListA);
    assertEquals("La lista delle navi (del profilo 1) ha esattamente 0 elementi.", ShipsListA.size(), 0);
    List<ShipType> shipsTypeListA = _battleshipPersistence.getShipsToPositioning(_profile1Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 1) non è null.", shipsTypeListA);
    assertEquals("La lista delle navi da posizionare (del profilo 1) ha esattamente 0 elementi.", shipsTypeListA.size(), 0);
    Field fieldTeamA = _battleshipPersistence.getTeamField(_team1Id);
    assertNull("Il Field (del team 1) restituito è null.", fieldTeamA);
    
    List<Ship> ShipsListB = _battleshipPersistence.getUserShips(_profile3Id);
    assertNotNull("La lista delle navi (del profilo 3) non è null.", ShipsListB);
    assertEquals("La lista delle navi (del profilo 3) ha esattamente 0 elementi.", ShipsListB.size(), 0);
    List<ShipType> shipsTypeListB = _battleshipPersistence.getShipsToPositioning(_profile1Id);
    assertNotNull("La lista delle navi da posizionare (del profilo 3) non è null.", shipsTypeListB);
    assertEquals("La lista delle navi da posizionare (del profilo 3) ha esattamente 0 elementi.", shipsTypeListB.size(), 0);
    Field fieldTeamB = _battleshipPersistence.getTeamField(_team2Id);
    assertNull("Il Field (del team 2) restituito è null.", fieldTeamB);
  }
}
