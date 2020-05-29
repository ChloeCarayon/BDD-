package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

public class Cons_ModifRDV extends Default_Page implements ActionListener {
    private JScrollPane listScrollRdv, listScrollCons ;
    private JList<String> rdv_List, cons_List_;
    private DefaultListModel<String> listpasse = new DefaultListModel<>();
    private DefaultListModel<String> listfutur = new DefaultListModel<>();
    private final JButton ModifButton = new JButton("Modifier RDV futur");
    private final JButton SupprButton = new JButton("Supprimer RDV futur");
    private final JButton ConsButton = new JButton("Ajouter consult ");
    private final JLabel Rdv_Passe = new JLabel("RDV Passés : ");
    private final JLabel Rdv_Futurs = new JLabel("RDV Futurs : ");

    public Cons_ModifRDV() {
        createWindow("Consulter les RDV", 400, 50, 600, 470);
        setList(true);
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);
    }

    private void setList(boolean type) {
        String Rdvpasse, Rdvfutur;
        try {
            listpasse.clear(); listfutur.clear();
            for (int i = 0; i < mySystem.rdvListe.size(); i++) {//commence liste a 1 pour pas avoir la psy dans les clients
                if(mySystem.patients.get(mySystem.current_client_id).getId_User() == mySystem.rdvListe.get(i).getClient1() ||
                        mySystem.patients.get(mySystem.current_client_id).getId_User() == mySystem.rdvListe.get(i).getClient2() ||
                mySystem.patients.get(mySystem.current_client_id).getId_User() == mySystem.rdvListe.get(i).getClient3()){
                    // RDV auxquels on doit ajouter une consultation
                    if ((sdf.format(calendar.getDate())).compareTo(mySystem.rdvListe.get(i).getDate().toString()) >= 0){
                        Rdvpasse = String.valueOf(mySystem.rdvListe.get(i).getId()); Rdvpasse += "  ";
                        Rdvpasse += String.valueOf(mySystem.rdvListe.get(i).getDate()); Rdvpasse += "  ";
                        Rdvpasse += String.valueOf(mySystem.rdvListe.get(i).getHeure()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient1()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient2()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient3());
                        listpasse.addElement(Rdvpasse);
                    }
                    // RDV qu'on peut modifier
                    if ((sdf.format(calendar.getDate())).compareTo(mySystem.rdvListe.get(i).getDate().toString()) < 0){
                        Rdvfutur = String.valueOf(mySystem.rdvListe.get(i).getId()); Rdvfutur += "  ";
                        Rdvfutur += String.valueOf(mySystem.rdvListe.get(i).getDate()); Rdvfutur += "  ";
                        Rdvfutur += String.valueOf(mySystem.rdvListe.get(i).getHeure()); Rdvfutur += "  ";
                        Rdvfutur += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient1()); Rdvfutur += "  ";
                        Rdvfutur += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient2()); Rdvfutur += "  ";
                        Rdvfutur += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient3());
                        listfutur.addElement(Rdvfutur);
                    }
                }

            }
            if (listpasse.size() < 1) {
                listpasse.addElement("Pas de RDV passés pour l'instant.");
            } if (listfutur.size() < 1)listfutur.addElement("Pas de RDV futurs pour l'instant.");
        } catch (Exception e) {
        }
        PrintList(type);
    }

    protected void PrintList(boolean type) {
        if (type) {
            cons_List_ = new JList<>(listpasse); rdv_List =  new JList<>(listfutur);
            rdv_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); cons_List_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listScrollRdv = new JScrollPane(rdv_List); listScrollCons= new JScrollPane(cons_List_);
        }
    }

    protected void setLocationAndSize() {
        Rdv_Passe.setBounds(20, 20, 150, 30);
        Rdv_Futurs.setBounds(20, 170, 150, 30);
        ConsButton.setBounds(425, 75, 170, 23);
        exitButton.setBounds(425, 400, 170, 23);
        ModifButton.setBounds(425, 225, 170, 23);
        SupprButton.setBounds(425, 255, 170, 23);
        listScrollRdv.setBounds(20, 200, 400, 100);
        listScrollCons.setBounds(20, 50, 400, 100);
    }

    protected void addComponentsToFrame() {
        this.add(Rdv_Passe);
        this.add(Rdv_Futurs);
        this.add(listScrollCons);
        this.add(listScrollRdv);
        this.add(exitButton);
        this.add(ModifButton);
        this.add(ConsButton);
        this.add(SupprButton);
        ModifButton.addActionListener(this);
        ConsButton.addActionListener(this);
        SupprButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    private void SupprRDV() {
        System.out.println(rdv_List.getSelectedIndex());
        if (rdv_List.getSelectedIndex() != -1) {
            if ((sdf.format(calendar.getDate())).compareTo(sdf.format(java.util.Calendar.getInstance().getTime())) < 0){
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas supprimer un RDV passé.");
            }
            else {
                String rdvsup = rdv_List.getModel().getElementAt(rdv_List.getSelectedIndex());
                String[] id_string = rdvsup.split("  ", 2);
                try {
                    mySystem.mariaconnexion.DeleteRdv(Integer.parseInt(id_string[0]));
                    setList(false);
                } catch (NumberFormatException | SQLException e) { }
            }
        }

    }

    private void ModifRDV() throws SQLException {
        System.out.println(rdv_List.getSelectedIndex());
        if (rdv_List.getSelectedIndex() != -1) {
            if ((sdf.format(calendar.getDate())).compareTo(sdf.format(java.util.Calendar.getInstance().getTime())) < 0){
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas modifier un RDV passé.");
            }
            else {
                String rdvmodif = rdv_List.getModel().getElementAt(rdv_List.getSelectedIndex());
                String[] id_string = rdvmodif.split("  ", 2);
                new ModifRdv_Patientpage(id_string[0], Calendar.getInstance().getTime(), true);
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == ModifButton ) && (rdv_List.getSelectedIndex() == -1 || rdv_List.getSelectedValue().equals("Pas de RDV pour l'instant à ce jour.")))
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un RDV.");
        if ((e.getSource() == ConsButton ) && (cons_List_.getSelectedIndex() == -1 || cons_List_.getSelectedValue().equals("Pas de RDV pour l'instant à ce jour.")))
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un RDV.");
        else {
            if (e.getSource() == ModifButton) {
                try {
                    ModifRDV();
                    this.dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (e.getSource() == ConsButton) {
               this.dispose();
            }
            if (e.getSource() == SupprButton) {
               SupprRDV();
            }
            if (e.getSource() == exitButton) {
                this.dispose();
                new MyPatientPage();
            }
        }
    }
    
}
