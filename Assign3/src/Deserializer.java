import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class Deserializer {
	int i = 0;
	public Object deserialize(Document doc){
		Element root = doc.getRootElement();
		List<Element>children = root.getChildren();
		Constructor c;
		Class cl = null;
		Object obj=null;
		Object fi=null;
		String name = children.get(i).getName();
		
		try {
			cl = children.get(i).getClass().forName(name);
			c = cl.getConstructor();
			obj = c.newInstance();
			if(i+1 < children.size()){;
			
				i++;
			}
			//System.out.println(obj);
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	
			
			List<Element> field = children.get(i).getChildren();
			Field []fields = obj.getClass().getDeclaredFields();
			System.out.println(fields.length);
			for(int y=0; y <fields.length; y++){
				fields[y].setAccessible(true);
				
				String f = field.get(y).getText();
				int test = Integer.parseInt(f);
				fields[y].set(obj, f);
				
				
			}

			
	

		
	}catch(Exception e){
	}
		return obj;
	}
	
}
