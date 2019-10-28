package fr.umlv.java.inside.lab5;

import static java.lang.invoke.MethodHandles.constant;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.guardWithTest;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Arrays;
import java.util.List;

public class StringSwitchExample {
	public static int stringSwitch(String str) {
		if (str.equals("foo")) {
			return 0;

		} else if (str.equals("bar")) {
			return 1;
		} else if (str.equals("bazz")) {
			return 2;
		} else {
			return -1;
		}

	}

	public static int stringSwitch2(String str) {
		switch (str) {
		case "foo":
			return 0;
		case "bar":
			return 1;
		case "bazz":
			return 2;
		default:
			return -1;
		}
	}

	public static MethodHandle createMHFromStrings2(String... strings) {
		var mh = constant(int.class, -1);
		var lookup = lookup();
		MethodHandle equalsMH;
		try {
			equalsMH = lookup.findVirtual(String.class, "equals", methodType(boolean.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError();// made a mistake
		}
		for (var i = 0; i < strings.length - 1; i++) {
			mh = guardWithTest(insertArguments(equalsMH, 1, strings[i]),
					dropArguments(constant(int.class, i), 0, String.class), mh);
		}
		/*
		 * assertEquals(1, (int) MethodHandles.insertArguments(guardMethodHandle, 1,
		 * "foo").invoke("foo")); assertEquals(-1, (int)
		 * MethodHandles.insertArguments(guardMethodHandle, 1, "foo").invoke("toto"));
		 */
		return mh;
	}
	/*
	 * Ecrire le code de la méthode stringSwitch2 en présupposant que la méthode
	 * createMHFromStrings existe.
	 */
	/*
	 * public static int stringSwitch2(String str) { var mh =
	 * createMHFromStrings2("foo", "bar", "bazz"); return mh.invokeExact(str); }
	 */

	public static MethodHandle createMHFromStrings3(String... matches) {
		return new InliningCache(matches).dynamicInvoker();
	}

	static class InliningCache extends MutableCallSite {
		private static final MethodHandle SLOW_PATH;
		static {
			// TODO
			SLOW_PATH = null;
		}

		private final List<String> matches;

		public InliningCache(String... matches) {
			super(MethodType.methodType(int.class, String.class));
			this.matches = List.of(matches);
			setTarget(insertArguments(SLOW_PATH, 0, this));

		}

	private int slowPath(String value) {
      // TODO
     var index=matches.indexOf(value);
     MethodHandle equalsMH;
     var lookup=lookup();
     try {
       equalsMH = lookup.findVirtual(String.class, "equals", methodType(boolean.class, Object.class));
     } catch (NoSuchMethodException | IllegalAccessException e) {
       throw new AssertionError();// made a mistake
     }
     var mh=guardWithTest(insertArguments(equalsMH, 1, value),
         dropArguments(constant(int.class, index), 0, String.class), .setTarget());
     setTarget(mh);
     return index;
     
    }

	}

	/*
	 * Question 5 : La meilleur implémentation consiste à écrire les nouveaux
	 * guardWithTest après les derniers guardWithTest.
	 * 
	 */

}
