package digitalsloths.socialtables.games.battleship.types;

/**
 *file Shot.java
 *author Saccon Daniele
 *date 24/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un colpo sparato da un giocatore.
 *use Utilizzato da Battleship per gestire le informazioni riguardanti un colpo sparato da un giocatore.
 */
public interface Shot {
    /**
     * @name getPosition
     * @desc Rappresenta la posizione del colpo sparato.
     * @returns {Position}
     * @memberOf Client.Games.Battleship.Types.Shoot
     */
    public Position getPosition();
}
