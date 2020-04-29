package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIFarmReport {
	
	private Farms fms;
	private final DecimalFormat DF;

	public GUIFarmReport (Farms fms, DecimalFormat DF) {
		this.fms = fms;
		this.DF = DF;
	}
	
	/**
     * Generate the farm report module
     */
    public void generateFarmRepModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nFarm Report\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Set the directions on the top
    	String guide = "\nPlease enter the farm ID and the year in\nthe text field below "
    			+ "to view a farm report."
    			+ "\nThe generated file can be found in the current"
    			+ "\ndirectory.";
    	Label lb = new Label(guide);
    	
    	// Fields for user to type inputs
    	HBox hb1 = new HBox();
    	Label id = new Label("ID: ");
    	Label year = new Label("  Year: ");
    	TextField getId = new TextField();
    	getId.setPrefWidth(70.0);
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);
    	hb1.getChildren().addAll(id, getId, year, getYear);
    	
    	// Button for user to enter input
    	Button bt = new Button("Display farm report");
    	bt.setOnAction(e -> {
    		getFarmRep(getId.getText(), getYear.getText());
    		getId.clear();
    		getYear.clear();
    	});
    	Button bt2 = new Button("Generate farm report file");
    	bt2.setOnAction(e -> {
    		getFarmRepFile(getId.getText(), getYear.getText());
    		getId.clear();
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
    	
    	// Spacing on both sides
    	Label space1 = new Label("   ");
    	Label space2 = new Label("   ");
    	hb.getChildren().addAll(space1, vb, space2);
    	root.setCenter(hb);
    	
    	// Display
    	Scene saveDataScene = new Scene(root, 295, 275);
    	stage.setScene(saveDataScene);
    	stage.setTitle("Farm Report");
    	stage.show();
    }
    
    /**
     * Helper class for the TableView of farm report
     * 
     * @author Xinrui Liu
     *
     */
    public static class SingleMonthData {
		private SimpleIntegerProperty month;
		private SimpleIntegerProperty weight;
		private SimpleStringProperty percentage;
		
		private SingleMonthData(int month, int weight, String percentage) {
			this.month = new SimpleIntegerProperty(month);
			this.weight = new SimpleIntegerProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);
		}
		
		public int getMonth() {
			return month.get();
		}
		
		public int getWeight() {
			return weight.get();
		}
		
		public String getPercentage() {
			return percentage.get();
		}
	}
    
    /**
     * Show a farm report according to the input id and year
     * 
     * @param id the id of the farm report
     * @param year the year of the farm report
     */
    private void getFarmRep(String id, String year) {
    	// Declare needed local fields
    	String ID = "";
    	int Year = 0;
    	FarmReport frp = null;
    	Stage farmRep = new Stage();
    	
    	// Read the user input
    	try {
    		ID = id;
    		Year = Integer.parseInt(year);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, "The format of ID or year is wrong.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Get the farm report
    	try {
    		frp = fms.getFarmRep(ID, Year);
    	} catch (IllegalArgumentException e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage());
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	
    	
    	// Set up the farm report
    	VBox vb = new VBox();
    	ObservableList<SingleMonthData> data = FXCollections.observableArrayList();
    	for (int i=1; i<=12; i++) {
    		SingleMonthData e = null;
    		e = new SingleMonthData(i, frp.getWeight(i), DF.format(frp.getPercentage(i)*100)+"%");
    		data.add(e);
    	}
    	
    	TableView tb = new TableView();
    	TableColumn month = new TableColumn("Month");
    	month.setPrefWidth(70.0);
    	month.setCellValueFactory(new PropertyValueFactory<SingleMonthData, Integer>("month"));
    	
    	TableColumn weight = new TableColumn("Milk Weight");
    	weight.setPrefWidth(120.0);
    	weight.setCellValueFactory(new PropertyValueFactory<SingleMonthData, Integer>("weight"));
    	
    	TableColumn percentage = new TableColumn("Percentage");
    	percentage.setPrefWidth(120.0);
    	percentage.setCellValueFactory(new PropertyValueFactory<SingleMonthData, String>("percentage"));
    	
    	tb.setItems(data);
    	tb.getColumns().addAll(month, weight, percentage);
    	vb.getChildren().add(tb);
    	
    	
    	// Button to close that report
    	Button close = new Button("Close report");
    	close.setOnAction(e -> farmRep.close());
    	
    	// Set the scene and show the report
    	BorderPane rep = new BorderPane();
    	Label farmid = new Label(id + "  Year " + year);
    	rep.setTop(farmid);
    	rep.setCenter(vb);
    	rep.setBottom(close);
    	rep.setAlignment(farmid, Pos.TOP_CENTER);
    	rep.setAlignment(close, Pos.BOTTOM_CENTER);
    	Scene s = new Scene(rep,312,356);
    	farmRep.setTitle("Farm Report");
    	farmRep.setScene(s);
    	farmRep.show();
    }
    
    /**
     * Generate a txt file of a farm report
     * 
     * @param id the id of the farm in the report
     * @param year the year of the report
     */
    private void getFarmRepFile(String id, String year) {
    	// Declare needed local fields
    	String ID = "";
    	int Year = 0;
    	FarmReport frp = null;
    	
    	// Read the user input
    	try {
    		ID = id;
    		Year = Integer.parseInt(year);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, "The format of ID or year is wrong.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Get the farm report
    	try {
    		frp = fms.getFarmRep(ID, Year);
    	} catch (IllegalArgumentException e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage());
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Generate file
    	File file = new File("Farm Report " + id + " Year " + year + ".txt");
    	
    	try {
    		PrintWriter pw = new PrintWriter(file);
    		pw.println("Month,Weight,Percentage");
    		for (int i=1; i<=12; i++) {
    			pw.println(i + "," + frp.getWeight(i) + "," + DF.format(frp.getPercentage(i)));
    		}
    		pw.close();
    	} catch (IOException e) {
    		
    	}
    	
    	Alert alt = new Alert(AlertType.INFORMATION, "File successfully generated.");
		alt.showAndWait().filter(r -> r==ButtonType.OK);
		return;
    }
}
