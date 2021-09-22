package C;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class B {
	public static void main(String[] args) {
		try {
		RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
	    FileChannel inChannel = aFile.getChannel();

	    ByteBuffer buf = ByteBuffer.allocate(48);

	    int bytesRead = inChannel.read(buf);
	    while (bytesRead != -1) {

	      System.out.println("Read " + bytesRead);
	      buf.flip();

	      while(buf.hasRemaining()){
	          System.out.print((char) buf.get());
	      }

	      buf.clear();
	      bytesRead = inChannel.read(buf);
	    }
	    aFile.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}
