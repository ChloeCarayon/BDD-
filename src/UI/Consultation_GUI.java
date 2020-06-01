package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Consultation_GUI extends Default_Page implements ActionListener {

    private JButton CreateConsButton = new JButton("Creer une consultation ");
    private final JLabel consult_title = new JLabel("Consultation : ");
    private final JLabel consult = new JLabel("Selectionnez un patient");
    private Rdv currentRdv; private boolean type;

    public Consultation_GUI(int ID_rdv, boolean tip) {
        type = tip;
        currentRdv =  mySystem.rdvListe.stream().filter(r -> r.getId() == ID_rdv).findFirst().get();
        createWindow("Consultation " + currentRdv.getDate() + "  " + currentRdv.getHeure(),500, 100, 500, 500);
        setListClient();
        setLocationAndSize();
        addComponentsToFrame();
        consult.setText("Selectionnez un patient");
        this.setVisible(true);
    }

    protected void setLocationAndSize() {

        CreateConsButton.setBounds(20,250,175,30);
        listScroll.setBounds(20,50,170,70);
        consult_title.setBounds(200,50,150,30);
        consult.setBounds(250,30,150,300);
        backButton.setBounds(20,300,100,30);
    }

    protected void addComponentsToFrame() {
        this.add(listScroll);
        this.add(consult_title);
        this.add(CreateConsButton);
        this.add(consult);
        this.add(backButton);
        CreateConsButton.setVisible(false);
        CreateConsButton.addActionListener(this);
        backButton.addActionListener(this);
        
        
        patientList.addMouseListener(new MouseAdapter() {//Recupere les valeurs quand clique sur l'item de la liste
        	public void mouseClicked(MouseEvent e) {
        		 if( patientList.getSelectedIndex() != -1)
        			 getConsult();
        	}
        	});
    }

    protected void getConsult() {
        String infos;
        try {
            String val_client = patientList.getModel().getElementAt(patientList.getSelectedIndex());
            String[] splitClient1 = val_client.split(" ", 3);
            int client = Integer.parseInt(splitClient1[0]);
            infos = mySystem.mariaconnexion.getConsultation(client,currentRdv.getId());
            if (infos.equals("")){
                CreateConsButton.setVisible(true);
                infos += "Aucune consult";
            }else CreateConsButton.setVisible(false);

        }catch(ArrayIndexOutOfBoundsException aiobe) {
            infos = "Aucune consultation selectionnee !";
        }
        catch(Exception e) {
            infos = "Vous devez creer la consultation";           
        }
        consult.setText(infos);
    }

    protected void setListClient() {
	    	String patient;
 		try {
            list.addElement(currentRdv.getClient1() +" "+ mySystem.mariaconnexion.getClient(currentRdv.getClient1()));
            patient =  mySystem.mariaconnexion.getClient(currentRdv.getClient2());
            if(!patient.equals("")) list.addElement(currentRdv.getClient2() +" "+ patient);
            patient =  mySystem.mariaconnexion.getClient(currentRdv.getClient3());
            if(!patient.equals("")) list.addElement(currentRdv.getClient3()+ " " +patient);
 		}catch(Exception e) {
 			list.addElement("Impossible d'afficher les clients");
 		}
	    	PrintListClient();  	
	    
    }

    private  void getCons_create() throws SQLException{
        if (patientList.getSelectedIndex() != -1){
            String pat = patientList.getModel().getElementAt(patientList.getSelectedIndex());
            String[] id_string = pat.split(" ", 2);
            new CreateConsultation_GUI(Integer.parseInt(id_string[0]), currentRdv, type);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        if(e.getSource()==CreateConsButton && patientList.getSelectedIndex() != -1 ){
           try {
               getCons_create();
           } catch (SQLException throwables) { throwables.printStackTrace(); }
           this.dispose();
        }
        if (e.getSource() == backButton){
            this.dispose();
            if(type )new ViewRDV(true);
            else new RDVpsy_GUI();
        }
    }
}
