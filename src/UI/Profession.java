package UI;

import java.sql.Date;
public class Profession {
	private String prof_name; 
	private Date prof_date;
	
	public Profession(String name, Date date) {
		this.prof_date = date; 
		this.prof_name = name;
	}
	
///Getters and setters 
	public String getProf_name() {
		return prof_name;
	}
	
	public void setProf_name(String prof_name) {
		this.prof_name = prof_name;
	}
	
	public Date getProf_date() {
		return prof_date;
	}
	
	public void setProf_date(Date prof_date) {
		this.prof_date = prof_date;
	}	
	
///Methodes 
	public String toString() {
		return this.prof_name+" le " +this.prof_date;
	}
}
