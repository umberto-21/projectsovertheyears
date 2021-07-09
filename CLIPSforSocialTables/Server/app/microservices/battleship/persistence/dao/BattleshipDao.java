package microservices.battleship.persistence.dao;
import microservices.battleship.types.Team;
import microservices.battleship.types.Ship;
import microservices.battleship.types.ShipType;
import microservices.battleship.types.Shot;
import microservices.battleship.types.Field;
import microservices.battleship.types.Local;
import java.util.List;

/**
 *file BattleshipDao.java
 *author Filinesi Skrypnyk Oleksandr
 *date 2/06/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative al gioco Battleship;
 *use Viene usata per esporre le possibili operazioni relative al gioco Battleship;
 */
public interface BattleshipDao {

  void addUserShips(int x, int y, boolean vertical, int profileId);
  boolean checkUserFinishShipPositioning(int profileId);
  boolean checkUserFinishShoots(int profileId);
  void deleteBattle(int teamId);
  Team getOpponentTeam(int teamId);
  List<Team> getReadyTeams(int localId);
  List<Shot> getShotList(int profileId);
  int getShotNumber(int profileId);
  Team getTeamFormProfileId(int profileId);
  Field getTeamField(int teamId);
  List<Ship> getUserShips(int profileId);
  void insertSearchingOpponent(int teamId);
  void insertShot(int x, int y, int profileId);
  boolean isBattleRunning(int teamId);
  boolean isTeamExists(String uuid, int major, int minor);
  void removeReadyTeam(int teamId);
  void removeTeamMember(int teamId, int profileId);
  void setOpponent(int teamOneId, int teamTwoId);
  void setShipToPositioning(int length, int profileId);//
  void updateField(String [][] grid, int teamId);
  void setAttackTeam(int teamId);
  boolean isAttackPhase(int teamId);//
  void removeAttackTeam(int teamId);
  Local getLocal(int profileId);//
  List<ShipType> getShipsToPositioning(int profileId);//
  boolean setMaxAvailableShots(int numberOfShots, int profileId);//
  void addReadyTeam(int teamId);
  void clearTeamShots(int teamId);
}
