package microservices.profile.types;

/**
 *file Local.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Interfaccia che gestisce le informazioni riguardanti un locale specifico.
 *use Viene utilizzata per gestire le informazioni riguardanti un locale specifico.
 */
public interface Local {
    /**
     * @name getId
     * @desc Ritorna l'id del locale.
     * @returns {int}
     * @memberOf Server.Microservices.Score.Types.Local
     */
    public int getId();
    /**
     * @name getNome
     * @desc Ritorna il nome del locale.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.Local
     */
    public String getName();
}
