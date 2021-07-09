package microservices.battleship.persistence.dao;
import microservices.battleship.types.Team;
import microservices.battleship.types.Table;
import java.util.List;

/**
 *file TeamDao.java
 *author Filinesi Skrypnyk Oleksandr
 *date 1/06/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad una squadra;
 *use Viene usata per esporre le possibili operazioni relative ad una squadra;
 */
public interface TeamDao {

  /**
  * @name addMember
  * @desc Aggiunge un profilo alla squadra;
  * @param {int} teamId - Identificatore del team;
  * @param {int} memberProfileId - Identificatore del profilo da aggiungere alla squadra;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  boolean addMember(int teamId, int memberProfileId);
  
  /**
  * @name changeTeamName
  * @desc Rinomina la squadra. Ritorna true se l'operazione è riuscita false altrimenti;
  * @param {int} teamId - Identificatore della squadra;
  * @param {String} teamName - Il nuovo nome da associare alla squadra;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */
  boolean changeTeamName(int teamId, String teamName);
  
  /**
  * @name createTeam
  * @desc Crea la squadra con il nome e il caposquadra passati come paramentri;
  * @param {String} teamName - Rappresenta il nome della squadra;
  * @param {int} leaderProfileId - Rappresenta il profilo dell'utente creatore del team (il caposquadra);
  * @returns {Team}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */
  Team createTeam(String teamName, int leaderProfileId);

  /**
  * @name deleteTeam
  * @desc Elimina la squadra indicata;
  * @param {int} teamId - Identificatore del team da eliminare;
  * @returns {void}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */
  void deleteTeam(int teamId);
  
  /**
  * @name getTeam
  * @desc Restituisce il team indicato;
  * @param {int} teamId - l'identificatore del team da ritornare;
  * @returns {Team}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */
  Team getTeam(int teamId);
  
  /**
  * @name isTeamExist
  * @desc Ritorna true se già esiste una squadra associata al tavolo indicato;
  * @param {Table} tableId - Identificativo del tavolo sul quale verificare se c'è un team associato;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  boolean isTeamExist(int tableId);
  
  /**
  * @name removeMember
  * @desc Rimuove un profilo dalla squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
  * @param {int} teamId - Identificatore del team;
  * @param {int} memberProfileId - Identificatore del profilo da rimuovere dalla squadra;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */
  boolean removeMember(int teamId, int memberProfileId);
  
  /**
  * @name setTeamLeader
  * @desc Imposta come leader il profilo passato come parametro. Il profilo deve già fare parte della squadra. Ritorna true se l'operazione è riuscita, false altrimenti;
  * @param {int} teamId - Rappresenta l'identificativo del team su cui cambiare il caposquadra;
  * @param {int} profileId - Rappresenta l'identificativo del profilo del nuovo caposquadra del team. Il profilo deve appartenere al team;
  * @returns {boolean}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  boolean setTeamLeader(int teamId, int profileId);

  /**
  * @name getTable
  * @desc Ritorna il tavolo associato al beacon;
  * @param {String} uuid - Uuid del beacon;
  * @param {int} major - Major del beacon;
  * @param {int} minor - Minor del beacon;
  * @returns {Table}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  Table getTable(String uuid, int major, int minor);
  
  /**
  * @name getTeamNameList
  * @desc Ritorna la lista di nomi di team del locale indicato;
  * @param {int} localId - Ritorna la lista di nomi di team del locale indicato;
  * @returns {List}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  List<String> getTeamNameList(int localId);
  
  /**
  * @name getTeamIdFromTableId
  * @desc Ritorna l'identificatore del team ricavato dall'identificatore del tavolo. Ritorna null se il reperimento fallisce;
  * @param {int} tableId - Identificatore del tavolo;
  * @returns {Integer}
  * @memberOf Server.Microservices.Battleship.Persistence.Dao.TeamDao
  */ 
  Integer getTeamIdFromTableId(int tableId);
  
  Integer getTeamIdFromProfileId(int profileId);
}
