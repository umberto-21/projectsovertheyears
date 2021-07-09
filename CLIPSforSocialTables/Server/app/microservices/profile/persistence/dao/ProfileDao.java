package microservices.profile.persistence.dao;
import microservices.profile.types.Error;
import microservices.profile.types.Profile;
import microservices.profile.types.Response;
import microservices.profile.types.Session;
import microservices.profile.types.Local;
import java.util.List;

/**
 *file ProfileDao.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Gestisce il salvataggio e il recupero di informazioni relative ad un profilo.
 */
public interface ProfileDao {

  /**
  * @name createProfile
  * @desc Crea un profilo sul nel database e ritorna un oggetto di tipo Response che contiene un oggetto di tipo Profile o un errore se la creazione del profilo è fallita.
  * @param {String} username - Nome da associare al profilo.
  * @param {int} sessionId - Identificatore della sessione alla quale associare il profilo.
  * @param {Integer} beaconId - Identificatore del beacon più vicino all'utente, o null se non c'è alcun beacon nelle vicinanze.
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  public Response<Profile> createProfile(String username, int sessionId, Integer beaconId);

  /**
  * @name getProfile
  * @desc Ritorna un oggetto di tipo Response contenente: l'oggetto di tipo Profile richiesto o un errore se il reperimento non è riuscito.
  * @param {int} sessionId - Identificatore della sessione da reperire.
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  public Response<Profile> getProfile(int sessionId);

  /**
  * @name deleteProfile
  * @desc Elimina il profilo associato alla sessione e ritorna un errore se l'eliminazione è fallita o null se l'eliminazione è riuscita.
  * @param {int} sessionId - Identificatore della sessione associata al profilo da eliminare.
  * @returns {Error}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  public Error deleteProfile(int sessionId);

  /**
  * @name modifyProfile
  * @desc Metodo usato per modificare un profilo nel database. Ritorna un errore se la modifica è fallita, null se è riuscita.
  * @param {String} newUsername - Nuovo nome per l'utente o null se non è richiesta la modifica del nome;
  * @param {int} sessionId - Identificatore della sessione associata al profilo da modificare;
  * @param {String} newBeaconId - Si può indicare: null se il cambio del beacon non è richiesto; "NULL" se si vuole disassociare il beacon associato al profilo; una stringa rapresentante l'identificatore del beacon da associare al profilo;
  * @returns {Error}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  public Error modifyProfile(String newUsername, int sessionId, String newBeaconId);  
  
  /**
  * @name getSession
  * @desc Ritorna un oggetto di tipo Response contenente: l'oggetto di tipo Session rapresentante la sessione associata al profilo; un errore se il reperimento non è riuscito;
  * @param {int} profileId - Identificatore del profilo di cui reperire la sessione;
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */
  public Response<Session> getSession(int profileId);
  
  /**
  * @name getNameList
  * @desc Ritorna la lista di nomi presenti nel locale indicato;
  * @param {int} localId - Identificatore del locale di cui si vuole avere la lista di nomi degli utenti;
  * @returns {List}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  public List<String> getNameList(int localId);
  
  /**
  * @name getBeaconId
  * @desc Restituisce un identificatore che rappresenta univocamente il beacon all'interno del sistema;
  * @param {String} uuid - Uuid associato al beacon;
  * @param {int} major - Major associato al beacon;
  * @param {int} minor - Minor associato al beacon;
  * @returns {int}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  int getBeaconId(String uuid, int major, int minor);
  
  /**
  * @name getLocal
  * @desc Ritorna il locale associato al profilo;
  * @param {int} profileId - l'identificatore del profilo;
  * @returns {Local}
  * @memberOf Server.Microservices.Profile.Persistence.Dao.ProfileDao
  */ 
  Local getLocal(int profileId);
}
