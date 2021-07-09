package microservices.profile.types;

/** Class used to store error types. */
public final class ErrorConstants{

  private ErrorConstants() {}

  public static final int UNKNOWN_ERROR = 0;
  public static final int DATABASE_ERROR = 1;
  public static final int DUPLICATE_ENTRY_OR_INVALID_SESSION = 2;
  public static final int INVALID_SESSION = 3;
  public static final int INVALID_PROFILE = 4;
}
