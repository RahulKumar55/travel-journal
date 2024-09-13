package application;

import java.util.Date;

//JournalEntry object contains a title, a description and an auto generated date at the time of writing.
public class JournalEntry implements java.io.Serializable {
	
	private String  title;
	private String entry;
	private Date entryDate;
	
	public JournalEntry() {
		entryDate = new Date();
		title = "";
		entry = "";
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getEntry() {
		return entry;
	}
	
	public void setEntry(String entry) {
		this.entry = entry;
	}
	
	public Date getDate() {
		return entryDate;
	}
	
	public void setDate(Date date) {
		this.entryDate = date;
	}

}
