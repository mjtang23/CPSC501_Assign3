
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {
	public static void main(String [] args){
		A a = new A();
		Class cl = a.getClass();
		Document doc = new Document();
		Object obj;
		try {
			obj = cl.newInstance();
			doc = serialized(obj);
			XMLOutputter serialize = new XMLOutputter(Format.getPrettyFormat());
		    FileOutputStream out = new FileOutputStream("output.xml");  
			serialize.output(doc, out);
			System.out.println("File Saved!");
			sendFile("localhost", 8080, "output.xml");
			
		} catch (InstantiationException | IllegalAccessException e) {			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	}

	public static Document serialized(Object obj){
		
		Element root = new Element("Serialized");
		Document doc = new Document(root);
		
		
		String classObj = obj.getClass().getName();
		Element test = new Element(classObj);
        test.setAttribute(new Attribute("id", "1"));
        Element name = new Element("name");
        Element name2 = new Element("name2");
        name.setText("Writing XML with JDom");
        name2.setText("Writing XML with JDom");
        Element test2 = new Element("TEST");
        
        
        test.addContent(name);
        test2.addContent(name2);
        root.addContent(test);
        root.addContent(test2);
    
		return doc;
	}
	
	
	public static void sendFile(String host, int port, String file) {
		try {
			Socket s = new Socket(host, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			FileInputStream din = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			byte[] buffer;
			
			while ((line) != null) {
				buffer = line.getBytes();
				din.read(buffer);
				dos.write(buffer);
				line = br.readLine();
			}

			din.close();
			dos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
