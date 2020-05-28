package UI;

import java.sql.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public final class Registration extends Default_Page implements ActionListener {
    boolean LogorRegis;
    private final JLabel nomLabel = new JLabel("Nom");
    private final JLabel prenomLabel = new JLabel("Prenom");
    private final JLabel emailLabel = new JLabel("Mail");
    private final JLabel passwordLabel = new JLabel("Password");
    private final String[] sexe = {"Homme", "Femme"};
    private final JLabel sexeLabel = new JLabel("Sexe");
    private final String[] pub = {"BaO", "autre patient", "docteur", "Pages Jaunes", "Internet", "autre"};
    private final String[] type = {"Adulte", "Ado", "Enfant"};
    private final JLabel pubLabel = new JLabel("Pub");
    private final JLabel typeLabel = new JLabel("Type");
    private final JLabel coupleLabel = new JLabel("En couple");

    private final JLabel IdLabel = new JLabel("ID");

    private JTextField IdField = new JTextField();
    private JTextField nomTextField = new JTextField();
    private JTextField prenomTextField = new JTextField();
    private JTextField emailText = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    
    private  JComboBox<String> PubComboBox = new JComboBox<>(pub);   
    private  JComboBox<String> SexeComboBox =  new JComboBox<>(sexe);
    private  JComboBox<String> TypeComboBox =  new JComboBox<>(type);
    private final JCheckBox checkCouple = new JCheckBox("Oui");
    private final JCheckBox checkCelib = new JCheckBox("Non");

    private boolean choice_celib, choice_couple;
    
    private JButton registerButton = new JButton("REGISTER");
    private JButton modifButton = new JButton("MODIFIER");

    
    public Registration(boolean choice, int id) {
        LogorRegis = choice;
        if (!LogorRegis)
        	  createWindow("Log In", 500, 100, 380, 250);
        else {
        	createWindow("Register", 500, 100, 380, 550);  
        	  if(id>0) {
              	modifPage();
              }
              else 
              	registerPage();
        }        	     
        
        setLocationAndSize();
        addComponentsToFrame();   
        this.setVisible(true);
    }

    protected void setLocationAndSize() {  

        if (LogorRegis) { //Register et modification
            nomLabel.setBounds(20, 20, 70, 70);
            prenomLabel.setBounds(20, 60, 70, 70);
            passwordLabel.setBounds(20, 100, 70, 70);
            emailLabel.setBounds(20, 140, 70, 70);
            typeLabel.setBounds(20,290,70,70);
            coupleLabel.setBounds(20,350,70,70);

            nomTextField.setBounds(180, 43, 165, 23);
            prenomTextField.setBounds(180, 83, 165, 23);
            passwordField.setBounds(180, 123, 165, 23);
            emailText.setBounds(180, 163, 165, 23);

            SexeComboBox.setBounds(180, 200, 165, 30);
            PubComboBox.setBounds(180, 260, 165, 30);
            sexeLabel.setBounds(20, 180, 70, 70);
            pubLabel.setBounds(20, 240, 70, 70);
            
            TypeComboBox.setBounds(180,310,70,30);
            checkCouple.setBounds(180,370,70,30);
            checkCelib.setBounds(250,370,70,30);

            registerButton.setBounds(20, 450, 100, 35);
            modifButton.setBounds(20, 450, 100, 35);
            exitButton.setBounds(150, 450, 70, 35);
            backButton.setBounds(250, 450, 70, 35);

        } else {//log in
            emailLabel.setBounds(20, 20, 70, 70);
            passwordLabel.setBounds(20, 60, 70, 70);
            emailText.setBounds(180, 43, 165, 23);
            passwordField.setBounds(180, 83, 165, 23);
            registerButton.setBounds(70, 150, 100, 35);
            exitButton.setBounds(220, 150, 100, 35);
            addImage(10,300); 
        }
    }
    
    protected void addComponentsToFrame() {   //Adding components to Frame
        ///commun aux trois pages 
        	this.add(passwordLabel);
            this.add(passwordField);
            this.add(emailLabel);
            this.add(emailText);
            this.add(exitButton);
            this.add(backButton); 
       
            
            if (LogorRegis) { //commun au registrer et au modif 
            	this.add(prenomLabel);
                this.add(nomLabel);
                this.add(nomTextField);
                this.add(prenomTextField);
                this.add(SexeComboBox);
                this.add(sexeLabel);
            }else {
            	this.add(registerButton);
              	registerButton.addActionListener(e -> { //log in page 
            		  Enter();
              	});
              	
              //Passer � la page suivante avec le boutton entrer              	
              	passwordField.addKeyListener(new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            Enter();
                         }
                     }
                 });              	
            }
            
            ///Commun aux trois pages 
            exitButton.addActionListener(e -> {
            		this.dispose();
            	});
            backButton.addActionListener(e -> {
	            	this.dispose(); 
	            	mySystem.page1.setVisible(true);
            	});
        }
    
    protected void Enter() {
    	if ( emailText.getText().equals("") || passwordField.getText().equals("") )
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
        else {
            try {
                String password = new String(passwordField.getPassword());
                mySystem.mariaconnexion.LogDB(emailText.getText(),password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (mySystem.user != null){
                if (mySystem.user.getId_User() == 1){
                    this.dispose();
                    new Psy_GUI();
                }
                else{
                    this.dispose();
                    new Patient_GUI();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Erreur mot de passe ou email.");                              
            }
        }
    }
    
    protected void modifPage() {
    	nomTextField.setText(mySystem.user.getNom());
    	 prenomTextField.setText(mySystem.user.getPrenom());
         passwordField.setText(mySystem.user.getPassword());
         emailText.setText(mySystem.user.getEmail());
         
         SexeComboBox.setSelectedItem(mySystem.user.getSexe());
         for(String p  : pub)
        	 if(p.equals(mySystem.user.getPub())) PubComboBox.setSelectedItem(p);     
         
        this.add(modifButton); 
        modifButton.addActionListener(e-> {
        	
        	try {
        		boolean s=false;
        		if (SexeComboBox.getSelectedItem().toString().equals("Homme")) s = true;
           	 
				mySystem.mariaconnexion.modifyClient(mySystem.user.getId_User(),nomTextField.getText(), prenomTextField.getText(), new String(passwordField.getPassword()),
				        emailText.getText(),PubComboBox.getSelectedItem().toString(),s);
				JOptionPane.showMessageDialog(null, "Profile Modifier avec succ�s");
				this.dispose();
				new Patient_GUI();
				mySystem.mariaconnexion.LogDB(mySystem.user.getEmail(), mySystem.user.getPassword()); //actualise les infos du client actuel
				
			}  catch (SQLException  throwables) {
                JOptionPane.showMessageDialog(null, "Impossible de modififer le profile");
                throwables.printStackTrace();
             }
        });
    }

    protected void registerPage() {         
         this.add(PubComboBox);
         this.add(pubLabel);
         this.add(checkCouple);
         this.add(coupleLabel);
         this.add(typeLabel);
         this.add(TypeComboBox);
         this.add(checkCelib);
         this.add(registerButton);
         
         checkCouple.addActionListener(e->{ //ne peut selectionner qu'un seul � la fois 
        	 	checkCelib.setSelected(false);
          		choice_celib = false;
          		choice_couple = true;  
          	});
         
         checkCelib.addActionListener(e -> {  //ne peut selectionner qu'un seul � la fois 
	        	checkCouple.setSelected(false);
	           	choice_celib = true;
	           	choice_couple = false;  
         	});
         
         registerButton.addActionListener(e-> {
	        	 if ( nomTextField.getText().equals("") || passwordField.getText().equals("") || prenomTextField.getText().equals("") || (!choice_couple && !choice_celib)
	                     ||   emailText.getText().equals(""))
	                 JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
	             else {
	                 boolean sexe,couple;
	                 if (SexeComboBox.getSelectedItem().toString().equals("Homme")) sexe = true;
	                 else sexe= false;                    
	                 if(choice_couple) couple = true;
	                 else couple = false;
	                            
	                 try {
	                     String password = new String(passwordField.getPassword());
	                     int id_nouveau_client = mySystem.mariaconnexion.readDBClient(nomTextField.getText(), prenomTextField.getText(),password,
	                             emailText.getText(),PubComboBox.getSelectedItem().toString() ,sexe);
	                     
	                     mySystem.mariaconnexion.addDBCouple(id_nouveau_client, null, couple);
	                     mySystem.mariaconnexion.addDBType(id_nouveau_client, null, TypeComboBox.getSelectedItem().toString() );
	
	                    //Actualise la liste de Patient
	                     mySystem.patients.clear();
	                     mySystem.patients = mySystem.mariaconnexion.getPatient();
	
	                     System.out.println(mySystem.patients.get(mySystem.patients.size()-1).getId_User());
	
	                     this.dispose();
	                     new ProfessionPage(id_nouveau_client);
	                     //new Psy_GUI();
		                 } catch(java.sql.SQLIntegrityConstraintViolationException icve) {
		                 	emailText.setText(" ");
		                 	emailText.setBorder(BorderFactory.createLineBorder(Color.red));
		                 	JOptionPane.showMessageDialog(null, "Cette addresse Email existe d�j�");
		                 }
		                 catch (SQLException  throwables) {
		                    JOptionPane.showMessageDialog(null, "Impossible de cr��er le patient");
		                    throwables.printStackTrace();
		                 }
	             }
         	});
   
    }   

    @Override
    public void actionPerformed(ActionEvent e) {    }
}