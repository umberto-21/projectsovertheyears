package microservices.battleship.business;

import microservices.battleship.business.BattleshipBusiness;
import microservices.battleship.business.BattleshipBusinessImpl;
import microservices.battleship.types.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import play.Application;
import play.test.Helpers;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by umberto on 6/15/16.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BattleshipBusinessImplDatabaseTest {

    private static Application _app;
    private BattleshipBusiness business =BattleshipBusinessImpl.getInstance();

    @BeforeClass
    public static void startApp() {
        _app = Helpers.fakeApplication();
        Helpers.start(_app);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(_app);
    }

    @Test
    public void T2_getShipsToPositioning () {
        Profile profile =new ProfileImpl("4_TestUsername", 4);
        List<ShipType> profileShip =business.getShipsToPositioning(profile);
        assertNotNull("La lista non è null", profileShip);
    }

    @Test
    public void T3_getAllayField () {
        Profile profile =new ProfileImpl("4_TestUsername", 4);
        Field field =business.getAllayField(profile);
        assertNotNull("L'oggetto Field non è null", field);
    }

    @Test
    public void T4_getEnemyField () {
        Profile profile =new ProfileImpl("4_TestUsername", 4);
        Field field =business.getEnemyField(profile);
        assertNotNull("L'oggetto Field non è null", field);
    }

    @Test
    public void T5_getEnemyTeamScore () {
        Profile profile =new ProfileImpl("4_TestUsername", 4);
        Score score =business.getEnemyTeamScore(profile);
        assertNotNull("L'oggetto Score non è null", score);
    }

    @Test
    public void T6_getShipsToPositioning () {
        Profile profile =new ProfileImpl("4_TestUsername", 4);
        List<ShipType> shipTypeList =business.getShipsToPositioning(profile);
        assertNotNull("L'oggetto List<ShipType> non è null", shipTypeList);
    }


}
