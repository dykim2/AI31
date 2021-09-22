package baselineCustomClasses;

public class PlayingCardException extends Exception {
	private String problem;
	//private static final long serialVersionUID = 185L;
	public PlayingCardException(String issue){
		problem = issue;
	}
	public String toString(){return super.toString()+": "+problem;}	
}
