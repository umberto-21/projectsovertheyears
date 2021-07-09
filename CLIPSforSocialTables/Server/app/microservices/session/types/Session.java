package microservices.session.types;

/**
 *file Session.java
 *author Ugo Padoan
 *date 26/05/2016
 *brief Interfaccia che gestisce i dati e le procedure che identificano una sessione.
 *use Viene utilizzata per gestire i dati e le procedure che identificano una sessione.
 */
public interface Session{

  /**
  * @name getSession
  * @desc Ritorna l'identificatore della sessione.
  * @returns {int}
  * @memberOf Server.Microservices.Session.Types.Session
  */ 
  int getId();
}
