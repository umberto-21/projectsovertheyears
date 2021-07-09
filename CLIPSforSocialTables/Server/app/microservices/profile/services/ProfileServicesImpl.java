package microservices.profile.services;
import microservices.profile.persistence.ProfilePersistence;
import microservices.profile.persistence.ProfilePersistenceImpl;
import microservices.profile.types.Beacon;
import microservices.profile.types.Local;
import microservices.profile.types.Profile;
import microservices.profile.types.Response;
import microservices.profile.types.Session;
import java.util.List;

/**
 *file ProfileServicesImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 29/05/2016
 *brief Classe che gestisce i dati relativi al profilo dell'utente.
 *use Implementa i metodi offerti dall'interfaccia ProfileServices, utilizzati per raggruppare e suddividere in maniera logica le componenti grezze fornite dallo strato sottostante (Persistence) e renderle quindi utili per la parte di Business.
 */
public enum ProfileServicesImpl implements ProfileServices{

  INSTANCE;
  
  private ProfilePersistence _profilePersistenceImpl;
  
  /**
  * @name ProfileServicesImpl
  * @desc Costruttore privato;
  * @memberOf Server.Microservices.Profile.Services.ProfileServicesImpl
  */
  private ProfileServicesImpl(){
    _profilePersistenceImpl = new ProfilePersistenceImpl();
  }
  
  /**
  * @name getInstance
  * @desc Metodo statico che ritorna un oggetto che implementa il desighn pattern singleton.
  * @returns {ProfileServicesImpl}
  * @memberOf Server.Microservices.Profile.Services.ProfileServicesImpl
  */ 
  public static ProfileServicesImpl getInstance(){
    return INSTANCE;
  }
  
  @Override
  public boolean changeProfile(Profile userProfile, Profile updateProfile){
    Response<Session> response = _profilePersistenceImpl.getSession(userProfile.getId());
	  Session session = response.getObject();
	  if(session == null)
		  return false;
	  microservices.profile.types.Error error = _profilePersistenceImpl.modifyProfile(updateProfile.getUsername(), session.getId(), null);
    return error == null;
  }
  
  @Override
  public Profile getNewProfile(Session session, Beacon beacon, Profile profile){
    Response<Profile> response = _profilePersistenceImpl.createProfile(profile.getUsername() ,session.getId(), getBeaconId(beacon));
	  return response.getObject();
  }
  
  @Override
  public Profile getProfile(Session session){
    Response<Profile> response = _profilePersistenceImpl.getProfile(session.getId());
	  return response.getObject();
  }
  
  @Override
  public boolean updateLocation(Profile profile, Beacon beacon){
    Response<Session> response = _profilePersistenceImpl.getSession(profile.getId());
	  Session session = response.getObject();
	  if(session == null)
	  	return false;
    
    String beaconString;
    if(beacon != null){
	    beaconString = ""+ getBeaconId(beacon);
	  }else{
	  	beaconString = "NULL";
	  }
    microservices.profile.types.Error error = _profilePersistenceImpl.modifyProfile(null, session.getId(), beaconString);
    return error == null;
  }
  
  @Override
  public List<String> getNameList(Local local){
    return _profilePersistenceImpl.getNameList(local.getId());
  }
  
  @Override
  public Local getLocal(Profile profile){
    return _profilePersistenceImpl.getLocal(profile.getId());
  }
  
  /**
  * @name getBeaconId
  * @desc Restituisce un identificatore che rappresenta univocamente il beacon all'interno del sistema; Memorizza il beacon nel sistema se non era già memorizzato.
  * @param {Beacon} beacon - Oggetto di tipo Beacon;
  * @returns {int}
  * @memberOf Server.Microservices.Profile.Services.ProfileServices
  */ 
  private int getBeaconId(Beacon beacon){
    return _profilePersistenceImpl.getBeaconId(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
  }
}
