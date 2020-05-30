package UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date;

public class User {
   private int Id_User; String Nom; String Prenom; String Email; Boolean Sexe; String Pub; String Password;
   private Map<Date, String> profList;
   private Map<Date,String> typeList; 
   private Map<Date,Boolean> coupleList;    
   private Date anciennete;
   
    public User(int id_User, String nom, String prenom,String password, String email,String pub, boolean sexe, Date ancien) {
        this.Id_User = id_User;
        this.Nom = nom;
        this.Prenom = prenom;
        this.Email = email;
        this.Sexe = sexe;
        this.Pub = pub;
        this.Password = password;
        this.profList = new HashMap<>();
        this.setAnciennete(ancien);
        this.typeList = new HashMap<>();
        this.coupleList = new HashMap<>();
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

	public  Map<Date,String>  getProfList() {
		return profList;
	}

	public Map<Date,Boolean> getCoupleList() {
		return coupleList;
	}

	public Date getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(Date anciennete) {
		this.anciennete = anciennete;
	}
	
	public Map<Date,String> getTypeList() {
		return typeList;
	}

////METHODES ADD
	public void addTypeList(Date d, String t) {
		this.typeList.put(d, t);
	}
	
	public void addCoupleList(Date d, Boolean c) {
		this.coupleList.put(d, c);
	}	

	public void addProfList(Date d, String p) {
		this.profList.put(d, p);
		System.out.println(Id_User+"  "+profList.size());
	}	
	
	public String CoupleToString(Date key) {
		if(this.coupleList.get(key))
			return "En Couple"; 
		return "Celibataire";
	}
////// Methode to String
	
	public String toString() {
		String p = " "; 
		if(this.profList.size()==0) 
			p = "Aucune profession enregistree";
		else {
			for(Date d : profList.keySet()) {
				p += profList.get(d);
				p +=" en ";
				p += d;
				p +=" <br>";				
			}
		}
		
		String t = " "; 
		if(this.typeList.size()==0) 
			t += "Aucun type enregistré";
		else {
			for(Date d : typeList.keySet()) {
				t += typeList.get(d);
				t +=" en ";
				t += d;
				t +=" <br>";
			}
		}
		
		String c = " "; 
		if(this.coupleList.size()==0) 
			c += "Aucun couple enregistré";
		else {
			for(Date d : coupleList.keySet()) {
				if(coupleList.get(d))
					c += "En couple";
				else c+= "Célibataire";				
				c +=" en ";
				c += d;
				c +=" <br>";
			}
		}
		
		return "<html> Nom :  " +this.Nom
				+ "<br> <br> Prenom : "+ this.Prenom
				+"<br> <br>  Email :  "+ this.Email
				+"<br> <br>  Client depuis  :  " +this.anciennete.toString()
				+"<br> <br>  Sexe : "+ this.getSexe() 
				+" <br> <br>  Type  : "+ t
				+" <br> <br>  Situation  : "+ c
				+" <br> <br> Professions exercees : "+p;
	}	
}	
