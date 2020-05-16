package UI;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.*;

public class RDVpsy_GUI extends Default_Page implements ActionListener {
    private JScrollPane listScroll;
    private JList<String> rdv_List;
    private DefaultListModel<String> list = new DefaultListModel<>();
    private final JButton AddConsButton = new JButton("Ajouter une consultation ");
    private final JButton CreateButton = new JButton("Creer ");
   // private final JButton ModifButton = new JButton("Modifier ");
    private final JButton SuprButton = new JButton("Supprimer ");
    private int index = -1;
    private final JLabel profile_title = new JLabel("Profile : ");
    private final JLabel profile = new JLabel("Selectionnez un profile");

    public RDVpsy_GUI() {
        createWindow("Consulter RDV", 400, 50, 600, 470);
        setList(true);
        setLocationAndSize();
        addComponentsToFrame();
        addCalendar();
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setList(false);
            }
        });
        this.setVisible(true);
    }

    private void setList(boolean type) {
        String Rdv;
        try {
            list.clear();
            for (int i = 0; i < mySystem.rdvListe.size(); i++) {//commence liste a 1 pour pas avoir la psy dans les clients
                if ((sdf.format(calendar.getDate())).equals(mySystem.rdvListe.get(i).getDate().toString())) {
                    Rdv = String.valueOf(mySystem.rdvListe.get(i).getId());
                    Rdv += "  ";
                    Rdv += String.valueOf(mySystem.rdvListe.get(i).getDate());
                    Rdv += "  ";
                    Rdv += String.valueOf(mySystem.rdvListe.get(i).getHeure());
                    Rdv += "  ";
                    Rdv += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient1());
                    Rdv += "  ";
                    Rdv += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient2());
                    Rdv += "  ";
                    Rdv += mySystem.mariaconnexion.getClient(mySystem.rdvListe.get(i).getClient3());
                    list.addElement(Rdv);
                }
            }
            if (list.size() < 1) {
                list.addElement("Pas de RDV pour l'instant à ce jour.");
            }
        } catch (Exception e) {
        }
        PrintList(type);
    }

    protected void PrintList(boolean type) {
        if (type) {
            rdv_List = new JList<>(list);
            rdv_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listScroll = new JScrollPane(rdv_List);
        }
    }

    protected void setLocationAndSize() {
        profile_title.setBounds(300, 50, 150, 30);
        profile.setBounds(280, 80, 150, 150);
        exitButton.setBounds(400, 400, 100, 23);
        SuprButton.setBounds(260, 400, 100, 23);
       // ModifButton.setBounds(180, 400, 100, 23);
        CreateButton.setBounds(110, 400, 100, 23);
        listScroll.setBounds(80, 230, 460, 100);
        calendar.setBounds(0, 0, 600, 220);
        AddConsButton.setBounds(200, 355, 200, 23);
    }

    protected void addComponentsToFrame() {
        this.add(listScroll);
        this.add(exitButton);
        this.add(CreateButton);
        this.add(SuprButton);
   //     this.add(ModifButton);
        this.add(calendar);
        this.add(AddConsButton);
        CreateButton.addActionListener(this);
        SuprButton.addActionListener(this);
    //    ModifButton.addActionListener(this);
        AddConsButton.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            this.dispose();
            new Psy_GUI();
        }
        if ( (e.getSource() == SuprButton || e.getSource() == AddConsButton) && (rdv_List.getSelectedIndex() == -1 || rdv_List.getSelectedValue().equals("Pas de RDV pour l'instant à ce jour.")))
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un RDV.");
        else {
            if (e.getSource() == SuprButton)
                SupprRDV();
           // if (e.getSource() == ModifButton) {
             //   this.dispose();
                //new CreatRdv_GUI();
            //}
            if (e.getSource() == AddConsButton) {
                this.dispose();
                //new CreatRdv_GUI();
            }

            if (e.getSource() == CreateButton) {
                if (mySystem.patients.size() == 1) {
                    JOptionPane.showMessageDialog(null, "Vous n'avez pas encore de patients.");
                } else {
                    this.dispose();
                    try {
                        new CreatRdv_GUI(sdf.format(calendar.getDate()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Impossible d'ouvrir cette page.");
                    }
                }
            }
        }
    }
}
