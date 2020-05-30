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
    static final String PASS = "bdd";

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
   preparedStatement = conn .prepareStatement("insert into db.Client values (default, ?, ?, ?, ? , ?,?,NOW())");
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
    
    public void LogDB(String mail, String mdp) throws SQLException { //sert aussi pour update le client quand il a modfif� ses infos
        preparedStatement = conn
                .prepareStatement("select * from db.Client  where mail= ? and mdp =? ");
        preparedStatement.setString(1, mail); // nom
        preparedStatement.setString(2, mdp);   // prénom
        ResultSet getlistes ;
        resultSet = preparedStatement.executeQuery();
        int id = -1;
        while( resultSet.next()){
        	id = resultSet.getInt("Id_Client");
            mySystem.user = new User(resultSet.getInt("Id_Client"),resultSet.getString("Nom_client"),
                    resultSet.getString("Prenom_client"),resultSet.getString("mdp"),resultSet.getString("mail"),
                    resultSet.getString("pub"),resultSet.getBoolean("sexe"), resultSet.getDate("Date_client"));
        }
        
        getlistes =  stmt.executeQuery("SELECT*FROM db.Prof_Client WHERE Id_Client = "+id);
  
  	  while(getlistes.next()) {
  		  String prof_name =  getlistes.getString("Nom_prof"); 
  		  Date prof_date =  getlistes.getDate("Prof_date");
  		  mySystem.user.addProfList(prof_date,prof_name);
  		  System.out.println(prof_name);
  		  System.out.println(mySystem.user.getProfList().size());
  	  }
  	  
  	  getlistes =  stmt.executeQuery("SELECT*FROM db.Couple WHERE Id_Client = "+id);
  	  while(getlistes.next()) {
  		  Boolean en_couple =  getlistes.getBoolean("en_couple"); 
  		  Date couple_date =  getlistes.getDate("Date_couple");
  		  mySystem.user.addCoupleList(couple_date,en_couple);
  	  }
  	  
  	  getlistes =  stmt.executeQuery("SELECT*FROM db.type_p WHERE Id_Client = "+id);
  	  while(getlistes.next()) {
  		  String type_name =  getlistes.getString("Nom_type"); 
  		  Date type_date =  getlistes.getDate("Date_type");
  		 mySystem.user.addTypeList(type_date, type_name);
  	  }
    }

    public boolean Datecheck(String date,int rdv) throws SQLException {
        int count=0;
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        preparedStatement = conn
                .prepareStatement("select * from db.rdv  where Date= ?");
        preparedStatement.setDate(1, sqlDate); // nom
        resultSet = preparedStatement.executeQuery();
        while( resultSet.next()){
            if(resultSet.getInt("Id_rdv") != rdv)
            count++;
        }
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

    public void ModifRDV_sql(Rdv rdv_m,String date )  throws SQLException{
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        preparedStatement = conn
                .prepareStatement("UPDATE db.rdv t SET t.Date = ?, t.Heure = ?, t.Prix = ?, t.Payement = ? WHERE t.Id_rdv = ?");
        preparedStatement.setDate(1, sqlDate);
        preparedStatement.setString(2, rdv_m.getHeure());
        preparedStatement.setFloat(3, rdv_m.getPrix());
        preparedStatement.setString(4, rdv_m.getPayement());
        preparedStatement.setInt(5, rdv_m.getId());
        preparedStatement.executeUpdate();

        mySystem.rdvListe.stream().filter(p-> p.getId()==rdv_m.getId()).map(p-> {p.setHeure(rdv_m.getHeure());
        p.setDate(sqlDate); p.setPayement(rdv_m.getPayement()); p.setPrix(rdv_m.getPrix());
        return null; }).collect(Collectors.toList());
    }

    public void DeleteRdv(int id) throws SQLException {
        stmt = conn.createStatement();
        resultSet= stmt.executeQuery("DELETE FROM db.rdv WHERE Id_rdv =" + id);
        mySystem.rdvListe.remove(mySystem.rdvListe.stream().filter(r -> (r.getId()== id)).findFirst().get());
      //  resultSet = stmt.executeQuery("SELECT * db.client_consultation WHERE Id_RDV =" + id);
      //  while()
            // RAJOUTER LA SUPPRESSION DE LA CONSULTATION

    }
    
    public void modifyClient(int id, String nom, String prenom, String mdp, String mail, String pub, boolean sexe) throws SQLException {
    	stmt = conn.createStatement(); 
    	resultSet = stmt.executeQuery(
    			"UPDATE db.client SET "
    					+ "Nom_client='"+nom+"' ,Prenom_client = '"+prenom+"' , mdp= '"+mdp+"', mail ='"+mail+"', pub ='"+pub+"', sexe ="+sexe
    			+ " WHERE  Id_Client="+id+"; ");
    }
    
    public <T> void modifyItem(String oldString, T newString, String oldDate,  String newDate,  int id, String item) throws SQLException {
    	
    	stmt = conn.createStatement(); 
    	
    	if(item.equals(mySystem.PROSSESSION)) {    		
    		resultSet = stmt.executeQuery(
        			"UPDATE db.prof_client SET "
        					+"Nom_prof='"+newString+"',Prof_date ='"+newDate+"'"
        			+ " WHERE  Id_Client="+id+ "AND Nom_prof="+oldString+"; " //2 primary key
        	);   
    	}
    	
    	if(item.equals(mySystem.TYPE)) {
    		resultSet = stmt.executeQuery(
        			"UPDATE db.type_p SET "
        					+"Nom_type='"+newString+"', Date_type ='"+newDate+"'"
        			+ " WHERE  Id_Client="+id+ "AND Date_type='"+oldDate+"';" //2 primary key
        	);   
    	}
    	
    	if(item.equals(mySystem.COUPLE)) {
    		resultSet = stmt.executeQuery(
        			"UPDATE db.couple SET "
        					+"Nom_type='"+newString+"', Date_Couple='"+newDate+"'"
        			+ " WHERE  Id_Client="+id+ "AND Date_Couple='"+oldDate+"';" //2 primary key
        	);   
    	}   
    	resultSet = preparedStatement.executeQuery(); 
    }
    

    public void deleteItem(String date, String a_supprimer,  int id, String item) throws SQLException {
        stmt = conn.createStatement();
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        if (item.equals(mySystem.PROSSESSION)) {
            preparedStatement= conn.prepareStatement(
                    "DELETE FROM db.prof_client  WHERE  Id_Client=" + id + " AND Nom_prof='" + a_supprimer + "' ;" //2 primary keys
            );
        }
        if (item.equals(mySystem.TYPE)) {
            resultSet = stmt.executeQuery(
                    "DELETE FROM db.type_p  WHERE  Id_Client=" + id + " AND Date_type='" + date + "' ;" //2 primary keys
            );
        }
        if (item.equals(mySystem.COUPLE)) {
            resultSet = stmt.executeQuery(
                    "DELETE FROM db.couple  WHERE  Id_Client=" + id + " AND Date_Couple='" + date + "' ;"
            );
        }
    }



    public void Consult(String patient, String anxiete, String posture, String Mot, int rdv_id) throws SQLException {
        stmt = conn.createStatement();
        String[] splitClient1 = patient.split(" ", 3);
        int anx= Integer.parseInt(anxiete);
        int client = Integer.parseInt(splitClient1[0]);
        resultSet = stmt.executeQuery(
                "SELECT * FROM db.client_consultation  WHERE  Id_Client="+ client+ " AND Id_RDV=" + rdv_id  //2 primary keys
                 );
        if (resultSet.next()){
            preparedStatement= conn.prepareStatement("UPDATE db.Consultation t SET t.Anxiete = ?, t.Mots_cles = ?," +
                    " t.Posture = ? WHERE t.Id_consultation = ?");
            preparedStatement.setInt(1,anx);
            preparedStatement.setString(2,Mot);
            preparedStatement.setString(3,posture);
            preparedStatement.setInt(4, resultSet.getInt("Id_consultation"));
            preparedStatement.executeUpdate();
        }
        else {
            preparedStatement = conn.prepareStatement("insert into db.Consultation values (default, ? , ? , ?)");
            preparedStatement.setInt(1,anx);
            preparedStatement.setString(2,Mot);
            preparedStatement.setString(3,posture);
            preparedStatement.executeUpdate();
            resultSet =preparedStatement.executeQuery("Select LAST_INSERT_ID()");
            if (resultSet.next()){
                preparedStatement = conn.prepareStatement("insert into db.client_consultation values ( ? , ? , ?)");
                preparedStatement.setInt(1,resultSet.getInt(1));
                preparedStatement.setInt(2,client);
                preparedStatement.setInt(3,rdv_id);
                preparedStatement.executeUpdate();
            }
       }
    }

    public String getConsultation(int id_client, int id_rdv) throws SQLException {
        stmt = conn.createStatement(); String info ="";
        resultSet = stmt.executeQuery(
                "SELECT * FROM db.client_consultation  WHERE  Id_Client="+ id_client+ " AND Id_RDV=" + id_rdv);
        if ( resultSet.next()){
            ResultSet res = stmt.executeQuery("SELECT * FROM db.Consultation WHERE Id_consultation = "+ resultSet.getInt("Id_consultation"));
            if (res.next()){
                info = "<html>Niveau d anxiete:  " + res.getInt("Anxiete") + "<br><br>Mots cles:<br>  "
                        + res.getString("Mots_cles") + "<br><br>Posture: <br>" + res.getString("Posture") + "</html>";
            }
        }
        return(info);
    }
    
    public <T> void addItem(int id_client, T a_ajouter, String date, String item) throws SQLException{
    	java.sql.Date sqlDate = java.sql.Date.valueOf(date);  	 
    	
    	if(item.equals(mySystem.PROSSESSION)) {
    		preparedStatement = conn
                    .prepareStatement("INSERT INTO `db`.`prof_client` (`Nom_prof`, `Id_Client`, `Prof_date`) VALUES ('"+a_ajouter+"',"+id_client+",'" + sqlDate + "')");   		   
    	}
    	
    	if(item.equals(mySystem.TYPE)) {
    		if(date == null) {//Pour la creation, la date se met automatiquement a celle d'aujourd'hui dan
                preparedStatement = conn
                        .prepareStatement("INSERT INTO `db`.`type_p` (`Date_type`, `Nom_type`, `Id_Client`) VALUES (default,'"+a_ajouter+"'," + id_client + ")");
        	}else 
              preparedStatement = conn
                      .prepareStatement("INSERT INTO `db`.`type_p` (`Date_type`, `Nom_type`, `Id_Client`) VALUES ('"+date+"','"+a_ajouter+"'," + id_client + ")");	
    	}
    	
    	if(item.equals(mySystem.COUPLE)) {
    		if(date == null) {
                preparedStatement = conn
                        .prepareStatement("insert into  db.couple values (default," + a_ajouter + "," + id_client + ")");
        	}else 
              preparedStatement = conn
                      .prepareStatement("insert into  db.couple values ('" + date + "'," + a_ajouter + "," + id_client + ")");
    	}    	
    	resultSet = preparedStatement.executeQuery();  
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