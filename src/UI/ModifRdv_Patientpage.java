package UI;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    JTextField PrixField;
    private JComboBox<String> HeureComboBox;
    private JComboBox<String> PaymentComboBox = new JComboBox<>(ListPayment);
    JButton ModifButton = new JButton("Modifier");

    public ModifRdv_Patientpage(String rdvString) throws SQLException {
        Optional<Rdv>  rdv = mySystem.rdvListe.stream().filter(r -> (r.getId()== Integer.parseInt(rdvString))).findFirst();
        rdv_actuel  = rdv.get();
        dateChooser = new JDateChooser(rdv_actuel.getDate());
        dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("BLABLABLA");
                SetListHeure(false);
            }
        });

        SetListHeure(true);
        PrixField = new JTextField((int) rdv_actuel.getPrix());
        setPatients();
        createWindow("Modification d'un RDV",500, 100, 380, 500);
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);

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

    private void SetListHeure(boolean type){
        String[] Heure = {"8h00","8h30", "9h00","9h30", "10h00","10h30", "11h00","11h30", "12h00","12h30", "13h00","13h30", "14h00",
                "14h30", "15h00","15h30", "16h00","16h30", "17h00","17h30", "18h00","18h30", "19h00","19h30", "20h00"};
        Dispo_H.removeAllElements();
        List<String> list_H = new ArrayList<String>(); list_H.add(rdv_actuel.getHeure());
        list_H.addAll(Arrays.asList(Heure));
        for(int cnt=0;cnt<mySystem.rdvListe.size();cnt++) {
            if ((sdf.format(dateChooser.getDate())).equals(mySystem.rdvListe.get(cnt).getDate().toString())){
                if(mySystem.rdvListe.get(cnt).getId() != rdv_actuel.getId()){
                    list_H.remove(mySystem.rdvListe.get(cnt).getHeure());
                }
           }
        }
        for(int c=0;c<list_H.size();c++) {
            Dispo_H.addElement(list_H.get(c));
        }
        //String[] Dispo = new String[0];
       //Dispo_H = new DefaultComboBoxModel<>(list.toArray(Dispo));
        PrintList(type);
    }

    private void PrintList(boolean type) {
        if (type) {
            HeureComboBox = new JComboBox<>(Dispo_H);
            listScroll = new JScrollPane(HeureComboBox);
        }else {
        }
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
                 // mariadb ici
                  this.dispose();
                  new MyPatientPage();
              }
              catch (NumberFormatException nfe) {
                  JOptionPane.showMessageDialog(null, "Veuillez entrer des chiffres pour le prix.");
                  PrixField.setBorder(BorderFactory.createLineBorder(Color.red));
              }
          }
        }
        else if(e.getSource()==backButton){
            this.dispose();
            new MyPatientPage();
        }
        else if(e.getSource()==exitButton){
            this.dispose();
            new Psy_GUI();
        }
    }

}
