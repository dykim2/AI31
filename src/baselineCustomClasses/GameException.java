package baselineCustomClasses;

public class GameException extends Exception {
	private String problem;
	private String errorCode;
	//private static final long serialVersionUID = 191L;
	/**
	 * Creates a new GameErrorException with the given reasoning and the error code. The error code is used to determine the source of the error.
	 * @param cause
	 * @param code - the error code of the thrown exception
	 */
	public GameException(String cause, String code){
		problem = cause;
		errorCode = code;
	}
	public String toString(){return super.toString()+": "+problem+"\nError code: "+errorCode;}
	
	
}
