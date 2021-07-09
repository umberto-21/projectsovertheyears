package digitalsloths.socialtables.games.utility.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 /*file ScoreImpl.java
 /*author Saccon Daniele
 /*date 03/05/2016
 /*brief Implementa l’interfaccia che gestisce le informazioni riguardanti i punteggi di una squadra in classifica.
 /*use Utilizzata dal package Games per gestire i punteggi.
 */
public class ScoreImpl implements Score{
    private String _teamName;
    private int _score;
    private final Integer _id;
    private static final String TAG = "Score";

    /**
     * @name ScoreImpl
     * @desc Costruttore della classe.
     * @param {String} teamName - Nome della squadra
     * @param {int} score - Punteggio fatto dalla squadra.
     * @param {Integer} id - Identificativo del punteggio.
     * @memberOf Client.Games.Utility.Types.ScoreImpl
     */
    public ScoreImpl(String team_name, int score, Integer id){
       //Log.v(TAG, ".ScoreImpl(" + (new Gson()).toJson(team_name) + ","+ (new Gson()).toJson(score) + ", "+ (new Gson()).toJson(id) + ")");
        _teamName = team_name;
        _score = score;
        _id = id;
       //Log.v(TAG, ".ScoreImpl(" + (new Gson()).toJson(team_name) + ","+ (new Gson()).toJson(score) + ", "+ (new Gson()).toJson(id) + ") return : costruttore");
    }
    /**
     * @name ScoreImpl
     * @desc Costruttore della classe.
     * @param {String} teamName - Nome della squadra
     * @param {int} score - Punteggio fatto dalla squadra.
     * @memberOf Client.Games.Utility.Types.ScoreImpl
     */
    public ScoreImpl(String team_name, int score){
       //Log.v(TAG, ".ScoreImpl(" + (new Gson()).toJson(team_name) + ","+ (new Gson()).toJson(score) + ")");
        _teamName = team_name;
        _score = score;
        _id = null;
       //Log.v(TAG, ".ScoreImpl(" + (new Gson()).toJson(team_name) + ","+ (new Gson()).toJson(score) + ") return : costruttore");
    }
    /**
     * @name getTeamNome
     * @desc Ritorna il nome della squadra.
     * @returns {String}
     * @memberOf Client.Games.Utility.Types.ScoreImpl
     */
    @Override
    public String getTeamName() {
       //Log.v(TAG, ".getTeamName()");
       //Log.v(TAG, ".getTeamName() return : " + (new Gson()).toJson(_teamName));
        return _teamName;
    }

    /**
     * @name getId
     * @desc Ritorna l'identificativo del punteggio.
     * @returns {Integer}
     * @memberOf Client.Games.Utility.Types.ScoreImpl
     */
    @Override
    public Integer getId() {
       //Log.v(TAG, ".getId()");
       //Log.v(TAG, ".getId() return : " + (new Gson()).toJson(_id));
        return _id;
    }

    /**
     * @name getScore
     * @desc Ritorna il punteggio fatto dalla squadra.
     * @returns {int}
     * @memberOf Client.Games.Utility.Types.ScoreImpl
     */
    @Override
    public int getScore() {
       //Log.v(TAG, ".getScore()");
       //Log.v(TAG, ".getScore() return : " + (new Gson()).toJson(_score));
        return _score;
    }

    @Override
    public boolean equals(Score score){
        return this.getTeamName() == score.getTeamName() && this._id == score.getId() && this.getScore() == score.getScore();
    }
}
