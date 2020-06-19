package animation;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import animation.model.Model;
import animation.view.View;

public class Interpreter extends Thread {

	private BlockingQueue<String> queue  = new PriorityBlockingQueue<String>();
	private View view;
	boolean debug = false;
	
	Model model = new Model();
	
	public Interpreter() {
		super();
	}
	
	@Override
	public void run() {
		super.run();	
		
		view = new View(model);
		view.repaint();
			
		Timer timer = new Timer();
    	TimerTask myTask = new TimerTask() {
    	    @Override
    	    public void run() {
    	    	pollingEvents();	
    	    }
    	};
    	
    	timer.schedule(myTask, 500, 500);
	    		
	}
	
	public void pollingEvents() {
		String event;
    	
    	while ((event = queue.poll()) != null) {
    		
    		if (debug)
    			System.out.println(event);
			
			String[] fields = event.split("[|]");

    		if (fields.length < 2) {
    			System.err.println("malformed event");
    			continue;
    		}
    		
    		int id = Integer.valueOf(fields[0]);
    		int color = Integer.valueOf(fields[1]);
    		model.forks[id].color=color;
    	}
    	view.repaint();
    	
	}

	public void pushEvent(String event) {
		if (!queue.offer(event))
			System.out.println("Impossible to insert into queue");
	}
}
