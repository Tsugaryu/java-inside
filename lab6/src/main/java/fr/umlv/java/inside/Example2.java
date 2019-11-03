package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Example2 {
  public static void main(String[] args) {
    var scope = new ContinuationScope("scope");
    var contiuation1 = new Continuation(scope, () -> {
      System.out.println("start 1");
      Continuation.yield(scope);
      System.out.println("end 1");
    });
    var contiuation2 = new Continuation(scope, () -> {
      System.out.println("start 2");
      Continuation.yield(scope);
      System.out.println("end 2");
    });
    var list = List.of(contiuation1, contiuation2);
    var copyList=new ArrayDeque<>(list);
    while (!copyList.isEmpty()) {
        var continuation =copyList.poll();
        continuation.run();
        if(!continuation.isDone()) {
          copyList.offer(continuation);
        }
        
      
    }
   
  }
}/*
ConcurrentModificationException e déclenche lorqu'on parcours une liste et qu'on s'amuse à modifier celle-ci
*/
/*
 * Note : Les BDD utilise pour la plupart de la cooperation.
 * Différence entre un thread et une continuation :
 * Dans les deux cas; il y a un runnable que l'on n'exécute qu'UNE SEULE fois.
 * Thread =
 *  on ne gère pas le scheduler.
 *  Concurrence / Non cooperative
 * Continuation =
 *  Un thread peut exécuter pls continuation.
 *  Cooperative
 *  
 */

