package microservices.profile.services;
import microservices.profile.types.Profile;
import microservices.profile.types.Beacon;
import microservices.profile.types.Local;
import microservices.profile.types.Session;
import java.util.List;

/**
 *file ProfileServices.java
 *author Filinesi Skrypnyk Oleksandr
 *date 29/05/2016
 *brief Interfaccia che gestisce i dati relativi al profilo dell'utente.
 *use Viene utilizzata per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business.
 */
public interface ProfileServices{

  /**
  * @name changeProfile
  * @desc Cambia i dati del profilo indicato dal primo parametro con quelli indicati dal secondo parametro. Ritorna true se l'operazione ha avuto successo false altrimenti;
  * @param {Profile} userProfile - Rappresenta il profilo dell'utente;
  * @param {Profile} updateProfile - Rappresenta i dati aggiornati associati all'utente;
  * @returns {void}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */ 
  boolean changeProfile(Profile userProfile, Profile updateProfile);

  /**
  * @name getNewProfile
  * @desc Ritorna un profilo nuovo associandolo alla sessione;
  * @param {Session} session - Rappresenta la sessione associata all'utente;
  * @param {Beacon} beacon - Rappresenta il beacon associato all'utente;
  * @param {Profile} profile - Rappresenta il prototipo del profilo da cerare e memorizzare sul server;
  * @returns {Profile}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */
  Profile getNewProfile(Session session, Beacon beacon, Profile profile);

  /**
  * @name getProfile
  * @desc Ritorna il profilo associato alla sessione;
  * @param {Session} session - Rappresenta la sessione associata all'utente;
  * @returns {Profile}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */ 
  Profile getProfile(Session session);

  /**
  * @name updateLocation
  * @desc Aggiorna il Beacon associato all'utente;
  * @param {Profile} profile - Rappresenta il profilo associato all'utente;
  * @param {Beacon} beacon - Rappresenta il beacon da associare all'profilo. null se si vuole disassociare il beacon dal profilo;
  * @returns {void}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */ 
  boolean updateLocation(Profile profile, Beacon beacon);
  
  /**
  * @name getNameList
  * @desc Ritorna la lista di nomi presenti nel locale indicato;
  * @param {Local} local - Rappresenta il locale di cui si vuole avere la lista di nomi degli utenti;
  * @returns {List}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */
  List<String> getNameList(Local local);
  
  /**
  * @name getLocal
  * @desc Ritorna il locale associato al profilo
  * @param {Profile} profile - Rappresenta il profilo dell'utente presente nel locale
  * @returns {Local}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */
  Local getLocal(Profile profile);
}
