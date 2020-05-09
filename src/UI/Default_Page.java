package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import com.toedter.calendar.JCalendar;

public class Default_Page extends JFrame {
	
///Variables pour les listes clients
	protected JScrollPane listScroll;
	protected JList<String> patientList;
	protected final DefaultListModel<String> list = new DefaultListModel<>();

///Variables pour la cr�ation d'un calendrier 
	protected JLabel background ;
	protected JCalendar calendar =  new JCalendar();
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

///Boutons courrants
	protected JButton exitButton = new JButton("Exit");
	protected JButton backButton=new JButton("Back");
	
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

	 protected void 
	 addComponentsToFrame() {
	      
	    }
	 
	 protected void setListClient() {
	    	String patient;     		
 		try {

 			for (int i=0; i<mySystem.patients.size();i ++)  {//commence liste a 1 pour pas avoir la psy dans les clients
 	    		patient = mySystem.patients.get(i).getNom();
 	    		patient += "  "; 
 	    		patient += mySystem.patients.get(i).getPrenom();
 	    		list.addElement(patient);
 	    	}	 
 			if(mySystem.patients.size()<1) {
 				list.addElement("Vous n'avez pas encore de clients !");
 			}
 		}catch(IndexOutOfBoundsException e) {
 			list.addElement("Vous n'avez pas encore de clients !");
 		}catch(Exception e) {
 			list.addElement("Impossible d'afficher les clients");
 		}
	    	PrintListClient();
	   }
	    
	   protected void PrintListClient() {
	    	patientList = new JList<>(list);
	    	patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        patientList.setVisibleRowCount(-1);
	        listScroll = new JScrollPane(patientList);	          
	    }
	 
	 protected void addCalendar() {
		 Container contentPane = getContentPane();
	        contentPane.setLayout(null);
	
	        JCalendar calendar4 =
	        		new JCalendar(
	        		    Locale.FRENCH,
	        		    false);   

	        Border etchedBorder =
	        		BorderFactory.createEtchedBorder();
	        	    Border emptyBorder =
	        		BorderFactory.createEmptyBorder(10, 10, 10, 10);
	        	    Border compoundBorder =
	        		BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);
	      
	        	    calendar4.setBorder(compoundBorder);
	        	    
	        	    
	        	    this.add(calendar4);
	       
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
