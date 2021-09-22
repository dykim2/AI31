package baselineCustomClasses;

public class GameErrorException extends Exception {
	private String problem;
	//private static final long serialVersionUID = 191L;
	public GameErrorException(String cause){
		problem = cause;
	}
	public String toString(){return super.toString()+": "+problem;}
	
	
}
