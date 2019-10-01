package fr.umlv.java.inside.lab2;

import java.lang.reflect.Method;
import java.util.Arrays;
import static java.util.stream.Collectors.joining;
public class JSONPrinter {
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
	  public static String toJSON(Object o) {
		 return Arrays.stream(o.getClass().getMethods())
		  		.filter(method->method.getName().startsWith("get"))
		  		.map(method->JSONPrinter.propertyName(method.getName()))
		  		.collect(joining(", ","{","}")); 
	  }
}
/*
 * Écrire une méthode toJSON qui prend en paramètre un Object, 
 * utilise la réflexion pour accéder à l'ensemble des méthodes publiques de la classe de l'objet (Object.getClass()
 *  puis java.lang.Class.getMethods), sélectionne les getters, puis renvoie le nom des propriétés (pour l'instant).
Le nom d'une propriété peut s'obtenir à partir du nom du getter en utilisant la fonction suivante (en supposant 
que votre getter s'appelle bien getSomething):  
 * 
 * */
 