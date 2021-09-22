package baselineCustomClasses;

public class GUISetUpException extends Exception {
	private String cause;
	private String errorCode; //letter then four numbers then letter
	public GUISetUpException(String cause, String errorCode) {
		this.cause = cause;
		this.errorCode = errorCode;
	}
	public String toString(){return super.toString()+": "+cause+"\nError code: "+errorCode;}
}
