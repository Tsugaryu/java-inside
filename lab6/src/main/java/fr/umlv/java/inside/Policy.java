package fr.umlv.java.inside;

public enum Policy {
  STACK(1),
  FIFO(2),
  RANDOM(3);

	 
	    public final int value;
	 
	    private Policy(int value) {
	        this.value=value;
	    }
}
