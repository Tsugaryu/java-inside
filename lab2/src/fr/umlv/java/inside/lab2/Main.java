package fr.umlv.java.inside.lab2;

public class Main {
	  public static String toJSON(Person person) {
	    return
	        "{\n" +
	        "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
	        "  \"lastName\": \"" + person.getLastName() + "\"\n" +
	        "}\n";
	  }

	  public static String toJSON(Alien alien) {
	    return 
	        "{\n" + 
	        "  \"planet\": \"" + alien.getPlanet() + "\"\n" + 
	        "  \"members\": \"" + alien.getAge() + "\"\n" + 
	        "}\n";
	  }
	  
	  public static void main(String[] args) {
	    var person = new Person("John", "Doe");
	    System.out.println(toJSON(person));
	    var alien = new Alien("E.T.", 100);
	    System.out.println(toJSON(alien));
	  }
	}