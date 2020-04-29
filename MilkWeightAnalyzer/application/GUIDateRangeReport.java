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

public class GUIDateRangeReport {
	
	/**
     * Generates the date range report module
     * 
     * @return a VBox representing the module that can generate date range report
     */
    public void generateDateRangeRepModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nDate Range Report\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// User directions
    	String guide = "\nPlease enter the range of date in the text\nfield below "
    			+ "to view a date range report.\n"
    			+ "If the report shows nothing, then you "
    			+ "haven't\nuploaded any file yet."
    			+ "\nIf the report shows 0's and NaN's for some"
    			+ "\nfarms, then the database does not contain"
    			+ "\nrecord for those farms in this year.";
    	Label lb = new Label(guide);
    	
    	// Input Fields
    	VBox vb2 = new VBox();
    	HBox hbyear = new HBox();
    	HBox hbstart = new HBox();
    	HBox hbend = new HBox();
    	Label year = new Label("Year: ");
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);
    	hbyear.getChildren().addAll(year, getYear);
    	Label startM = new Label("Start month: ");
    	TextField getStartM = new TextField();
    	getStartM.setPrefWidth(40.0);
    	Label startD = new Label("  Start day: ");
    	TextField getStartD = new TextField();
    	getStartD.setPrefWidth(40.0);
    	hbstart.getChildren().addAll(startM, getStartM, startD, getStartD);
    	Label endM = new Label("End month:   ");
    	TextField getEndM = new TextField();
    	getEndM.setPrefWidth(40.0);
    	Label endD = new Label("  End day:  ");
    	TextField getEndD = new TextField();
    	getEndD.setPrefWidth(40.0);
    	hbend.getChildren().addAll(endM, getEndM, endD, getEndD);
    	
    	// Button to enter inputs
    	Button bt = new Button("Display date range report");
    	bt.setOnAction(e -> {
    		Main.getTimeRep("Date Range", getYear.getText(), getStartM.getText(), 
    				getStartD.getText(), getEndM.getText(), getEndD.getText());
    		getYear.clear();
    		getStartM.clear();
    		getStartD.clear();
    		getEndM.clear();
    		getEndD.clear();
    	});
    	
    	Button bt2 = new Button("Generate date range report file");
    	bt2.setOnAction(e -> {
    		Main.getTimeRepFile("Date Range", getYear.getText(), getStartM.getText(), 
					getStartD.getText(), getEndM.getText(), getEndD.getText());
    		getYear.clear();
    		getStartM.clear();
    		getStartD.clear();
    		getEndM.clear();
    		getEndD.clear();
    	});
    	
    	vb2.getChildren().addAll(hbyear, hbstart, hbend);
    	vb.getChildren().addAll(lb, vb2, bt, bt2);
    	
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
    	Scene saveDataScene = new Scene(root, 287, 370);
    	stage.setScene(saveDataScene);
    	stage.setTitle("Date Range Report");
    	stage.show();
    }
}
