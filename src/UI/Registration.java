package UI;
import oracle.jvm.hotspot.jfr.JFREventWriter;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public final class Registration extends JFrame implements ActionListener {
    boolean LogorRegis;
    JLabel nomLabel = new JLabel("Nom");
    JLabel prenomLabel = new JLabel("Prénom");
    JLabel emailLabel = new JLabel("mail");
    JLabel passwordLabel = new JLabel("Mdp");
    String[] sexe = {"Homme", "Femme"};
    JLabel sexeLabel = new JLabel("Sexe");
    String[] pub = {"BaO", "autre patient", "docteur", "Pages Jaunes", "Internet", "autre"};
    JLabel pubLabel = new JLabel("Pub");

    JLabel IdLabel = new JLabel("ID");
    JTextField IdField = new JTextField();

    JTextField nomTextField = new JTextField();
    JTextField prenomTextField = new JTextField();
    JTextField emailText = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JComboBox PubComboBox;
    JComboBox SexeComboBox;

    JButton registerButton = new JButton("REGISTER");
    JButton resetButton = new JButton("EXIT");

    public Registration(boolean choice) {
        LogorRegis = choice;
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    public void createWindow() {
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

    public void setLocationAndSize() {   // Log in

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

        } else {
            IdLabel.setBounds(20, 20, 70, 70);
            passwordLabel.setBounds(20, 60, 70, 70);
            IdField.setBounds(180, 43, 165, 23);
            passwordField.setBounds(180, 83, 165, 23);
            registerButton.setBounds(70, 150, 100, 35);
            resetButton.setBounds(220, 150, 100, 35);
        }
    }

    public void addComponentsToFrame() {   //Adding components to Frame
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(registerButton);
        this.add(resetButton);
        // login select technician / customer / manager
        if (LogorRegis) {
            this.add(prenomLabel);
            this.add(nomLabel);
            this.add(emailLabel);
            this.add(nomTextField);
            this.add(emailText);
            this.add(prenomTextField);
            this.add(SexeComboBox);
            this.add(sexeLabel);
            this.add(PubComboBox);
            this.add(pubLabel);
        }
        else{  this.add(IdLabel);       this.add(IdField);}

        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("REGISTER")) {
            // sql registration
            System.out.println("SQL registration");
        }
        if (e.getActionCommand().equals("EXIT")) {
            this.dispose();
            // sql exit
        }
    }
}