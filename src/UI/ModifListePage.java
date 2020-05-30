package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JButton deleteButton = new JButton("Supprimer");
	private JLabel clientLabel = new JLabel();
	
    private final JCheckBox checkCouple = new JCheckBox("Couple");
    private final JCheckBox checkCelib = new JCheckBox("Celibataire");

	private JLabel dateLabel = new JLabel("Date : ");
	
	 private JList<String> selectlist;
	 private DefaultListModel<String> list = new DefaultListModel<>();
	
	private Calendar cal = Calendar.getInstance();
	private JDateChooser dateChooser = new JDateChooser(cal.getTime());
	boolean choice_couple;
	private Date oldDate;

	private String a_modifier ;
	
	public ModifListePage(String a_modifier) {
		this.a_modifier = a_modifier;	
		createWindow(a_modifier,500, 100, 380, 300);
		
		choice(true);	
		profLabel = new JLabel(a_modifier+" : ");
		setLocationAndSize();
		addComponentsToFrame();
		setVisible(true);
	}
	
	
	protected void setLocationAndSize() {
		 profLabel.setBounds(20, 85, 100, 70);		
		 dateChooser.setBounds(180, 145, 165, 23 );
		 dateLabel.setBounds(20, 120, 160, 70);
		 
		 addButton.setBounds(15, 220, 70, 28); 
		 backButton.setBounds(300, 220, 70, 28);
		 modifButton.setBounds(100,220, 80, 28);
		 deleteButton.setBounds(195,220,90, 28);	 
	}
	
	protected void choice(boolean c) {
		if(a_modifier.equals(mySystem.PROSSESSION)) {
			setList(mySystem.user.getProfList(),c);
			afficheCouple(c,false);
		}	

		else if(a_modifier.equals(mySystem.TYPE)) {
			setList(mySystem.user.getTypeList(),c);
			afficheCouple(c,false);
		}
		
		else if(a_modifier.equals(mySystem.COUPLE)) {
			setList(mySystem.user.getCoupleList(),c);
			afficheCouple(c,true);
		}
			
	}
	
	protected void afficheCouple(boolean c, boolean couple) {
		if(c) 
			if(couple) {
				System.out.print("oui");
				checkCouple.setBounds(180, 110, 70, 23);
				checkCelib.setBounds(260, 110, 165, 23);
				
				 this.add(checkCelib);
		         this.add(checkCouple);
		         
		         checkCouple.addActionListener(e->{ //ne peut selectionner qu'un seul � la fois 
		        	 	checkCelib.setSelected(false);
		          		choice_couple = true;  
		          	});
		         
		         checkCelib.addActionListener(e -> {  //ne peut selectionner qu'un seul � la fois 
			        	checkCouple.setSelected(false);
			           	choice_couple = false;  
		         	});	         
			}
			else 
				profText.setBounds(180, 110, 165, 23);
	}
	
	protected <T > void setList(Map<Date, T> infos, boolean choice) {//T = string (profession ou type) ou boolean (en couple ou non)
		if(infos.size()==0) list.addElement("Aucun.e  "+a_modifier+" enregist�.es");
	
		for(Map.Entry<Date, T> mapentry : infos.entrySet()) {
			if(a_modifier.contentEquals(mySystem.COUPLE)) {
				list.addElement((mapentry.getKey()+ " : "+mySystem.user.CoupleToString(mapentry.getKey())));
			}
			else 
				list.addElement((mapentry.getKey()+ " : "+mapentry.getValue()));	
		}	
        

        if(choice) {
        	selectlist = new JList<>(list);
    		selectlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		selectlist.setVisibleRowCount(-1);
            listScroll = new JScrollPane(selectlist);
            
            selectlist.addMouseListener(new MouseAdapter() {//R�cup�re les valeurs quand clique sur l'item de la liste
    	        	public void mouseClicked(MouseEvent e) {
    		        		if (e.getClickCount() == 1) {	        			
    		        			profText.setText(selectlist.getSelectedValue().substring(13));	
    		        			for(Map.Entry<Date, T > mapentry : infos.entrySet()) {
    		        				if (profText.getText().equals(mapentry.getValue())) {
    		        					dateChooser.setDate(mapentry.getKey());	 
    		        					oldDate = mapentry.getKey();
    		        				}
    		        				else if((boolean) mapentry.getValue()) {
    		        					checkCouple.setSelected(true);
    		        					dateChooser.setDate(mapentry.getKey());	 
    		        					oldDate = mapentry.getKey();
    		        				}
    		        				else if(!(boolean) mapentry.getValue()) {
    		        					checkCelib.setSelected(true);
    		        					dateChooser.setDate(mapentry.getKey());	 
    		        					oldDate = mapentry.getKey();
    		        				}
    		        			}
    		              }
    	        	}        		
            	});

            listScroll.setBounds(80,20,200,50);
            this.add(listScroll);
        }

	}
	

	
	protected void addComponentsToFrame() {
		this.add(profLabel);
		this.add(profText); 
		this.add(addButton); 
		this.add(backButton); 
		this.add(dateChooser);
		this.add(dateLabel);
		this.add(clientLabel);		
		this.add(modifButton);
		this.add(deleteButton);
		
		
		deleteButton.addActionListener(e -> {			
				try {
					
					mySystem.mariaconnexion.deleteItem(sdf.format(dateChooser.getDate()).toString(), profText.getText(),mySystem.user.getId_User(), a_modifier);
					mySystem.mariaconnexion.LogDB(mySystem.user.getEmail(), mySystem.user.getPassword()); //actualise les infos du client actuel
					
					//Supprime la liste, update et r�affiche 
					list.removeAllElements();
					choice(false);					
					JOptionPane.showMessageDialog(null, "Supprime avec succes");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Impossible de supprimer");
					e1.printStackTrace();
				}			
		});
		
				
		modifButton.addActionListener(e -> {
			try {
				if(a_modifier.contentEquals(mySystem.COUPLE))
					mySystem.mariaconnexion.modifyItem(null, choice_couple,  //La nouvelle valeur du boolean
													sdf.format(oldDate).toString(), sdf.format(dateChooser.getDate()), 	 //Les dates initiales et modifi�es
													mySystem.user.getId_User(), a_modifier); 							 //L'id et la table a update dans la db
				else 
					mySystem.mariaconnexion.modifyItem(selectlist.getSelectedValue().substring(13), choice_couple,  //La nouvelle valeur du boolean
							sdf.format(oldDate).toString(), sdf.format(dateChooser.getDate()), 	 //Les dates initiales et modifi�es
							mySystem.user.getId_User(), a_modifier); 
					
					
				mySystem.mariaconnexion.LogDB(mySystem.user.getEmail(), mySystem.user.getPassword()); //actualise les infos du client actuel
				
				//Supprime la liste, update et r�affiche 
				list.removeAllElements();
				choice(false);			
			} catch (SQLIntegrityConstraintViolationException icve) {
	            JOptionPane.showMessageDialog(null,"Vous avez d�j� rentr� cette profession !");
			}catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Impossible de modifier ");
			}
		});
		
		
		addButton.addActionListener(e -> {

			try {
				if(a_modifier.contentEquals(mySystem.COUPLE))
					mySystem.mariaconnexion.addItem(mySystem.user.getId_User(), choice_couple ,sdf.format(dateChooser.getDate()).toString(), a_modifier);
				else 
					mySystem.mariaconnexion.addItem(mySystem.user.getId_User(), profText.getText() ,sdf.format(dateChooser.getDate()).toString(), a_modifier);

				mySystem.mariaconnexion.LogDB(mySystem.user.getEmail(), mySystem.user.getPassword()); //actualise les infos du client actuel

				//Supprime la liste, update et r�affiche 
				list.removeAllElements();
				choice(false);

			
				
				 JOptionPane.showMessageDialog(null, a_modifier+" "+profText.getText()+" ajout�.e avec succ�s !");
		         profText.setText(" ");
			} catch (SQLIntegrityConstraintViolationException icve) {
	            JOptionPane.showMessageDialog(null,"Vous avez d�j� rentr� cet element !");
			}
			catch (SQLException e1) {
	            JOptionPane.showMessageDialog(null,"Impossible d'ajouter l'element");
	            e1.printStackTrace();
			}	 		
		});
		backButton.addActionListener(e -> {
			this.dispose();
			if(mySystem.backPage == 1)
				new MyPatientPage();
			else 
				new Patient_GUI();
		});
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub		
	}


}
