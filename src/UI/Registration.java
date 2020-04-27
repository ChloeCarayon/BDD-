package UI;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public final class Registration extends JFrame implements ActionListener {
    boolean LogorRegis;
    private JLabel nomLabel = new JLabel("Nom");
    private JLabel prenomLabel = new JLabel("Prénom");
    private JLabel emailLabel = new JLabel("Mail");
    private JLabel passwordLabel = new JLabel("Password");
    private String[] sexe = {"Homme", "Femme"};
    private JLabel sexeLabel = new JLabel("Sexe");
    private String[] pub = {"BaO", "autre patient", "docteur", "Pages Jaunes", "Internet", "autre"};
    private JLabel pubLabel = new JLabel("Pub");

    private JLabel IdLabel = new JLabel("ID");

    JTextField IdField = new JTextField();
    JTextField nomTextField = new JTextField();
    JTextField prenomTextField = new JTextField();
    JTextField emailText = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JComboBox PubComboBox;
    JComboBox SexeComboBox;

    JButton registerButton = new JButton("REGISTER");
    JButton resetButton = new JButton("EXIT");
    JButton backButton=new JButton("Back");

    public Registration(boolean choice) {
        LogorRegis = choice;
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    private void createWindow() {
        this.setTitle("Form");
        if (!LogorRegis)
            this.setBounds(500, 100, 380, 250);
        else
            this.setBounds(500, 100, 380, 500);
        this.getContentPane().setBackground(Color.lightGray);
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        PubComboBox = new JComboBox(pub);
        SexeComboBox = new JComboBox(sexe);
    }

    private void setLocationAndSize() {   // Log in

        if (LogorRegis) {
            nomLabel.setBounds(20, 20, 70, 70);
            prenomLabel.setBounds(20, 60, 70, 70);
            passwordLabel.setBounds(20, 100, 70, 70);
            emailLabel.setBounds(20, 140, 70, 70);

            nomTextField.setBounds(180, 43, 165, 23);
            prenomTextField.setBounds(180, 83, 165, 23);
            passwordField.setBounds(180, 123, 165, 23);
            emailText.setBounds(180, 163, 165, 23);

            SexeComboBox.setBounds(180, 183, 165, 70);
            PubComboBox.setBounds(180, 243, 165, 70);
            sexeLabel.setBounds(20, 180, 70, 70);
            pubLabel.setBounds(20, 240, 70, 70);

            registerButton.setBounds(70, 400, 100, 35);
            resetButton.setBounds(220, 400, 100, 35);
            backButton.setBounds(200, 450, 100, 35);

        } else {
            emailLabel.setBounds(20, 20, 70, 70);
            passwordLabel.setBounds(20, 60, 70, 70);
            emailText.setBounds(180, 43, 165, 23);
            passwordField.setBounds(180, 83, 165, 23);
            registerButton.setBounds(70, 150, 100, 35);
            resetButton.setBounds(220, 150, 100, 35);
        }
    }

    private void addComponentsToFrame() {   //Adding components to Frame
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(emailLabel);
        this.add(emailText);
        this.add(registerButton);
        this.add(resetButton);
        this.add(backButton); 
        
        if (LogorRegis) {
            this.add(prenomLabel);
            this.add(nomLabel);
            this.add(nomTextField);
            this.add(prenomTextField);
            this.add(SexeComboBox);
            this.add(sexeLabel);
            this.add(PubComboBox);
            this.add(pubLabel);
        }

        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("REGISTER")) {
            // sql registration
            if (!LogorRegis){
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
            else {
                if ( nomTextField.getText().equals("") || passwordField.getText().equals("") || prenomTextField.getText().equals("")
                        ||   emailText.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                else {
                    boolean sexe;
                    if (SexeComboBox.getSelectedItem().toString().equals("Homme")) sexe = true;
                    else sexe= false;
                    try {
                        String password = new String(passwordField.getPassword());
                        mySystem.mariaconnexion.readDBClient(nomTextField.getText(), prenomTextField.getText(),password,
                                emailText.getText(),PubComboBox.getSelectedItem().toString() ,sexe);
                       //Actualise la liste de Patient
                        mySystem.rdvList.clear();
                        mySystem.rdvList = mySystem.mariaconnexion.getPatient();

                        this.dispose();
                        new Psy_GUI();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace(); JOptionPane.showMessageDialog(null, "Erreur mot de passe ou email.");
                    }
                }
            }
        }
        if (e.getActionCommand().equals("EXIT")) {
            this.dispose();
            // sql exit
        }
        if(e.getActionCommand().equals("Back")) {
        	this.dispose(); 
        	mySystem.page1.setVisible(true);
        }
    }

}
//https://www.vogella.com/tutorials/MySQLJava/article.html