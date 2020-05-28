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
	   	private JButton RDVButton = new JButton("Voir les RDV ");
	   	private final JLabel profile_title = new JLabel("Profile : ");
	   	private final JLabel profile = new JLabel("Selectionnez un profile");


	 
	 public MyPatientPage() {
			createWindow(); 
			setListClient();
	        setLocationAndSize();
	        addComponentsToFrame();
	        profile.setText("Selectionnez un profile");
	        this.setVisible(true);
	    }

	    public void createWindow() {
	    	 this.setTitle("My Patient");
	         this.setBounds(400, 150, 500, 500);
	        this.getContentPane().setLayout(null);
	         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         this.setResizable(false);
	     }
	        

	    protected void setLocationAndSize() {

	    	ShowButton.setBounds(20,350,165,23);
			RDVButton.setBounds(250,350,165,23);
			exitButton.setBounds(250,400,100,23);
	    	listScroll.setBounds(20,20,200,150);
	    	profile_title.setBounds(150,50,150,30);

	    	profile.setBounds(250,30,150,300);
	    }    

	    protected void addComponentsToFrame() {
	    	this.add(listScroll);	      
	    	this.add(exitButton);
	    	this.add(profile_title);	
	    	this.add(ShowButton);
			this.add(RDVButton);
			this.add(profile);

			RDVButton.addActionListener(this);
	    	exitButton.addActionListener(this);
	    	ShowButton.addActionListener(this);
	    }
	    
	    protected void getProfile(int index) {    	
	    	String infos;
	    	try {
	    		infos = mySystem.patients.get(index).toString();
		    	
	    	}catch(ArrayIndexOutOfBoundsException aiobe) {
	    		infos = "Aucun profile selectionne !";
	    	}
	    	catch(Exception e) {
	    		infos = "Impossible d'afficher le profile";
	    		e.printStackTrace();
	    	}
	    	
	    	profile.setText(infos);
	    }
	    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitButton){
	            this.dispose();
	            new Psy_GUI();
	        }
		if(e.getSource() == ShowButton && patientList.getSelectedIndex() != -1 );
			/*                                 Affiche nouveau profile quand s�lectionne nouveau nom                    */
	       { 
	            getProfile(patientList.getSelectedIndex());
	       }
		if(e.getSource()==RDVButton && patientList.getSelectedIndex() != -1 ){
			mySystem.current_client_id = patientList.getSelectedIndex();
			this.dispose();
			new Cons_ModifRDV();
		}
	}
}
