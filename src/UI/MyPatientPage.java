package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MyPatientPage extends Default_Page implements ActionListener{
	
	   private JButton ShowButton = new JButton("Voir le profile");
	   
	   private final JLabel profile_title = new JLabel("Profile : ");
	   private final JLabel profile = new JLabel("Selectionnez un profile");

	 
	 public MyPatientPage() {
			createWindow();
			setListClient();
	        setLocationAndSize();
	        addComponentsToFrame();
	        this.setVisible(true);
	    }

	    public void createWindow() {
	    	 this.setTitle("My Patient");
	         this.setBounds(400, 150, 500, 500);
	         this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
	         this.getContentPane().setLayout(null);	       
	         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         this.setResizable(false);
	     }
	        

	    protected void setLocationAndSize() {
	    	exitButton.setBounds(300,350,100,23);
	    	ShowButton.setBounds(100,350,165,23);
	    	listScroll.setBounds(100,100,100,100); 
	    	profile_title.setBounds(150,50,150,30);
	    	profile.setBounds(250,30,150,300);
	    }
	    
	    private void getProfile() {
	    	int index = patientList.getSelectedIndex(); 
	    	String infos;
	    	try {
	    		infos = mySystem.patients.get(index).toString();
		    	
	    	}catch(ArrayIndexOutOfBoundsException aiobe) {
	    		infos = "Aucun profile selectionn� !";
	    	}
	    	catch(Exception e) {
	    		infos = "Impossible d'afficher le profile";
	    	}
	    	
	    	profile.setText(infos);
	    }
	    
	    protected void addComponentsToFrame() {
	    	this.add(listScroll);	      
	    	this.add(exitButton);
	    	this.add(profile_title);	
	    	this.add(ShowButton);
	    	this.add(profile);
	    	
	    	exitButton.addActionListener(this);
	    	ShowButton.addActionListener(this);
	    }
	    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitButton){
	            /*                                   EXIT                                      */
	            this.dispose();
	            new Psy_GUI();
	        }	
		
		if(e.getSource() == ShowButton && patientList.getSelectedIndex() != -1 );
			/*                                 Affiche nouveau profile quand s�lectionne nouveau nom                    */
	       { 
	            getProfile();
	       }
	}
}
