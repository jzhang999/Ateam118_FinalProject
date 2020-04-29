package application;

import java.io.PrintWriter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIChangeData {
	
	private Farms fms;
	private PrintWriter datapw;
	
	public GUIChangeData(Farms fms, PrintWriter datapw) {
		this.fms = fms;
		this.datapw = datapw;
	}
	
	/**
     * Generate the module that allows user to change the data of a 
     * single farm at a single day.
     */
    public void generateChangeDataModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nEdit or Add Data\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Set up the directions
    	String guide = 
    			"\nPlease enter the information below to change\n"
    			+ "the data of a given farm at a given date.\n"
    			+ "If the farm or the date is new, that data will\n"
    			+ "be added to the database.\n"
    			+ "If both the farm and the date exist, the data on\n"
    			+ "that day will be changed according to inputs.";
    	Label lb = new Label(guide);
    	vb.getChildren().add(lb);
    	
    	// Set up the input fields
    	HBox date = new HBox();
    	Label year = new Label("Year: ");
    	Label month = new Label(" Month: ");
    	Label day = new Label(" Day: ");
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);
    	TextField getMonth = new TextField();
    	getMonth.setPrefWidth(40.0);
    	TextField getDay = new TextField();
    	getDay.setPrefWidth(40.0);
    	date.getChildren().addAll(year, getYear, month, getMonth, day, getDay);
    	vb.getChildren().add(date);
    	
    	HBox id = new HBox();
    	Label ID = new Label("Farm ID: ");
    	TextField getID = new TextField();
    	getID.setPrefWidth(72.0);
    	Label weight = new Label(" Milk Weight: ");
    	TextField getWeight = new TextField();
    	getWeight.setPrefWidth(60.0);
    	id.getChildren().addAll(ID, getID, weight, getWeight);
    	vb.getChildren().add(id);
    	
    	// Set up the button
    	Button bt = new Button("Edit or add data");
    	bt.setOnAction(e -> {
    		try {
				fms.changeData(getYear.getText(), getMonth.getText(),
						getDay.getText(), getID.getText(), getWeight.getText());
				datapw.println(getYear.getText() + "-" + getMonth.getText() + "-" +
						getDay.getText() + "," + getID.getText() + "," + getWeight.getText());
				Alert alt = new Alert(AlertType.INFORMATION, "Successfully changed or added data.");
	    		alt.showAndWait().filter(r -> r==ButtonType.OK);
			} catch (Exception e1) {
				Alert alt = new Alert(AlertType.WARNING, "Invalid input. Check the format or value"
						+ " for each text field. Value might be too large.");
	    		alt.showAndWait().filter(r -> r==ButtonType.OK);
			}
    		getYear.clear();
    		getMonth.clear();
    		getDay.clear();
    		getID.clear();
    		getWeight.clear();
    	});
    	vb.getChildren().add(bt);
    	
    	// Set up the button to close the window
    	Button exit = new Button("Back");
    	exit.setOnAction(e -> {
    		stage.close();
    	});
    	root.setBottom(exit);
    	root.setAlignment(exit, Pos.BOTTOM_CENTER);
    	
    	// Set up spacing on both sides
    	Label space1 = new Label("   ");
    	Label space2 = new Label("   ");
    	hb.getChildren().addAll(space1, vb, space2);
    	root.setCenter(hb);
    	
    	// Display the window
    	Scene saveDataScene = new Scene(root, 295, 300);
    	stage.setScene(saveDataScene);
    	stage.setTitle("Edit or add Data");
    	stage.show();
    }
}
