package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

class SchedulerTest {

	@Test
	  public void testSchedulerWithNull() {
	    assertThrows(NullPointerException.class, () -> new Scheduler(null));
	 }
	@Test
	public void testPullWithStack(){

	    var scheduler = new Scheduler(Policy.STACK);
	    var scope = new ContinuationScope("fifo");
	    var joiner = new StringJoiner(System.lineSeparator());
	    var joinerToCheck=new StringJoiner(System.lineSeparator());
	    
	    joinerToCheck.add("start 1");
	    joinerToCheck.add("start 2");
	    joinerToCheck.add("middle 1");
	    joinerToCheck.add("middle 2");
	    joinerToCheck.add("end 1");
	    joinerToCheck.add("end 2");

	    expectedStringBuilder.append("start 1\n").append("start 2\n");
	    expectedStringBuilder.append("middle 2\n").append("end 2\n");
	    expectedStringBuilder.append("middle 1\n").append("end 1\n");


	    var continuation1 = new Continuation(scope, () ->
	    {
	      joiner.add("start 1");
	      scheduler.enqueue(scope);
	      joiner.add("middle 1");
	      scheduler.enqueue(scope);
	      joiner.add("end 1");
	    });

	    var continuation2 = new Continuation(scope, () ->
	    {
	        joiner.add("start 2");
		      scheduler.enqueue(scope);
		      joiner.add("middle 2");
		      scheduler.enqueue(scope);
		      joiner.add("end 2");
	    });
	    var list = List.of(continuation1, continuation2);
	    list.forEach(Continuation::run);
	    scheduler.runLoop();
	    assertEquals(joinerToCheck.toString(), joiner.toString());

	}
	@Test
	public void testPullWithFifo(){
		
		    var scheduler = new Scheduler(Policy.FIFO);
		    var scope = new ContinuationScope("fifo");
		    var joiner = new StringJoiner(System.lineSeparator());
		    var joinerToCheck=new StringJoiner(System.lineSeparator());
		    
		    joinerToCheck.add("start 1");
		    joinerToCheck.add("start 2");
		    joinerToCheck.add("middle 1");
		    joinerToCheck.add("middle 2");
		    joinerToCheck.add("end 1");
		    joinerToCheck.add("end 2");


		    var continuation1 = new Continuation(scope, () ->
		    {
		      joiner.add("start 1");
		      scheduler.enqueue(scope);
		      joiner.add("middle 1");
		      scheduler.enqueue(scope);
		      joiner.add("end 1");
		    });

		    var continuation2 = new Continuation(scope, () ->
		    {
		        joiner.add("start 2");
			      scheduler.enqueue(scope);
			      joiner.add("middle 2");
			      scheduler.enqueue(scope);
			      joiner.add("end 2");
		    });
		    var list = List.of(continuation1, continuation2);
		    list.forEach(Continuation::run);
		    scheduler.runLoop();
		    assertEquals(joinerToCheck.toString(), joiner.toString());
		  
	}
	@Test
	public void testRandomPull() {
		
	}
}
