package animation.model;

public class Model {
	
	public Fork forks[] = {
			new Fork(), // fork occupied
			new Fork(), // fork released
			new Fork(), // philosopher got neither
			new Fork(), // philosopher got left
			new Fork() // philosopher got right
		};
}
