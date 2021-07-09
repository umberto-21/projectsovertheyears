package microservices.battleship.types;

/**
 *file Score.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un punteggio effettuato da una squadra in un determinato locale in una partita ad un gioco.
 *use Viene utilizzata per gestire le informazioni che rappresentano un punteggio effettuato da una squadra in un determinato locale in una partita ad un gioco.
 */
public interface Score {
    /**
     * @name getScore
     * @desc Ritorna il punteggio fatto dalla squadra.
     * @returns {int}
     * @memberOf Server.Microservices.Score.Types.Score
     */
    public int getScore();
    /**
     * @name getTeamName
     * @desc Ritorna il nome della squadra che ha fatto il punteggio.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.Score
     */
    public String getTeamName();
    /**
     * @name getId
     * @desc Ritorna l'identificativo del punteggio.
     * @returns {Integer}
     * @memberOf Server.Microservices.Score.Types.Score
     */
    public Integer getId();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {ScoreImpl} s - Rappresenta il score da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Score
     */
    public boolean equals(Score s);
}
