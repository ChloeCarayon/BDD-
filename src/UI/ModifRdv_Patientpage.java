package UI;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public final class ModifRdv_Patientpage extends Default_Page implements ActionListener {
    private JLabel DateLabel = new JLabel("Date");
    private JLabel HeureLabel = new JLabel("Heure");
    private JLabel Patient1Label = new JLabel("Patient 1");
    private JLabel Patient2Label = new JLabel("Patient 2");
    private JLabel Patient3Label = new JLabel("Patient 3");
    private JLabel PrixLabel = new JLabel("Prix ( € )");
    private JLabel PaymentLabel = new JLabel("Payement");
    private String [] ListPayment = {"CB", "Cheques", "Espece"};
    private JDateChooser dateChooser;
    private DefaultComboBoxModel<String> Dispo_H =new DefaultComboBoxModel<>();
    private Rdv rdv_actuel;
    private JLabel Patient1Text,Patient2Text,Patient3Text;
    private  JTextField PrixField = new JTextField();
    private JComboBox HeureComboBox =  new JComboBox<>();
    private JComboBox<String> PaymentComboBox = new JComboBox<>(ListPayment);
    JButton ModifButton = new JButton("Modifier");
    boolean type;

    public ModifRdv_Patientpage(String rdvString, Date date, boolean pass,boolean typ) throws SQLException {
        type =typ;
        Optional<Rdv>  rdv = mySystem.rdvListe.stream().filter(r -> (r.getId()== Integer.parseInt(rdvString))).findFirst();
        rdv_actuel  = rdv.get();
        if (pass){
            dateChooser = new JDateChooser(rdv_actuel.getDate());
        }
        else {
            dateChooser = new JDateChooser(date);
        }
        dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ((sdf.format(calendar.getDate())).compareTo(sdf.format(dateChooser.getDate())) > 0){
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez pas choisir une date passee.");
                    dateChooser.setDate(rdv_actuel.getDate());
                }
                else{
                    try {
                        if (calendar.getCalendar().get(Calendar.DAY_OF_WEEK) !=Calendar.SUNDAY){
                            if (!mySystem.mariaconnexion.Datecheck(sdf.format(dateChooser.getDate()),rdv_actuel.getId())){
                                JOptionPane.showMessageDialog(null, "Vous travaillez 10h à ce jour.\nVeuillez selectionner une autre date");
                                dateChooser.setDate(rdv_actuel.getDate());
                            }
                            else SetListHeure(false);
                        }
                        else JOptionPane.showMessageDialog(null, "Vous ne pouvez pas avoir de rendez-vous le dimanche.");
                    } catch (SQLException throwables) {
                        JOptionPane.showMessageDialog(null, "Erreur sql");
                    }
                }
            }
        });
       // SetListHeure(true);
        SetListHeure(true);

        setPatients();
        createWindow("Modification d'un RDV",500, 100, 380, 500);
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);

    }


    private void SetListHeure(boolean type ){
        String[] Heure = {"8h00","8h30", "9h00","9h30", "10h00","10h30", "11h00","11h30", "12h00","12h30", "13h00","13h30", "14h00",
                "14h30", "15h00","15h30", "16h00","16h30", "17h00","17h30", "18h00","18h30", "19h00","19h30", "20h00"};
        List<String> list = new ArrayList<String>(Arrays.asList(Heure));
        for(int cnt=0;cnt<mySystem.rdvListe.size();cnt++) {
            if ((sdf.format(dateChooser.getDate())).equals(mySystem.rdvListe.get(cnt).getDate().toString()))
                list.remove(mySystem.rdvListe.get(cnt).getHeure());
        }
        if (type){
            String[] Dispo = new String[0];
            Dispo = list.toArray(Dispo);
            Dispo_H = new DefaultComboBoxModel<>(Dispo);
            HeureComboBox.setModel(Dispo_H);
        }
        else{
            Dispo_H.removeAllElements();
            for(int i=0; i< list.size();i++){
                Dispo_H.addElement(list.get(i));
            }
        }
    }

    protected void setLocationAndSize() {   // Log in
        DateLabel.setBounds(30, 10, 70, 70);
        HeureLabel.setBounds(30, 60, 70, 70);
        Patient1Label.setBounds(30, 110, 70, 70);
        Patient2Label.setBounds(30, 160, 70, 70);
        Patient3Label.setBounds(30, 210, 70, 70);
        PrixLabel.setBounds(30, 260, 70, 70);
        PaymentLabel.setBounds(30, 310, 70, 70);

        dateChooser.setBounds(150, 35, 200, 20);
        HeureComboBox.setBounds(150, 80, 200, 20);
        Patient1Text. setBounds(150, 135, 200, 20);
        Patient2Text. setBounds(150, 185, 200, 20);
        Patient3Text. setBounds(150, 235, 200, 20);
        PrixField.setBounds(150, 285, 200, 23);
        PaymentComboBox.setBounds(150, 310, 200, 70);

        ModifButton.setBounds(20, 400, 100, 23);
        exitButton.setBounds(250, 400, 100, 23);
        backButton.setBounds(130, 400, 100, 23);
    }

    protected void addComponentsToFrame() {   //Adding components to Frame
        this.add(DateLabel);
        this.add(HeureLabel);
        this.add(Patient1Label);
        this.add(Patient2Label);
        this.add(Patient3Label);
        this.add(PrixLabel);
        this.add(PaymentLabel); 
        this.add(dateChooser);
        this.add(HeureComboBox);
        this.add(Patient1Text);
        this.add(Patient2Text);
        this.add(Patient3Text);
        this.add(PrixField);
        this.add(PaymentComboBox);
        this.add(ModifButton);
        this.add(exitButton);
        this.add(backButton);

        ModifButton.addActionListener(this);
        exitButton.addActionListener(this);
        backButton.addActionListener(this);
    }


    private void setPatients(){
       Patient1Text = new JLabel(mySystem.patients.stream().distinct().filter(p-> p.getId_User() == rdv_actuel.getClient1() ).map(p-> p.getPrenom()+ "  " + p.getNom()).reduce("", String::concat));
       Patient2Text = new JLabel(mySystem.patients.stream().distinct().filter(p-> p.getId_User() == rdv_actuel.getClient2() ).map(p-> p.getPrenom()+ "  " + p.getNom()).reduce("", String::concat));
       Patient3Text = new JLabel(mySystem.patients.stream().distinct().filter(p-> p.getId_User() == rdv_actuel.getClient3() ).map(p-> p.getPrenom()+ "  " + p.getNom()).reduce("", String::concat));

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ModifButton){
          if (PrixField.getText().equals("")) JOptionPane.showMessageDialog(null, "Veuillez rentrer un prix.");
          else {
              try {
                  rdv_actuel.setHeure(HeureComboBox.getSelectedItem().toString());
                  rdv_actuel.setPrix(Float.parseFloat(PrixField.getText()));
                  rdv_actuel.setPayement(PaymentComboBox.getSelectedItem().toString());
                 mySystem.mariaconnexion.ModifRDV_sql(rdv_actuel, sdf.format(dateChooser.getDate()));
                  if(type ) new MyPatientPage();
                  else {new ViewRDV(false);}
                  this.dispose();

              }
              catch (SQLException nfe) {
                  JOptionPane.showMessageDialog(null, "Erreur dans le sql.");
                  PrixField.setBorder(BorderFactory.createLineBorder(Color.red));
              }
              catch (NumberFormatException fe) {
                  JOptionPane.showMessageDialog(null, "Veuillez entrer des chiffres pour le prix.");
                  PrixField.setBorder(BorderFactory.createLineBorder(Color.red));
              }
          }
        }
        else if(e.getSource()==backButton){
            if(type )new ViewRDV(true);
            else new ViewRDV(false);
            this.dispose();

        }
        else if(e.getSource()==exitButton){
            if(type) new Psy_GUI();
            else new Patient_GUI();
            this.dispose();

        }
    }

}