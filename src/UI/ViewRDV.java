package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

public class ViewRDV extends Default_Page implements ActionListener {
    private JScrollPane listScrollRdv, listScrollCons ;
    private JList<String> rdv_List, cons_List_;
    private DefaultListModel<String> listpasse = new DefaultListModel<>();
    private DefaultListModel<String> listfutur = new DefaultListModel<>();
    private final JButton ModifButton = new JButton("Modifier RDV futur");
    private final JButton SupprButton = new JButton("Supprimer RDV futur");
    private final JButton SeeConsButton = new JButton("Voir consult ");
    private final JButton SeeRdvButton = new JButton("Voir RDV futur");
    private final JButton SeeRdvButton2 = new JButton("Voir RDV anterieur");
    private final JLabel Rdv_Passe = new JLabel("RDV Passés : ");
    private final JLabel Rdv_Futurs = new JLabel("RDV Futurs : ");
    private final JLabel rdv_contenu = new JLabel("Selectionnez un RDV");
    private boolean type;

    public ViewRDV(boolean typ) {
        type= typ;
        createWindow("Consulter les RDV", 500, 100, 600, 470);
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
                    if ((sdf.format(calendar.getDate())).compareTo(mySystem.rdvListe.get(i).getDate().toString()) >= 0){
                        Rdvpasse = String.valueOf(mySystem.rdvListe.get(i).getId()); Rdvpasse += "  ";
                        Rdvpasse += String.valueOf(mySystem.rdvListe.get(i).getDate()); Rdvpasse += "  ";
                        Rdvpasse += String.valueOf(mySystem.rdvListe.get(i).getHeure()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient1()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient2()); Rdvpasse += "  ";
                        Rdvpasse += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient3());
                        listpasse.addElement(Rdvpasse);
                    }
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
                listpasse.addElement("Pas de RDV passes pour l'instant.");
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
        SeeConsButton.setBounds(425,75,170,23 );
        SeeRdvButton2.setBounds(425,110,170,23 );
        exitButton.setBounds(425, 400, 170, 23);
        ModifButton.setBounds(425, 210, 170, 23);
        SupprButton.setBounds(425, 235, 170, 23);
        SeeRdvButton.setBounds(425,265,170,23);
        listScrollRdv.setBounds(20, 200, 400, 100);
        listScrollCons.setBounds(20, 50, 400, 100);
        rdv_contenu.setBounds(20, 290,400,160);
    }

    protected void addComponentsToFrame() {
        this.add(Rdv_Passe);
        this.add(Rdv_Futurs);
        this.add(listScrollCons);
        this.add(listScrollRdv);
        this.add(exitButton);
        this.add(SeeRdvButton);
        this.add(rdv_contenu);
        this.add(SeeRdvButton2);
        SeeRdvButton.addActionListener(this);
        SeeRdvButton2.addActionListener(this);

        if(type){
            this.add(ModifButton);
            this.add(SupprButton);
            this.add(SeeConsButton);
            ModifButton.addActionListener(this);
            SupprButton.addActionListener(this);
            SeeConsButton.addActionListener(this);
        }
        exitButton.addActionListener(this);

    }

    private void SupprRDV() {
        System.out.println(rdv_List.getSelectedIndex());
        if (rdv_List.getSelectedIndex() != -1) {
            if ((sdf.format(calendar.getDate())).compareTo(sdf.format(java.util.Calendar.getInstance().getTime())) < 0){
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas supprimer un RDV passe.");
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
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas modifier un RDV passe.");
            }
            else {
                String rdvmodif = rdv_List.getModel().getElementAt(rdv_List.getSelectedIndex());
                String[] id_string = rdvmodif.split("  ", 2);
                new ModifRdv_Patientpage(id_string[0], Calendar.getInstance().getTime(), true);
            }
        }
    }

    private  void getCons() throws SQLException{
        if (cons_List_.getSelectedIndex() != -1){
            String rdvId = cons_List_.getModel().getElementAt(cons_List_.getSelectedIndex());
            String[] id_string = rdvId.split("  ", 2);
            new Consultation_GUI(Integer.parseInt(id_string[0]),true);
        }
    }

    private void seeRDV(boolean choice) throws SQLException {
        String rdvId, info;
        if(choice){  rdvId = rdv_List.getModel().getElementAt(rdv_List.getSelectedIndex()); info = "<html>RDV futur<br>"; }
        else  {rdvId = cons_List_.getModel().getElementAt(cons_List_.getSelectedIndex()); info = "<html>RDV passe<br>";}
        String[] id_string = rdvId.split("  ", 2);
        Rdv currentRdv =  mySystem.rdvListe.stream().filter(r -> r.getId() == Integer.parseInt(id_string[0])).findFirst().get();
        info += "Date : " + currentRdv.getDate() + " - " + currentRdv.getHeure() + "<br>Patient(s) : " +
                mySystem.mariaconnexion.getClient(currentRdv.getClient1()) +"  " +  mySystem.mariaconnexion.getClient(currentRdv.getClient2())
                +"  "+  mySystem.mariaconnexion.getClient(currentRdv.getClient3()) + "<br>Prix : " + currentRdv.getPrix() + "€  en " + currentRdv.getPayement() + "</html>";
        rdv_contenu.setText(info);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == ModifButton || e.getSource() == SeeRdvButton || e.getSource() == SupprButton) && (rdv_List.getSelectedIndex() == -1 || rdv_List.getSelectedValue().equals("Pas de RDV futurs pour l'instant." )))
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un RDV.");
        else {
            if ((e.getSource() == SeeConsButton) && (cons_List_.getSelectedIndex() == -1 || cons_List_.getSelectedValue().equals("Pas de RDV passes pour l'instant.")))
                JOptionPane.showMessageDialog(null, "Veuillez selectionner un RDV.");
            else {
                if (e.getSource() == ModifButton) {
                    try {
                        ModifRDV();
                        this.dispose();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if (e.getSource() == SeeRdvButton2){
                    try {
                        seeRDV(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un RDV.");
                    }
                }

                if (e.getSource() == SeeConsButton) {
                        try {
                            getCons();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        this.dispose();

                }
                if (e.getSource() == SeeRdvButton) {
                   try {
                        seeRDV(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner un RDV.");
                    }
                }
                if (e.getSource() == SupprButton) {
                    SupprRDV();
                }
                if (e.getSource() == exitButton) {
                    this.dispose();
                    if (type) new MyPatientPage();
                    else new Patient_GUI();
                }
            }
        }
    }
}
