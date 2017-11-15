
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;


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
            int count = -1;
			while ((count = din.read(buffer)) > 0) {
				fout.write(buffer, 0, count);
				
			}
			
			fout.close();
			ss.close();
			
			
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build("recieved.xml");
			Deserializer deserial = new Deserializer();
			Element root = doc.getRootElement();
			List<Element>children = root.getChildren();
			ObjectInspector insp = new ObjectInspector();
			Object obj;
			
			for(int i=0; i < children.size(); i++){
				obj = deserial.deserialize(doc);
				insp.inspect(obj, true);
			}
			
			
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	

	
	
}