package UI;

import java.util.ArrayList;
import java.sql.Date;

public class User {
   private int Id_User; String Nom; String Prenom; String Email; Boolean Sexe; String Pub; String Password;
   private ArrayList<Profession> profList; 
   private Date anciennete;
   
    public User(int id_User, String nom, String prenom,String password, String email,String pub, boolean sexe, Date ancien) {
        this.Id_User = id_User;
        this.Nom = nom;
        this.Prenom = prenom;
        this.Email = email;
        this.Sexe = sexe;
        this.Pub = pub;
        this.Password = password;
        this.profList = new ArrayList<>();
        this.setAnciennete(ancien);
    }

////Getters and Setters 
    public User(int id_User, String password) {
        Id_User = id_User;
        Password = password;
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSexe() {
    	if(this.Sexe)
    		return "Homme";
        return "Femme";
    }

    public void setSexe(Boolean sexe) {
        Sexe = sexe;
    }

    public String getPub() {
        return Pub;
    }

    public void setPub(String pub) {
        Pub = pub;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

	public ArrayList<Profession> getProfList() {
		return profList;
	}

	public void addProfList(Profession p) {
		this.profList.add(p);
	}

	public Date getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(Date anciennete) {
		this.anciennete = anciennete;
	}
	
	
////// Methodes 
	public String toString() {
		
		String p = " "; 
		if(this.profList.size()==0) 
			p = "Aucune profession enregistrée";
		else {
			for(Profession k : profList) {
				p += k.toString();
				p +="<br>";
			}
		}
		
		
		return "<html> Nom :  " +this.Nom
				+ "<br> <br> Prénom : "+ this.Prenom
				+"<br> <br>  Email :  "+ this.Email
				+"<br> <br>  Client depuis  :  " +this.anciennete.toString()
				+"<br> <br>  Sexe :"+ this.getSexe() 
				+ "<br> <br> Professions exercées : "+p;
	}
}	
