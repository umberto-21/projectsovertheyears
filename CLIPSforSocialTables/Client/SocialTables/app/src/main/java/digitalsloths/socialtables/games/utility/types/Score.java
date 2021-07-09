package digitalsloths.socialtables.games.utility.types;

/**
 *file Score.java
 *author Saccon Daniele
 *date 03/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti i punteggi di una squadra in classifica.
 *use Utilizzata dal package Games per gestire i punteggi.
 */
public interface Score {
    /**
     * @name getScore
     * @desc Ritorna il punteggio fatto dalla squadra.
     * @returns {int}
     * @memberOf Client.Games.Utility.Types.Score
     */
    public int getScore();
    /**
     * @name getTeamNome
     * @desc Ritorna il nome della squadra.
     * @returns {String}
     * @memberOf Client.Games.Utility.Types.Score
     */
    public String getTeamName();

    /**
     * @name getId
     * @desc Ritorna l'identificativo del punteggio.
     * @returns {Integer}
     * @memberOf Client.Games.Utility.Types.Score
     */
    public Integer getId();

    public boolean equals(Score score);
}
