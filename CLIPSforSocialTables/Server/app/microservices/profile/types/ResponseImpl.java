package microservices.profile.types;

public class ResponseImpl <T> implements Response <T>{
  private Error _error;
  private T _object;

  /**
   * Complete constructor.
   * @param object object of type T or null.
   * @param error object of type Error or null.
   */
  public ResponseImpl(T object, Error error){
    _object = object;
    _error = error;
  }

  /**
   * Getter method.
   * @return object of type T
   */
  public T getObject(){
    return _object;
  }

  /**
   * Getter method.
   * @return object of type error.
   */
  public Error getError(){
    return _error;
  }

  /**
   * Tells if the response contains object.
   * @return true if the response contains a not null object of type T, false otherwise.
   */
  public boolean hasObject(){
    return _object != null;
  }

  /**
   * Tells if the response contains an error
   * @return true if the response contains a not null object of type Error, false otherwise
   */
  public boolean hasError(){
    return _error != null;
  }
}
