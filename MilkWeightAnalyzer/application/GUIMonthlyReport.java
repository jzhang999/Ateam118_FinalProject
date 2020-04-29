package application;

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

public class GUIMonthlyReport {

	/**
     * Generates the monthly report module
     * 
     * @return a VBox representing the module that can generate monthly report
     */
    public void generateMonthlyRepModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nMonthly Report\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Directions for users
    	String guide = "\nPlease enter the year and the month in the\ntext field below "
    			+ "to view a monthly report.\n"
    			+ "If the report shows nothing, then you "
    			+ "haven't\nuploaded any file yet."
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
    	Label month = new Label("  Month: ");
    	TextField getMonth = new TextField();
    	getMonth.setPrefWidth(70.0);
    	hb1.getChildren().addAll(year, getYear, month, getMonth);
    	
    	// Button to enter inputs
    	Button bt = new Button("Display monthly report");
    	bt.setOnAction(e -> {
    		String yeaR = getYear.getText();
    		String montH = getMonth.getText();
    		try {
    			Main.getTimeRep("Monthly", yeaR, montH, "1", montH, 
    					Integer.toString(daysInMonth(Integer.parseInt(yeaR), 
    							Integer.parseInt(montH))));
    		} catch (IllegalStateException ex) {
    			Alert alt = new Alert(AlertType.WARNING, "Invalid month.");
        		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		} catch (Exception ex) {
    			Alert alt = new Alert(AlertType.WARNING, "The format of date is wrong.");
        		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		}
    		getYear.clear();
    		getMonth.clear();
    	});
    	Button bt2 = new Button("Generate monthly report file");
    	bt2.setOnAction(e -> {String yeaR = getYear.getText();
    		String montH = getMonth.getText();
    		try {
    			Main.getTimeRepFile("Monthly", yeaR, montH, "1", montH, 
    					Integer.toString(daysInMonth(Integer.parseInt(yeaR), 
    							Integer.parseInt(montH))));
    		} catch (IllegalStateException ex) {
    			Alert alt = new Alert(AlertType.WARNING, "Invalid month.");
        		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		} catch (Exception ex) {
    			Alert alt = new Alert(AlertType.WARNING, "The format of date is wrong.");
        		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		}
    		getYear.clear();
    		getMonth.clear();
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
    	Scene saveDataScene = new Scene(root, 295, 355);
    	stage.setScene(saveDataScene);
    	stage.setTitle("Monthly Report");
    	stage.show();
    }
    
    /**
	 * Helper method to decide the number of days in a month
	 * 
	 * @param year the year 
	 * @param month the month
	 * @return the number of days in a month 
	 * @throws IllegalStateException if the month is invalid
	 */
	private int daysInMonth(int year, int month) {
		if (month < 1 || month > 12) {
			throw new IllegalStateException();
		}
		switch (month) {
			case 2:
				if (isLeap(year)) {
					return 29;
				} else {
					return 28;
				}
			case 4:
				return 30;
			case 6:
				return 30;
			case 9:
				return 30;
			case 11:
				return 30;
			default:
				return 31;
		}
	}
	
	/**
	 * Helper method to decide whether a year is a leap year or not.
	 * 
	 * @param year the number of the year
	 * @return true if this year is leap year, false otherwise
	 */
	private boolean isLeap(int year) {
		if (year%4 == 0) {
			if (year%100 == 0) {
				if (year%400 == 0) {
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
}
