package microservices.profile.types;

/** Class used to hold a response made of an error or an object */
public interface Response <T> {

  /**
   * Getter method.
   * @return object of type T.
   */
  public T getObject();

  /**
   * Getter method.
   * @return object of type error.
   */
  public Error getError();

  /**
   * Tells if the response contains object.
   * @return true if the response contains a not null object of type T, false otherwise.
   */
  public boolean hasObject();

  /**
   * Tells if the response contains an error
   * @return true if the response contains a not null object of type Error, false otherwise
   */
  public boolean hasError();
}
