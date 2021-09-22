package C;
import java.io.*;
import java.util.*;
import java.awt.Desktop;

public class L {
	//this makes a new file object and using input of 1 or 2 it either writes over the file or deletes it
	//have the user decide where they want the file to go, isn't there a gui for that?
	
	public static void main(String[] args) {
		//attemptFileOpen();
		
		//System.exit(0);
		Scanner n = new Scanner(System.in);
		System.out.println("1 or 2?");
		int num = n.nextInt();
		try {

			File f = new File("test.txt");
			File g = new File("C:/Users/yello/Documents/new.txt");
			if(num == 1) {
				g.createNewFile();
				String s = "test test test";
				BufferedWriter writer = new BufferedWriter(new FileWriter(g));
				writer.write(s);
				writer.close();
				openFile(g);
			}
			else {
				g.delete();
			}
			System.out.println("Does exist: "+g.exists());
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void attemptFileOpen() {
		Desktop d = null;
		File f = new File(System.getenv("programfiles(x86)"));
		if(Desktop.isDesktopSupported()) {
			d = Desktop.getDesktop();
		}
		try {
			d.open(f);
		}
		catch(Exception e) {
			
		}
	}


    private static void openFile(File f) {
        try {
            Runtime.getRuntime().exec("explorer.exe  /select," + f.getAbsolutePath());            
        } catch (Exception ex) {
            System.out.println("Error - " + ex);
        }
    }


}
