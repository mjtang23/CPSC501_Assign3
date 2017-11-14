import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;

public class Deserializer {
	
	public Object deserialize(Document doc){
		List<Element> objs= new Vector();
		List<Element>fields= new Vector();
		Element root = doc.getRootElement();
		Element obj;
		Element field;
		Object object = null;
		Class cl;
		objs = root.getChildren();
		Field [] objF = null;
		
		for(int i=0; i< objs.size(); i++){
			String name = objs.get(i).getName();
			fields = objs.get(i).getChildren();
			try {
				cl = objs.get(i).getClass().forName(name);
				object = cl.newInstance();
				System.out.println(object.getClass().getName());
				objF = object.getClass().getDeclaredFields();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			}
			
			
			
		}

		
		return object;
	}
	
}
