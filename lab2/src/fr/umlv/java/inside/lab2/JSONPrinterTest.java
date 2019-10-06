package fr.umlv.java.inside.lab2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class JSONPrinterTest {

	public static class  Alien{
		  private final String planet;
		  private final int age;

		  public Alien(String planet, int age) {
		    if (age <= 0) {
		      throw new IllegalArgumentException("Too young...");
		    }
		    this.planet = Objects.requireNonNull(planet);
		    this.age = age;
		  }
		  @JSONProperty
		  public String getPlanet() {
		    return planet;
		  }
		  @JSONProperty
		  public int getAge() {
		    return age;
		  }
		
		
	} ;
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
	
	public static class Person{
		public final String firstName;
		  private final String lastName;

		  public Person(String firstName, String lastName) {
		    this.firstName = Objects.requireNonNull(firstName);
		    this.lastName = Objects.requireNonNull(lastName);
		  }
		  @JSONProperty
		  public String getFirstName() {
		    return firstName;
		  }
		  @JSONProperty
		  public String getLastName() {
		    return lastName;
		  }
		  
	} ;
	
	@Test
	public void shouldConvertPersonToJSON(){
		 var person = new Person("John", "Doe");
		 System.out.println(toJSON(person));
	}
	@Test
	public void shouldConvertAlienToJSON(){
		
	}
	@Test
	public void shouldNotConvertPersonToJSON(){
		 var person = new Person("John", "Doe");
		 System.out.println(toJSON(person));
	}
	@Test
	public void shouldNotConvertAlienToJSON(){
		
	}

}
