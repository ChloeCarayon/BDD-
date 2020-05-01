package UI;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Default_Page extends JFrame {
	protected JLabel background ;
	public Default_Page() {
		
	}
	 protected void createWindow(String title, int x, int y, int width, int height) {
	        this.setTitle(title);
	        this.setBounds(x,y,width,height);
	        this.getContentPane().setBackground(Color.white);
	        this.getContentPane().setLayout(null);  
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setResizable(false);
	    }

	 protected void setLocationAndSize() {
	    
	    }

	 protected void addComponentsToFrame() {
	      
	    }
	protected void addImage(int x, int y) {
		 try {
				BufferedImage img = ImageIO.read(new File("Test.png"));
				background = new JLabel(new ImageIcon(img));
				background.setBounds(x,y,300,310);
				this.add(background);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

}
