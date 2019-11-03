package fr.umlv.java.inside;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;

public class OldScheduler {
	private final ArrayDeque<Continuation> waitList;
	
	/* utilise une arrayList en cas de random donc */
	public Scheduler() {
			this.waitList = new ArrayDeque<Continuation>();
		}

	/*
	 * Note : dans les threads la priorité n'est pas prise en compte. Créer une
	 * classe Scheduler
	 * 
	 * 
	 * qui possède une méthode runLoop qui en boucle, décide parmis les continuation
	 * enregistrées (les continuations en attente) quelle va être la prochaine
	 * continuation à exécuter et exécute celle-ci et s'arrête si il n'y a plus de
	 * continuation à exécuter.
	 * 
	 * Pour l'instant on va dire que lorsque l'on doit choisir parmis plusieurs
	 * continuations, on prend la dernière qui s'est enregistrée.
	 */
	/*
	 * enqueue devient un wrapper qui redirige vers la bonne méthode enqueue
	 * utilisée en fonction de la classe politic
	 */
	
	public void enqueue(ContinuationScope current) {
		 
	    var continuation = Continuation.getCurrentContinuation(current);
	    if (Objects.isNull(continuation)) {
	      throw new IllegalStateException("The continuation is already done");
	    }
	    this.waitList.offer(continuation);
	    Continuation.yield(current);
	  }

	public void runLoop() {
		while (!waitList.isEmpty()) {
			var continuation = waitList.poll();
			continuation.run();
		}
	}

	public static void main(String[] args) {
		var scope = new ContinuationScope("scope");
		var scheduler = new Scheduler();
		var continuation1 = new Continuation(scope, () -> {
			System.out.println("start 1");
			scheduler.enqueue(scope);
			System.out.println("middle 1");
			scheduler.enqueue(scope);
			System.out.println("end 1");
		});
		var continuation2 = new Continuation(scope, () -> {
			System.out.println("start 2");
			scheduler.enqueue(scope);
			System.out.println("middle 2");
			scheduler.enqueue(scope);
			System.out.println("end 2");
		});
		var list = List.of(continuation1, continuation2);
		list.forEach(Continuation::run);
		scheduler.runLoop();
	}
}

}
