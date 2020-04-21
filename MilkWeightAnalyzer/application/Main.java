package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * GUI for the Milk Weight Analyzer
 * 
 * @author Xinrui Liu
 *
 */
public class Main extends Application {
    // static dataBase;
	private static final int WINDOW_WIDTH = 400; // width of main page
    private static final int WINDOW_HEIGHT = 850; // height of main page
    private static final String APP_TITLE = "Welcome to Milk Weight Analyzer!"; // title of app
    private static final DecimalFormat DF = new DecimalFormat("0.00000");// formatter for the percentage
    private static Farms fms;// The back-end that stores all the information
    
    /**
     * Start method.
     * 
     * @param primaryStage the main stage of the GUI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	fms = new Farms();

    	BorderPane root = new BorderPane(); // Main layout of Border Pane
        
        // set top, label of the company view
        ImageView imageView1 = setImage("topOfMainPage.png");
        imageView1.setFitWidth(400);
        imageView1.setPreserveRatio(true);
        root.setTop(imageView1);
        
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
        VBox layout = new VBox();
        VBox readFile = generateReadFileModule();
        VBox farmRep = generateFarmRepModule();
        VBox annualRep = generateAnnualRepModule();
        VBox monthlyRep = generateMonthlyRepModule();
        VBox dateRangeRep = generateDateRangeRepModule();
        layout.getChildren().addAll(readFile, farmRep, annualRep, monthlyRep, dateRangeRep);
        root.setCenter(layout);
        
        // Bottom exit button
        Button exit = new Button("Exit");
        exit.setOnAction(e -> primaryStage.close());
        root.setBottom(exit);
        BorderPane.setAlignment(exit, Pos.BOTTOM_CENTER);
        
        // Display the window
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
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
    private static VBox generateReadFileModule() {
    	VBox vb = new VBox();
    	String guide = "\n                          UPLOAD FILE"
    			+ "\nPlease enter the name of the file that you\nwant to upload in"
    			+ " the text box below:";
    	Label lb = new Label(guide);
    	TextField tf = new TextField();
    	Button bt = new Button("Upload file");
    	bt.setOnAction(e -> {
    		readFile(tf.getText());
    		tf.clear();
    	});
    	vb.getChildren().addAll(lb, tf, bt);
    	return vb;
    }
    
    /**
     * Read the file according to the fileName that user types
     * 
     * @param fileName the name of the file
     */
    private static void readFile(String fileName) {
    	try {
    		fms.readCsvFile(fileName);
    		Alert alt = new Alert(AlertType.INFORMATION, "Upload Succeeded!");
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    	} catch (Exception e) {
    		Alert alt = new Alert(AlertType.WARNING, e.getMessage());
    		alt.showAndWait().filter(r -> r==ButtonType.OK);
    	}
    }
    
    /**
     * Generate the farm report module
     * 
     * @return a VBox representing the module that can generate farm reports.
     */
    private static VBox generateFarmRepModule() {
    	VBox vb = new VBox();
    	
    	// Set the directions on the top
    	String guide = "\n                     GET FARM REPORT"
    			+ "\nPlease enter the farm ID and the year in\nthe text field below "
    			+ "to view a farm report.";
    	Label lb = new Label(guide);
    	
    	// Fields for user to type inputs
    	HBox hb = new HBox();
    	Label id = new Label("ID: ");
    	Label year = new Label("  Year: ");
    	TextField getId = new TextField();
    	getId.setPrefWidth(70.0);
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);
    	hb.getChildren().addAll(id, getId, year, getYear);
    	
    	// Button for user to enter input
    	Button bt = new Button("Get farm report");
    	bt.setOnAction(e -> {
    		getFarmRep(getId.getText(), getYear.getText());
    		getId.clear();
    		getYear.clear();
    	});
    	
    	
    	vb.getChildren().addAll(lb, hb, bt);
    	return vb;
    }
    
    /**
     * Show a farm report according to the input id and year
     * 
     * @param id the id of the farm report
     * @param year the year of the farm report
     */
    private static void getFarmRep(String id, String year) {
    	// Declare needed local fields
    	int ID = 0;
    	int Year = 0;
    	FarmReport frp = null;
    	Stage farmRep = new Stage();
    	
    	// Read the user input
    	try {
    		ID = Integer.parseInt(id);
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
    	
    	// Display the farm report
    	VBox month = new VBox();
    	Label MONTH = new Label("Month");
    	month.getChildren().add(MONTH);
    	VBox weight = new VBox();
    	Label WEIGHT = new Label("Milk Weight");
    	weight.getChildren().add(WEIGHT);
    	VBox percentage = new VBox();
    	Label PERCENTAGE = new Label("Percentage");
    	percentage.getChildren().add(PERCENTAGE);
    	for (int i=1; i<=9; i++) {
    		Label Month = new Label(Integer.toString(i)+"          ");
    		month.getChildren().add(Month);
    		Label Weight = new Label(Integer.toString(frp.getWeight(i))+"          ");
    		weight.getChildren().add(Weight);
    		Label Percentage = new Label(DF.format(frp.getPercentage(i)));
    		percentage.getChildren().add(Percentage);
    	}
    	for (int i=10; i<=12; i++) {
    		Label Month = new Label(Integer.toString(i)+"         ");
    		month.getChildren().add(Month);
    		Label Weight = new Label(Integer.toString(frp.getWeight(i))+"          ");
    		weight.getChildren().add(Weight);
    		Label Percentage = new Label(DF.format(frp.getPercentage(i)));
    		percentage.getChildren().add(Percentage);
    	}
    	
    	// Button to close that report
    	Button close = new Button("Close report");
    	close.setOnAction(e -> farmRep.close());
    	
    	// Set the scene and show the report
    	BorderPane rep = new BorderPane();
    	Label farmid = new Label("Farm " + id + "  Year " + year);
    	rep.setTop(farmid);
    	rep.setLeft(month);
    	rep.setCenter(weight);
    	rep.setRight(percentage);
    	rep.setBottom(close);
    	rep.setAlignment(farmid, Pos.TOP_CENTER);
    	rep.setAlignment(close, Pos.BOTTOM_CENTER);
    	Scene s = new Scene(rep,200,240);
    	farmRep.setTitle("Farm Report");
    	farmRep.setScene(s);
    	farmRep.show();
    }
    
    /**
     * Generates the annual report module
     * 
     * @return a VBox representing the module that can generate annual report
     */
    private static VBox generateAnnualRepModule() {
    	VBox vb = new VBox();
    	
    	// Directions for users
    	String guide = "\n                   GET ANNUAL REPORT"
    			+ "\nPlease enter the year in the text field below\n"
    			+ "to view an annual report.\n"
    			+ "If the report shows nothing, then you"
    			+ " haven't\nuploaded any file yet.";
    	Label lb = new Label(guide);
    	
    	// Input fields
    	HBox hb = new HBox();
    	Label year = new Label("Year: ");
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);    	
    	hb.getChildren().addAll(year, getYear);
    	
    	// Button to enter inputs
    	Button bt = new Button("Get annual report");
    	bt.setOnAction(e -> {
    		getTimeRep("Annual", getYear.getText(), "1", "1", "12", "31");
    		getYear.clear();
    	});
    	
    	vb.getChildren().addAll(lb, hb, bt);
    	return vb;
    }
    
    /**
     * Generates the monthly report module
     * 
     * @return a VBox representing the module that can generate monthly report
     */
    private static VBox generateMonthlyRepModule() {
    	VBox vb = new VBox();
    	// Directions for users
    	String guide = "\n                  GET MONTHLY REPORT"
    			+ "\nPlease enter the year and the month in the\ntext field below "
    			+ "to view a monthly report.\n"
    			+ "If the report shows nothing, then you "
    			+ "haven't\nuploaded any file yet.";
    	Label lb = new Label(guide);
    	
    	// Input fields
    	HBox hb = new HBox();
    	Label year = new Label("Year: ");
    	TextField getYear = new TextField();
    	getYear.setPrefWidth(70.0);
    	Label month = new Label("  Month: ");
    	TextField getMonth = new TextField();
    	getMonth.setPrefWidth(70.0);
    	hb.getChildren().addAll(year, getYear, month, getMonth);
    	
    	// Button to enter inputs
    	Button bt = new Button("Get monthly report");
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
    	
    	
    	vb.getChildren().addAll(lb, hb, bt);
    	return vb;
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
    private static VBox generateDateRangeRepModule() {
    	VBox vb = new VBox();
    	
    	// User directions
    	String guide = "\n                GET DATE RANGE REPORT"
    			+ "\nPlease enter the range of date in the text\nfield below "
    			+ "to view a date range report.\n"
    			+ "If the report shows nothing, then you "
    			+ "haven't\nuploaded any file yet.";
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
    	Button bt = new Button("Get date range report");
    	bt.setOnAction(e -> {
    		getTimeRep("Date Range", getYear.getText(), getStartM.getText(), 
    				getStartD.getText(), getEndM.getText(), getEndD.getText());
    		getYear.clear();
    		getStartM.clear();
    		getStartD.clear();
    		getEndM.clear();
    		getEndD.clear();
    	});
    	
    	vb2.getChildren().addAll(hbyear, hbstart, hbend);
    	vb.getChildren().addAll(lb, vb2, bt);
    	return vb;
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
    	int Width = 0;
    	Label ID = new Label("Farm ID");
    	Label WEIGHT = new Label("Milk Weight");
    	Label PERCENTAGE = new Label("Percentage");
    	Label ID2 = new Label("Farm ID");
    	Label WEIGHT2 = new Label("Milk Weight");
    	Label PERCENTAGE2 = new Label("Percentage");
    	Label ID3 = new Label("Farm ID");
    	Label WEIGHT3 = new Label("Milk Weight");
    	Label PERCENTAGE3 = new Label("Percentage");
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
    	
    	// Display the time report
    	// Case 1: less than or equal to 50 farms
    	if (trp.getId().length<=50) {
    		// Set width for the window
    		Width = 260;
    		
    		HBox hb = new HBox();
    		
    		// Set three column
    		VBox id = new VBox();
        	id.getChildren().add(ID);
        	VBox weight = new VBox();
        	weight.getChildren().add(WEIGHT);
        	VBox percentage = new VBox();
        	percentage.getChildren().add(PERCENTAGE);
        	
        	// Add data to each column
    		for (int i=0; i<trp.getId().length; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage.getChildren().add(Percentage);
    		}
    		
    		// Set up the pane
    		hb.getChildren().addAll(id, weight, percentage);
    		rep.setCenter(hb);
    	} 
    	// Case 2: 51 to 100 farms
    	else if (trp.getId().length<=100) {
    		// Set width for the window
    		Width = 400;
    		
    		// Set up the first column
    		HBox hb = new HBox();
    		VBox id = new VBox();
        	id.getChildren().add(ID);
        	VBox weight = new VBox();
        	weight.getChildren().add(WEIGHT);
        	VBox percentage = new VBox();
        	percentage.getChildren().add(PERCENTAGE);
        	
    		for (int i=0; i<50; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage.getChildren().add(Percentage);
    		}
    		
    		hb.getChildren().addAll(id, weight, percentage);
    		rep.setLeft(hb);
    		
    		
    		// Set up the second column
    		HBox hb2 = new HBox();
    		VBox id2 = new VBox();
        	id2.getChildren().add(ID2);
        	VBox weight2 = new VBox();
        	weight2.getChildren().add(WEIGHT2);
        	VBox percentage2 = new VBox();
        	percentage2.getChildren().add(PERCENTAGE2);
        	
    		for (int i=50; i<trp.getId().length; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id2.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight2.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage2.getChildren().add(Percentage);
    		}
    		
    		hb2.getChildren().addAll(id2, weight2, percentage2);
    		rep.setRight(hb2);
    		
    	} 
    	// Case 3: more than 100 farms
    	else {
    		// Set width for the window
    		Width = 600;
    		
    		
    		// Set up the first column
    		HBox hb = new HBox();
    		
    		VBox id = new VBox();
        	id.getChildren().add(ID);
        	VBox weight = new VBox();
        	weight.getChildren().add(WEIGHT);
        	VBox percentage = new VBox();
        	percentage.getChildren().add(PERCENTAGE);
        	
    		for (int i=0; i<50; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage.getChildren().add(Percentage);
    		}
    		
    		hb.getChildren().addAll(id, weight, percentage);
    		rep.setLeft(hb);
    		
    		
    		// Set up the second column
    		HBox hb2 = new HBox();
    		
    		VBox id2 = new VBox();
        	id2.getChildren().add(ID2);
        	VBox weight2 = new VBox();
        	weight2.getChildren().add(WEIGHT2);
        	VBox percentage2 = new VBox();
        	percentage2.getChildren().add(PERCENTAGE2);
        	
    		for (int i=50; i<100; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id2.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight2.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage2.getChildren().add(Percentage);
    		}
    		
    		hb2.getChildren().addAll(id2, weight2, percentage2);
    		rep.setCenter(hb2);
    		
    		
    		//Set up the third column
    		HBox hb3 = new HBox();
    		
    		VBox id3 = new VBox();
        	id3.getChildren().add(ID3);
        	VBox weight3 = new VBox();
        	weight3.getChildren().add(WEIGHT3);
        	VBox percentage3 = new VBox();
        	percentage3.getChildren().add(PERCENTAGE3);
        	
    		for (int i=100; i<trp.getId().length; i++) {
    			Label Id = new Label(Integer.toString(trp.getId()[i])+"          ");
        		id3.getChildren().add(Id);
        		Label Weight = new Label(Integer.toString(trp.getWeight()[i])+"          ");
        		weight3.getChildren().add(Weight);
        		Label Percentage = new Label(DF.format(trp.getPercentage()[i]));
        		percentage3.getChildren().add(Percentage);
    		}
    		
    		hb3.getChildren().addAll(id3, weight3, percentage3);
    		rep.setRight(hb3);
    	}
    	
    	// Set the information on the top
    	VBox vb2 = new VBox();
    	Label lb2 = new Label(type + " Report   " + startM + "/"
    			+ startD + "/" + year + " to " + endM + "/"
    			+ endD + "/" + year
    			+ "\n------------------------------------------------"
    			+ "\nIf the report shows 0's and NaN's for some\nfarms, "
    			+ "then the database does not contain\nrecord for "
    			+ "that farm in this year.\n------------------------------------------------");
    	rep.setTop(lb2);
    	rep.setAlignment(lb2, Pos.TOP_CENTER);
    	
    	// Button to close the report
    	Button close = new Button("Close report");
    	close.setOnAction(e -> timeRep.close());
    	rep.setBottom(close);
    	rep.setAlignment(close, Pos.BOTTOM_CENTER);
    	
    	// Display the scene
    	Scene s = new Scene(rep, Width, 890);
    	timeRep.setScene(s);
    	timeRep.setTitle(type + " Report");
    	timeRep.show();
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