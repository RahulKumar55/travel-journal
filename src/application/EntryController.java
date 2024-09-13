package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EntryController implements Initializable{
	
	@FXML Text textHeader;
	@FXML TextField tfTitle;
	@FXML TextArea tfDesc;
	@FXML Button btnBack;
	@FXML Button btnDelete;
	@FXML Button btnEdit;
	@FXML Button btnSave;
	
	public static JournalEntry openedJournal;
	public static int openIndex;
	
	@Override
	   public void initialize(URL location, ResourceBundle resources) {
		
		//Shows the author of the journal
		textHeader.setText(Main.currentUser.getUsername() + "'s " + "Journal");
		
		//Fills the uneditable text fields with entry title and description
		tfTitle.setText(openedJournal.getTitle());
		tfDesc.setText(openedJournal.getEntry());
		
		//Button to go back to home screen
		btnBack.setOnAction(event -> {
			goBack();
		});
		
		// Button to delete the entry and go back
		btnDelete.setOnAction(event -> {
			Main.currentUser.removeEntry(openedJournal);
			goBack();
		});
		
		//Edit button makes the fields editable and displays a save button
		btnEdit.setOnAction(event -> {
			tfTitle.setEditable(true);
			tfDesc.setEditable(true);
			btnSave.setVisible(true);
		});
		
		//Saves all the changes and sets the fields uneditable
		btnSave.setOnAction(event ->{
			tfTitle.setEditable(false);
			tfDesc.setEditable(false);
			
			openedJournal.setTitle(tfTitle.getText().trim());
			openedJournal.setEntry(tfDesc.getText().trim());
			Main.currentUser.updateEntry(openedJournal, openIndex);
			
			btnSave.setVisible(false);
		});
		
	}
	
	//This method change the scene to homepage.
	void goBack() {
		Stage ps = (Stage) btnBack.getScene().getWindow();
		BorderPane home;
		
		try {
			home = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage.fxml"));
			Scene homeScene = new Scene(home);
			ps.setScene(homeScene);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}