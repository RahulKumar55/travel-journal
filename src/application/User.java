package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// User object contains username, password and a list of journal entries
public class User implements java.io.Serializable{
	
	private String username;
	private String password;
	private ArrayList<JournalEntry> journal = new ArrayList<>();
	
	public User() {
		username = "";
		password = "";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<JournalEntry> getJournal(){
		return journal;
	}
	
	public void setJournal(ArrayList<JournalEntry> journal){
		this.journal = journal;
	}
	
	public void addEntry(JournalEntry entry) {
		journal.add(0, entry);
	}
	
	public void removeEntry(JournalEntry entry) {
		journal.remove(entry);
	}
	
	public void updateEntry(JournalEntry entry, int in) {
		journal.set(in, entry);
	}

	// Changes the password before writing for security purposes
	private void writeObject(ObjectOutputStream os) throws IOException {
	    this.password = "r#1h#xVU$xB" + password;
	    os.defaultWriteObject();
	}
	
	//Removes the changed made while writing
	private void readObject(ObjectInputStream is) throws ClassNotFoundException, IOException {
	    is.defaultReadObject();
	    this.password = password.substring(11);
	}
}
