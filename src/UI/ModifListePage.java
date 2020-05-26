package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;

public class ModifListePage  extends Default_Page implements ActionListener {
	private JTextField profText = new JTextField(); 
	private JLabel profLabel;
	private JButton addButton = new JButton("Add");	
	private JButton modifButton = new JButton("Modify");	
	private JLabel clientLabel = new JLabel();

	private JLabel dateLabel = new JLabel("Date : ");
	
	 private JList<String> selectlist;
	 private DefaultListModel<String> list = new DefaultListModel<>();
	
	private Calendar cal = Calendar.getInstance();
	private JDateChooser dateChooser = new JDateChooser(cal.getTime());

	private String a_modifier ;
	
	public ModifListePage( String a_modifier) {
		
		createWindow(a_modifier,500, 100, 380, 300);
		setList(mySystem.patients.get(mySystem.current_client_id).getProfList());
	
		profLabel = new JLabel(a_modifier);
		setLocationAndSize();
		addComponentsToFrame();
		this.a_modifier = a_modifier;

	
		
		setVisible(true);
	}
	
	protected void setLocationAndSize() {
		 profLabel.setBounds(20, 85, 70, 70);
		 profText.setBounds(180, 110, 165, 23);
		 dateChooser.setBounds(180, 145, 165, 23 );
		 dateLabel.setBounds(20, 120, 160, 70);
		 
		 addButton.setBounds(30, 200, 70, 35); 
		 backButton.setBounds(250, 200, 70, 35);
		 modifButton.setBounds(100,200, 70, 35);
		 
		 listScroll.setBounds(80,20,200,50);
	}
	
	protected <T> void setList(Map<Date, T> infos) {//T = string (profession ou type) ou boolean (en couple ou non)
		if(infos.size()==0) list.addElement("Aucune profession enregistée");
		
		for(Map.Entry<Date, T> mapentry : infos.entrySet())
			list.addElement((mapentry.getKey()+ "  "+mapentry.getValue()));
		
		selectlist = new JList<>(list);
		selectlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectlist.setVisibleRowCount(-1);
        listScroll = new JScrollPane(selectlist);	        
	}
	
	protected void addComponentsToFrame() {
		this.add(profLabel);
		this.add(profText); 
		this.add(addButton); 
		this.add(backButton); 
		this.add(dateChooser);
		this.add(dateLabel);
		this.add(clientLabel);
		this.add(listScroll);
		this.add(modifButton);
		
		
		modifButton.addActionListener(e -> {
			
		});
		addButton.addActionListener(e -> {
			if(a_modifier.contentEquals("Profession")) {
				try {
					mySystem.mariaconnexion.addDBProfession(mySystem.current_client_id, profText.getText(),sdf.format(dateChooser.getDate()));
				} catch (SQLException e1) {
		            JOptionPane.showMessageDialog(null,"Impossible d'ajouter l'élément");
				}
	            JOptionPane.showMessageDialog(null, "Profession "+profText.getText()+" ajoutée avec succès !");
	            profText.setText(" ");
			}
			else if(a_modifier.contentEquals("Couple")) {
				
			}
			else if(a_modifier.contentEquals("Type")) {
				
			}
		
		});
		backButton.addActionListener(e -> {
			this.dispose();
			new Patient_GUI(mySystem.current_client_id);
		});
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
