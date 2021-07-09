package microservices.battleship.persistence.dao;
import java.util.List;
import microservices.battleship.types.Score;


/**
 *file ScoreDao.java
 *author Filinesi Skrypnyk Oleksandr
 *date 30/05/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative al punteggio di una partita;
 */
public interface ScoreDao {

  /**
  * @name addScore
  * @desc Memorizza lo score passato associandolo al gioco e alla squadra identificati dai parametri;
  * @param {int} gameId - Identificatore del gioco al quale associare il punteggio;
  * @param {int} score - Punteggio da inserire nella classifica del gioco indicato;
  * @param {String} teamName - Nome del team al quale associare il punteggio;
  * @param {int} localId - Identificatore del locale al quale associare il punteggio;
  * @returns {void}
  * @memberOf Server.Microservices.Score.Persistence.Dao.ScoreDao
  */
  void addScore(int gemeId, int score, String teamName, int localId);
  
  /**
  * @name getLeaderboard
  * @desc Ritorna la classifica associata al gioco indicato;
  * @param {int} gameId - Identificatore del gioco del quale si vuole avere la leaderboard;
  * @param {int} localId - Identificatore del locale del quale si vuole avere la leaderbord;
  * @returns {List}
  * @memberOf Server.Microservices.Score.Persistence.Dao.ScoreDao
  */ 
  List<Score> getLeaderboard(int gameId, int localId);
  
  void deleteScore(int scoreId);
}
