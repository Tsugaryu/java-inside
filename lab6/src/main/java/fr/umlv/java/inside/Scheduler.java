package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {
	private final Collection<Continuation> waitList;
	private Policy politic;
	private Continuation recorder;
	ArrayDeque<E>
	public enum Policy {
		STACK(1), FIFO(2), RANDOM(3);

		public final int value;

		private Policy(int value) {
			this.value = value;
		}
	}

	/* utilise une arrayList en cas de random donc */
	public Scheduler(Policy politic) {
		Objects.requireNonNull(politic);
		if (politic.value==3) { //random mode
			this.waitList = new ArrayList<Continuation>();
		}
		else { // stack et fifo
			this.waitList = new ArrayDeque<Continuation>();	
		}
		this.politic = politic;
		this.recorder=null;
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
	
	


	private void runLoopFifo() {
		if (this.politic.value != 1)
			return;
		this.recorder=this.waitList.pollFirst();
	}

	private void runLoopStack() {
		if (this.politic.value != 2)
			return;
		this.recorder=this.waitList.pollLast();
	}

	private void runLoopRandom() {
		if (this.politic.value != 3)
			return;
		 this.recorder= this.waitList.remove(ThreadLocalRandom.current().nextInt(this.waitList.size()));
	}

	public void enqueue(ContinuationScope current) {
	
    var continuation = Continuation.getCurrentContinuation(current);
    if (Objects.isNull(continuation)) {
      throw new IllegalStateException("The continuation is already done");
    }  
    this.waitList.offer(continuation);
    Continuation.yield(current);
  
  }
	public void run() {
	
		while (!waitList.isEmpty()) {
			runLoopFifo();
			runLoopStack();
			runLoopRandom();
			this.recorder.run();
		}
	}

	public static void main(String[] args) {
		var scope = new ContinuationScope("scope");
		var scheduler = new Scheduler(Policy.FIFO);
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
