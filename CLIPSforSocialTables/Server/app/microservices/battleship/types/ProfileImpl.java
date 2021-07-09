package microservices.battleship.types;

import com.google.gson.Gson;

/**
 *file ProfileImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 07/06/2016
 *brief Interfaccia che gestisce le informazioni che rappresentano un profilo;
 *use Viene utilizzata per gestire le informazioni che rappresentano un profilo;
 */
public class ProfileImpl implements Profile{
  private static final String TAG = "Profile";
  private Integer _id;
  private String _username;

  /**
  * @name ProfileImpl
  * @desc Costruttore completo;
  * @param {String} username - Nome da associare al profilo;
  * @param {Integer} profileId - Identificatore del profilo, null se non si dispone dell'identificatore associato al profilo;
  * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
  */ 
  public ProfileImpl(String username, Integer profileId){
    String methodSignature = ".ProfileImpl(" + (new Gson()).toJson(username) + ", " + (new Gson()).toJson(profileId) +")";
 //play.Logger.info(TAG + methodSignature);
    _username = username;
    _id = profileId;
 //play.Logger.info(TAG + methodSignature + " return: costruttore");
  }
  
  /**
  * @name ProfileImpl
  * @desc Costruttore della classe;
  * @param {String} username - Rappresenta l'username dell'utente;
  * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
  */
  public ProfileImpl(String username){
    String methodSignature = ".ProfileImpl(" + (new Gson()).toJson(username) + ", " +")";
 //play.Logger.info(TAG + methodSignature);
    _username = username;
    _id = null;
 //play.Logger.info(TAG + methodSignature + " return: costruttore");
  }
  /**
   * @name getId
   * @desc Ritorna l'identificatore del profilo, null se nessun identificatore è associato al profilo;
   * @returns {Integer}
   * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
   */
  @Override
  public Integer getId(){
   //play.Logger.info(TAG + ".getId()");
   //play.Logger.info(TAG + ".getId() return: " + (new Gson()).toJson(_id));
      return _id;
  }

  /**
   * @name getUsername
   * @desc Ritorna il nome associato al profilo;
   * @returns {String}
   * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
   */
  @Override
  public String getUsername(){
   //play.Logger.info(TAG + ".getUsername()");
   //play.Logger.info(TAG + ".getUsername() return: " + (new Gson()).toJson(_username));
      return _username;
  }

  /**
   * @name setUsernames
   * @desc Setta l'username dell'utente.
   * @returns {void}
   * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
   */
  @Override
  public void setUsername(String username) {
   //play.Logger.info(TAG + ".setUsername(" + (new Gson()).toJson(username) + ")");
      _username = username;
   //play.Logger.info(TAG + ".setUsername(" + (new Gson()).toJson(username) + ") return: void");

  }
  /**
   * @name equals
   * @desc Dice se i due oggetti sono uguali.
   * @param {ProfileImpl} p - Rappresenta il profilo da confrontare.
   * @returns {boolean}
   * @memberOf Server.Microservices.Battleship.Types.ProfileImpl
   */
  @Override
  public boolean equals(Profile p) {
      return (this.getId().equals(p.getId()))&&(this.getUsername().equals(p.getUsername()));
  }
}
