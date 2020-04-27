package UI;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Mariadb {
    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://127.0.0.1/db";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "new_password";

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Connection getConn() {
        return conn;
    }
    public ResultSet getResultSet() {
        return resultSet;
    }

    public void Connection() throws Exception{
        try{
            Class.forName(JDBC_DRIVER);
            // Setup the connection with the DB
            conn = DriverManager
                    .getConnection(DB_URL, USER, PASS);

            // Statements allow to issue SQL queries to the database
            stmt = conn.createStatement();
        }catch (Exception e) {
            throw e;
        }
    }

    public void readDBClient(String nom, String prenom, String mdp, String mail, String pub, boolean sexe) throws SQLException {
        resultSet = stmt
                .executeQuery("select * from db.Client");
        writeResultSet(resultSet);
        preparedStatement = conn
                .prepareStatement("insert into  db.Client values (default, ?, ?, ?, ? , ?,?)");
        // Parameters start with 1
        preparedStatement.setString(1, nom); // nom
        preparedStatement.setString(2, prenom);   // prénom
        preparedStatement.setString(3, mdp);      // mdp
        preparedStatement.setString(4, mail);      // mail
        preparedStatement.setString(5, pub);      //pub
        preparedStatement.setBoolean(6, sexe);
        preparedStatement.executeUpdate();

        preparedStatement = conn
                .prepareStatement("SELECT Id_client, Nom_client, Prenom_client, mdp, mail, pub,sexe from db.Client");
        resultSet = preparedStatement.executeQuery();
        writeResultSet(resultSet);
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            int Id = resultSet.getInt("Id_Client");
            String nom = resultSet.getString("Nom_client");
            String prenom = resultSet.getString("Prenom_client");
            String mdp = resultSet.getString("mdp");
            String mail = resultSet.getString("mail");
            String pub = resultSet.getString("pub");
            boolean sexe = resultSet.getBoolean("sexe");
            java.lang.System.out.println("Id: " + Id);
            java.lang.System.out.println("Nom: " + nom);
            java.lang.System.out.println("Prenom: " + prenom);
            java.lang.System.out.println("mdp: " + mdp);
            java.lang.System.out.println("mail: " + mail);
            java.lang.System.out.println("pub: " + pub);
            java.lang.System.out.println("sexe: " + sexe);
        }
    }

    public void LogDB(String mail, String mdp) throws SQLException {
        preparedStatement = conn
                .prepareStatement("select * from db.Client  where mail= ? and mdp =? ");
        preparedStatement.setString(1, mail); // nom
        preparedStatement.setString(2, mdp);   // prénom
        resultSet = preparedStatement.executeQuery();
        while( resultSet.next()){
            mySystem.user = new User(resultSet.getInt("Id_Client"),resultSet.getString("Nom_client"),
                    resultSet.getString("Prenom_client"),resultSet.getString("mdp"),resultSet.getString("mail"),
                    resultSet.getString("pub"),resultSet.getBoolean("sexe"));
        }
    }
    
    public ArrayList<User> getPatient() throws SQLException {
    	ArrayList<User> list = new ArrayList<>();
    	
    	Statement st = conn.createStatement();
    	 ResultSet rs = st.executeQuery("SELECT*FROM db.Client");
    	 while(rs.next()) { 
    	  int id = rs.getInt("Id_Client"); 
    	  String name= rs.getString("Nom_client");
    	  String prenom = rs.getString("Prenom_client");
    	  String mdp = rs.getString("mdp");
    	  String mail = rs.getString("mail");
    	  String pub = rs.getString("pub");
    	  boolean sexe = rs.getBoolean("sexe");
    	  list.add(new  User(id, name, prenom, mdp, mail, pub, sexe));
    	 }
    	 list.remove(0); // retire la psy de la liste
    	 return  list; 
    }
    
    public ArrayList<Rdv> getRdv() throws SQLException {
    	ArrayList<Rdv> list = new ArrayList<>();
    	  	
    	Statement st = conn.createStatement();
    	 ResultSet rs = st.executeQuery("SELECT*FROM db.rdv");
    	 
    	 while(rs.next()) { 
    	  int id = rs.getInt("Id_rdv");
    	  Date jour= rs.getDate("Date");
    	  Time heure = rs.getTime("Heure");
    	  float prix = rs.getFloat("Prix");
    	  String pay = rs.getString("Payement");
    	  int cl1 = rs.getInt("Id_Client");
    	  int cl2 = rs.getInt("Id_Client_2");
    	  int cl3 = rs.getInt("Id_Client_3");
    	  int consul = rs.getInt("Id_consultation");
  
    	  list.add(new  Rdv(id, jour, heure, prix, pay, cl1, cl2, cl3, consul));  
    	 }
    	 
    	 return  list; 
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {

        }
    }
    }


/*
EXEMPLE POUR COMPRENDRE


    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.mariadb.jdbc.Driver");
            // Setup the connection with the DB
            conn = DriverManager
                    .getConnection("jdbc:mariadb://127.0.0.1/db", USER, PASS);

            // Statements allow to issue SQL queries to the database
            stmt = conn.createStatement();
            // Result set get the result of the SQL query
            resultSet = stmt
                    .executeQuery("select * from db.Client");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            boolean sexe = true;
            preparedStatement = conn
                    .prepareStatement("insert into  db.Client values (default, ?, ?, ?, ? , ?,?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Dupond"); // nom
            preparedStatement.setString(2, "Michel");   // prénom
            preparedStatement.setString(3, "amamama");      // mdp
            preparedStatement.setString(4, "mich@gmail.com");      // mail
            preparedStatement.setString(5, "BaO");      //pub
            preparedStatement.setBoolean(6, sexe);
            preparedStatement.executeUpdate();

            preparedStatement = conn
                    .prepareStatement("SELECT Id_client, Nom_client, Prenom_client, mdp, mail, pub,sexe from db.Client");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = conn
                    .prepareStatement("delete from db.client where Nom_client= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = stmt
                    .executeQuery("select * from db.Client");
            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

        public void readDB() throws SQLException {
        resultSet = stmt
                .executeQuery("select * from db.Client");
        writeResultSet(resultSet);
        boolean sexe = false;
        preparedStatement = conn
                .prepareStatement("insert into  db.Client values (default, ?, ?, ?, ? , ?,?)");
        // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
        // Parameters start with 1
        preparedStatement.setString(1, "Beau"); // nom
        preparedStatement.setString(2, "Corinne");   // prénom
        preparedStatement.setString(3, "papapa");      // mdp
        preparedStatement.setString(4, "corinne@gmail.com");      // mail
        preparedStatement.setString(5, "Internet");      //pub
        preparedStatement.setBoolean(6, sexe);
        preparedStatement.executeUpdate();

        preparedStatement = conn
                .prepareStatement("SELECT Id_client, Nom_client, Prenom_client, mdp, mail, pub,sexe from db.Client");
        resultSet = preparedStatement.executeQuery();
        writeResultSet(resultSet);
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
 */