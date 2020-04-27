package UI;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class RDVpsy_GUI extends JFrame implements ActionListener{
    private JCalendar calendar =new JCalendar();
    private JScrollPane listScroll;
    private JList<String> rdv_List;
    private final DefaultListModel<String> list = new DefaultListModel<>();
    private JButton ExitButton=new JButton("Exit");
    private JButton ShowButton = new JButton("Voir ");
    private JButton ModifButton = new JButton("Modifier ");
    private JButton SuprButton = new JButton("Supprimer ");

    private final JLabel profile_title = new JLabel("Profile : ");
    private final JLabel profile = new JLabel("Selectionnez un profile");

    public RDVpsy_GUI() {
        createWindow();
        setList();
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);
    }

    public void createWindow() {
        this.setTitle("My Patient");
        this.setBounds(400, 50, 700, 700);
        this.getContentPane().setBackground(Color.getHSBColor(269, 100, 95));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void setList() {
        String Rdv;
        try {
            for (int i=0; i<mySystem.rdvListe.size();i ++)  {//commence liste a 1 pour pas avoir la psy dans les clients
                Rdv = String.valueOf(mySystem.rdvListe.get(i).getId());
                Rdv += "  ";
                list.addElement(Rdv);
            }
            if(mySystem.rdvListe.size()<1) {
                list.addElement("Vous n'avez pas encore de RDV !");
            }
        }catch(IndexOutOfBoundsException e) {
            list.addElement("Vous n'avez pas encore de RDV !");
        }catch(Exception e) {
            list.addElement("Impossible d'afficher les RDV");
        }
        PrintList();
    }

    private void PrintList() {
        rdv_List = new JList<>(list);
        rdv_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rdv_List.setVisibleRowCount(-1);
        listScroll = new JScrollPane(rdv_List);
    }

    private void setLocationAndSize() {
        ExitButton.setBounds(500,500,100,23);
        ShowButton.setBounds(400,500,100,23);
        SuprButton.setBounds(300,500,100,23);
        ModifButton.setBounds(200,500,100,23);
        listScroll.setBounds(100,350,150,100);
        calendar.setBounds(30,30,420,220);
        }

    private void getProfile() {
        int index = rdv_List.getSelectedIndex();
        String infos;
        try {
            infos = "<html> Nom :  ";
            infos += String.valueOf(mySystem.rdvListe.get(index).getId());
        }catch(ArrayIndexOutOfBoundsException aiobe) {
            infos = "Aucun rdv selectionne !";
        }
        catch(Exception e) {
            infos = "Impossible d'afficher le rdv";
        }

        profile.setText(infos);
    }

    private void addComponentsToFrame() {
        this.add(listScroll);
        this.add(ExitButton);
        this.add(ShowButton);
        this.add(SuprButton);
        this.add(ModifButton);
        this.add(calendar);
        ExitButton.addActionListener(this);
        ShowButton.addActionListener(this);
    }

    private void CreateCAl(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ExitButton){
            /*                                   EXIT                                      */
            this.dispose();
            new Psy_GUI();
        }
        else if(e.getSource()==SuprButton){
            /*                                   EXIT                                      */
            this.dispose();
            new Psy_GUI();
        }

        if(e.getSource() == ShowButton && rdv_List.getSelectedIndex() != -1 );
        /*                                 Affiche nouveau profile quand s�lectionne nouveau nom                    */
        {
            getProfile();
        }
    }
}
