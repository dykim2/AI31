package baselineCustomClasses;

public class GUISetUpException extends Exception {
	private String cause;
	private String errorCode; //letter then four numbers then letter
	/**
	 * Creates a new GUISetUpException, occurring when the GUI is not set up properly.
	 * @param cause - the reason for the exception
	 * @param errorCode - the code that desscribes the error
	 */
	public GUISetUpException(String cause, String errorCode) {
		this.cause = cause;
		this.errorCode = errorCode;
	}
	public String toString(){return super.toString()+": "+cause+"\nError code: #"+errorCode;}
}
