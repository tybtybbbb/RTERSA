package animation;

import animation.server.SocketServer;

public class DiningPhilosopher {

	private Interpreter interpreter;
	private SocketServer server;
	
	public DiningPhilosopher() {
		interpreter = new Interpreter();
		server = new SocketServer();
		server.setInterpreter(interpreter);
	}
	
	public void play() {
		interpreter.start();
		server.start();
	}

	public static void main(String[] args) {
		new DiningPhilosopher().play();
	}

}
