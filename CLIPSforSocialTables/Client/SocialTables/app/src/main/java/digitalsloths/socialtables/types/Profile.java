package digitalsloths.socialtables.types;

/**
 *file Profile.java
 *author Andria Umberto
 *date 28/05/2016
 *brief
 *use
 */
public interface Profile {

    /**
     * @name getUsername
     * @desc ritorna il nome utente;
     * @returns {String}
     * @memberOf Client.Types.Profile
     */
    public String getUsername ();
    public void setUsername (String username);
    public int getId();
    public boolean equals(Profile p);
}
