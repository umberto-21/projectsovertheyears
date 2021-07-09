package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file ScoreImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni che rappresentano un punteggio effettuato da una squadra in un determinato locale in una partita ad un gioco.
 *use Implementa i metodi offerti dall'interfaccia Score, utilizzati per la gestione delle informazioni che rappresentano un punteggio effettuato da una squadra in un determinato locale in una partita ad un gioco.
 */
public class ScoreImpl implements Score {
    private int _score;
    private String _teamName;
    private final Integer _id;
    private static final String TAG = "Score";

    /**
     * @name ScoreImpl
     * @desc Costruttore della classe.
     * @param {int} score - Rappresenta il punteggio della squadra.
     * @param {String} teamName - Rappresenta il nome della squadra che ha fatto il punteggio.
     * @param {Integer} id - Rappresenta l'identificativo del punteggio.
     * @memberOf Server.Microservices.Score.Types.ScoreImpl
     */
    public ScoreImpl(int score, String teamName, Integer id){
        String methodSignature = ".ScoreImpl(" + (new Gson()).toJson(score) + ", " + (new Gson()).toJson(teamName) + ", " + (new Gson()).toJson(id) +")";
     //play.Logger.info(TAG + methodSignature);
        _score = score;
        _teamName = teamName;
        _id = id;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name ScoreImpl
     * @desc Costruttore della classe.
     * @param {int} score - Rappresenta il punteggio della squadra.
     * @param {String} teamName - Rappresenta il nome della squadra che ha fatto il punteggio.
     * @memberOf Server.Microservices.Score.Types.ScoreImpl
     */
    public ScoreImpl(int score, String teamName){
        String methodSignature = ".ScoreImpl(" + (new Gson()).toJson(score) + ", " + (new Gson()).toJson(teamName) + ", " +")";
     //play.Logger.info(TAG + methodSignature);
        _score = score;
        _teamName = teamName;
        _id = null;
     //play.Logger.info(TAG + methodSignature + " return: costruttore");
    }
    /**
     * @name getScore
     * @desc Ritorna il punteggio fatto dalla squadra.
     * @returns {int}
     * @memberOf Server.Microservices.Score.Types.ScoreImpl
     */
    @Override
    public int getScore() {
     //play.Logger.info(TAG + ".getScore()");
     //play.Logger.info(TAG + ".getScore() return: " + (new Gson()).toJson(_score));
        return _score;
    }
    /**
     * @name getTeamName
     * @desc Ritorna il nome della squadra che ha fatto il punteggio.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.ScoreImpl
     */
    @Override
    public String getTeamName() {
     //play.Logger.info(TAG + ".getTeamName()");
     //play.Logger.info(TAG + ".getTeamName() return: " + (new Gson()).toJson(_teamName));
        return _teamName;
    }
    /**
     * @name getId
     * @desc Ritorna l'identificativo del punteggio.
     * @returns {Integer}
     * @memberOf Server.Microservices.Score.Types.ScoreImpl
     */
    @Override
    public Integer getId() {
     //play.Logger.info(TAG + ".getId()");
     //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ScoreImpl} s - Rappresenta il score da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.ScoreImpl
     */
    @Override
    public boolean equals(Score s) {
        return (this.getId().equals(s.getId()))&&(this.getScore()==s.getScore())&&(this.getTeamName().equals(s.getTeamName()));
    }
}
