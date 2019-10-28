package fr.umlv.java.inside.lab5;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.util.function.ToIntFunction;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class StringSwitchExampleTest {

  @Test
  public void expectReturnOK() {
    assertAll("string", () -> assertEquals(0, StringSwitchExample.stringSwitch("foo")),
        () -> assertEquals(2, StringSwitchExample.stringSwitch("bazz")),
        () -> assertEquals(1, StringSwitchExample.stringSwitch("bar")),
        () -> assertEquals(-1, StringSwitchExample.stringSwitch("toto")));

  }
  

  
  
  
  
  
  
  
  /*
  @ParameterizedTest
  @MethodSource("switchProvider")
  void testWithExplicitLocalMethodSource(ToIntFunction<String> argument) {
    assertEquals(0, argument.applyAsInt("foo"));
  }

  static ToIntFunction<String> switchProvider() {

    return str -> StringSwitchExample.stringSwitch(str);

  }
*/
}
/*
 * les méthodes peuvent être vus comme des objets
 */
