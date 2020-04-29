package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIReadFile {
	
	private Farms fms;
	private PrintWriter datapw;
	private File selectedFile;
	
	public GUIReadFile(Farms fms, PrintWriter datapw, File selectedFile) {
		this.fms = fms;
		this.datapw = datapw;
		this.selectedFile = selectedFile;
	}
	
	/**
     * Generate the read file module of the GUI
     */
    public void generateReadFileModule() {
    	// Declare needed field.
    	Stage readFileStage = new Stage();
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	
    	// Set page title
    	String title = "\nUpload File\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Add guides and text fields
    	// Upload by typing file name
    	String guide = "\nPlease enter the name of the file that you\nwant to upload in"
    			+ " the text box below:";
    	Label lb = new Label(guide);
    	String guide2 = "Note: The file MUST be in the current directory."
    			+ "\n          DO NOT upload \"data.csv\" here.";
    	Label lb2 = new Label(guide2);
    	TextField tf = new TextField();
    	Button bt = new Button("Upload file");
    	bt.setOnAction(e -> {
    		readFile(tf.getText());
    		tf.clear();
    	});
    	vb.getChildren().addAll(lb, tf, bt, lb2);
    	
    	// Upload by select file
    	String guide3 = "\nIf you want to select a file from your computer, "
    			+ "\nplease click the button below.";
    	Label lb3 = new Label(guide3);
    	String guide4 = "Note: The file MUST be a csv file."
    			+ "\n          DO NOT upload \"data.csv\" here.";
    	Label lb4 = new Label(guide4);
    	vb.getChildren().add(lb3);
    	
    	FileChooser fileChooser = new FileChooser();
    	Button chooseFile = new Button("Choose a file from the computer");
    	chooseFile.setOnAction(e -> {
    		selectedFile = fileChooser.showOpenDialog(readFileStage);
    		try {
    			readFile(selectedFile.getAbsolutePath());
    		} catch (Exception e1) {
    			Alert alt = new Alert(AlertType.WARNING, "An error occured while choosing a file.");
        		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		}
    	});
    	vb.getChildren().add(chooseFile);
    	vb.getChildren().add(lb4);
    	
    	// Upload history data
    	String guide5 = "\nIf you want to upload \"data.csv\", the file that"
    			+ "\ncontains the data you have uploaded last time,"
    			+ "\nclick the button below.";
    	Label lb5 = new Label(guide5);
    	vb.getChildren().add(lb5);
    	
    	Button uploadDataCsv = new Button("Upload \"data.csv\"");
    	uploadDataCsv.setOnAction(e -> {
    		readFile("data.csv");
    	});
    	vb.getChildren().add(uploadDataCsv);
    	
    	String guide6 = "Note: The file \"data.csv\" must be in the current "
    			+ "\ndirectory."
    			+ "\n          If you do not upload \"data.csv\" this time, "
    			+ "\nyou will lose all the data in it.";
    	Label lb6 = new Label(guide6);
    	vb.getChildren().add(lb6);
    	
    	Label space1 = new Label("   ");
    	Label space2 = new Label("   ");
    	hb.getChildren().addAll(space1, vb, space2);
    	
    	root.setCenter(hb);
    	
    	// Add exit button
    	Button exit = new Button("Back");
    	exit.setOnAction(e -> {
    		readFileStage.close();
    	});
    	root.setBottom(exit);
    	root.setAlignment(exit, Pos.BOTTOM_CENTER);
    	
    	// Display window
    	Scene readFileScene = new Scene(root, 295, 550);
    	readFileStage.setScene(readFileScene);
    	readFileStage.setTitle("Upload File");
    	readFileStage.show();
    }
    
    /**
     * Read the file according to the fileName that user types
     * 
     * @param fileName the name of the file
     */
    private void readFile(String fileName) {
    	try {
    		if (!fileName.equals("data.csv")) {
    			fms.readCsvFile(fileName);
    			BufferedReader br = new BufferedReader(new FileReader(fileName));
    			String newLine = br.readLine();
    			newLine = br.readLine();
    			while (newLine != null) {
    				datapw.println(newLine);
    				newLine = br.readLine();
    			}
    		} else {
    			readFileNoAlert("dataTemp.csv");
    		}
    		Alert alt = new Alert(AlertType.INFORMATION, "Upload Succeeded!");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage() 
    				+ " File contain error or not in the MilkWeightAnalyzer directory.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    	}
    }
    
    /**
     * Helper method to read "data.csv"
     * 
     * @param fileName the name of the file that needs to be read
     */
    private void readFileNoAlert(String fileName) {
    	try {
    		fms.readCsvFile(fileName);
    		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String newLine = br.readLine();
    		newLine = br.readLine();
    		while (newLine != null) {
    			datapw.println(newLine);
    			newLine = br.readLine();
    		}
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage() 
    				+ " File contain error or not in the MilkWeightAnalyzer directory.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    	}
    }
}
