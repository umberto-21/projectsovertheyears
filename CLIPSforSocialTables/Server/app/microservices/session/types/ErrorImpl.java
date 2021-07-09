package microservices.session.types;

/**
 *file ErrorImpl.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Classe che rappresenta un errore.
 *use Viene usata per segnalare il fallimento di un operazione richiesta;
 */
public class ErrorImpl implements Error {

  private int _error;

  /**
  * @name ErrorImpl
  * @desc Costruttore completo;
  * @param {int} error - Identificatore della tipologia d'errore;
  * @memberOf Server.Microservices.Session.Types.ErrorImpl
  */ 
  public ErrorImpl(int error){
    _error = error;
  }
  
  @Override
  public int getError(){
    return _error;
  }
}
