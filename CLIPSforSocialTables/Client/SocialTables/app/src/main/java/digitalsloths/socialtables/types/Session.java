package digitalsloths.socialtables.types;

/**
 *file Session.java
 *author Andria Umberto
 *date 28/05/2016
 *brief
 *use
 */
public interface Session {

    /**
     * @name getId
     * @desc ritorno l'id della sessione;
     * @returns {int}
     * @memberOf Client.Types.Session
     */
    public int getId ();

    public boolean equals(Session s);
}
