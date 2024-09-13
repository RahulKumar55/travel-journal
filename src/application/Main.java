/**********************************************
Final Project
Course: APS 545 - Winter
Last Name: Kumar
First Name: Rahul
ID: 157197211
Section: NBB
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 04/09/2024
**********************************************/

package application;
	
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static ArrayList<User> users = new ArrayList<>(); // List of all users;
	public static User currentUser = null;	//Logged in User;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Write the list of user objects on window closing;
			primaryStage.setOnCloseRequest(event -> {
				try {
					ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("data.txt"));
					for(User u: users) {
						out.writeObject(u);					
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//Read the user objects and store it in a list at the start of application;
		try {
			FileInputStream istream = new FileInputStream("data.txt");
			ObjectInputStream in = new ObjectInputStream(istream);
			while(istream.available() > 0) {
				User u;
				u = (User) in.readObject();
				users.add(u);
			}
			in.close();
			istream.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		};
		launch(args);
	}
}
