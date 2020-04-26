package UI;

import java.sql.*;
import java.util.ArrayList;

public class mySystem {
    public static Log page1;
    public static Mariadb mariaconnexion = new Mariadb();
    public static User user;
    public static ArrayList<User> patients = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        mariaconnexion.Connection();
        page1 = new Log();    
        page1.setVisible(true);
        
       patients = mariaconnexion.getPatient();
        new Psy_GUI();
       
    } 
}
