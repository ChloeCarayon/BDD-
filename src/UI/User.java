package UI;

public class User {
    int Id_User; String Nom; String Prenom; String Email; Boolean Sexe; String Pub; String Password;

    public User(int id_User, String nom, String prenom,String password, String email,String pub, boolean sexe) {
        Id_User = id_User;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        Sexe = sexe;
        Pub = pub;
        Password = password;
    }

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

    public Boolean getSexe() {
        return Sexe;
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
}
