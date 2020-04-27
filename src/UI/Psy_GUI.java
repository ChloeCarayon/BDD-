package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Psy_GUI extends JFrame implements ActionListener  {

    private JButton PatientButton=new JButton("Creation Patient");
    private JButton RdvButton=new JButton("Gestion Rdv");
    private JButton ExitButton=new JButton("EXIT");
    private JButton ClientButton=new JButton("Mes patients");

    public Psy_GUI() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    private void createWindow() {
        this.setTitle("Psy Side");
        this.setBounds(500, 100, 400, 300);
        this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void setLocationAndSize() {
        PatientButton.setBounds(10,60,165,23);
        RdvButton.setBounds(210,60,165,23);
        ExitButton.setBounds(210,110,165,23);
        ClientButton.setBounds(10,110,165,23);
    }

    private void addComponentsToFrame() {
        this.add(PatientButton);
        this.add(RdvButton);
        this.add(ExitButton);
        this.add(ClientButton);

        PatientButton.addActionListener(this);
        RdvButton.addActionListener(this);
        ExitButton.addActionListener(this);
        ClientButton.addActionListener(this);
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
            //new Rdv();
        }
        else if(e.getSource()==ExitButton){
            /*                                   EXIT                                      */
                this.dispose();
                mySystem.page1.setVisible(true);
        }
        else if(e.getSource()==ClientButton){
            /*                                   CLients                                     */
                this.dispose();
                new MyPatientPage();
        }
    }

}
