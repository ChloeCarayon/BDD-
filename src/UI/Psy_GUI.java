package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Psy_GUI extends JFrame implements ActionListener  {

    JButton PatientButton=new JButton("Creation Patient");
    JButton RdvButton=new JButton("Gestion Rdv");
    JButton ExitButton=new JButton("EXIT");

    public Psy_GUI() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    public void createWindow() {
        this.setTitle("Psy Side");
        this.setBounds(500, 100, 380, 200);
        this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void setLocationAndSize() {
        PatientButton.setBounds(10,60,165,23);
        RdvButton.setBounds(210,60,165,23);
        ExitButton.setBounds(110,110,165,23);
    }

    public void addComponentsToFrame() {
        this.add(PatientButton);
        this.add(RdvButton);
        this.add(ExitButton);

        PatientButton.addActionListener(this);
        RdvButton.addActionListener(this);
        ExitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== PatientButton){
            /*                                CREATION PATIENT                                 */
            this.dispose();
            new Registration(true);

        }
        else if(e.getSource()==RdvButton){
            /*                                 RDV                                  */
            this.dispose();
        }
        else if(e.getSource()==ExitButton){
            /*                                   EXIT                                      */
                this.dispose();
                mySystem.page1.setVisible(true);
        }
    }

}
