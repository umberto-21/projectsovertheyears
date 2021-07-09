package microservices.session.types;

/**
 *file ErrorConstants.java
 *author Filinesi Skrypnyk Oleksandr
 *date 26/05/2016
 *brief Classe che contiene le tipologie di errori.
 *use Le classi che si occupano della gestione di errori fanno riferimento a questa classe per specificare la tipologia di errore.
 */
public final class ErrorConstants{


  private ErrorConstants() {}
  //Errore che non rientra in nessuna delle altre tipologie d'errore.
  public static final int UNKNOWN_ERROR = 0;
  
  //Rapresenta un errore che si è verificato nel database.
  public static final int DATABASE_ERROR = 1;
  
  //La sessione indicata non esiste nel database.
  public static final int INVALID_SESSION = 2;
}
