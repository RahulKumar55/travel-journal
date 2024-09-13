package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	@FXML TextField tfUsername;
	@FXML PasswordField pfPassword;
	@FXML Button btnRegister;
	@FXML Button btnLogin;
	
	Map<String, String> usersDetails = new HashMap<>(); //This map is used for login purposes
	
	@Override
	   public void initialize(URL location, ResourceBundle resources) {
		
		//Fill the userdetails map
		for(User u: Main.users) {
			usersDetails.put(u.getUsername(), u.getPassword());
		}
		
		//Register button, shows errors or stores the new user in Main.users list
		btnRegister.setOnAction(event -> {
			if(tfUsername.getText().trim().isEmpty() || pfPassword.getText().trim().isEmpty()) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Field Missing");
				alert.setContentText("Both Password and Username are required");
				alert.showAndWait();
				
			}else if(usersDetails.containsKey(tfUsername.getText().trim())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("User exists");
				alert.setContentText("Username is already being used");
				alert.showAndWait();
			}else {
				User u = new User();
				u.setUsername(tfUsername.getText().trim());
				u.setPassword(pfPassword.getText().trim());
				Main.users.add(u);
				usersDetails.put(tfUsername.getText().trim(), pfPassword.getText().trim());
			}
			tfUsername.setText("");
			pfPassword.setText("");
		});
		
		//Login button, shows errors or changes the scene if a valid used tries to login.
		//At login sets the Main.currentUser as Logged in user.
		btnLogin.setOnAction((event) -> {
			if (!usersDetails.containsKey(tfUsername.getText().trim())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("User not found");
				alert.setContentText("Username doesn't exist");
				alert.showAndWait();
			}else if(!pfPassword.getText().trim().equals(usersDetails.get(tfUsername.getText().trim()))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Wrong Password");
				alert.setContentText("Password is incorrect");
				alert.showAndWait();
			}else {
				for(User u: Main.users) {
					if(tfUsername.getText().trim().equals(u.getUsername()) && pfPassword.getText().trim().equals(u.getPassword())) {
						Main.currentUser = u;
						continue;
					}
				}
				Stage primaryStage = (Stage) tfUsername.getScene().getWindow();
				BorderPane home;
				try {
					home = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage.fxml"));
					Scene homeScene = new Scene(home);
					primaryStage.setScene(homeScene);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			tfUsername.setText("");
			pfPassword.setText("");
		});

		
	}
	
}
