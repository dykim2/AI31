package AI31;
import java.util.ArrayList;
import java.util.List;

public abstract class AI31Functions implements AI31Constants{
	public static int[] convertInts(List<Integer> list) {
		int[] vals = new int[list.size()];
		for(int i=0; i<list.size(); i++) {vals[i] = list.get(i);}
		return vals;
	}
	/**
     * Finds whether the object of instance T is contained in the given ArrayList<T>. Uses .equals() for comparison.
     * @param <T> the object type 
     * @param list - the list you want to search
     * @param param - the object you want to find in the list
     * @return true if the object is in the list, false otherwise
     */
	public static <T> boolean contains(ArrayList<T> list, T param) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals(param)) {
				return true;
			}
		}
		return false;
	}
	/**
     * Finds whether the object T is contained in the given T[]. Uses .equals() for comparison.
     * 
     * @param list - the list you want to search
     * @param param - the object you want to find in the list
     * @return true if the object is in the list, false otherwise
     */
	public static <T> boolean contains(T[] list, T param) {
		for(int i=0; i<list.length; i++) {
			if(list[i].equals(param)) {
				return true;
			}
		}
		return false;
	}
}
