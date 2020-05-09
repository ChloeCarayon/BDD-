package UI;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class RDVpsy_GUI extends Default_Page implements ActionListener {
    
    private JScrollPane listScroll;
    private JList<String> rdv_List;
    private final DefaultListModel<String> list = new DefaultListModel<>();

    private JButton AddConsButton = new JButton("Ajouter une consultation ");
    private JButton ExitButton=new JButton("Exit");
    private JButton CreateButton = new JButton("Creer ");
    private JButton ModifButton = new JButton("Modifier ");
    private JButton SuprButton = new JButton("Supprimer ");
   
    private final JLabel profile_title = new JLabel("Profile : ");
    private final JLabel profile = new JLabel("Selectionnez un profile");

    public RDVpsy_GUI() {
        createWindow("Consulter RDV",400, 50, 600, 470 );
        setList();
        setLocationAndSize();
        addComponentsToFrame();
        addCalendar();
        this.setVisible(true);
    }

    private void setList() {
        String Rdv;
        try {
            for (int i=0; i<mySystem.rdvListe.size();i ++)  {//commence liste a 1 pour pas avoir la psy dans les clients
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
            if(mySystem.rdvListe.size()<1) {
                list.addElement("Pas de RDV pour l'instant.");
            }
        } catch(Exception e) { }
        PrintList();
    }

    protected void PrintList() {
        rdv_List = new JList<>(list);
        rdv_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rdv_List.setVisibleRowCount(-1);
        listScroll = new JScrollPane(rdv_List);
    }

    protected void setLocationAndSize() {
        profile_title.setBounds(300,50,150,30);
        profile.setBounds(280,80,150,150);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        JCalendar calendar4 =
        		new JCalendar(
        		    Locale.FRENCH,
        		    false);
        Border etchedBorder =
        		BorderFactory.createEtchedBorder();
        	    Border emptyBorder =
        		BorderFactory.createEmptyBorder(10, 10, 10, 10);
        	    Border compoundBorder =
        		BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);
      
        	    calendar4.setBorder(compoundBorder);
        	    this.add(calendar4);
        exitButton.setBounds(490,400,100,23);
        SuprButton.setBounds(340,400,100,23);
        ModifButton.setBounds(180,400,100,23);
        CreateButton.setBounds(20,400,100,23);
        listScroll.setBounds(80,230,460,100);
        calendar.setBounds(0,0,600,220);
        AddConsButton.setBounds(200,355,200,23);

       }

    protected void addComponentsToFrame() {
        this.add(listScroll);
        this.add(exitButton);
        this.add(CreateButton);
        this.add(SuprButton);
        this.add(ModifButton);
        this.add(calendar);
        this.add(AddConsButton);
        ExitButton.addActionListener(this);
        CreateButton.addActionListener(this);
        SuprButton.addActionListener(this);
        ModifButton.addActionListener(this);
        AddConsButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exitButton){
            /*                                   EXIT                                      */
            this.dispose();
            new Psy_GUI();
        }
        if(e.getSource()==SuprButton && (rdv_List.getSelectedIndex() != -1) ){
            /*                                   EXIT                                      */
            this.dispose(); //patientList.getSelectedIndex() != -1
            new Psy_GUI();
        }
        if(e.getSource()==ModifButton && (rdv_List.getSelectedIndex() != -1) ) {
            /*                                   EXIT                                      */
            this.dispose();
            //new CreatRdv_GUI();
        }
        if(e.getSource()==AddConsButton && (rdv_List.getSelectedIndex() != -1) ) {
            /*                                   EXIT                                      */
            this.dispose();
            //new CreatRdv_GUI();
        }
        if(e.getSource() == CreateButton) {
            if (mySystem.patients.size() == 1) {
                JOptionPane.showMessageDialog(null, "Vous n'avez pas encore de patients."); 
             }
            else {
                this.dispose();
                try {
                    new CreatRdv_GUI(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getDate()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Impossible d'ouvrir cette page."); 
                }
            }
        }
        if ((e.getSource()==ModifButton || e.getSource()==SuprButton || e.getSource()==AddConsButton ) && (rdv_List.getSelectedIndex() == -1))
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un RDV.");
    }
}
