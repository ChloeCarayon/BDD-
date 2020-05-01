package UI;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;


public class RDVpsy_GUI extends JFrame implements ActionListener{

   // Calendar calendar2 = Calendar.getInstance();
    private JCalendar calendar =  new JCalendar();
    
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
        this.setBounds(400, 150, 500, 500);
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
        ExitButton.setBounds(330,350,100,23);
        ShowButton.setBounds(130,350,100,23);
        SuprButton.setBounds(50,350,100,23);
        ModifButton.setBounds(190,350,100,23);
        listScroll.setBounds(100,100,150,100);
        profile_title.setBounds(300,50,150,30);
        profile.setBounds(280,80,150,150);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
       // MyDateListener listener = new MyDateListener();
        JCalendar calendar4 =
        		new JCalendar(
        		    Locale.FRENCH,
        		    false);
        	 //   calendar4.addDateListener(listener);
        	   

        Border etchedBorder =
        		BorderFactory.createEtchedBorder();
        	    Border emptyBorder =
        		BorderFactory.createEmptyBorder(10, 10, 10, 10);
        	    Border compoundBorder =
        		BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);
      
        	    calendar4.setBorder(compoundBorder);
        	    
        	    
        	    this.add(calendar4);
        	//    contentPane.add(panel1);
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
        this.add(profile_title);
        this.add(ShowButton);
        this.add(SuprButton);
        this.add(ModifButton);
        this.add(profile);
        this.add(calendar);

        ExitButton.addActionListener(this);
        ShowButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ExitButton){
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
