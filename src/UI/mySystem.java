package UI;

import java.util.ArrayList;

public class mySystem {
    public static Log page1;
    public static Mariadb mariaconnexion = new Mariadb();
    public static User user;

    public static ArrayList<User> patients = new ArrayList<>();

    public static ArrayList<Rdv> rdvListe = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        mariaconnexion.Connection();
        page1 = new Log();    
        page1.setVisible(true);
        new Psy_GUI();

       patients = mariaconnexion.getPatient();
       rdvListe = mariaconnexion.getRdv();

       new MyPatientPage();       
    } 
}
