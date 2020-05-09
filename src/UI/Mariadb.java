package UI;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

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

public int readDBClient(String nom, String prenom, String mdp, String mail, String pub, boolean sexe) throws SQLException { 
  resultSet = stmt .executeQuery("select * from db.Client");
   writeResultSet(resultSet); preparedStatement = conn .prepareStatement("insert into db.Client values (default, ?, ?, ?, ? , ?,?,NOW())");
   preparedStatement.setString(1, nom);
   preparedStatement.setString(2, prenom); // prÃ©nom
  preparedStatement.setString(3, mdp); // mdp 
  preparedStatement.setString(4, mail); // mail 
  preparedStatement.setString(5, pub); //pub 
  preparedStatement.setBoolean(6, sexe); preparedStatement.executeUpdate(); preparedStatement = conn .prepareStatement("SELECT Nom_client, Prenom_client, mdp, mail, pub,sexe from db.Client"); 
  preparedStatement.execute(); 
  ResultSet rs = preparedStatement.executeQuery("Select LAST_INSERT_ID()"); 
  int id_nouveau_client = -1; 
  if(rs.next()) { 
    id_nouveau_client = rs.getInt(1); } 
    return id_nouveau_client;
    }
    
    public void addDBCouple(int id_client, Date date, boolean couple) throws SQLException{
    	resultSet = stmt
                .executeQuery("select * from db.couple");
    	
    	if(date == null) {
            preparedStatement = conn
                    .prepareStatement("insert into  db.couple values (default," + couple + "," + id_client + ")");
    	}else 
          preparedStatement = conn
                  .prepareStatement("insert into  db.couple values (" + date + "," + couple + "," + id_client + ")");
         
    	resultSet = preparedStatement.executeQuery();          
    }

    
    public void addDBType(int id_client, Date date, String type) throws SQLException{
    	resultSet = stmt.executeQuery("select * from db.type");

    	if(date == null) {
            preparedStatement = conn
                    .prepareStatement("INSERT INTO `db`.`type` (`Date_type`, `Nom_type`, `Id_Client`) VALUES (default,'"+type+"'," + id_client + ")");
    	}else 
          preparedStatement = conn
                  .prepareStatement("INSERT INTO `db`.`type` (`Date_type`, `Nom_type`, `Id_Client`) VALUES ('"+date+"','"+type+"'," + id_client + ")");

    	resultSet = preparedStatement.executeQuery();          
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
                    resultSet.getString("pub"),resultSet.getBoolean("sexe"), resultSet.getDate("Date_client"));
        }
    }

    public boolean Datecheck(String date) throws SQLException {
        int count=0;
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        preparedStatement = conn
                .prepareStatement("select * from db.rdv  where Date= ?");
        preparedStatement.setDate(1, sqlDate); // nom
        resultSet = preparedStatement.executeQuery();
        while( resultSet.next()) count++;
        return count != 20;
    }
    
    public ArrayList<User> getPatient() throws SQLException {
    	ArrayList<User> list = new ArrayList<>();
    	Statement st = conn.createStatement();
    	 ResultSet rs = st.executeQuery("SELECT*FROM db.Client");
    	 ResultSet getlistes ;
         while(rs.next()) {
    	  int id = rs.getInt("Id_Client"); 
    	  String name= rs.getString("Nom_client");
    	  String prenom = rs.getString("Prenom_client");
    	  String mdp = rs.getString("mdp");
    	  String mail = rs.getString("mail");
    	  String pub = rs.getString("pub");
    	  boolean sexe = rs.getBoolean("sexe");
    	  Date client_date = rs.getDate("Date_client");
    	  User client  = new  User(id, name, prenom, mdp, mail, pub, sexe, client_date);
    	  
    	  
    	  getlistes =  st.executeQuery("SELECT*FROM db.Prof_Client WHERE Id_Client = "+id);
    	  //Recup�re les datas de la table profession_client correspondant au client 
    	  while(getlistes.next()) {
    		  String prof_name =  getlistes.getString("Nom_prof"); 
    		  Date prof_date =  getlistes.getDate("Prof_date");
    		  client.addProfList(prof_date,prof_name);
    	  }
    	  
    	  getlistes =  st.executeQuery("SELECT*FROM db.Couple WHERE Id_Client = "+id);
    	  while(getlistes.next()) {
    		  Boolean en_couple =  getlistes.getBoolean("en_couple"); 
    		  Date couple_date =  getlistes.getDate("Date_couple");
    		  client.addCoupleList(couple_date,en_couple);
    	  }
    	  
    	  getlistes =  st.executeQuery("SELECT*FROM db.type_p WHERE Id_Client = "+id);
    	  while(getlistes.next()) {
    		  String type_name =  getlistes.getString("Nom_type"); 
    		  Date type_date =  getlistes.getDate("Date_type");
    		  client.addTypeList(type_date, type_name);
    	  }
    	  
    	  list.add(client);
    	 }
    	 list.remove(0); // retire la psy de la liste client
    	 return  list;
    	 // java.sql.Date sqlDate = java.sql.Date.valueOf(date);
    }
    
    public ArrayList<Rdv> getRdv() throws SQLException {
    	ArrayList<Rdv> list = new ArrayList<>();
    	Statement st = conn.createStatement();
    	 ResultSet rs = st.executeQuery("SELECT*FROM db.rdv");
    	 while(rs.next()) { 
    	  int id = rs.getInt("Id_rdv");
    	  Date jour= rs.getDate("Date");
    	  String heure = rs.getString("Heure");
    	  float prix = rs.getFloat("Prix");
    	  String pay = rs.getString("Payement");
    	  int cl1 = rs.getInt("Id_Client");
    	  int cl2 = rs.getInt("Id_Client_2");
    	  int cl3 = rs.getInt("Id_Client_3");
    	  int consul = rs.getInt("Id_consultation");
    	  list.add(new  Rdv(id, (java.sql.Date) jour, heure, prix, pay, cl1, cl2, cl3, consul));
    	 }
    	 return  list; 
    }

    private Integer FindClient(String Cl) throws SQLException {
        if ( !Cl.equals("null")){
            String[] splitClient1 = Cl.split(" . ", 5);
            preparedStatement = conn.prepareStatement("select * from db.Client  where Nom_client= ? and Prenom_client =? ");
            preparedStatement.setString(1, splitClient1[0]);
            preparedStatement.setString(2, splitClient1[1]);
            resultSet = preparedStatement.executeQuery();
            while( resultSet.next())  return(resultSet.getInt("Id_Client"));
        }
        return null;
    }

    public String getClient(int Cl) throws SQLException {
        preparedStatement = conn.prepareStatement("select * from db.Client  where Id_client=" + Cl);
        resultSet = preparedStatement.executeQuery();
        String Client="";
        while( resultSet.next()){
            Client = resultSet.getString("Nom_client"); Client+=" ";
            Client += resultSet.getString("Prenom_client");
        }
        return(Client);
    }

    public void FillRDV(String date, String heure, float prix, String pay, String cl1, String cl2, String cl3, Integer cons )  throws SQLException{
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        Integer c1, c2, c3;
        c1 = FindClient(cl1); c2 = FindClient(cl2); c3 = FindClient(cl3);
        preparedStatement = conn
                .prepareStatement("insert into  db.rdv values (default, ?, ?, ?, ? , ?, ?, ?,?)");

        preparedStatement.setDate(1, sqlDate); 
        preparedStatement.setString(2, heure);
        preparedStatement.setFloat(3, prix);
        preparedStatement.setString(4, pay);
        if(c1 == null) preparedStatement.setNull(5, Types.NULL);
        else preparedStatement.setInt(5, c1);
        if(c2 == null) preparedStatement.setNull(6, Types.NULL);
        else preparedStatement.setInt(6, c2);
        if(c3 == null) preparedStatement.setNull(7, Types.NULL);
        else preparedStatement.setInt(7, c3);
        preparedStatement.setNull(8, Types.NULL);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.executeQuery("Select LAST_INSERT_ID()");
        if(rs.next()) {
           int id = rs.getInt(1);
           rs = preparedStatement.executeQuery("Select * from db.rdv where Id_rdv=" +id);
            while(rs.next()) {
                int c_1 = rs.getInt("Id_Client");
                int c_2 = rs.getInt("Id_Client_2");
                int c_3 = rs.getInt("Id_Client_3");
                int consul = rs.getInt("Id_consultation");
                mySystem.rdvListe.add(new  Rdv(id,sqlDate , heure, prix, pay, c_1, c_2, c_3, consul));
            }
        }
    }

    public boolean DeleteRdv(int id) {
        try{
        stmt = conn.createStatement();
        resultSet= stmt.executeQuery("DELETE FROM db.rdv WHERE Id_rdv =" + id);
        mySystem.rdvListe.remove(mySystem.rdvListe.stream().filter(r -> (r.getId()== id)).findFirst().get());
            // RAJOUTER LA SUPPRESSION DE LA CONSULTATION
        return true; }
        catch( SQLException e){
            return false;
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