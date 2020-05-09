package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ProfessionPage extends Default_Page implements ActionListener {
	private JTextField profText = new JTextField(); 
	private JLabel profLabel = new JLabel("Profession");
	private JButton addButton = new JButton("Add");	
	private JButton backButton = new JButton("Back");
	
	
	
	public ProfessionPage() {
		createWindow("Profession",500, 100, 380, 300);
		setLocationAndSize();
		addComponentsToFrame();
		setVisible(true);
	}
	
	protected void setLocationAndSize() {
		 profLabel.setBounds(20, 20, 70, 70);
		 profText.setBounds(180, 43, 165, 23);
		 
		 addButton.setBounds(50, 150, 70, 35); 
		 backButton.setBounds(200, 150, 70, 35);
	}
	
	protected void addComponentsToFrame() {
		this.add(profLabel);
		this.add(profText); 
		this.add(addButton); 
		this.add(backButton); 
		
		addButton.addActionListener(this);
		backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
