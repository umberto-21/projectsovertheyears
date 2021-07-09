package digitalsloths.socialtables.games.battleship.types;

/**
 *file Position.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti la posizione di un oggetto nel campo di battaglia.
 *use Utilizzato da Battleship per gesetire le informazioni riguardanti la posizione di un oggetto nel campo di battaglia (es. colpo, nave).
 */
public interface Position {
    /**
     * @name getX
     * @desc Ritorna la coordinata X della posizione.
     * @returns {int}
     * @memberOf Client.Games.Battleship.Types.Position
     */
    public int getX();
    public int getY();
    public boolean equals(Position position);
}
