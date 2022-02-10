package baselineCustomClasses;
/**
 * The primary error type of AI31, thrown whenever something goes wrong within the game's mechanics.
 * 
 * @author Dong-Yon Kim
 *
 */
@SuppressWarnings("serial")
public class GameException extends Exception {
	private String problem;
	private String errorCode;
	// private static final long serialVersionUID = 191L;
	/**
	 * Creates a new GameErrorException with the given reasoning and the error code. The error code is used to determine the
	 * source of the error.
	 * 
	 * @param cause the cause of the error
	 * @param code the error code of the thrown exception
	 */
	public GameException(String cause, String code) {
		problem = cause;
		errorCode = code;
	}
	@Override
	public String toString() { return super.toString() + ": " + problem + "\nError code: " + errorCode; }

}
