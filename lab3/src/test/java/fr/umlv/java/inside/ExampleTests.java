package fr.umlv.java.inside;

import static java.lang.invoke.MethodHandles.constant;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ExampleTests {
	@Tag("Q1")
	@Test
	public void callingAStaticHelloTest() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method example=Example.class.getDeclaredMethod("aStaticHello", int.class);
		example.setAccessible(true);
		assertEquals(("question " +5), example.invoke(null,5));
				
	}
	@Tag("Q1")
	@Test
	public void callingAnInstanceHelloTest() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method example=Example.class.getDeclaredMethod("anInstanceHello", int.class);
		example.setAccessible(true);
		assertEquals(("question " +5), example.invoke(new Example(),5));
				
	}
	@Tag("Q4")
	@Test
	public void callingAStaticHelloTestWithLookup() throws Throwable {
		var lookup= MethodHandles.lookup();
		var lookupInExample=MethodHandles.privateLookupIn(Example.class, lookup);
		var methodHandle=lookupInExample.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class));
		assertEquals(("question " +5), (String)	methodHandle.invokeExact(5));// o,n fait un caast pour le compilateur car invokeExact a besoin de la signature exact 
	
	}
	@Tag("Q5")
	@Test
	public void callingAnInstanceHelloTestWithLookup() throws Throwable {
		var lookup= lookup();
		var lookupInExample=privateLookupIn(Example.class, lookup);
		var methodHandle=lookupInExample.findVirtual(Example.class, "anInstanceHello", methodType(String.class,int.class));
		assertEquals(("question " +5), (String)	methodHandle.invokeExact(new Example(),5));// o,n fait un caast pour le compilateur car invokeExact a besoin de la signature exact 
	}
	/*findVirtual utilisés pour les méthodes d'instances et les méthodes d'interface, cela est utilisé dans les cas des appels polymorphique
	 * on a pas besoin d'insert pour drop
	 * Il faut créer les appels des fonctions dans l'ordre inverse si on veut faire les opérations en premier Cela est vrai pour toute api non mutable*/
	@Tag("Q6")
	@Test
	public void callingAnInstanceHelloTestWithInsertingArguments() throws Throwable {
	
		var lookup= lookup();
		var lookupInExample=privateLookupIn(Example.class, lookup);
		var methodHandle=lookupInExample.findVirtual(Example.class, "anInstanceHello", methodType(String.class,int.class));
		var methodWithArgate=MethodHandles.insertArguments(methodHandle, 1, Integer.valueOf(8));
		assertEquals(("question " +8), (String)	methodWithArgate.invokeExact(new Example()));// o,n fait un caast pour le compilateur car invokeExact a besoin de la signature exact 
		
	}
	/*
	 * A quoi sert la méthode MethodHandles.insertArguments() ? 
	 * Fabrique un nouveau MethodHandle avec les arguments qui ont été ajoutés dans la méthode
	 * When it is invoked, it receives arguments for any non-bound parameters, binds the saved arguments to their corresponding parameters, and calls the original target.
	 * Each given argument object must match the corresponding bound parameter type. 
	 * the argument object must be a wrapper, and will be unboxed to produce the primitive value.
	 * MethodHandles.insertArguments(target, pos, values)
	 */
	/*Utiliser la méthode MethodHandles.insertArguments pour créer à partir d'un MethodHandle 
	 * créé sur la méthode d'instance anInstanceHello un nouveau méthode handle 
	 * dont l'argument est toujours 8. Ecrire un test Junit permettant de tester ce nouveau method handle
	 * Tous les test doivent être le plus basique.
	 * Note pour moi : La position 0 pour insertArguments c'est la classe pas la première variable
	 */
	
	/*Comment utiliser MethodHandles.dropArguments ?
	Ecrire un nouveau test vérifiant que dropArguments marche correctement. 
	 * Pour utiliser on utilise un dropArguments pour retirer la valeur donné à l'un des paramètres d'une méthode
	 * C
	 */
	@Tag("Q7")
	@Test
	public void callingAnInstanceHelloTestWithDroppingArguments() throws Throwable{
		var lookup= lookup();
		var lookupInExample=privateLookupIn(Example.class, lookup);
		var methodHandle=lookupInExample.findVirtual(Example.class, "anInstanceHello", methodType(String.class,int.class));
		var methodArgDropped=dropArguments(methodHandle, 1, String.class);
		assertEquals(("question " +666), (String)	methodArgDropped.invokeExact(new Example(),"MACHIN",666));// o,n fait un caast pour le compilateur car invokeExact a besoin de la signature exact 
		//invokeExact a maintenant 3 arguments eXAMPLE,sTRING,INT
		}
	/*
	 * n veut utiliser l'un-boxing automatique pour que si l'on appel un method handle avec un Integer, 
	 * celui-ci soit transformer en int.
Ecrivez un test en utilisant la méthode d'instance methodHandle.asType(). 
	 */
	@Tag("Q8")
	@Test
	public void callingAnInstanceHelloTestByUsingIntegerToInt() throws Throwable{
		var lookup= lookup();
		var lookupInExample=privateLookupIn(Example.class, lookup);
		var methodHandle=lookupInExample.findVirtual(Example.class, "anInstanceHello", methodType(String.class,int.class));
		var methodAsType=methodHandle.asType( methodType(String.class,Example.class,Integer.class));
		assertEquals(("question " +666), (String)	methodAsType.invokeExact(new Example(),Integer.valueOf(666)));
		//invokeExact a maintenant 3 arguments eXAMPLE,sTRING,INT
		}
	
/*A quoi sert la méthode MethodHandles.constant ?
Ecrire un nouveau test testant que la méthode MethodHandles.constant fonctionne correctement. 
*/
	@Tag("Q9")
	@Test
	public void callingConstantTest() throws Throwable{
		var methodConstant=constant(String.class, "question 555");
		assertEquals(("question " +555), (String)	methodConstant.invokeExact());
		//invokeExact a maintenant 3 arguments eXAMPLE,sTRING,INT
		
	}
	/*
	 * A quoi sert la méthode MethodHandles.guardWithTest ?
Ecrire un nouveau test testant que la méthode MethodHandle.guardWithTest fonctionne correctement en créant un method handle qui renvoie 1 si le paramètre du méthode handle est bien égal (equals) à "foo" et -1 sinon. 
Guard with test va prendre trois methodHandle, si le premier argument est ok, il execute le deuxieme argument, snon le troisième argument
Note : le type de retour du premier est boolean, et les deux autres arguments doivent avoir la même valeur de retour. Les trois arguments doivent avoir les mêmes paramères
Il faudra donc utiliser dropArgument,InsertArgument pour l'utiliser

Ecrire un nouveau test testant que la méthode MethodHandle.guardWithTest fonctionne correctement en créant un method handle 
qui renvoie 1 si le paramètre du méthode handle est bien égal (equals) à "foo" et -1 sinon. 
	 * */
	@Tag("Q10")
	@Test
	public void callingAnInstanceHelloTestByGuarding() throws Throwable{
		var lookup= lookup();
		var lookupInExample=privateLookupIn(Example.class, lookup);
		var equalsMethodHandle=lookupInExample.findVirtual(String.class, "equals", methodType(boolean.class,Object.class));
		var secondMethodHandle=dropArguments( MethodHandles.constant(int.class, 1), 0, String.class, Object.class);
		var thirdMethodHandle=dropArguments( MethodHandles.constant(int.class, -1), 0, String.class, Object.class);
		var guardMethodHandle = MethodHandles.guardWithTest(equalsMethodHandle, secondMethodHandle, thirdMethodHandle);
		assertEquals(1, (int) MethodHandles.insertArguments(guardMethodHandle, 1, "foo").invoke("foo"));
		assertEquals(-1, (int) MethodHandles.insertArguments(guardMethodHandle, 1, "foo").invoke("toto"));
		}
	
	
	
}
/*
return Arrays.stream(o.getClass().getMethods())
	.filter(method->method.getName().startsWith("get"))
	.filter(method->method.isAnnotationPresent(JSONProperty.class))
	.map(method->JSONPrinter.propertyName(method.getName()+"\\"+callGetter(o,method)))
	.collect(joining(", ","{","}")); 
	getDeclaredMethod(): renvoie toutes les méthodes (dont les méthodes privées)
	method.setAccessible(true)
	a.m
	a= receiver
*/
/*
 * On ne met pas le nom des softs dans le code
 * On met pas normalement de try... catch dans les Tests (principalement dans les test qui vérifie que sa marche
 * ctrl+shift+m = import static; oublie pas ctrl shift o pour placer un max d'import
 * */
/*
 * Slide :
method_reflect
Lookup : En java lorsqu'on cherche à insérer/accéder quelque chose,Il est nécessaire d'avoir la mm visibilité, le mm module. 
Son package : java.lang.inside.MethodeHandles
MethodHandles.lookup(); => contient la configuration de la classe courante. 
Lorsqu'on veut accéder à une méthode privée, on peut aller voir le lookup de la classe qui a une méthode privé pour allé chercher la méthode à l'aide de privateLookupIn 
 findStatic sur un Lookup et invokeExact à l'aide de la class MethodHandle
 */
