package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Rdv extends JFrame implements ActionListener{

	 public Rdv() {
	        createWindow();
	        setLocationAndSize();
	        addComponentsToFrame();
	    }

	    public void createWindow() {
	    	 this.setTitle("My Rdv");
	         this.setBounds(400, 150, 400, 500);
	         this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
	         this.getContentPane().setLayout(null);
	         this.setVisible(true);
	         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         this.setResizable(false);
	    }

	    public void setLocationAndSize() {
	       
	    }

	    public void addComponentsToFrame() {
	        
	    }
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
