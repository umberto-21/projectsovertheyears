package microservices.battleship.types;

import java.util.List;

/**
 *file AdminField.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che rappresenta un campo da gioco sul quale si possono aggiungere nuovi colpi.
 *use Viene utilizzata per rappresentare un campo da gioco sul quale si possono aggiungere nuovi colpi.
 */
public interface AdminField extends TeamField {
    /**
     * @name Shot
     * @desc Aggiunge un colpo singolo, aggiornando di conseguenza la stato del campo.
     * @param {Shot} shot - Rappresenta il colpo sparato dall'avversario.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.AdminField
     */
    public void Shot (Shot shot);
    /**
     * @name ShootList
     * @desc Aggiunge una lista di colpi aggiornando la situazione del campo.
     * @param {List} shootList - Rappresenta la lista di colpi sparati dalla squadra avversaria.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.AdminField
     */
    public void ShootList (List<Shot> shootList);
}
