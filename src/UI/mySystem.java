package UI;

import java.util.ArrayList;

public class mySystem {
    public static Log page1;
    public static Mariadb mariaconnexion = new Mariadb();
    public static User user;
    public static int current_client_id;
    public static User current_client;

    public static ArrayList<User> patients = new ArrayList<>();
    public static ArrayList<Rdv> rdvListe = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        mariaconnexion.Connection();
        patients = mariaconnexion.getPatient();
        rdvListe = mariaconnexion.getRdv();
        page1 = new Log();    
        page1.setVisible(true);

      //  new Psy_GUI();
        //new ProfessionPage(59);
       // new Patient_GUI();
    }
}
