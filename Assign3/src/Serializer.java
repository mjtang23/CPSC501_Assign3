import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class Serializer {
		
		Element root = new Element("Serialized");
		Document doc = new Document(root);
		IdentityHashMap ihm = new IdentityHashMap();
		int index = 0;
		String name;
		Class classObj;
		Field [] fields;
		Field f;
		Object value;
		
	public Document serialized(Object obj){
		classObj = obj.getClass();
		String className = classObj.getName();
		Element objectName = new Element(className);
		name = Integer.toString(index);
        objectName.setAttribute("id", name);
        ihm.put(index, objectName);
        index++;
        
        fields = classObj.getDeclaredFields();
        for(int x = 0; x < fields.length; x++){
        	f = fields[x];
        	Type t = f.getType();
        		
        		f.setAccessible(true);
        		name = f.getName();
        		Element fieldName = new Element(name);
        		fieldName.setAttribute("type", f.getGenericType().getTypeName());
        	    
//       			
        		try {
        			f.setAccessible(true);
        			Object ref = f.get(obj);
        			Boolean check = f.getType().isPrimitive();
        			System.out.println(ref.getClass());
        			System.out.println(check);
        			if(!check ){
        				name = Integer.toString(index);
                        fieldName.setAttribute("id", name);
                        ihm.put(index, fieldName);
                        index++;
        			    ref = f.get(obj);
                		String refObj;
                		Element objRef;
                		Field [] refField = ref.getClass().getDeclaredFields();
                		
                		for(int y=0; y < refField.length; y++ ){
                		   Field fRef = refField[y];
                		   fRef.setAccessible(true);
                		   name = fRef.getName();
                		   System.out.println(name);
               			   value = fRef.get(ref);
               			   
               			   
               			   objRef = new Element(name);
               			   objRef.setAttribute("type", fRef.getGenericType().getTypeName());
                	       objRef.setText(value.toString());
                           fieldName.addContent(objRef);
                         
//                			
//                			
                		}
                	}else{
                		value = f.get(obj);
                		fieldName.setText(value.toString());
                	}
        		} catch (IllegalArgumentException | IllegalAccessException e) {
				
        			e.printStackTrace();
        		}
        	
        	
			
        	objectName.addContent(fieldName);
        }
        
//        Element name2 = new Element("name2");
//        name.setText("Writing XML with JDom");
//        name2.setText("Writing XML with JDom");
//        Element test2 = new Element("TEST");
        
        
//        test.addContent(name);
//        test2.addContent(name2);
//        root.addContent(test);
        root.addContent(objectName);
    
		return doc;
	}
}
