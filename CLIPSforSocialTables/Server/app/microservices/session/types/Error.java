package microservices.session.types;

/**
 *file Error.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Interfaccia che permette la creazione degli errori.
 */
public interface Error{
	
  /**
  * @name getError
  * @desc Metodo che ritorna identificatore dell'errore;
  * @returns {int}
  * @memberOf Server.Microservices.Session.Types.Error
  */ 
  int getError();
}
