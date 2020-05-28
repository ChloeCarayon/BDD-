package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateConsultation_GUI extends Default_Page implements ActionListener {
    private final JLabel clientLabel = new JLabel("Client");
    private final JLabel anxieteLabel = new JLabel("Anxiete");
    private final JLabel motLabel = new JLabel("Mots cles");
    private final JLabel postureLabel = new JLabel("Posture");
    JComboBox<String> PatientComboBox;
    private DefaultComboBoxModel<String> Patient = new DefaultComboBoxModel<>();
    private final String[] anxiete = {"1","2","3","4","5","6","7","8","9","10"};
    private  JComboBox<String> AnxComboBox = new JComboBox<>(anxiete);
    private JTextField PostField = new JTextField();
    private JTextArea MotField = new JTextArea();
    private JButton addButton = new JButton("<html>Ajouter <br> Modifier</html>");
    private Rdv rdv_cons;

    CreateConsultation_GUI(int id_RDV) throws SQLException {
        createWindow("Consultation", 500, 100, 380, 480);
        GetClientCombo(id_RDV);
        this.getContentPane().setBackground(Color.getHSBColor(188,16,100));
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);
    }

    private void GetClientCombo(int ID_rdv) throws SQLException {
        rdv_cons = mySystem.rdvListe.stream().filter(r -> r.getId() == ID_rdv).findFirst().get();
        String tmp;
        Patient.addElement(rdv_cons.getClient1() +" "+ mySystem.mariaconnexion.getClient(rdv_cons.getClient1()));
        tmp =  mySystem.mariaconnexion.getClient(rdv_cons.getClient2());
        if(!tmp.equals("")) Patient.addElement(rdv_cons.getClient2() +" "+ tmp);
        tmp =  mySystem.mariaconnexion.getClient(rdv_cons.getClient3());
        if(!tmp.equals("")) Patient.addElement(rdv_cons.getClient3()+ " " +tmp);
        PatientComboBox =new JComboBox<>(Patient);
    }

    protected void setLocationAndSize() {
        clientLabel.setBounds(20, 20, 70, 70);
        anxieteLabel.setBounds(20, 60, 70, 70);
        postureLabel.setBounds(20, 100, 70, 70);
        motLabel.setBounds(20, 140, 70, 70);

        PatientComboBox.setBounds(180, 43, 165, 23);
        AnxComboBox.setBounds(180, 83, 165, 23);
        PostField.setBounds(180, 123, 165, 23);
        MotField.setBounds(180, 163, 165, 165);

        addButton.setBounds(20, 365, 100, 50);
        exitButton.setBounds(250, 380, 100, 23);
        backButton.setBounds(130, 380, 100, 23);
    }



    protected void addComponentsToFrame() {   //Adding components to Frame
        this.add(clientLabel);
        this.add(anxieteLabel);
        this.add(postureLabel);
        this.add(motLabel);
        this.add(PatientComboBox);
        this.add(AnxComboBox);
        this.add(PostField);
        this.add(MotField);
        this.add(addButton);
        this.add(exitButton);
        this.add(backButton);

        addButton.addActionListener(this);
        exitButton.addActionListener(this);
        backButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if ( PostField.getText().equals("") || MotField.getText().equals("") )
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
            else {
                try {
                    mySystem.mariaconnexion.Consult(PatientComboBox.getSelectedItem().toString(),AnxComboBox.getSelectedItem().toString(),PostField.getText(),MotField.getText(),rdv_cons.getId());
                    JOptionPane.showMessageDialog(null, "Ajout ou modification effectués.");
                    this.dispose();
                    new Cons_ModifRDV();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
        if (e.getSource() == backButton) {
            this.dispose();
            new Cons_ModifRDV();
        }
        if (e.getSource() == exitButton) {
            this.dispose();
        }
    }
}
