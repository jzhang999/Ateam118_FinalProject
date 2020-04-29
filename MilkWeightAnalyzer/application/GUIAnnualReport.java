package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIAnnualReport {
	
	/**
     * Generates the annual report module
     * 
     * @return a VBox representing the module that can generate annual report
     */
    public void generateAnnualRepModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nAnnual Report\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Directions for users
    	String guide = "\nPlease enter the year in the text field below\n"
    			+ "to view an annual report.\n"
    			+ "If the report shows nothing, then you"
    			+ " haven't\nuploaded any file yet."
    			+ "\nThe generated file can be found in the current"
    			+ "\ndirectory."
    			+ "\nIf the report shows 0's and NaN's for some"
    			+ "\nfarms, then the database does not contain"
    			+ "\nrecord for those farms in this year.";
    	Label lb = new Label(guide);
    	
    	// Input fields
    	HBox hb1 = new HBox();
    	Label year = new Label("Year: ");
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);    	
    	hb1.getChildren().addAll(year, getYear);
    	
    	// Button to enter inputs
    	Button bt = new Button("Display annual report");
    	bt.setOnAction(e -> {
    		Main.getTimeRep("Annual", getYear.getText(), "1", "1", "12", "31");
    		getYear.clear();
    	});
    	Button bt2 = new Button("Generate annual report file");
    	bt2.setOnAction(e -> {
    		Main.getTimeRepFile("Annual", getYear.getText(), "1", "1", "12", "31");
    		getYear.clear();
    	});
    	
    	vb.getChildren().addAll(lb, hb1, bt, bt2);
    	
    	// Close window button
    	Button exit = new Button("Back");
    	exit.setOnAction(e -> {
    		stage.close();
    	});
    	root.setBottom(exit);
    	root.setAlignment(exit, Pos.BOTTOM_CENTER);
    	
    	// Spacing on left and right
    	Label space1 = new Label("   ");
    	Label space2 = new Label("   ");
    	hb.getChildren().addAll(space1, vb, space2);
    	root.setCenter(hb);
    	
    	// Display the window
    	Scene s = new Scene(root, 295, 365);
    	stage.setScene(s);
    	stage.setTitle("Annual Report");
    	stage.show();
    }
}
