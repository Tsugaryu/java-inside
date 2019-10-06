package fr.umlv.java.inside.lab2;

import java.lang.reflect.Method;
import java.util.Arrays;
import static java.util.stream.Collectors.joining;
public class JSONPrinter {
	 private final static ClassValue<Method[]> cachedMethod = new ClassValue<>() {
	        @Override
	        protected Method[] computeValue(Class<?> getMethodResult) {
	            return getMethodResult.getMethods();
	        }
	    };
	  public static String toJSON(Person person) {
		    return
		        "{"+System.lineSeparator() +
		        "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
		        "  \"lastName\": \"" + person.getLastName() + "\"\n" +
		        "}"+System.lineSeparator();
		  }
	  private static String propertyName(String name) {
	       return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	  }
	  private Object executeGetter(Object classe, Method method) {
		  try {
	      
			  return method.invoke(classe);
	        
		  } catch (IllegalAccessException e) {
	        	
	            throw new IllegalStateException(e);
	        
	        } catch (InvocationTargetException e) {
	          
	        	var cause = e.getCause();
	            
	        	if ( cause instanceof RuntimeException ) {
	            
	        		throw (RuntimeException) cause;
	            }
	            
	        	if ( cause instanceof Error ) {
	            
	        		throw (Error) cause;
	            }
	            throw new UndeclaredThrowableException(cause);
	        }
	  }
	  public static String toJSON(Object o) {
		  return Arrays.stream(o.getClass().getMethods())
		  	.filter(method->method.getName().startsWith("get"))
		  	.filter(method -> method.isAnnotationPresent(JSONProperty.class))
		  	.map(method -> "\"" + JSONPrinter.propertyName(method.getName()) + "\": \"" + callGetter(o, method) + "\"")
		  	.collect(joining(",","{","}")); 
		  }
	
}


}
