package microservices.profile.types;

/**
 *file LocalImpl.java
 *author Saccon Daniele
 *date 25/05/2016
 *brief Classe che gestisce le informazioni riguardanti un locale specifico.
 *use Implementa i metodi offerti dall'interfaccia Profile, utilizzati per per gestire le informazioni riguardanti un locale specifico.
 */
public class LocalImpl implements Local {
    private int _id;
    private String _name;
    /**
     * @name LocalImlp
     * @desc Costruttore della classe.
     * @param {String} name - Rappresenta il nome del locale.
     * @param {int} id - Rappresenta l'identificativo del locale.
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    public LocalImpl(int id, String name){
        _id = id;
        _name = name;
    }
    /**
     * @name getId
     * @desc Ritorna l'id del locale.
     * @returns {int}
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    @Override
    public int getId() {
        return _id;
    }
    /**
     * @name getNome
     * @desc Ritorna il nome del locale.
     * @returns {String}
     * @memberOf Server.Microservices.Score.Types.LocalImlp
     */
    @Override
    public String getName() {
        return _name;
    }
}
