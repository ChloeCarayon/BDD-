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

public class MyPatientPage extends JFrame implements ActionListener{
	
	   private JScrollPane listScroll;
	   private JList<String> patientList;
	   private final DefaultListModel<String> list = new DefaultListModel<>();
	   private JButton ExitButton=new JButton("Exit");
	   private JButton ShowButton = new JButton("Voir le profile");
	   
	   private final JLabel profile_title = new JLabel("Profile : ");
	   private final JLabel profile = new JLabel("Selectionnez un profile");

	 
	 public MyPatientPage() {
			createWindow();
			setList();
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
	    
	    private void setList() {
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
	    	PrintList();
	    }
	    
	    private void PrintList() {
	    	patientList = new JList<>(list);
	    	patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        patientList.setVisibleRowCount(-1);
	        listScroll = new JScrollPane(patientList);	          
	    }

	    private void setLocationAndSize() {
	    	ExitButton.setBounds(300,350,100,23);
	    	ShowButton.setBounds(100,350,165,23);
	    	listScroll.setBounds(100,100,100,100); 
	    	profile_title.setBounds(300,50,150,30);
	    	profile.setBounds(280,80,150,150);
	    }
	    
	    private void getProfile() {
	    	int index = patientList.getSelectedIndex(); 
	    	String infos;
	    	try {
	    		infos = "<html> Nom :  "; 
		    	infos += mySystem.patients.get(index).getNom(); 
		    	infos += "<br> <br> Prénom  " ;
		    	infos += mySystem.patients.get(index).getPrenom(); 
		    	infos += "<br> <br>  Email :  " ;
		    	infos += mySystem.patients.get(index).getEmail();
		    	
		    	if( mySystem.patients.get(index).getSexe())
		    		infos +="<br><br>  Sexe :  " +"Homme"+"</html>";  
		    	else 
		    		infos += "<br><br>  Sexe :  " +"Femme"+"</html>"; 
		    	
	    	}catch(ArrayIndexOutOfBoundsException aiobe) {
	    		infos = "Aucun profile selectionné !";
	    	}
	    	catch(Exception e) {
	    		infos = "Impossible d'afficher le profile";
	    	}
	    	
	    	profile.setText(infos);
	    }
	    
	    private void addComponentsToFrame() {
	    	this.add(listScroll);	      
	    	this.add(ExitButton);
	    	this.add(profile_title);	
	    	this.add(ShowButton);
	    	this.add(profile);
	    	
	    	ExitButton.addActionListener(this);
	    	ShowButton.addActionListener(this);
	    }
	    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ExitButton){
	            /*                                   EXIT                                      */
	            this.dispose();
	            new Psy_GUI();
	        }	
		
		if(e.getSource() == ShowButton && patientList.getSelectedIndex() != -1 );
			/*                                 Affiche nouveau profile quand sélectionne nouveau nom                    */
	       { 
	            getProfile();
	       }
	}
}
