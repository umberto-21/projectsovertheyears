package microservices.battleship.types;

import java.util.List;

/**
 *file TeamField.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che rappresenta il campo avversario del team di cui si fa parte.
 *use Viene utilizzata per rappresentare il campo avversario del team di cui si fa parte.
 */
public interface TeamField extends Field {
    /**
     * @name getShipList
     * @desc Ritorna la lista delle navi presenti nel campo.
     * @returns {List}
     * @memberOf Server.Microservices.Battleship.Types.TeamField
     */
    public List<Ship> getShipList ();
    /**
     * @name isBattleLost
     * @desc Ritorna true se e solo se tutte le navi sono state affondate.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamField
     */
    public boolean isBattleLost ();
    /**
     * @name set_shipList
     * @desc Setta la lista di navi.
     * @param {List } shipList - Rappresenta la lista di navi della squadra.
     * @returns {void}
     * @memberOf Server.Microservices.Battleship.Types.TeamField
     */
    public void set_shipList(List<Ship> shipList);
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TeamFieldImpl} t - Rappresenta il teamField da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.TeamField
     */
    public boolean equals(TeamField t);
}
