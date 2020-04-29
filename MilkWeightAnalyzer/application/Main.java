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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;

/**
 * GUI for the Milk Weight Analyzer
 * 
 * @author Wenfei Huang, Hanjiang Wu, Jing Zhang, Yucheng Lin, Xinrui Liu
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
        
        // generate needed modules
    	GUIReadFile readFileModule = new GUIReadFile(fms, datapw, selectedFile);
    	GUISaveData saveDataModule = new GUISaveData(datapw);
    	GUIChangeData changeDataModule = new GUIChangeData(fms, datapw);
    	GUIRemoveData removeDataModule = new GUIRemoveData(fms, datapw);
    	
    	GUIFarmReport farmReportModule = new GUIFarmReport(fms, DF);
    	GUIAnnualReport annualReportModule = new GUIAnnualReport();
    	GUIMonthlyReport monthlyReportModule = new GUIMonthlyReport();
    	GUIDateRangeReport dateRangeReportModule = new GUIDateRangeReport();
        
        Button readFile = new Button("Upload file");
        setButtonStyle(readFile);
        readFile.setOnAction(e -> {
        	readFileModule.generateReadFileModule();
        });
        
        Button saveData = new Button("Save data");
        setButtonStyle(saveData);
        saveData.setOnAction(e -> {
        	saveDataModule.generateSaveDataModule();
        });
        
        Button changeData = new Button("Edit or add data");
        setButtonStyle(changeData);
        changeData.setOnAction(e -> {
        	changeDataModule.generateChangeDataModule();
        });
        
        Button removeData = new Button("Remove data");
        setButtonStyle(removeData);
        removeData.setOnAction(e -> {
        	removeDataModule.generateRemoveDataModule();
        });
        
        Button farmRep = new Button("Farm report");
        setButtonStyle(farmRep);
        farmRep.setOnAction(e -> {
        	farmReportModule.generateFarmRepModule();
        });
        
        Button annualRep = new Button("Annual report");
        setButtonStyle(annualRep);
        annualRep.setOnAction(e -> {
        	annualReportModule.generateAnnualRepModule();
        });
        
        Button monthlyRep = new Button("Monthly report");
        setButtonStyle(monthlyRep);
        monthlyRep.setOnAction(e -> {
        	monthlyReportModule.generateMonthlyRepModule();
        });
        
        Button dateRangeRep = new Button("Date range report");
        setButtonStyle(dateRangeRep);
        dateRangeRep.setOnAction(e -> {
        	dateRangeReportModule.generateDateRangeRepModule();
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
     * Show a time report according to the input data. This method is used by several 
     * classes, so it is put in the Main method.
     * 
     * @param type the type of this report, can be annual, monthly, or date range
     * @param year the year of the report
     * @param startM the start month of the report
     * @param startD the start day of the report
     * @param endM the end month of the report
     * @param endD the end day of the report
     */
    public static void getTimeRep(String type, String year, String startM, 
    		String startD, String endM, String endD) {
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
    		SingleFarmData e = new SingleFarmData(trp.getId()[i], trp.getWeight()[i], 
    				DF.format(trp.getPercentage()[i]*100)+"%");
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
     * Generate a time range report txt file. This method is used by several 
     * classes, so it is put in the Main method.
     * 
     * @param type the type of this report, can be annual, monthly, or date range
     * @param year the year of the report
     * @param startM the start month of the report
     * @param startD the start day of the report
     * @param endM the end month of the report
     * @param endD the end day of the report
     */
    public static void getTimeRepFile(String type, String year, String startM, String startD, 
    		String endM, String endD) {
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
    			pw.println(trp.getId()[i]+","+trp.getWeight()[i]+","
    					+DF.format(trp.getPercentage()[i]));
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