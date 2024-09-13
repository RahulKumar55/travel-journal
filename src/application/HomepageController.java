package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomepageController implements Initializable{
	
	@FXML MenuItem miExit;
	@FXML MenuItem miAbout;
	@FXML Text textHeader;
	@FXML TextField tfAddTitle;
	@FXML TextArea tfAddDesc;
	@FXML Button btnAdd;
	@FXML Button btnOpen;
	@FXML ListView<String> lvEntries;
	
	@Override
	   public void initialize(URL location, ResourceBundle resources) {

		//Shows the author of the journal
		textHeader.setText(Main.currentUser.getUsername() + "'s " + "Journal");
		
		//This function fills the listView with journal entries
		fillList();
		
		//clicking menu item Exit closes window.
		//Writes data to the file while closing;
		miExit.setOnAction(event -> {
			try {
				ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("data.txt"));
				for(User u: Main.users) {
					out.writeObject(u);					
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		});
		
		//Clicking menu item about shows an information dialog box.
		miAbout.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("This is a travel Journal.\n"
					+ "You can create entries with a title and a description.\n"
					+ "It will add the date of writing automatically.\n"
					+"All the entries are displayed here. You can open to read them.\n"
					+ "Open entries to read, update or delete them.");

			alert.showAndWait();
		});
		
		//Adds new entries to the journal.
		//Shows appropriate errors.
		btnAdd.setOnAction(event -> {
			if(!tfAddTitle.getText().trim().isEmpty() && !tfAddDesc.getText().trim().isEmpty()) {
				JournalEntry temp = new JournalEntry();
				temp.setTitle(tfAddTitle.getText().trim());
				temp.setEntry(tfAddDesc.getText().trim());
				Main.currentUser.addEntry(temp);
				fillList();
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Field Missing");
				alert.setContentText("Both fields are required");
				alert.showAndWait();
			}
			tfAddTitle.setText("");
			tfAddDesc.setText("");
		});
		
		//Select an entry and click open to change scene with entry details.
		btnOpen.setOnAction(event -> {
			int index = lvEntries.getSelectionModel().getSelectedIndex();
			if(index > -1) {
				EntryController.openIndex = index;
				EntryController.openedJournal = Main.currentUser.getJournal().get(index);
				
				Stage primaryStage = (Stage) btnOpen.getScene().getWindow();
				BorderPane entry;
				try {
					entry = (BorderPane)FXMLLoader.load(getClass().getResource("entry.fxml"));
					Scene entryScene = new Scene(entry);
					primaryStage.setScene(entryScene);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		});
		
	}
	
	public void fillList() {
		lvEntries.getItems().clear();
		for(JournalEntry j: Main.currentUser.getJournal()) {
			lvEntries.getItems().add(j.getTitle() + "\t\t\t\t" + j.getDate().toLocaleString());
		}
	}
}
