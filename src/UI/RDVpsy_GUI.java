package UI;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.Border;


public class RDVpsy_GUI extends Default_Page implements ActionListener {
    private JCalendar calendar =  new JCalendar();
    private JScrollPane listScroll;
    private JList<String> rdv_List;
    private final DefaultListModel<String> list = new DefaultListModel<>();
    private JButton ExitButton=new JButton("Exit");
    private JButton CreateButton = new JButton("Creer ");
    private JButton ModifButton = new JButton("Modifier ");
    private JButton SuprButton = new JButton("Supprimer ");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final JLabel profile_title = new JLabel("Profile : ");
    private final JLabel profile = new JLabel("Selectionnez un profile");

    public RDVpsy_GUI() {
        createWindow("Consulter RDV",400, 50, 600, 470 );
        setList();
        setLocationAndSize();
        addComponentsToFrame();

        this.setVisible(true);
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
                list.addElement("bloup bloup ");
            }
        }catch(IndexOutOfBoundsException e) {
            list.addElement("bloup bloup");
        }catch(Exception e) {
            list.addElement("bloup bloup");
        }
        PrintList();
    }

    protected void PrintList() {
        rdv_List = new JList<>(list);
        rdv_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rdv_List.setVisibleRowCount(-1);
        listScroll = new JScrollPane(rdv_List);
    }

    protected void setLocationAndSize() {

        ExitButton.setBounds(330,350,100,23);
        ModifButton.setBounds(130,350,100,23);
        SuprButton.setBounds(50,350,100,23);
        CreateButton.setBounds(190,350,100,23);
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
    

        ExitButton.setBounds(490,400,100,23);
        SuprButton.setBounds(340,400,100,23);
        ModifButton.setBounds(180,400,100,23);
        CreateButton.setBounds(20,400,100,23);
        listScroll.setBounds(80,250,460,100);
        calendar.setBounds(0,0,600,220);
       }

    protected void addComponentsToFrame() {
        this.add(listScroll);
        this.add(ExitButton);
        this.add(CreateButton);
        this.add(SuprButton);
        this.add(ModifButton);
        this.add(calendar);
        ExitButton.addActionListener(this);
        CreateButton.addActionListener(this);
        SuprButton.addActionListener(this);
        ModifButton.addActionListener(this);
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

        if(e.getSource() == CreateButton) {
            if (mySystem.patients.size() == 1)
                JOptionPane.showMessageDialog(null, "Vous n'avez pas encore de patients.");
            else {
                this.dispose();
              /*  try {
                 //   new CreatRdv_GUI(calendar.getDate());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }*/
            }
        }
    }
}
