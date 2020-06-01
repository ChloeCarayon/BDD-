package UI;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MyPatientPage extends Default_Page implements ActionListener{ 

	private JButton RDVButton = new JButton("Voir les RDV ");
	   	private final JLabel profile_title = new JLabel("Profile");
	   	private final JLabel profile = new JLabel("Selectionnez un profile");
	 
	 public MyPatientPage() {
		 	
			createWindow("My Patients", 500, 100, 500, 500);
			setListClient();
			
			patientList.setSelectedIndex(0);
			getProfile(0);

	        setLocationAndSize();
	        addComponentsToFrame();
	        
	        this.setVisible(true);			
	        mySystem.backPage=1;
	    }
	        
	    protected void setLocationAndSize() {

			RDVButton.setBounds(40,230,170,23);
			modifButton.setBounds(40,270,170,23);			
			coupleButton.setBounds(40,310,170,23);
			typeButton.setBounds(40,350,170,23);
			professionButton.setBounds(40,390,170,23);
			exitButton.setBounds(40,430,170,23);		
			
	    	listScroll.setBounds(20,30,200,150);
	    	
	    	profile_title.setBounds(320,20,70,30);
	    	profile.setBounds(250,30,230,400);
	    	
	    	profile_title.setFont(new Font("INK FREE", 16, 20));
	    	profile_title.setForeground(new Color(159 , 232 , 85));
	    }    


	    protected void addComponentsToFrame() {
	    	this.add(listScroll);	      
	    	this.add(exitButton);
	    	this.add(profile_title);
			
			this.add(profile);	
			
			if( mySystem.patients.size()>0) {

				this.add(RDVButton);
				addModificationButtons();
			}
			
			RDVButton.addActionListener(e->{
	    		if(patientList.getSelectedIndex() != -1) {
	    			mySystem.current_client_id = patientList.getSelectedIndex();
	    			this.dispose();
	    			new ViewRDV(true);
	    		}
	    	});
			
			exitButton.addActionListener(e->{
	    		 	this.dispose();
		            new Psy_GUI();
	    	});
	    	patientList.addMouseListener(new MouseAdapter() {//R�cup�re les valeurs quand clique sur l'item de la liste
	        	public void mouseClicked(MouseEvent e) {
	        		if (e.getClickCount() == 1 && patientList.getSelectedIndex() != -1 && mySystem.patients.size()>0) {
	        			getProfile(patientList.getSelectedIndex());	  
	        	    	mySystem.user = mySystem.patients.get(patientList.getSelectedIndex());
	        		}
	        			
	        	}
	        });
	   }
	    
	    protected void getProfile(int index) {    	
	    	String infos;
	    	try {    
	   
	    		infos = mySystem.patients.get(index).toString();	    		
		    	mySystem.user = mySystem.patients.get(patientList.getSelectedIndex());
	    	}
	    	catch(ArrayIndexOutOfBoundsException aiobe) {
	    		infos = "Aucun profile selectionne !";
	    	}
	    	catch(IndexOutOfBoundsException iobe) {
	    		infos = "Vous n'avez pas encore de patients";
	    	}
	    	
	    	catch(Exception e) {
	    		infos = "Impossible d'afficher le profile";
	    		//e.printStackTrace();
	    	}
	    	
	    	profile.setText(infos);
	    }
	    
	@Override

	public void actionPerformed(ActionEvent e) {	

	}
}
