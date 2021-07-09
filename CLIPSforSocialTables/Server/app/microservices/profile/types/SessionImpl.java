package microservices.profile.types;

/**
 *file SessionImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Classe che gestisce i dati e le procedure che identificano una sessione.
 *use Implementa i metodi offerti dall'interfaccia Session, utilizzati per la gestione dei dati e le procedure che identificano una sessione.
 */
public class SessionImpl implements Session{

  private int _id;

  /**
  * @name SessionImpl
  * @desc Costruttore completo;
  * @param {int} session - Identificatore della sessione;
  * @memberOf Server.Microservices.Session.Types.SessionImpl
  */ 
  public SessionImpl(int id){
    _id = id;
  }
  
  @Override
  public int getId(){
    return _id;
  }
}
