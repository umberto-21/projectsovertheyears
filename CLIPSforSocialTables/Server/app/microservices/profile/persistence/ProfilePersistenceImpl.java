package microservices.profile.persistence;
import microservices.profile.persistence.dao.DaoFactory;
import microservices.profile.persistence.dao.ProfileDao;
import microservices.profile.types.Error;
import microservices.profile.types.Profile;
import microservices.profile.types.Response;
import microservices.profile.types.Session;
import microservices.profile.types.Local;
import java.util.List;

/**
 *file ProfilePersistenceImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 28/05/2016
 *brief Classe che gestisce i dati elementari relativi al profilo dell'utente, quindi ha il compito di interfacciarsi e comunicare direttamente con il database utilizzato.
 *use Implementa i metodi offerti dall'interfaccia ProfilePersistence, utilizzati per interfacciarsi e comunicare direttamente con il database utilizzato.
 */
public class ProfilePersistenceImpl implements ProfilePersistence{

  private DaoFactory _daoFactory;

  /**
  * @name ProfilePersistenceImpl
  * @desc Costruttore completo;
  * @memberOf Server.Microservices.Profile.Persistence.ProfilePersistenceImpl
  */ 
  public ProfilePersistenceImpl(){
    _daoFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
  }

  @Override
  public Response<Profile> createProfile(String username, int sessionId, Integer beaconId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.createProfile(username, sessionId, beaconId);
  }

  @Override
  public Response<Profile> getProfile(int sessionId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.getProfile(sessionId);
  }
  
  @Override
  public Error deleteProfile(int sessionId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.deleteProfile(sessionId);
  }

  @Override
  public Error modifyProfile(String newUsername, int sessionId, String newBeaconId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.modifyProfile(newUsername, sessionId, newBeaconId);
  }
  
  @Override
  public Response<Session> getSession(int profileId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.getSession(profileId);
  }
  
  @Override
  public List<String> getNameList(int localId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.getNameList(localId);
  }
  
  @Override
  public int getBeaconId(String uuid, int major, int minor){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.getBeaconId(uuid, major, minor);
  }
  
  @Override
  public Local getLocal(int profileId){
    ProfileDao profileDao = _daoFactory.getProfileDao();
    return profileDao.getLocal(profileId);
  }
}