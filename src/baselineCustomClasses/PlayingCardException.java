package baselineCustomClasses;

public class PlayingCardException extends Exception {
	private String problem;
	private String errorCode;
	//private static final long serialVersionUID = 185L;
	/**
	 * Creates a new checked PlayingCardException.
	 * @param issue - what went wrong in the program
	 * @param code - the specific error code, so it is more clear what went wrong
	 */
	public PlayingCardException(String issue, String code){
		problem = issue;
		errorCode = code;
	}
	public String toString(){return super.toString()+": "+problem+"\nError code: #"+errorCode;}	
}
