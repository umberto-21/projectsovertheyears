package microservices.profile.types;

public class ErrorImpl implements Error {

  private int _error;

  /**
   * Complete constructor
   * @param error identifier of the error.
   */
  public ErrorImpl(int error){
    _error = error;
  }
  
  /**
   * Getter method
   * @return identifier of the error.
   */
  public int getError(){
    return _error;
  }
}
