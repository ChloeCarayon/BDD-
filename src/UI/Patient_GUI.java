package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Patient_GUI extends JFrame implements ActionListener  {

    JButton HistoButton=new JButton("Historique");
    JButton RdvButton=new JButton("Rdv à venir");
    JButton ExitButton=new JButton("EXIT");

    public Patient_GUI() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    public void createWindow() {
        this.setTitle("Patient Side");
        this.setBounds(500, 100, 380, 200);
        this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void setLocationAndSize() {
        HistoButton.setBounds(10,60,165,23);
        RdvButton.setBounds(210,60,165,23);
        ExitButton.setBounds(110,110,165,23);
    }

    public void addComponentsToFrame() {
        this.add(HistoButton);
        this.add(RdvButton);
        this.add(ExitButton);

        HistoButton.addActionListener(this);
        RdvButton.addActionListener(this);
        ExitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== HistoButton){
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
        }
    }

}
