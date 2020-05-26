package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Patient_GUI extends Default_Page implements ActionListener  {

    private JButton modifButton=new JButton("Modifier mon profile");
    private JButton rdvButton=new JButton("Rdv a venir");
    private JButton coupleButton = new JButton("Modifier ma situation");
    private JButton typeButton = new JButton("Modifier mon type");
    private JButton professionButton = new JButton("Modifier ma profession");
   
    
    public Patient_GUI() {
        createWindow("Patient Side",500, 100, 500, 500);
        setLocationAndSize();
        addComponentsToFrame();
        addImage(200,250);
        getProfile();
        this.setVisible(true);
    }

    protected void setLocationAndSize() {
    	modifButton.setBounds(300,130,165,23);
        rdvButton.setBounds(300,90,165,23);
        profile.setBounds(20,50,250,300);
        coupleButton.setBounds(300,170,165,23);
        typeButton.setBounds(300,210,165,23);
        professionButton.setBounds(300,250,165,23);
    }

    protected void addComponentsToFrame() {
        this.add(modifButton);
        this.add(rdvButton);
        this.add(exitButton);
        this.add(profile);
        this.add(typeButton); 
        this.add(coupleButton); 
        this.add(professionButton);
        
        profile.setFont(new Font("Verdana", 12, 12));

        modifButton.addActionListener(e-> {
        	this.dispose();
        	new Registration(true,mySystem.user.getId_User());
        	});
        rdvButton.addActionListener(e-> {
        	this.dispose();
        	});
        exitButton.addActionListener(e-> {
        	this.dispose();
        	});
        professionButton.addActionListener(e -> {
        	this.dispose(); 
        	new ModifListePage("Profession");
        });
    }
    
    protected void getProfile() {    	
    	String infos;
    	try {
    		infos = mySystem.user.toString();
	    	
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
    public void actionPerformed(ActionEvent e) { }

}
