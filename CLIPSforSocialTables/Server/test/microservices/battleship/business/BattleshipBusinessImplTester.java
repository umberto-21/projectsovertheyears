package microservices.battleship.business;

import com.google.gson.Gson;
import microservices.battleship.services.BattleshipServices;
import microservices.battleship.types.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


/**
 * Created by umberto on 6/18/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BattleshipBusinessImplTester {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @InjectMocks
    BattleshipBusinessImpl business =BattleshipBusinessImpl.getInstance();

    @Mock
    BattleshipServices services;


    @Test
    public void T1_chekStartBattle () {

        business.set_services(services);

        //The two team have finished the ship positioning phase
        Profile captainOne =new ProfileImpl("CaptainOne", 1);
        Profile captainTwo =new ProfileImpl("CaptainTwo", 2);
        List<Profile> teamMatesOne =new LinkedList<>();
        List<Profile> teamMatesTwo =new LinkedList<>();
        teamMatesOne.add(captainOne); teamMatesTwo.add(captainTwo);
        Team teamOne =new TeamImpl ("TeamOne", captainOne, teamMatesOne);
        Team teamTwo =new TeamImpl ("TeamTwo", captainTwo, teamMatesTwo);

        when (services.checkFinishShipPositioning(teamOne)).thenReturn(true);
        when (services.checkFinishShipPositioning(teamTwo)).thenReturn(true);

        boolean result =business.checkStartBattle(teamOne, teamTwo);
        Assert.assertEquals("T1_chekStartBattle", result, true);
    }

    @Test
    public void T2_createTeam_TheTableIsNull () {

        business.set_services(services);

        String teamName ="TestTeam";
        Profile profile =new ProfileImpl("TestProfile", 0);
        Beacon beacon =new BeaconImpl("TestUUID",0,0);

        when (services.getTable(beacon)).thenReturn(null);

        //The table is null --> the method should return false;

        boolean result =business.createTeam(teamName, profile, beacon);
        Assert.assertEquals("T2_createTeam_TheTableIsNull", result, false);
    }

    @Test
    public void T3_createTeam_TheNameAlreadyExists () {

        business.set_services(services);

        String teamName ="TestTeam";
        Profile profile =new ProfileImpl("TestProfile", 0);
        Beacon beacon =new BeaconImpl("TestUUID",0,0);

        Table table =new TableImpl("TestTable");

        when (services.getTable(beacon)).thenReturn(table);
        when (services.isTeamExist(table)).thenReturn(false);

        Local local =new LocalImpl("TestLocal");

        when (services.getLocal (profile)).thenReturn(local);

        List<String> testTeamName =new LinkedList<>();
        testTeamName.add("TestTeam_0");
        testTeamName.add("TestTeam_1");
        testTeamName.add("TestTeam");

        when (services.getTeamNameList (local)).thenReturn(testTeamName);

        //The teamName is already in the list so the method must return false;

        boolean result =business.createTeam(teamName, profile, beacon);
        Assert.assertEquals ("T3_createTeam_TheNameAlreadyExists", result, false );
    }

    @Test
    public void T4_searchingOpponentPhase_TheTeamIsInTheList () {

        business.set_services(services);

        Profile profile =new ProfileImpl("ProfileTest");
        Local local =new LocalImpl("LocalTest");

        when (services.getLocal(profile)).thenReturn(local);

        List<Team> testTeamList =new LinkedList<>();
        testTeamList.add(new TeamImpl("TeamTest_0", 0, null, null));
        testTeamList.add(new TeamImpl("TeamTest_1", 1, null, null));
        testTeamList.add(new TeamImpl("TeamTest_2", 2, null, null));

        when (services.getReadyTeams(local)).thenReturn(testTeamList);

        Team team =new TeamImpl("TeamTest_1", 1, null, null);

        when (services.getTeam (profile)).thenReturn(team);

        boolean result =business.searchingOpponentPhase(profile);
        Assert.assertEquals("T4_searchingOpponentPhase_TheTeamIsTheList", result, true);
    }

    @Test
    public void T5_joinTeam_TheTableIsNull () {

        business.set_services(services);

        Profile profile =new ProfileImpl("testProfile");
        Beacon beacon =new BeaconImpl("Test_UUID", 0, 0);

        when (services.getTable(beacon)).thenReturn(null);

        Assert.assertEquals("T5_joinTeam_TheTableIsNull", business.joinTeam(profile,beacon), false);

    }

    @Test
    public void T6_joinTeam_TheTeamIsFull () {

        business.set_services(services);

        List<Profile> testTeamMates =new LinkedList<>();
        testTeamMates.add(new ProfileImpl("testProfile_1"));
        testTeamMates.add(new ProfileImpl("testProfile_2"));
        testTeamMates.add(new ProfileImpl("testProfile_3"));
        testTeamMates.add(new ProfileImpl("testProfile_4"));
        testTeamMates.add(new ProfileImpl("testProfile_5"));
        testTeamMates.add(new ProfileImpl("testProfile_6"));

        Team team =new TeamImpl("testTeam", new ProfileImpl ("testProfile_1"), testTeamMates);

        when (services.getTeam(new TableImpl("testTable"))).thenReturn(team);

        Assert.assertEquals ("T6_joinTeam_TheTeamIsFull", business.joinTeam(new ProfileImpl ("testProfile_1"), new BeaconImpl ("test_UUID",0,0)),
                false);

    }

    @Test
    public void T7_setShipPosition_TheShipIsInCollision () {

        business.set_services(services);

        Position positionTest =new PositionImpl(5,4);
        Ship shipTest =new ShipImpl(true, 3, positionTest);
        Profile profileTest =new ProfileImpl("UsernameTest");
        Team teamTest =new TeamImpl("NameTeamTest", null, null);

        when (services.getTeam(profileTest)).thenReturn (teamTest);

        when (services.checkUserFinishShipPositioning(profileTest)).thenReturn(false);

        List<Ship> shipListTest =new LinkedList<>();
        shipListTest.add (new ShipImpl (true, 3, new PositionImpl(5,1)));
        shipListTest.add (new ShipImpl (false, 3, new PositionImpl(9,3)));
        shipListTest.add (new ShipImpl (true, 3, new PositionImpl(5,4)));

        when (services.getShips(teamTest)).thenReturn(shipListTest);

        boolean result =business.setShipPosition(shipTest, profileTest);
        Assert.assertFalse ("T7_setShipPosition_TheShipIsInCollision", result);
    }

    @Test
    public void T8_setShipPosition_TheShipIsOutOfBorder () {
        business.set_services(services);

        Position positionTest =new PositionImpl(11,3);
        Ship shipTest =new ShipImpl(true, 3, positionTest);
        Profile profileTest =new ProfileImpl("UsernameTest");
        Team teamTest =new TeamImpl("NameTeamTest", null, null);

        when (services.getTeam(profileTest)).thenReturn (teamTest);

        when (services.checkUserFinishShipPositioning(profileTest)).thenReturn(false);

        List<Ship> shipListTest =new LinkedList<>();
        shipListTest.add (new ShipImpl (true, 3, new PositionImpl(5,1)));
        shipListTest.add (new ShipImpl (false, 3, new PositionImpl(9,3)));
        shipListTest.add (new ShipImpl (true, 3, new PositionImpl(5,4)));

        boolean result =business.setShipPosition(shipTest, profileTest);
        Assert.assertFalse ("T8_setShipPosition_TheShipIsOutOfBorder", result);
    }

    @Test
    public void T9_collision_TheShipsAreInCollision () {
        Ship shipOneTest =new ShipImpl(false, 3, new PositionImpl(3,3));
        Ship shipTwoTest =new ShipImpl(true, 3, new PositionImpl(5,3));

        boolean result =business.collision(shipOneTest, shipTwoTest);

        Assert.assertTrue("T9_collision_TheShipsAreInCollision", result);
    }

    @Test
    public void T10_removeTeamMember_TheMethodDoWhatExpected () {
        business.set_services(services);


        List<Profile> profileListTest =new LinkedList<>();

        Profile profileTest_1 =new ProfileImpl ("ProfileTest_1", 1);
        Profile profileTest_2 =new ProfileImpl ("ProfileTest_2", 2);

        profileListTest.add(profileTest_1); profileListTest.add(profileTest_2);

        Team teamTest =new TeamImpl ("TeamTest", profileTest_1, profileListTest);

        System.out.println (new Gson().toJson(teamTest));

        when (services.getTeam(profileTest_2)).thenReturn(teamTest);

        business.removeTeamMember(profileTest_1, profileTest_2);
        System.out.println (new Gson().toJson(teamTest));


    }

    @Test
    public void T11_startSearchingOpponent_TheTeamIsTheOnlyOneInTheList () {
        business.set_services(services);

        Profile profileTest =new ProfileImpl ("profileTest", 1);
        Local localTest =new LocalImpl ("localTest");
        when(services.getLocal (profileTest)).thenReturn(localTest);

        List<Profile> teamMatesTestList =new LinkedList<>();
        Team teamTest =new TeamImpl ("teamTest", 1, profileTest, teamMatesTestList);
        when(services.getTeam(profileTest)).thenReturn(teamTest);

        List<Team> readyTeamTestList =new LinkedList<>();
        readyTeamTestList.add(teamTest);
        when (services.getReadyTeams(localTest)).thenReturn(readyTeamTestList);

        business.startSearchingOpponent(profileTest);

        Assert.assertEquals ("NotPass", "TheTeamIsTheOnlyOneInTheList", outContent.toString());

    }

    @Test
    public void T12_startSearchingOpponent_TheTeamIsNotInTheListAndTheListHasOneTeam () {
        business.set_services(services);

        Profile profileTest =new ProfileImpl ("profileTest", 1);
        Local localTest =new LocalImpl ("localTest");
        when(services.getLocal (profileTest)).thenReturn(localTest);

        List<Profile> teamMatesTestList =new LinkedList<>();
        Team teamTest =new TeamImpl ("teamTest", 1, profileTest, teamMatesTestList);
        when(services.getTeam(profileTest)).thenReturn(teamTest);

        List<Profile> teamMatesTestList_2 =new LinkedList<>();
        Team teamTest_2 =new TeamImpl("teamTest_2", 2, new ProfileImpl("teamCaptain_2"), teamMatesTestList_2);
        List<Team> readyTeamTestList =new LinkedList<>();
        readyTeamTestList.add(teamTest_2);
        when (services.getReadyTeams(localTest)).thenReturn(readyTeamTestList);

        business.startSearchingOpponent(profileTest);

        Assert.assertEquals ("NotPass", "TheTeamIsNotInTheListAndTheListHasOneTeam", outContent.toString());
    }


    @Test
    public void T13_startSearchingOpponent_TheTeamIsInTheListAndTheListHasMoreThanOneTeam () {
        business.set_services(services);

        Profile profileTest =new ProfileImpl ("profileTest", 1);
        Local localTest =new LocalImpl ("localTest");
        when(services.getLocal (profileTest)).thenReturn(localTest);

        List<Profile> teamMatesTestList =new LinkedList<>();
        Team teamTest =new TeamImpl ("teamTest", 1, profileTest, teamMatesTestList);
        when(services.getTeam(profileTest)).thenReturn(teamTest);

        List<Profile> teamMatesTestList_2 =new LinkedList<>();
        Team teamTest_2 =new TeamImpl("teamTest_2", 2, new ProfileImpl("teamCaptain_2"), teamMatesTestList_2);
        List<Team> readyTeamTestList =new LinkedList<>();
        readyTeamTestList.add(teamTest_2);
        readyTeamTestList.add(teamTest);
        when (services.getReadyTeams(localTest)).thenReturn(readyTeamTestList);

        business.startSearchingOpponent(profileTest);

        Assert.assertEquals ("NotPass", "0", outContent.toString());

    }


}
