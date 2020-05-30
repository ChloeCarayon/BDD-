package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Patient_GUI extends Default_Page implements ActionListener  {

    private JButton rdvButton=new JButton("Rdv a venir");
   
    
    public Patient_GUI() {
        createWindow("Patient Side",500, 100, 500, 500);
        setLocationAndSize();
        addComponentsToFrame();
        addImage(200,250);
        getProfile();
        this.setVisible(true);
        mySystem.backPage=0;
    }

    protected void setLocationAndSize() {
    	profile.setBounds(20,20,250,350);
    	
        rdvButton.setBounds(300,50,165,23);
        modifButton.setBounds(300,90,165,23);
        coupleButton.setBounds(300,130,165,23);
        typeButton.setBounds(300,170,165,23);
        professionButton.setBounds(300,210,165,23);        
        exitButton.setBounds(300,250,165,23);
    }

    protected void addComponentsToFrame() {
        this.add(rdvButton);
        this.add(exitButton);
        this.add(profile);
        
        addModificationButtons();
        
        rdvButton.addActionListener(e-> {
        		this.dispose();
        	});
        exitButton.addActionListener(e-> {
        		this.dispose();
        		new Log();
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
