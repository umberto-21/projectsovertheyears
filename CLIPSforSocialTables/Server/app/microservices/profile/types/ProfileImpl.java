package microservices.profile.types;

/**
 *file ProfileImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 29/05/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un profilo;
 *use Viene utilizzata per gestire le informazioni che rappresentano un profilo;
 */
public class ProfileImpl implements Profile{

  private Integer _id;
  private String _username;

  /**
  * @name ProfileImpl
  * @desc Costruttore completo;
  * @param {String} username - Nome da associare al profilo;
  * @param {Integer} profileId - Identificatore del profilo, null se non si dispone dell'identificatore associato al profilo;
  * @memberOf Server.Microservices.Profile.Types.ProfileImpl
  */ 
  public ProfileImpl(String username, Integer profileId){
    _username = username;
    _id = profileId;
  }
  
  /**
  * @name ProfileImpl
  * @desc Costruttore della classe;
  * @param {String} username - Rappresenta l'username dell'utente;
  * @memberOf Server.Microservices.Profile.Types.ProfileImpl
  */
  public ProfileImpl(String username){
    _username = username;
    _id = null;
  }
  
  @Override
  public Integer getId(){
    return _id;
  }

  @Override
  public String getUsername(){
    return _username;
  }
  
  @Override
  public void setUsername(String username) {
    _username = username;
  }
}
