package Utilities;

public interface Command extends Action<Void, Void> {
	  default Void execute(Void v) {
	    execute();
	    return null;
	  }

	  void execute();
	}