package UI;

import java.sql.*;

public class Main {
    public static Log page1;
    public static Mariadb mariaconnexion = new Mariadb();
    public static void main(String[] args) throws Exception {
        mariaconnexion.readDataBase();
        page1 = new Log();
        page1.setVisible(true);
    }

    public static void Connexion(){

    }
}
