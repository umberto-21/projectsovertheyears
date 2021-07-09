package microservices.profile.persistence;
import microservices.profile.types.Error;
import microservices.profile.types.Profile;
import microservices.profile.types.Response;
import microservices.profile.types.Session;
import microservices.profile.types.Local;
import java.util.List;

/**
 *file ProfilePersistence.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Interfaccia che gestisce i dati elementari relativi al profilo dell'utente, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato.
 *use Viene utilizzata per interfacciarsi e comunicare direttamente con il database utilizzato.
 */
public interface ProfilePersistence{
	
  /**
  * @name createProfile
  * @desc Crea un profilo e ritorna un oggetto di tipo Response che contiene un oggetto di tipo Profile o un errore se la creazione del profilo è fallita.
  * @param {String} username - Nome da associare al profilo.
  * @param {int} sessionId - Identificatore della sessione alla quale associare il profilo.
  * @param {Integer} beaconId - Identificatore del beacon più vicino all'utente, o null se non c'è alcun beacon nelle vicinanze.
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */
  Response<Profile> createProfile(String username, int sessionId, Integer beaconId);
  
  /**
  * @name getProfile
  * @desc Ritorna un oggetto di tipo Response contenente: l'oggetto di tipo Profile richiesto o un errore se il reperimento non è riuscito.
  * @param {int} sessionId - Identificatore della sessione da reperire.
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  Response<Profile> getProfile(int sessionId);
  
  /**
  * @name deleteProfile
  * @desc Elimina il profilo associato alla sessione e ritorna un errore se l'eliminazione è fallita o null se l'eliminazione è riuscita.
  * @param {int} sessionId - Identificatore della sessione associata al profilo da eliminare.
  * @returns {Error}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  Error deleteProfile(int sessionId);
  
  /**
  * @name modifyProfile
  * @desc Metodo usato per modificare un profilo. Ritorna un errore se la modifica è fallita, null se è riuscita.
  * @param {String} newUsername - Nuovo nome per l'utente o null se non è richiesta la modifica del nome.
  * @param {int} sessionId - Identificatore della sessione associata al profilo da modificare.
  * @param {String} newBeaconId - Si può indicare: null se il cambio del beacon non è richiesto;
  *      "NULL" se si vuole disasociare il beacon associato al profilo;
  *      una stringa rapresentante l'identificatore del beacon da associare al profilo;
  * @returns {Error}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  Error modifyProfile(String newUsername, int sessionId, String newBeaconId);
  
  /**
  * @name getSession
  * @desc Ritorna un oggetto di tipo Response contenente: l'oggetto di tipo Session rapresentante la sessione associata al profilo; un errore se il reperimento non è riuscito;
  * @param {int} profileId - Identificatore del profilo di cui reperire la sessione.
  * @returns {Response}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  Response<Session> getSession(int profileId);
  
  /**
  * @name getNameList
  * @desc Ritorna la lista di nomi presenti nel locale indicato;
  * @param {int} localid - Identificatore del locale di cui si vuole avere la lista di nomi degli utenti;
  * @returns {List}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  List<String> getNameList(int localId);
  
  /**
  * @name getBeaconId
  * @desc Restituisce un identificatore che rappresenta univocamente il beacon all'interno del sistema;
  * @param {String} uuid - Uuid associato al beacon;
  * @param {int} major - Major associato al beacon;
  * @param {int} minor - Minor associato al beacon;
  * @returns {int}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */
  int getBeaconId(String uuid, int major, int minor);
  
  /**
  * @name getLocal
  * @desc Ritorna il locale associato al profilo;
  * @param {int} profileId - l'identificatore del profilo;
  * @returns {Local}
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistence
  */ 
  Local getLocal(int profileId);
}