package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class ProfessionPage extends Default_Page implements ActionListener {
	private JTextField profText = new JTextField(); 
	private JLabel profLabel = new JLabel("Profession");
	private JButton addButton = new JButton("Add");	
	private JLabel clientLabel = new JLabel();
	private JLabel dateLabel = new JLabel("Date de la profession : ");
	
	private Calendar cal = Calendar.getInstance();
	private JDateChooser dateChooser = new JDateChooser(cal.getTime());
	private int current ; 
	
	public ProfessionPage(int id_client) {
		createWindow("Profession",500, 100, 380, 250);
		setLocationAndSize();
		addComponentsToFrame();
		try {
			clientLabel.setText("Professions de  :  "+mySystem.mariaconnexion.getClient(id_client));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			clientLabel.setText("Aucun client selectionné");
		}		
		current = id_client; 
		setVisible(true);
	}
	
	protected void setLocationAndSize() {
		 profLabel.setBounds(20, 45, 70, 70);
		 profText.setBounds(180, 65, 165, 23);
		 dateChooser.setBounds(180, 105, 165, 23 );
		 dateLabel.setBounds(20, 85, 145, 70);
		 clientLabel.setBounds(20, 20, 250, 23);
		 
		 addButton.setBounds(80, 150, 70, 35); 
		 backButton.setBounds(200, 150, 70, 35);
	}
	
	protected void addComponentsToFrame() {
		this.add(profLabel);
		this.add(profText); 
		this.add(addButton); 
		this.add(backButton); 
		this.add(dateChooser);
		this.add(dateLabel);
		this.add(clientLabel);
		
		addButton.addActionListener(this);
		backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			try {
				mySystem.mariaconnexion.addDBProfession(current, profText.getText(),sdf.format(dateChooser.getDate()));
                JOptionPane.showMessageDialog(null, "Profession "+profText.getText()+" ajoutée avec succès !");
                profText.setText(" ");
			} catch (SQLException throwables) {
				// TODO Auto-generated catch block
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter "+profText.getText()+" pour le "+sdf.format(dateChooser.getDate()));
			}
		}
		if(e.getSource() == backButton) {
			this.dispose(); 
			new Psy_GUI();
		}
			
		
	}

}
