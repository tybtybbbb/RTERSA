package animation.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import animation.model.Model;
import animation.model.Fork;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Color[] colors= {Color.black,Color.red,Color.blue};
//	public Color[] colors = {
//			Color.orange,
//			Color.magenta,
//			Color.red,
//			Color.cyan
//	};
//	
//	public Color green = new Color(0,128,0);
//	public Color backgroundColor;
	
	// For buffering 
	private Graphics2D bufferGraphics;
	private Image offscreen;
	
    // Dimension of the frame
	private Dimension dim = new Dimension(850, 650); 
	
    // Position of the graphics
	private int x, y;
	
	Model model;

	public View(Model model) throws HeadlessException {
		this.model = model;

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		setSize(dim);
	    this.setVisible(true);
	    
	    this.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            	init();
            }

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentShown(ComponentEvent arg0) {}
        });
	}
	
	private void init () {
		
		
		dim = getSize();
    	x = dim.width / 2 - 40;
		y = 50; 
		
		// Create an offscreen image to draw on 
		offscreen = createImage(dim.width,dim.height); 
		
		// by doing this everything that is drawn by bufferGraphics 
        // will be written on the offscreen image.
        bufferGraphics = (Graphics2D)offscreen.getGraphics();
    	
        
	}
	
	public void createPhilosopher(Graphics2D g2, int x, int y, int d) throws IOException {
		BufferedImage srcImage=ImageIO.read(new File("src/x.jpg"));
		g2.drawImage(srcImage, x,y,40,40,null);
	}
	
	@Override
	public void paint(Graphics g) {
		
		if (bufferGraphics == null)
			return;
		
		// Wipe off everything that has been drawn before 
        // Otherwise previous drawings would also be displayed.
		bufferGraphics.clearRect(0, 0, dim.width, dim.width);
    	
    	this.createFork(bufferGraphics, x, y+20, 1, model.forks[0]);
    	this.createFork(bufferGraphics, x-200, 200+y, 2, model.forks[1]);
    	this.createFork(bufferGraphics, x-150, 400+y, 3, model.forks[2]);
    	this.createFork(bufferGraphics, x+150, 400+y, 4, model.forks[3]);
    	this.createFork(bufferGraphics, x+200, 200+y, 5, model.forks[4]);
    	
    	 try {
 			this.createPhilosopher(bufferGraphics, x-130, y+100, 1);
 			this.createPhilosopher(bufferGraphics, x-175, y+300, 2);
 	    	this.createPhilosopher(bufferGraphics, x, y+430, 3);
 	    	this.createPhilosopher(bufferGraphics, x+175, y+300, 4);
 	    	this.createPhilosopher(bufferGraphics, x+130, y+100, 5);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
     	
        g.drawImage(offscreen,0,0,this);
        
	}
	
	public void createFork(Graphics2D g2, int x, int y, int d, Fork s) {
		g2.fill(new Rectangle2D.Double(35+x, 20+y, 30, 30));
		g2.drawString("Fork" + d, -60+x, 70+y);
		g2.setFont(new Font("Arial", Font.PLAIN, 20));
		g2.setColor(colors[s.color]);
	}
	
  
}
