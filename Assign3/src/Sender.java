
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {
	
	public static void main(String [] args){
		ObjectCreator choice = new ObjectCreator();
		choice.menu();
		Object obj = new Object();
		Vector<Object> objs = choice.getObjs();
		Serializer serial = new Serializer ();
		Document doc = null;
		Enumeration en = objs.elements();
		try{
			while(en.hasMoreElements()){
				obj = en.nextElement();
				doc = serial.serialized(obj);
			}
		
			XMLOutputter serialized = new XMLOutputter(Format.getPrettyFormat());
		    FileOutputStream out = new FileOutputStream("output.xml");  
			serialized.output(doc, out);
			System.out.println("File Saved!");
			sendFile("localhost", 8000, "output.xml");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	}

	
	
	public static void sendFile(String host, int port, String file) {
		try {
			Socket s = new Socket(host, port);
			int bufferSize = s.getReceiveBufferSize();
			FileInputStream din = new FileInputStream(file);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			//BufferedReader br = new BufferedReader(new FileReader(file));
			
			byte[] buffer = new byte[512];
		    int count = -1;
	
		    
		    
			while ((count = din.read(buffer)) > 0 ) {
				dos.write(buffer, 0, count);
				
			}
			
		
			
			din.close();
			dos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
