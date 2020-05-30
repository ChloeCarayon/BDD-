package UI;

import java.util.ArrayList;

public class mySystem {
    public static Log page1;
    public static Mariadb mariaconnexion = new Mariadb();
    public static User user;
    public static int current_client_id;
    public static User current_client;
    public static int backPage = 0;
    
    public static final String PROSSESSION = "Profession";
    public static final String COUPLE = "Couple";
    public static final String TYPE = "Type";
    
    public static ArrayList<User> patients = new ArrayList<>();
    public static ArrayList<Rdv> rdvListe = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        mariaconnexion.Connection();
        patients = mariaconnexion.getPatient();
        rdvListe = mariaconnexion.getRdv();
        page1 = new Log();    
        page1.setVisible(true);
        new MyPatientPage(); 
    }
}
