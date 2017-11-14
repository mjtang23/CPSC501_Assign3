
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Receiver {
	
	public static void main(String [] args){
		receiveFile();
		
	}
	
	public static void receiveFile(){
		try{
			ServerSocket ss = new ServerSocket(8000);
			Socket s = ss.accept();
			int bufferSize = s.getReceiveBufferSize();
			DataInputStream din = new DataInputStream(s.getInputStream());
			FileOutputStream fout = new FileOutputStream("recieved.xml");
            byte[] buffer = new byte[512];
			
			while (din.read(buffer, 0 , buffer.length) > -1) {
				fout.write(buffer, 0, buffer.length);
				
			}
			
			fout.close();
			ss.close();
			
//			File inputFile = new File("recieved.xml");
//			SAXBuilder saxBuilder = new SAXBuilder();
//			Document doc = saxBuilder.build(inputFile);
//			deserialized(doc);
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	
//	public static Object deserialized(Document doc){
//		
//		Element root = doc.getRootElement();
//		String name = root.getName();
//		Object obj = new Object();
//		Class classObj = 
//		obj.set
//		
//
//	}
	
	
}