package microservices.battleship.types;

/**
 * Created by Ivan Parise
 * Created on 01/06/16.
 * Description: Interfaccia che gestisce le informazioni associate ad un tavolo;
 * Use: Utilizzato da BattleshipBusiness per controllare se ci sono team presenti nel tavolo;
 */
public interface Table {
    /**
     * @name getId
     * @desc Ritorna l'ID associato al tavolo;
     * @returns {int}
     * @memberOf Server.Microservices.Battleship.Types.Table
     */
    public Integer getId();
    /**
     * @name getName
     * @desc Ritorna il nome associato al tavolo;
     * @returns {String}
     * @memberOf Server.Microservices.Battleship.Types.Table
     */
    public String getName();
    /**
     * @name equals
     * @desc Dice se i due oggetti sono uguali.
     * @param {TableImpl} t - Rappresenta il tavolo da confrontare.
     * @returns {boolean}
     * @memberOf Server.Microservices.Battleship.Types.Table
     */
    public boolean equals(Table t);
}
