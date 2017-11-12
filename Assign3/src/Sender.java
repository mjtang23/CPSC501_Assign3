
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Sender {
	public static void main(String [] args){
		Test a = new Test();
		Class cl = a.getClass();
		Document doc = new Document();
		Object obj;
		try {
			obj = cl.newInstance();
			doc = serialized(obj);
			XMLOutputter serialize = new XMLOutputter();
		    FileOutputStream out = new FileOutputStream("output.xml");  
			serialize.output(doc, out);
			System.out.println("File Saved!");
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
		Document doc = new Document();
		doc.setRootElement(root);

		return doc;
	}
}
