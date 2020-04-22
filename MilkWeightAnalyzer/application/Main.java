package application;

import java.text.DecimalFormat;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

/**
 * GUI for the Milk Weight Analyzer
 * 
 * @author Xinrui Liu
 *
 */
public class Main extends Application {

	private static final int WINDOW_WIDTH = 810; // width of main page
    private static final int WINDOW_HEIGHT = 625; // height of main page
    private static final String APP_TITLE = "Welcome to Milk Weight Analyzer!"; // title of app
    private static final DecimalFormat DF = new DecimalFormat("0.00000");// formatter for the percentage
    private static Farms fms;// The back-end that stores all the information
    private static File data;// File to save the data 
    private static File dataTemp;// File to temporarily store data
    private static File selectedFile;// File to record the selected file while uploading files
    private static Scanner dataScn;// Scanner to temporarily store history data
    private static PrintWriter datapw;// PrintWriter to write data to the file that stores the data
    
    /**
     * Start method.
     * 
     * @param primaryStage the main stage of the GUI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	// Initialize fields
    	fms = new Farms();
    	data = new File("data.csv");
    	if (data.exists()) {
    		dataScn = new Scanner(data);
    		dataTemp = new File("dataTemp.csv");
    		PrintWriter dtpw = null;
    		try {
    			dtpw = new PrintWriter("dataTemp.csv");
    		} catch (Exception e) {
    			
    		}
    		while (dataScn.hasNextLine()) {
    			dtpw.println(dataScn.nextLine());
    		}
    		dtpw.close();
    		datapw = new PrintWriter(new FileWriter(data, false));
    		datapw.println("date,farm_id,weight");
    	} else {
    		datapw = new PrintWriter(new FileWriter(data, false));
    		datapw.println("date,farm_id,weight");
    	}
    	selectedFile = null;
    	

    	BorderPane root = new BorderPane(); // Main layout of Border Pane
        
        // set top, label of the company view
        ImageView imageView1 = setImage("topOfMainPage.png");
        imageView1.setFitWidth(810);
        imageView1.setPreserveRatio(true);
        root.setTop(imageView1);
        BorderPane.setAlignment(imageView1, Pos.TOP_CENTER);
        
        // set right image view
        ImageView imageView2 = setImage("cheese1.png");
        imageView2.setFitHeight(400);
        imageView2.setPreserveRatio(true);
        root.setRight(imageView2);
        
        // set left image view
        ImageView imageView3 = setImage("cheese2.png");
        imageView3.setFitHeight(80);
        imageView3.setFitWidth(80);
        imageView3.setPreserveRatio(true);
        root.setLeft(imageView3);
        BorderPane.setAlignment(imageView3, Pos.BOTTOM_CENTER);
        
        // set center, user choices to redirect
        HBox layout = new HBox();
        VBox layoutLeft = new VBox();
        VBox layoutRight = new VBox();
        VBox layoutCenter = new VBox();
        Label space = new Label("                 ");
        layoutCenter.getChildren().add(space);
        
        Label space2 = new Label("           ");
        Label space3 = new Label("           ");
        Label space4 = new Label("           ");
        Label space5 = new Label("           ");
        Label lb1 = new Label("Process Data");
        setTopicStyle(lb1);
        Label lb2 = new Label(" Get Reports");
        setTopicStyle(lb2);
        
        Button readFile = new Button("Upload file");
        setButtonStyle(readFile);
        readFile.setOnAction(e -> {
        	generateReadFileModule();
        });
        
        Button saveData = new Button("Save data");
        setButtonStyle(saveData);
        saveData.setOnAction(e -> {
        	generateSaveDataModule();
        });
        
        Button changeData = new Button("Edit or add data");
        setButtonStyle(changeData);
        changeData.setOnAction(e -> {
        	generateChangeDataModule();
        });
        
        Button removeData = new Button("Remove data");
        setButtonStyle(removeData);
        removeData.setOnAction(e -> {
        	generateRemoveDataModule();
        });
        
        Button farmRep = new Button("Farm report");
        setButtonStyle(farmRep);
        farmRep.setOnAction(e -> {
        	generateFarmRepModule();
        });
        
        Button annualRep = new Button("Annual report");
        setButtonStyle(annualRep);
        annualRep.setOnAction(e -> {
        	generateAnnualRepModule();
        });
        
        Button monthlyRep = new Button("Monthly report");
        setButtonStyle(monthlyRep);
        monthlyRep.setOnAction(e -> {
        	generateMonthlyRepModule();
        });
        
        Button dateRangeRep = new Button("Date range report");
        setButtonStyle(dateRangeRep);
        dateRangeRep.setOnAction(e -> {
        	generateDateRangeRepModule();
        });
        
        layoutLeft.getChildren().addAll(space4, lb1, readFile, saveData, changeData, removeData);
        layoutRight.getChildren().addAll(space5, lb2, farmRep, annualRep, monthlyRep, dateRangeRep);
        layoutLeft.setSpacing(5.0);
        layoutRight.setSpacing(5.0);
        layout.getChildren().addAll(space2, layoutLeft, layoutCenter, layoutRight, space3);
        root.setCenter(layout);
        
        // Bottom exit button
        Button exit = new Button("Exit");
        exit.setOnAction(e -> {
        	datapw.close();
        	System.exit(0);
        });
        root.setBottom(exit);
        BorderPane.setAlignment(exit, Pos.BOTTOM_CENTER);
        
        // Display the window
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    /**
     * Helper method to format main menu buttons.
     * 
     * @param btn the button that need to be formatted
     */
    private static void setButtonStyle(Button btn) {
    	btn.setPrefSize(265.0, 80.0);
        btn.setStyle("-fx-font-family:'Georgia';"
        		+ "-fx-font-size:25;"
        		+ "-fx-font-weight: bold");
    }
    
    /**
     * Helper method to format topic
     * 
     * @param lb the label that need to be formated
     */
    private static void setTopicStyle(Label lb) {
    	lb.setStyle("-fx-font-family:'Georgia';"
        		+ "-fx-font-size:40;"
        		+ "-fx-font-weight: bold");
    }
    
    /**
     * Read the image for GUI.
     * 
     * @param fileName the name of the image file
     * @return an ImageView of that image
     * @throws FileNotFoundException if any exception happens while reading the file
     */
    private static ImageView setImage(String fileName) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(fileName);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    
    /**
     * Generate the read file module of the GUI
     * 
     * @return a VBox that represents the read file module
     */
    private static void generateReadFileModule() {
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
    	Button chooseFile = new Button("Choose file from the computer");
    	chooseFile.setOnAction(e -> {
    		selectedFile = fileChooser.showOpenDialog(readFileStage);
    		try {
    			readFile(selectedFile.getAbsolutePath());
    		} catch (Exception e1) {
    			Alert alt = new Alert(AlertType.WARNING, "An error occured while choosing file.");
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
    private static void readFile(String fileName) {
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
    private static void readFileNoAlert(String fileName) {
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
    
    /**
     * Generate the directions about saving data.
     */
    private static void generateSaveDataModule() {
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
    
    /**
     * Generate the module that allows user to change the data of a 
     * single farm at a single day.
     */
    private static void generateChangeDataModule() {
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
						+ " for each text field");
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
    
    /**
     * Generate the module that allows user to remove the data of a 
     * single farm at a single day.
     */
    private static void generateRemoveDataModule() {
    	// Declare needed fields
    	VBox vb = new VBox();
    	vb.setSpacing(5.0);
    	HBox hb = new HBox();
    	BorderPane root = new BorderPane();
    	Stage stage = new Stage();
    	
    	// Set page title
    	String title = "\nRemove Data\n";
    	Label titlelb = new Label(title);
    	titlelb.setStyle("-fx-font-size: 15");
    	root.setTop(titlelb);
    	root.setAlignment(titlelb, Pos.TOP_CENTER);
    	
    	// Set up the directions
    	String guide = 
    			"\nPlease enter the information below to remove\n"
    			+ "the data of a given farm at a given date.\n"
    			+ "If the farm or the date is new, the system will\n"
    			+ "save the data \n"
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
    	id.getChildren().addAll(ID, getID);
    	vb.getChildren().add(id);
    	
    	// Set up the button
    	Button bt = new Button("Remove data");
    	bt.setOnAction(e -> {
    		try {
    			if (fms.contains(getID.getText()) && 
    					fms.getFarm(getID.getText()).getYearData().contains(Integer.parseInt(getYear.getText()))) {
    				fms.changeData(getYear.getText(), getMonth.getText(),
    						getDay.getText(), getID.getText(), "0");
    				datapw.println(getYear.getText() + "-" + getMonth.getText() + "-" +
    						getDay.getText() + "," + getID.getText() + "," + "0");
    				Alert alt = new Alert(AlertType.INFORMATION, "Successfully removed data.");
    				alt.showAndWait().filter(r -> r==ButtonType.OK);
    			} else {
    				Alert alt = new Alert(AlertType.WARNING, "The data you want to remove does not exist.");
    				alt.showAndWait().filter(r -> r==ButtonType.OK);
    			}
			} catch (Exception e1) {
				Alert alt = new Alert(AlertType.WARNING, "Invalid input. Check the format or value"
						+ " for each text field");
	    		alt.showAndWait().filter(r -> r==ButtonType.OK);
			}
    		getYear.clear();
    		getMonth.clear();
    		getDay.clear();
    		getID.clear();
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
    	Scene saveDataScene = new Scene(root, 295, 310);
    	stage.setScene(saveDataScene);
    	stage.setTitle("Remove Data");
    	stage.show();
    }
    
    /**
     * Generate the farm report module
     * 
     * @return a VBox representing the module that can generate farm reports.
     */
    private static void generateFarmRepModule() {
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
    private static void getFarmRep(String id, String year) {
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
    private static void getFarmRepFile(String id, String year) {
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
    	File file = new File(id + "  Year " + year + ".txt");
    	
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
    
    /**
     * Generates the annual report module
     * 
     * @return a VBox representing the module that can generate annual report
     */
    private static void generateAnnualRepModule() {
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
    		getTimeRep("Annual", getYear.getText(), "1", "1", "12", "31");
    		getYear.clear();
    	});
    	Button bt2 = new Button("Generate annual report file");
    	bt2.setOnAction(e -> {
    		getTimeRepFile("Annual", getYear.getText(), "1", "1", "12", "31");
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
    
    /**
     * Generates the monthly report module
     * 
     * @return a VBox representing the module that can generate monthly report
     */
    private static void generateMonthlyRepModule() {
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
    			getTimeRep("Monthly", yeaR, montH, "1", montH, 
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
    			getTimeRepFile("Monthly", yeaR, montH, "1", montH, 
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
	private static int daysInMonth(int year, int month) {
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
	private static boolean isLeap(int year) {
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
    
	 /**
     * Generates the date range report module
     * 
     * @return a VBox representing the module that can generate date range report
     */
    private static void generateDateRangeRepModule() {
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
    		getTimeRep("Date Range", getYear.getText(), getStartM.getText(), 
    				getStartD.getText(), getEndM.getText(), getEndD.getText());
    		getYear.clear();
    		getStartM.clear();
    		getStartD.clear();
    		getEndM.clear();
    		getEndD.clear();
    	});
    	Button bt2 = new Button("Generate date range report file");
    	bt2.setOnAction(e -> {
    		getTimeRepFile("Date Range", getYear.getText(), getStartM.getText(), 
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
    
    /**
     * Helper class for TableView to generate reports based on time range.
     * 
     * @author Xinrui Liu
     *
     */
    public static class SingleFarmData {
		private SimpleStringProperty id;
		private SimpleIntegerProperty weight;
		private SimpleStringProperty percentage;
		
		private SingleFarmData(String id, int weight, String percentage) {
			this.id = new SimpleStringProperty(id);
			this.weight = new SimpleIntegerProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);
		}
		
		public String getId() {
			return id.get();
		}
		
		public int getWeight() {
			return weight.get();
		}
		
		public String getPercentage() {
			return percentage.get();
		}
	}
    
    /**
     * Show a time report according to the input data
     * 
     * @param type the type of this report, can be annual, monthly, or date range
     * @param year the year of the report
     * @param startM the start month of the report
     * @param startD the start day of the report
     * @param endM the end month of the report
     * @param endD the end day of the report
     */
    private static void getTimeRep(String type, String year, String startM, String startD, String endM, String endD) {
    	// Declare needed local fields
    	int Year = 0;
    	int StartM = 0;
    	int StartD = 0;
    	int EndM = 0;
    	int EndD = 0;
    	Stage timeRep = new Stage();
    	BorderPane rep = new BorderPane();
    	TimeReport trp = null;
    	
    	// Read user inputs
    	try {
    		Year = Integer.parseInt(year);
    		StartM = Integer.parseInt(startM);
    		StartD = Integer.parseInt(startD);
    		EndM = Integer.parseInt(endM);
    		EndD = Integer.parseInt(endD);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, "The format of date is wrong.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Get the time report
    	try {
    		trp = fms.getDateRangeRep(Year, StartM, StartD, EndM, EndD);
    	} catch (IllegalArgumentException e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage());
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Set up the farm report
    	VBox vb = new VBox();
    	ObservableList<SingleFarmData> data = FXCollections.observableArrayList();
    	for (int i=0; i<trp.getId().length; i++) {
    		SingleFarmData e = new SingleFarmData(trp.getId()[i], trp.getWeight()[i], DF.format(trp.getPercentage()[i]*100)+"%");
    		data.add(e);
    	}
    	
    	TableView tb = new TableView();
    	TableColumn farmID = new TableColumn("Farm ID");
    	farmID.setPrefWidth(90.0);
    	farmID.setCellValueFactory(new PropertyValueFactory<SingleFarmData, String>("id"));
    	
    	TableColumn weight = new TableColumn("Milk Weight");
    	weight.setPrefWidth(120.0);
    	weight.setCellValueFactory(new PropertyValueFactory<SingleFarmData, Integer>("weight"));
    	
    	TableColumn percentage = new TableColumn("Percentage");
    	percentage.setPrefWidth(120.0);
    	percentage.setCellValueFactory(new PropertyValueFactory<SingleFarmData, String>("percentage"));
    	
    	tb.setItems(data);
    	tb.getColumns().addAll(farmID, weight, percentage);
    	vb.getChildren().add(tb);
    	
    	// Button to close the report
    	Button close = new Button("Close report");
    	close.setOnAction(e -> timeRep.close());
    	rep.setBottom(close);
    	rep.setAlignment(close, Pos.BOTTOM_CENTER);
    	
    	// Set the scene and show the report
    	Label lbb = new Label(type + " Report  "+ year + "/"
    			+ startM + "/" + startD + " to " + year + "/"
    			+ endM +"/" + endD);
    	rep.setTop(lbb);
    	rep.setCenter(vb);
    	rep.setBottom(close);
    	rep.setAlignment(lbb, Pos.TOP_CENTER);
    	rep.setAlignment(close, Pos.BOTTOM_CENTER);
    	Scene s = new Scene(rep,345,356);
    	timeRep.setTitle(type + " Report");
    	timeRep.setScene(s);
    	timeRep.show();
    }
    
    /**
     * Generate a time range report txt file.
     * 
     * @param type the type of this report, can be annual, monthly, or date range
     * @param year the year of the report
     * @param startM the start month of the report
     * @param startD the start day of the report
     * @param endM the end month of the report
     * @param endD the end day of the report
     */
    private static void getTimeRepFile(String type, String year, String startM, String startD, String endM, String endD) {
    	// Declare needed local fields
    	int Year = 0;
    	int StartM = 0;
    	int StartD = 0;
    	int EndM = 0;
    	int EndD = 0;
    	TimeReport trp = null;
    	
    	// Read user inputs
    	try {
    		Year = Integer.parseInt(year);
    		StartM = Integer.parseInt(startM);
    		StartD = Integer.parseInt(startD);
    		EndM = Integer.parseInt(endM);
    		EndD = Integer.parseInt(endD);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, "The format of date is wrong.");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Get the time report
    	try {
    		trp = fms.getDateRangeRep(Year, StartM, StartD, EndM, EndD);
    	} catch (IllegalArgumentException e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage());
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    		return;
    	}
    	
    	// Generate file
    	File file = new File(type + " Report  " + startM + "."
    			+ startD + "." + year + " to " + endM + "."
    			+ endD + "." + year + ".txt");
    	
    	PrintWriter pw = null;
    	
    	try {
    		pw = new PrintWriter(file);
    		pw.println("If the report shows 0's and NaN's for some\nfarms, "
    			+ "then the database does not contain\nrecord for "
    			+ "that farm in this year.");
    		pw.println("Farm ID,Weight,Percentage");
    		for (int i=0; i<trp.getId().length; i++) {
    			pw.println(trp.getId()[i]+","+trp.getWeight()[i]+","+DF.format(trp.getPercentage()[i]));
    		}
    		pw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	Alert alt = new Alert(AlertType.INFORMATION, "File successfully generated.");
		alt.showAndWait().filter(r -> r==ButtonType.OK);
		return;
    }

    /**
     * Main method to launch the GUI.
     * 
     * @param args input arguments
     */
    public static void main(String[] args) {
           launch(args);
    }
}