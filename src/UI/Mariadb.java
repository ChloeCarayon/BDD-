package UI;
import java.sql.*;

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
            Class.forName("org.mariadb.jdbc.Driver");
            // Setup the connection with the DB
            conn = DriverManager
                    .getConnection("jdbc:mariadb://127.0.0.1/db", "root", "new_password");

            // Statements allow to issue SQL queries to the database
            stmt = conn.createStatement();
        }catch (Exception e) {
            throw e;
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

    public void readDBClient(String nom, String prenom, String mdp, String mail, String pub, boolean sexe) throws SQLException {
        resultSet = stmt
                .executeQuery("select * from db.Client");
        writeResultSet(resultSet);
        preparedStatement = conn
                .prepareStatement("insert into  db.Client values (default, ?, ?, ?, ? , ?,?)");
        // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
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




    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.mariadb.jdbc.Driver");
            // Setup the connection with the DB
            conn = DriverManager
                    .getConnection("jdbc:mariadb://127.0.0.1/db", "root", "new_password");

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

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        System.out.println("ECRIRE LES R2SULTATS");
        while (resultSet.next()) {
            String nom = resultSet.getString("Nom_client");
            String prenom = resultSet.getString("Prenom_client");
            String mdp = resultSet.getString("mdp");
            String mail = resultSet.getString("mail");
            String pub = resultSet.getString("pub");
            boolean sexe = resultSet.getBoolean("sexe");
            System.out.println("Nom: " + nom);
            System.out.println("Prenom: " + prenom);
            System.out.println("mdp: " + mdp);
            System.out.println("mail: " + mail);
            System.out.println("pub: " + pub);
            System.out.println("sexe: " + sexe);

        }
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
