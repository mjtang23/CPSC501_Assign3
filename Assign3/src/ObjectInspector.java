import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ObjectInspector {
	public void inspect(Object obj, boolean recursive){
		
		   Class ObjClass = getClass(obj);
		   printObjectClass(obj, recursive);
		   printSuperClass(ObjClass, recursive);
		   printInterface(ObjClass, recursive);
		   printMethods(ObjClass, recursive);   
		   printFields(obj, recursive);
		   printConstructors(ObjClass);
		     
	}
	//The main inspector class that takes the input from the TestDriver
	public void printObjectClass(Object obj, Boolean recurse){
		
		    if(obj.getClass().isArray()){
			   Class conType = obj.getClass();
			   while(conType.isArray()){
			    	conType = conType.getComponentType();
			   }
			
			   int length = Array.getLength(obj);
			   System.out.println("inside inspector: " + obj.getClass().getCanonicalName() +  " (recursive = "+recurse+")");
			   System.out.println("\tLength: "  + length + " Component Type: " + conType);
			   Object array;
			   System.out.println("Array elements: ");
			   for(int k = 0; k < length; k++ ){
				   try{
			   	      array = Array.get(obj, k);
			   	      if(array instanceof Object && recurse ==true){
			   	    	inspect(array, recurse);
			   	      }else{
			   	    	  System.out.println(array);
			   	      }
			          
			         }catch(NullPointerException e){
				    	System.out.println(e);
				   }
				
			   }
			
		    }else{
			System.out.println("inside inspector: " + obj.getClass().getCanonicalName() + " (recursive = "+recurse+")");
		   }
		
	
	}
	
 // Returns the class that we explore further into
	public Class getClass(Object obj){
		return obj.getClass();
		
	}
	
	//prints the super class and iterates till it hits a null value
	public void printSuperClass(Class objClass, Boolean recurse){
		System.out.println("Super Class: " + getSClass(objClass));
		SClassLoop(objClass.getSuperclass());	
		
		
	}
	
	//gets the super class name and returns as a string
	public String getSClass(Class objClass){
		return objClass.getSuperclass().getName();
	}
	
	//iterates through the class's super classes till it has reached the end of the hierarchy
	public void SClassLoop(Class superClass){
		if(superClass.getSuperclass() != null){
			System.out.println("\tSuper Class: " + getSClass(superClass));
			SClassLoop(superClass.getSuperclass());
		}else{
			return;
		}
	}
	
	//prints the interfaces associated with class
	public void printInterface(Class objClass, Boolean recurse){
		int a = 0;
		Class interfaces[] = getInterfaces(objClass);
		System.out.print("Implemented interfaces: " );
		if(interfaces.length == 0){
			System.out.println("None");
		}else{
		   while(interfaces.length > (a+1)){
		      System.out.print(interfaces[a].getSimpleName()+ "," + " " );
		      if(interfaces[a].getSuperclass() != null){
		    	  printInterface(interfaces[a].getSuperclass(), recurse);
		    	  
		      }
		      a++;
		   }
		   System.out.println(interfaces[a].getSimpleName());
		   if(getSClass(objClass) != null){
			   printInterface(objClass.getSuperclass(), recurse);
		   }
		}
	}
	
	//returns an array with interfaces associated with class
	public Class[]getInterfaces(Class obj){
		return obj.getInterfaces();
	}
	
	//prints the method associated with the class
	public void printMethods(Class objClass, Boolean recurse){
		int i = 0;
		while(objClass.isArray()){
		  objClass = objClass.getComponentType();
		}
			
     Method [] mArray = getMethodArray(objClass);
		System.out.println("----Methods---- \n" );
		if(mArray.length == 0){
			System.out.println("None");
		}else{
			getMethods(mArray, objClass, recurse);
			if(getInterfaces(objClass).length != 0){
				Class []inter = getInterfaces(objClass);
				//Takes each interface that is implemented 
				//to this class and gathers its methods
				for(int z = 0; z < inter.length; z++){
					mArray = inter[z].getDeclaredMethods();
					getMethods(mArray, inter[z], recurse);
				}
				
			}
		}
	    
	}
	
	//returns the array of methods of all visibility
	public Method[] getMethodArray(Class objClass){
		Method mArray[]= objClass.getDeclaredMethods();
		return mArray;
	}
	
	//returns the methods associated with the class. Including parent methods
	//associated with it
	public void getMethods(Method mArray[], Class obj, Boolean recurse){
		int i = 0;
		while(mArray.length > i){
		      System.out.println("Method Name: "+ mArray[i].getName());
		      Class mExceptions[] = mArray[i].getExceptionTypes();
		      Class params[] = mArray[i].getParameterTypes();
		      for(int x =0; x < mExceptions.length; x++){
		    	  System.out.println("\t Exception: "+ mExceptions[x].getSimpleName());
		      }
		      System.out.print("Parameters:");
		      if(params.length == 0){
		    	  System.out.println(" None required");
		      }
		      else{
		    	  System.out.print("(");
		    	  int y = 0;
		    	  
			      while((y+1) < params.length){
			    	  //handles if the parameter is an array
			    	  if(params[y].isArray()){
			    		
			    		  System.out.print(params[y].getCanonicalName()+ " ,");  
			    		  
			    		 
			    	  }
			    	  System.out.print(params[y].getCanonicalName()+ " ,");
			    	  y++;
			      }
			      System.out.println(params[y].getCanonicalName() + ")");
		      } 
		      System.out.println("Return type: " + mArray[i].getReturnType().getCanonicalName());
		      System.out.println("Method Modifier: " + printModifier(mArray[i])+ "\n");
		      i++;
		      
		}
		if(obj.getSuperclass()!= null){
	    	  getMethods(getMethodArray(obj.getSuperclass()), obj.getSuperclass(), recurse);
		}
	}
	
	//gets the fields associated with the class
	public void printFields(Object obj, Boolean recurse){
		  Field [] fields = obj.getClass().getDeclaredFields();
		  
	      System.out.println("Fields: ");
	      if(fields.length == 0){
	    	  System.out.println("No fields in this class");
	      }else{
	    	  getFields(fields, obj, recurse);
	      }
	      
	}
	
	//gets fields associated from class and its parents. Does not say what
	//was inherited, but just included in the fields
	public void getFields(Field [] fields,Object obj, Boolean recurse){
		for(int z =0; z < fields.length; z++){
			  String fname = fields[z].getName();
			  System.out.println("\tName: " + fname);
	    	  //handles if field value is an array
	    	  if(fields[z].getType().isArray()){
	    		  Class conType = obj.getClass();
				   while(conType.isArray()){
				    	conType = conType.getComponentType();
				   }
				   System.out.println("\tName: " + conType);
				   
				   
			      }else{
			 
	    	 System.out.println("\tType: " + fields[z].getType());
			      }

	    	  System.out.println("\tField Modifier: " + printModifier(fields[z]) );
	    	  
		    	  
				try {
					Class cl = obj.getClass();
 				Field f = cl.getDeclaredField(fname);
 				f.setAccessible(true);
					Object value = f.get(obj);
					
					if(value.getClass().isArray()){
						Class conType = value.getClass();
						   while(conType.isArray()){
						    	conType = conType.getComponentType();
						   }
						
						   int length = Array.getLength(obj);
						   System.out.println("\tName: " + obj.getClass().getCanonicalName() +  " (recursive = "+recurse+")");
						   System.out.println("\t\tLength: "  + length + " Component Type: " + conType);
						   Object array;
						   System.out.println("Array elements: ");
						   for(int k = 0; k < length; k++ ){
							   try{
						   	      array = Array.get(obj, k);
						   	      if(array instanceof Object && recurse ==true){
						   	    	inspect(array, recurse);
						   	      }else{
						   	    	  System.out.println("\tValue: " + array + "\n");
						   	      }
						          
						         }catch(NullPointerException e){
							    	System.out.println(e);
							   }
						   }
					}
					System.out.println("\tValue: " + value + "\n");
					
				} catch (Exception e){
					
					System.out.println("\tValue: null \n");
				} 
				    
				
	      }
		//gathers the superclass fields here
		  Class SCFields = obj.getClass().getSuperclass();
		  while(SCFields != null){
			  fields = SCFields.getDeclaredFields();
			  for(int z =0; z < fields.length; z++){
				  System.out.println("From: " + SCFields);
				  String fname = fields[z].getName();
			      System.out.println("\tName: " + fname);
			      System.out.println("\tType: " + fields[z].getType());
			      System.out.println("\tField Modifier: " + printModifier(fields[z]) );
			  }
		    
		     SCFields = SCFields.getSuperclass();
		  }

		
	}
	//returns an array with fields of all visibility. 
	public Field[] getFieldArray(Class objClass){
		Field fArray[]= objClass.getDeclaredFields();
		return fArray;
	}
	
	//prints out all the constructors associated with the class
	public void printConstructors(Class objClass){
		Constructor construct[] = getConstructor(objClass);
		System.out.println("Constructors:");
	      for(int x =0; x < construct.length; x++){
	    	  System.out.println(" " + construct[x]);
	      }
	}
	
	
	//returns all constructors associated with the class
	public Constructor[] getConstructor(Class objClass){
		return objClass.getDeclaredConstructors();
	}
	
	//returns modifiers for object type Field
	public String printModifier(Field fMod){
		int modCode = fMod.getModifiers();
	    String modifier = Modifier.toString(modCode);
		return modifier;
	}
	
	//returns modifiers for object type Method
 public String printModifier(Method mMod){
	   int modCode = mMod.getModifiers();
	   String modifier = Modifier.toString(modCode);
	   return modifier;
 }
 

}


