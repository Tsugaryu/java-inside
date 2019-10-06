package fr.umlv.java.inside.lab1;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import fr.umlv.java.inside.lab1.SwitchExample;

public class SwitchExampleTest {


 
  @DisplayName("Test of the switch function")
  @Tag("switch")
  @Test
  public static void switchExampleTest(String s) {
       assertEquals(1, SwitchExample.switchExample("cat"));
       assertEquals(2, SwitchExample.switchExample("dog"));
       assertEquals(4, SwitchExample.switchExample("cats n dogs"));
       /*
       assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
                  + "to avoid constructing complex messages unnecessarily.");
       assertNotNull(firstName);*/
  }

}
