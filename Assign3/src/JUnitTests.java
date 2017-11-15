import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFileFormat.Type;

import org.jdom2.Document;
import org.junit.Test;

public class JUnitTests {

	@Test
	public void Servertest() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(8000);
			Socket s = ss.accept();
			ss.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}
	}
	@Test
	public void Sockettest() {
	try{
		Socket s = new Socket("localhost", 8000);
			s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}


