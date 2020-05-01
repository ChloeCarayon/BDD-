package UI;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class Log extends Default_Page implements ActionListener {
    private Button b1, b2;
    private JLabel titre = new JLabel("Bienvenue ! ");
    public Log(){
    	createWindow("Bienvenue",500,100,350,350);
    	setLocationAndSize(); 
    	addComponentsToFrame(); 
    	addImage(10,50);
    	this.setVisible(true);
    }
    protected void setLocationAndSize() {
    	 b1 = new Button(" Log in ");
         b2 = new Button(" Exit ");
         b1.setBounds(50, 80, 100, 25);
         b2.setBounds(200, 80, 100, 25);
         titre.setBounds(120,20,200,50);
         titre.setFont(new Font("Calibri", Font.PLAIN, 24));
    }

    protected void addComponentsToFrame() {
    	b1.addActionListener(this);
        b2.addActionListener(this);
        this.add(b1);
        this.add(b2);
        this.add(titre);
    }
     

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            /*                                 Log IN                                                     */
            mySystem.page1.setVisible(false);
            new Registration(false);

        } else{
            /*                                   EXIT                                        */
            mySystem.page1.dispose();
        }
    }
}
