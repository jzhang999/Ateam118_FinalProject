package application;

import java.io.PrintWriter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUISaveData {
	
	private PrintWriter datapw;
	
	public GUISaveData(PrintWriter datapw) {
		this.datapw = datapw;
	}
	
	/**
     * Generate the directions about saving data.
     */
    public void generateSaveDataModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage saveDataStage = new Stage();
    	
    	// Set page title
    	String title = "\nSave Data\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Set up guide for user
    	String guide = 
    			  "\nWhen you click the exit button at the main"
    			+ "\npage of this program. The program will"
    			+ "\nautomatically save all the data you have"
    			+ "\nuploaded this time in a csv file named"
    			+ "\n\"data.csv\"."
    			+ "\nYou can also save the data and exit by clicking"
    			+ "\nthe button below."
    			+ "\nDO NOT move or edit \"data.csv\" because"
    			+ "\notherwise you will not be able to retrieve your"
    			+ "\ndata next time."
    			+ "\nYou can copy the \"data.csv\" to another"
    			+ "\ndirectory to save or edit it.";
    	Label lb = new Label(guide);
    	vb.getChildren().add(lb);
    	
    	// Exit button to close the application
    	Button saveAndExit = new Button("Save data and exit the program");
    	saveAndExit.setOnAction(e -> {
    		datapw.close();
    		System.exit(0);
    	});
    	vb.getChildren().add(saveAndExit);
    	
    	// Button to close the window
    	Button exit = new Button("Back");
    	exit.setOnAction(e -> {
    		saveDataStage.close();
    	});
    	root.setBottom(exit);
    	root.setAlignment(exit, Pos.BOTTOM_CENTER);
    	
    	// Set space on both sides
    	Label space1 = new Label("   ");
    	Label space2 = new Label("   ");
    	hb.getChildren().addAll(space1, vb, space2);
    	root.setCenter(hb);
    	
    	// Display the window
    	Scene saveDataScene = new Scene(root, 295, 343);
    	saveDataStage.setScene(saveDataScene);
    	saveDataStage.setTitle("Save Data");
    	saveDataStage.show();
    }
}
