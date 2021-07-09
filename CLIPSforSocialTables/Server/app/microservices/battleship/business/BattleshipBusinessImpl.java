package microservices.battleship.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import microservices.battleship.services.BattleshipServices;
import microservices.battleship.services.BattleshipServicesImpl;
import microservices.battleship.types.*;
import microservices.battleship.types.Profile;
import microservices.battleship.types.Score;
import microservices.battleship.types.Team;

/**
 * file : BattleshipBusinessImpl.java
 * author : Andria Umberto
 * date : 25/05/2016
 * brief : Classe che gestisce la logica di funzionamento del servizio e risponde agli input e alle richieste
 * che arrivano dall'esterno per quanto riguarda il gioco Battleship.
 * use : Implementa i metodi offerti dall'interfaccia BattleshipBusiness,
 * utilizzati per gestire la logica del gioco Battleship.
 */
public class BattleshipBusinessImpl implements BattleshipBusiness {

    private static final String TAG = "BattleshipBusinessImpl";
    private static final int _numeroNavi = 6;


    private static BattleshipBusinessImpl singleton;
    private static BattleshipServices _services =BattleshipServicesImpl.getInstance();

    //to test purpose
    public static void set_services(BattleshipServices _services) {
        BattleshipBusinessImpl._services = _services;
    }

    //to test purpose
    public MyGame get_MyGameInstance (Team teamOne, Team teamTwo, Local local, Game game) {
        return new MyGame (teamOne, teamTwo, local, game);
    }

    //public to test purpose
    public class MyGame implements Runnable {

        private Team _teamOne;
        private Team _teamTwo;
        private Local _local;
        private Game _game;

        public MyGame (Team teamOne, Team teamTwo, Local local, Game game) {
            _teamOne =teamOne;
            _teamTwo =teamTwo;
            _local =local;
            _game =game;
        }

        private void setRandomShips (List<Profile> teamMates) {

            Iterator<Profile> iterator =teamMates.iterator();
            while (iterator.hasNext()) {
                setRandomShipsPositions(iterator.next());
            }
        }

        private void setRandomShots (List<Profile> teamMates) {

            Iterator<Profile> iterator =teamMates.iterator();
            while (iterator.hasNext()) {
                setRandomShot(iterator.next());
            }
        }

        @Override
        public void run () {

            setMaxAvailableShot(_teamOne);
            setMaxAvailableShot(_teamTwo);
          //play.Logger.info(TAG + ".Posizionamento navi "  + (new Gson()).toJson(_teamOne) + " VS "  + (new Gson()).toJson(_teamTwo));

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {e.printStackTrace();}


            //Posiziona le navi restanti delle due squadre
            setRandomShipsPositions(_teamOne.getCaptain());
            setRandomShipsPositions(_teamTwo.getCaptain());
            setRandomShips(_teamOne.getTeammates());
            setRandomShips(_teamTwo.getTeammates());

            //Assegna un team come attaccante
            if (_services.isTeamExists (_teamOne)) {
                _services.setAttackTeam(_teamOne);
            }

            //Controlla se la battaglia è finita
            while ( _services.isTeamExists(_teamOne) &&
                    _services.isTeamExists(_teamTwo) &&
                    !allShipSunk(_teamOne) &&
                    !allShipSunk(_teamTwo)) {
                //30 secondi

              //play.Logger.info(TAG + ".Attacca il team " + (new Gson()).toJson(_teamOne));
                try {
                    Thread.sleep(25000);
                } catch (InterruptedException e) {e.printStackTrace();}
                //The two teams exist

                if (!_services.isTeamExists(_teamOne) || !_services.isTeamExists(_teamTwo)) break;

                _services.removeAttackTeam(_teamOne);
                setRandomShot(_teamOne.getCaptain());
                setRandomShots(_teamOne.getTeammates());
                if (allShipSunk(_teamOne) || allShipSunk(_teamTwo)) break;
                //The two team exist and nobody has win the battle

                _services.clearTeamShots(_teamTwo);
                _services.clearTeamShots(_teamOne);
                _services.setAttackTeam(_teamTwo);

              //play.Logger.info(TAG + ".Attacca il team " + (new Gson()).toJson(_teamTwo));
                try {
                    Thread.sleep(25000);
                } catch (InterruptedException e) {e.printStackTrace();}

                if (!_services.isTeamExists(_teamOne) || !_services.isTeamExists(_teamTwo)) break;

                _services.removeAttackTeam(_teamTwo);
                setRandomShot(_teamTwo.getCaptain());
                setRandomShots(_teamTwo.getTeammates());
                if (allShipSunk(_teamOne) || allShipSunk(_teamTwo)) break;
                //The two team exist and nobody has win the battle

                _services.clearTeamShots(_teamTwo);
                _services.clearTeamShots(_teamOne);
                _services.setAttackTeam(_teamOne);
            }

          //play.Logger.info(TAG + ".Fine partita "  + (new Gson()).toJson(_teamOne) + " VS "  + (new Gson()).toJson(_teamTwo));
            //Se è finita calcola il punteggio e lo memorizza

            if (!_services.isTeamExists(_teamOne)) {
                Score teamTwoScore =new ScoreImpl (60, _teamTwo.getNameTeam());
                _services.addScore(_game, teamTwoScore, _local);
            }
            else if (!_services.isTeamExists(_teamTwo)) {
                Score teamOneScore =new ScoreImpl (60, _teamOne.getNameTeam());
                _services.addScore(_game, teamOneScore, _local);
            }
            else {
                Score teamOneScore =getTeamFinalScore(_teamOne);
                Score teamTwoScore =getTeamFinalScore(_teamTwo);
                _services.addScore(_game, teamOneScore, _local);
                _services.addScore(_game, teamTwoScore, _local);
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {e.printStackTrace();}
                _services.deleteBattle(_teamOne);
                _services.deleteBattle(_teamOne);



        }
    }

    private BattleshipBusinessImpl () {

    }

    public static BattleshipBusinessImpl getInstance () {
    //play.Logger.info(TAG + ".getInstance()");

        if (singleton == null) singleton =new BattleshipBusinessImpl();

    //play.Logger.info(TAG + ".getInstance() return: " + singleton);
        return singleton;
    }


    @Override
    public boolean checkStartBattle(Team teamOne, Team teamTwo) {
        String methodSignature = ".checkStartBattle(" + (new Gson()).toJson(teamOne) + "," +
                (new Gson()).toJson(teamTwo) + ")";
        if(teamOne == null || teamTwo == null) {
         //play.Logger.info(TAG + methodSignature + "return false (team null)");
            return false;
        }
        boolean resultTeamOne =_services.checkFinishShipPositioning(teamOne);
        boolean resultTeamTwo =_services.checkFinishShipPositioning(teamTwo);
       //play.Logger.info(TAG + methodSignature + " return : " + (new Gson()).toJson(resultTeamOne && resultTeamTwo));
        return resultTeamOne && resultTeamTwo;
    }

    @Override
    public boolean createTeam(String teamName, Profile profile, Beacon beacon) {
        String methodSignature = ".createTeam(" + (new Gson()).toJson(teamName) + "," +
                (new Gson()).toJson(profile) + "," + (new Gson()).toJson(beacon) + ")";
        //play.Logger.info(TAG + methodSignature);

        Table table =_services.getTable (beacon);
        boolean result =_services.isTeamExist (table);
        if (result == true) {
        //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(!result));
            return false;
        }

        Local local =_services.getLocal(profile);
        List<String> teamsName = _services.getTeamNameList (local);
        Iterator<String> itr = teamsName.iterator();
        boolean isUnique =true;
        while (itr.hasNext() && isUnique) {
            if (itr.next() == teamName) isUnique =false;
        }

        if (isUnique == false) {
        //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(isUnique));
            return isUnique;
        }

        List<Profile> teamMatesList =new LinkedList<>();
        microservices.battleship.types.Team team =new microservices.battleship.types.TeamImpl(teamName, profile, teamMatesList);
        //play.Logger.info (TAG +methodSignature + new Gson().toJson(team)); // TODO: 6/30/16
        _services.createTeam (team);
        //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(true));
        return true;
    }

    @Override
    public Field getAllayField(microservices.battleship.types.Profile profile) {
        String methodSignature =".getAlleyField (" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        Team team =_services.getTeam(profile);
        Field field =_services.getTeamField(team);

    //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(field));
        return field;
    }

    @Override
    public Field getEnemyField(Profile profile) {
        String methodSignature =".getEnemyField (" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        Team team =_services.getTeam(profile);
        Team opponentTeam =_services.getOpponentTeam(team);
        Field field =_services.getTeamField(opponentTeam);

    //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(field));
        return field;
    }

    private int getScore (List<Ship> shipList, Grid grid) {
        Iterator<Ship> itr =shipList.iterator ();
        int score =0;
        while (itr.hasNext ()) {
            Ship ship =itr.next ();
            if (!ship.isSink (grid)) score = score + 10;
        }
        return score;
    }

    @Override
    public List<ShipType> getShipsToPositioning(Profile profile) {
        String methodSignature = ".getShipsToPositioning (" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        if(isStartAttackPhase(profile) || isStartEnemyAttackPhase(profile)) return new ArrayList<ShipType>(0);

        List<ShipType> shipTypeList = _services.getShipsToPositioning (profile);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(shipTypeList));
        return shipTypeList;
    }

    @Override
    public int getShotNumber(microservices.battleship.types.Profile profile) {
        String methodSignature = ".getShotNumber(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        if (! isStartAttackPhase(profile)) {
            //play.Logger.info (TAG + methodSignature + "return " + new Gson().toJson(0));
            return 0;
        }
        else {
            int shotNumber =_services.getShotNumber(profile);
            //play.Logger.info (TAG + methodSignature + "return: " + (new Gson()).toJson(shotNumber));

            return shotNumber;
        }

    }

    @Override
    public microservices.battleship.types.Team getTeam(Profile profile) {
        String methodSignature = ".getTeam(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        microservices.battleship.types.Team team =_services.getTeam(profile);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(team));
        return team;
    }

    @Override
    public Score getTeamFinalScore(Team team) {
        String methodSignature =".getTeamScore (" + (new Gson()).toJson(team) + ")";
    play.Logger.info(TAG + methodSignature);

        if (allShipSunk(team)) return new ScoreImpl(0, team.getNameTeam());
        int score = 60;

        List<Ship> shipList =_services.getShips(team);
        Grid grid =_services.getTeamField(team).getShootGrid();
        score = score + getScore(shipList, grid);

        Score finalScore =new ScoreImpl(score, team.getNameTeam());
    play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(finalScore));
        return finalScore;
    }

    @Override
    public List<Ship> getTeamShips(microservices.battleship.types.Profile profile) {
        String methodSignature = ".getTeamShips(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        microservices.battleship.types.Team team =_services.getTeam(profile);
        List<Ship> shipList =_services.getShips(team);

    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(shipList));
        return shipList;
    }

    @Override
    public boolean isBattleEnd(Profile profile) {
        String methodSignature = ".isBattleEnd()";
    //play.Logger.info(TAG + methodSignature);

        Team team = _services.getTeam (profile);
        Team opponentTeam = _services.getOpponentTeam(team);
        boolean isRunning =_services.isBattleRunning(team);
        if(!isRunning) return true;
        boolean isWin = allShipSunk(team);
        boolean isLoose = allShipSunk(opponentTeam);
        boolean result = isWin || isLoose;

    //play.Logger.info (TAG + methodSignature + "return: " + (new Gson()).toJson(!result));
        return result;
    }

    @Override
    public boolean isBattleRunning(Beacon beacon) {
        String methodSignature = ".isBattleRunning(" + (new Gson()).toJson(beacon) + ")";
    //play.Logger.info(TAG + methodSignature);
        Table table = _services.getTable (beacon);
        microservices.battleship.types.Team team = _services.getTeam (table);
        boolean result =_services.isBattleRunning(team);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean isBattleStart(Profile profile) {
        String methodSignature = ".isBattleStart(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        microservices.battleship.types.Team team =_services.getTeam(profile);
        boolean result =_services.isBattleRunning(team);

    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean allShipSunk(Team team) {
        String methodSignature = ".isBattleWin(" + (new Gson()).toJson(team) + ")";
      play.Logger.info(TAG + methodSignature);

        boolean sink =true;

        Grid grid =_services.getTeamField(team).getShootGrid();
        List<Ship> shipList =_services.getShips (team);
        Iterator<Ship> itr =shipList.iterator ();

        while (itr.hasNext () && sink) {
            Ship s =itr.next ();
            sink =s.isSink (grid);
        }
      play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(!sink));
        return sink;
    }

    @Override
    public boolean isCaptain(Profile profile) {
        if(profile == null){
            return false;
        }
        String methodSignature = ".isCaptain(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        microservices.battleship.types.Team team =_services.getTeam(profile);
        if (team == null) {
        //play.Logger.info(TAG + methodSignature + " return: false" );
            return false;
        }
        Profile captain =team.getCaptain();
        boolean result = profile.getId() == captain.getId();

    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean isStartAttackPhase(Profile profile) {
        String methodSignature = ".isStartAttackPhase (" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        Team team =_services.getTeam(profile);
        if(team == null){
            return false;
        }
        boolean result =_services.isAttackPhase(team);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) { exception.printStackTrace();}
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;

    }

    @Override
    public boolean isStartEnemyAttackPhase(Profile profile) {

        String methodSignature = ".isStartEnemyAttackPhase (" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        Team team =_services.getTeam(profile);
        if(team == null){
            return false;
        }
        Team opponentTeam =_services.getOpponentTeam(team);
        boolean result =_services.isAttackPhase(opponentTeam);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) { exception.printStackTrace();}
        
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    //public to test purpose
    public boolean searchingOpponentPhase (Profile profile) {
        String methodSignature = ".searchingOpponentPhase(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        Local local =_services.getLocal (profile);
        List<microservices.battleship.types.Team> readyTeams =_services.getReadyTeams (local);
        microservices.battleship.types.Team team =_services.getTeam(profile);
        if(team == null){
            return false;
        }
        boolean isThere =false;
        Iterator<microservices.battleship.types.Team> itr =readyTeams.iterator();
        while (itr.hasNext() && !isThere) {
            if (team.getId() == itr.next().getId()) isThere =true;
        }
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(isThere));
        if(isThere) return true;

        return isThere;
    }

    @Override
    public boolean isStartSearchingOpponent(Profile profile) {
        String methodSignature = ".isStartSearchingOpponent(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);


        //boolean isThere =searchingOpponentPhase(profile) || (isBattleStart(profile) && !isBattleEnd(profile));
        boolean isThere =searchingOpponentPhase(profile) || isBattleStart(profile);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(isThere));
        return isThere;
    }

    @Override
    public boolean isStopSearchingOpponent(Profile profile) {
        String methodSignature = ".isStopSearchingOpponent(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        boolean isThere =searchingOpponentPhase(profile);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(!isThere));
        return !isThere;
    }

    @Override
    public boolean isTeamExists(Beacon beacon) {
        String methodSignature = ".isTeamExist(" + (new Gson()).toJson(beacon) + ")";
    //play.Logger.info(TAG + methodSignature);
        boolean result =_services.isTeamExists(beacon);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    @Override
    public boolean joinTeam(microservices.battleship.types.Profile profile, Beacon beacon) {
        String methodSignature = ".joinTeam (" + (new Gson()).toJson(profile) + "," + (new Gson()).toJson(beacon) +
                ")";
    //play.Logger.info(TAG + methodSignature);

        Table table = _services.getTable (beacon);
        microservices.battleship.types.Team team =_services.getTeam (table);
        if(team == null){
            return false;
        }
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(false));
        if (team.isFull()) return false;

         _services.joinTeam (profile, table);
    //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(true));
        return true;
    }

    @Override
    public void leaveTeam(Profile profile) {
        String methodSignature = ".leaveTeam(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);

        microservices.battleship.types.Team team =_services.getTeam(profile);
        if(team == null){
            return ;
        }
        List<Profile> marinai = team.getTeammates();
        if (team.getCaptain().getId() == profile.getId()){
            if( marinai.size() > 0) {
                Profile primoUfficiale = marinai.get(0);
                _services.setCaptain(team, primoUfficiale);
                _services.removeTeamMember(team, profile);
            } else {
                _services.deleteTeam(team);
            }
        } else {
            _services.removeTeamMember(team, profile);
        }


    //play.Logger.info(TAG + methodSignature + " return: void");
    }

    @Override
    public void removeTeamMember(microservices.battleship.types.Profile profile, microservices.battleship.types.Profile profileToRemove) {
        String methodSignature = ".removeTeamMember(" + (new Gson()).toJson(profile) +
                (new Gson()).toJson(profileToRemove) + ")";
      //play.Logger.info(TAG + methodSignature);

        if (isCaptain (profile)) {
            microservices.battleship.types.Team team =_services.getTeam(profileToRemove);
            if(team == null){
                return;
            }
            // TODO: 6/29/16//play.Logger.info(new Gson ().toJson(team.getTeammates()));
            if (team.getTeammates().size() > 0) {
                if (isCaptain (profileToRemove)) {
                    Profile newCaptain =team.getTeammates().get(0);
                    team.getTeammates().remove(0);
                    team.setCaptain(newCaptain);
                    _services.setCaptain (team, newCaptain);
                }
                else {
                    List<Profile> teamMatesList =team.getTeammates();
                    Iterator<Profile> itr =teamMatesList.iterator();
                    int index =0;
                    while (itr.hasNext() && !(itr.next()).equals(profileToRemove)) {
                        itr.next();
                        index ++;
                    }
                    teamMatesList.remove(index);
                    team.setTeammates(teamMatesList);
                }

              //play.Logger.info(TAG + methodSignature + " return: void ");
                _services.removeTeamMember(team, profileToRemove);
            }
            else {
              //play.Logger.info (TAG + methodSignature + " return: void ");
                _services.deleteTeam(team);
            }
        }
      //play.Logger.info(TAG + methodSignature + " return: void ");
    }

    private boolean isUnknow(Shot shot, Profile profile){
        Team team =_services.getTeam(profile);
        Team opponentTeam =_services.getOpponentTeam(team);
        Field field =_services.getTeamField(opponentTeam);
        Grid grid =field.getShootGrid();
        int x = shot.getPosition().getX();
        int y = shot.getPosition().getY();
        Cell cell = grid.getCell(x, y);
        return cell.isUnknown();
    }

    @Override
    public boolean sendShot(Shot shot, Profile profile) {
        String methodSignature = ".sendShot(" + (new Gson()).toJson(shot) +
                (new Gson()).toJson(profile) + ")";
      //play.Logger.info(TAG + methodSignature);

        if (! isStartAttackPhase(profile)) {
          //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(false) );
            return false;
        }

        int shotNumber =_services.getShotNumber(profile);
        if (shotNumber <= 0) {
          //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(false) + "n colpi = " + shotNumber);
            return false;
        }

        if (!isUnknow(shot, profile)){
          //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(false) + "n colpi = " + shotNumber);
            return false;
        }
        boolean value = insertShot(shot, profile);
      //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(value));
        return value;

    }

    private boolean insertShot(Shot shot, Profile profile){
        String methodSignature = ".insertShot(" + (new Gson()).toJson(shot) +
                (new Gson()).toJson(profile) + ")";
        _services.insertShot(shot, profile);

        Team team =_services.getTeam(profile);
        Team opponentTeam =_services.getOpponentTeam(team);
        Field field =_services.getTeamField(opponentTeam);
        Grid grid =field.getShootGrid();

        //check if a ship is hit;
        List<Ship> shipList =_services.getShips(opponentTeam);
        boolean hit =false;
        Iterator<Ship> itr =shipList.iterator();
        while (itr.hasNext() && !hit) {
            hit =itr.next().isHit(shot);
        }
      //play.Logger.info("E' stata colpita una nave ? " + (new Gson()).toJson(hit));
        //access the cell and set the new state
        if (hit) {
            grid.setCell(shot.getPosition().getX(), shot.getPosition().getY(), CellImpl.State.HIT);
        }
        else {
            grid.setCell(shot.getPosition().getX(), shot.getPosition().getY(), CellImpl.State.MISS);
        }

        //update the Field
        _services.updateField(field,opponentTeam);

      //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(true));
        return true;
    }

    private boolean isOutOfBorder (Ship ship, Grid grid) {
        String methodSignature = ".isOutOfBorder(" + (new Gson()).toJson(ship) +
                (new Gson()).toJson(grid) + ")";
        //play.Logger.info(TAG + methodSignature);
        int heigth =grid.getHeight();
        int length =grid.getLength();
        boolean isOutOfBorder =false;

        List<Position> shipPositions =ship.calculateShipPositions();
        Iterator<Position> itr =shipPositions.iterator();

        while (itr.hasNext() && !isOutOfBorder) {
            Position position =itr.next();
            if (position.getX() >= length || position.getY() >= heigth) {
                isOutOfBorder =true;
            }
        }

        //play.Logger.info(TAG + methodSignature + "return: " + (new Gson()).toJson(isOutOfBorder));
        return isOutOfBorder;
        //return true sse la nave è fuori dalla griglia;
    }

    //public to test purpose
    public boolean collision (Ship firstShip, Ship secondShip) {
        List<Position> firstShipPositions =firstShip.calculateShipPositions();
        List<Position> secondShipPositions = secondShip.calculateShipPositions();

        Iterator<Position> firstItr =firstShipPositions.iterator();

        boolean collision =false;

        while (firstItr.hasNext() && !collision) {
            Position p1 =firstItr.next();
            Iterator<Position> secondItr =secondShipPositions.iterator();
            while (secondItr.hasNext() && !collision) {
                Position p2 =secondItr.next();
                if ((p1.getX() == p2.getX())&&(p1.getY() == p2.getY())) {
                    collision =true;

                }

            }
        }

        return collision;
        /*
        return true sse le navi sono in collisione;
         */
    }

    @Override
    public boolean setShipPosition(Ship ship, Profile profile) {

        String methodSignature = ".setShipPosition (" + (new Gson()).toJson(ship) +
                (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);
        boolean result =false;
        microservices.battleship.types.Team team = _services.getTeam(profile);
        if(team == null){
            return false;
        }

        if (!_services.checkUserFinishShipPositioning (profile)) {
        //if (true) {
            List<Ship> shipList = _services.getShips(team);
            Iterator<Ship> itr = shipList.iterator();
            boolean shipCollision = false;
            while (itr.hasNext() && !shipCollision) {
                Ship s = itr.next();
                shipCollision = collision(s, ship);
            }

            if (shipCollision) {
                //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(false));
                return false; //la nave non è stata posizionata
            }

            //Creo la griglia di gioco
            Grid grid = new GridImpl(10, 10);
            boolean isOutOfBorder = isOutOfBorder(ship, grid);
            if (isOutOfBorder) {
                //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(false));
                return false;
            }
            _services.addUserShips (ship, profile);
            result =true;
        }

        Team opponentTeam =_services.getOpponentTeam (team);
        //controllo se il gioco può`iniziare
        checkStartBattle (team, opponentTeam);

        //play.Logger.info(TAG + methodSignature + " return: " + (new Gson()).toJson(result));
        return result;
    }

    private int containsTheTeam (Team team, List<Team> readyTeams) {
        Iterator<Team> itr =readyTeams.iterator();
        int indexToReturn =-1;
        int index =0;
        while (itr.hasNext() && indexToReturn == -1) {
            Team readyTeam =itr.next();
            if (team.equals(readyTeam)) {
                indexToReturn =index;
            }
            index++;
        }
        return indexToReturn;
    }


    @Override
    public void startSearchingOpponent(Profile profile) {
        String methodSignature = ".startSearchingOpponent(" + (new Gson()).toJson(profile) + ")";
    //play.Logger.info(TAG + methodSignature);
        Local local =_services.getLocal (profile);
        if (isCaptain(profile) && !isBattleStart(profile)) {
            Team team = _services.getTeam(profile);
            List<Team> readyTeams = _services.getReadyTeams (local);
            if(team == null){
                return ;
            }
            if (readyTeams.size() == 0) {
                _services.addReadyTeam (team);
            }
            else {
                int index =containsTheTeam (team, readyTeams);
                if (readyTeams.size() == 1) {
                    if (index != -1) {
                        // TODO: 7/9/16 to test purpose
                        System.out.print ("TheTeamIsTheOnlyOneInTheList");
                        return;
                    }
                    else {
                        // TODO: 7/9/16 to test purpose
                        System.out.print ("TheTeamIsNotInTheListAndTheListHasOneTeam");
                        Field field =new FieldImpl(10,10);
                        _services.updateField(field, team);
                        _services.updateField(field, readyTeams.get(0));
                        setShipToPositioning(team, readyTeams.get(0));
                        Game game =new GameImpl(1, "Battleship");
                        _services.removeReadyTeam (readyTeams.get(0));
                        _services.setOpponent(team, readyTeams.get(0));
                        Thread thread =new Thread(new MyGame(team, readyTeams.get(0), local, game));
                        thread.start();
                    }
                }
                else {
                    if (index != -1) {
                        int opponentIndex =0;
                        for (int i=0; i<readyTeams.size() && opponentIndex == index; i++) {
                            opponentIndex ++;
                        }
                        // TODO: 7/9/16 to test purpose
                        System.out.print (opponentIndex);
                        Field field =new FieldImpl(10,10);
                        _services.updateField(field, readyTeams.get(index));
                        _services.updateField(field, readyTeams.get(opponentIndex));
                        setShipToPositioning(readyTeams.get(index), readyTeams.get(opponentIndex));
                        Game game =new GameImpl(1, "Battleship");
                        _services.removeReadyTeam (readyTeams.get(index));
                        _services.removeReadyTeam (readyTeams.get(opponentIndex));
                        _services.setOpponent(readyTeams.get(index), readyTeams.get(opponentIndex));
                        Thread thread =new Thread(new MyGame(readyTeams.get(index), readyTeams.get(opponentIndex), local, game));
                        thread.start();
                    }
                    else {
                        Field field =new FieldImpl(10,10);
                        _services.updateField(field, team);
                        _services.updateField(field, readyTeams.get(0));
                        setShipToPositioning(team, readyTeams.get(0));
                        Game game =new GameImpl(1, "Battleship");
                        _services.removeReadyTeam (readyTeams.get(0));
                        _services.setOpponent(team, readyTeams.get(0));
                        Thread thread =new Thread(new MyGame(team, readyTeams.get(0), local, game));
                        thread.start();
                    }
                }

            }
        }
    //play.Logger.info(TAG + methodSignature + " return: void ");
    }

    @Override
    public void stopSearchingOpponent(microservices.battleship.types.Profile profile) {
        String methodSignature = ".stopSearchingOpponent()";
    //play.Logger.info(TAG + methodSignature);
        microservices.battleship.types.Team team =_services.getTeam (profile);
        if(team == null){
            return ;
        }
        _services.removeReadyTeam (team);
    //play.Logger.info(TAG + methodSignature + "return : void");
    }

    @Override
    public void setShipToPositioning(Team teamOne, Team teamTwo) {

        List<Profile> teamOneList =teamOne.getTeammates ();
        teamOneList.add(0, teamOne.getCaptain());
        List<Profile> teamTwoList =teamTwo.getTeammates ();
        teamTwoList.add(0, teamTwo.getCaptain());

        ShipType ship= new ShipTypeImpl(3);

        int j =0;
        int dim =teamOneList.size ();
        for (int i=0; dim>0 && i<6; i++) {
            _services.setShipToPositioning (ship, teamOneList.get(j%dim));
            j++;
        }
        j =0;
        dim =teamTwoList.size ();
        for (int i=0; dim>0 && i<6; i++) {
            _services.setShipToPositioning (ship, teamTwoList.get(j%dim));
            j++;
        }

        teamOneList.remove (0);
        teamTwoList.remove (0);

    }

    @Override
    public void setRandomShipsPositions(Profile profile) {
        String methodSignature = "setRandomShipsPositions ";
      //play.Logger.info(TAG + methodSignature);


        //obatain the ship not positioned associated to the profile;
        List<ShipType> shipList = _services.getShipsToPositioning(profile);
        //to generate random boolean and random integer;
        Random random =new Random();

        System.out.println (new Gson().toJson(shipList));
        //iterate for all ShipType objects;
        Iterator<ShipType> itr =shipList.iterator();
        while (itr.hasNext()) {
            //create a new random boolean for the new ship
            boolean isVertical =random.nextBoolean();
            //check if the ship is in vertical position or not and create a sense position for the ship
            Position p;
            if (isVertical) p =new PositionImpl (random.nextInt(10), random.nextInt (8));
            else p =new PositionImpl (random.nextInt(8), random.nextInt(10));
            //create a number of Ship objects as the number of ShipType objects;
            Ship ship =new ShipImpl(isVertical, 3, p);

            while (! setShipPosition(ship,profile)) {
                //create a new random boolean for the new ship
                isVertical =random.nextBoolean();
                //check if the ship is in vertical position or not and create a sense position for the ship
                if (isVertical) p =new PositionImpl (random.nextInt(10), random.nextInt (8));
                else p =new PositionImpl (random.nextInt(8), random.nextInt(10));
                //create a number of Ship objects as the number of ShipType objects;
                ship =new ShipImpl(isVertical, 3, p);
            }

            itr.next();
        }

      //play.Logger.info(TAG + methodSignature + " return ");
    }

    @Override
    public void setRandomShot(Profile profile) {
        //random object
        Random random =new Random();
        //check if the profile has not shot all his shot
        int shot =_services.getShotList(profile).size();
        int numberOfShot =_services.getShotNumber(profile);
      //play.Logger.info ("shot: " + new Gson().toJson(shot));
      //play.Logger.info ("numberOfShot: " + new Gson().toJson(numberOfShot));
        int remainingShot = numberOfShot - shot;
        Shot newShot;
        Position position;
        int i;
        for (; remainingShot>0; --remainingShot) {
            i = 0;
            do {
                ++i;
                position = new PositionImpl(random.nextInt(10), random.nextInt(10));
                newShot = new ShotImpl(position);
            } while(!isUnknow(newShot, profile)  && i<1000);

            insertShot(newShot, profile);
        }

    }

    @Override
    public List<Score> getBattleFinalScore(Profile profile) {
        List<Score> scoreList =new LinkedList<>();
        Team team = _services.getTeam(profile);
        Score scoreTeam =getTeamFinalScore(team);
        //'scoreTeam' is the score of the team of profile
        Team opponentTeam = _services.getOpponentTeam(team);
        Score scoreOpponent =getTeamFinalScore(opponentTeam);
        //'scoreOpponent' is the score of the opponent team of profile
        scoreList.add(scoreTeam);
        scoreList.add(scoreOpponent);
        //'scoreList' contains the two score; first one the opponent
        // team score of profile; second one the team score of profile;

        return scoreList;
    }

    private void setMaxAvailableShot (Team team) {
        int MAX_numberOfShot = _numeroNavi;
        int players =team.getTeammates().size() +1;

        int shotForPlayer =MAX_numberOfShot/players;
        int shotForCaptain =shotForPlayer + MAX_numberOfShot%players;

        _services.setMaxAvailableShots(shotForCaptain, team.getCaptain());

        List<Profile> profileList =team.getTeammates();
        Iterator<Profile> itr =profileList.iterator();
        while (itr.hasNext()) _services.setMaxAvailableShots(shotForPlayer, itr.next());

    }

}
