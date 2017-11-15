import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class Serializer {
		
		Element root = new Element("serialized");
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
        		//name = f.getName();
        		Element fieldName = new Element("Field");
        		name = classObj.getName();
        		
        	    
//       			
        		try {
        			f.setAccessible(true);
        			Object ref = f.get(obj);
        			Boolean check = f.getType().isPrimitive();
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
       
               			   value = fRef.get(ref);
               			   
               			   
               			   objRef = new Element("Value");
               			   
               			   objRef.setAttribute("name", fRef.getGenericType().getTypeName());
                	       objRef.setText(value.toString());
                           fieldName.addContent(objRef);   
                           
                		}
                	}else{
                		fieldName.setAttribute("name", f.getName());
                		fieldName.setAttribute("declaringclass", name);
                		Element prim = new Element("value");
                		value = f.get(obj);
                		prim.setText(value.toString());
                		fieldName.addContent(prim); 
                		
                	}
        		} catch (IllegalArgumentException | IllegalAccessException e) {
				
        			e.printStackTrace();
        		}
        	
        	
			
        	objectName.addContent(fieldName);
        }
        
        root.addContent(objectName);
    
		return doc;
	}
}
