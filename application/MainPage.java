package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class represent a main menu for the application
 * 
 * @author Jing Zhang
 *
 */
public class MainPage {

    private Stage mainMenu; // primary stage to represent main menu
    private FileChooser fileChooser; // file chooser to choose file
    private File selectedFile; // selected file to read
    private static final int WINDOW_WIDTH = 400; // width of main page
    private static final int WINDOW_HEIGHT = 700; // height of main page
    private static final  String APP_TITLE = "Welcome to Milk Weight!"; // title of app

    /**
     * Constructor of the mainPage
     * 
     * @param primaryStage
     */
    public MainPage(Stage primaryStage) {
        this.mainMenu = primaryStage;
    }
    
    /**
     * set up the main scence
     * 
     * @return the main scene to the main method
     * @throws FileNotFoundException 
     */
    public Scene mainSceneSetUp() throws FileNotFoundException {
        // set the title 
        mainMenu.setTitle(APP_TITLE);
        
        // create a main scene        
        BorderPane root = new BorderPane(); // Main layout of Border Pane 
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
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
        
        // set left image view and file selection
        VBox upload = fileSelection();
        root.setLeft(upload);
        
        // set center, user choices to redirect
        VBox layout = new VBox();
        layout = choices(layout);
        layout.setAlignment(Pos.TOP_CENTER);
        root.setCenter(layout);
        
        // Bottom exit button
        Button exit = new Button("Done");
        exit.setOnAction(e -> mainMenu.close());
        root.setBottom(exit);
        BorderPane.setAlignment(exit, Pos.BOTTOM_RIGHT);
        
        return mainScene;
    }
    
    /**
     * create a pane for the user choices
     * @return
     */
    private VBox choices(VBox layout) {       
        layout.setSpacing(20); 
        
        Label label = new Label("Your Choices: ");
        label.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));      
        layout.getChildren().add(label);
  
        Button btn1 = new Button("Farm Report");
        btn1.setPrefSize(200, 80);
        btn1.setStyle("-fx-font-size:20");
        layout.getChildren().add(btn1);
        btn1.setOnAction(e -> {
          FarmReport farmReport = new FarmReport(mainMenu);
          Scene farmScene = farmReport.setUp();
          mainMenu.setScene(farmScene);
          mainMenu.show();
        });
        
        Button btn2 = new Button("Annual Report");
        btn2.setPrefSize(200, 80);
        btn2.setStyle("-fx-font-size:20");
        layout.getChildren().add(btn2);
        btn2.setOnAction(e -> {
            AnnualReport annualReport = new AnnualReport(mainMenu);
            Scene annualScene = annualReport.setUp();
            mainMenu.setScene(annualScene);
            mainMenu.show();
        });
  
        Button btn3 = new Button("Monthly Report");
        btn3.setPrefSize(200, 80);
        btn3.setStyle("-fx-font-size:20");
        layout.getChildren().add(btn3);
        btn3.setOnAction(e -> {
            MonthlyReport monthlyReport = new MonthlyReport(mainMenu);
            Scene monthlyScene = monthlyReport.setUp();
            mainMenu.setScene(monthlyScene);
            mainMenu.show();
        });
        
        Button btn4 = new Button("Date Range Report");
        btn4.setPrefSize(200, 80);
        btn4.setStyle("-fx-font-size:20");
        layout.getChildren().add(btn4);
        btn4.setOnAction(e -> {
            DataRangeReport dataRangeReport = new DataRangeReport(mainMenu);
            Scene dataScene = dataRangeReport.setUp();
            mainMenu.setScene(dataScene);
            mainMenu.show();
        });
        
        return layout;
    }
    
    /**
     * create the image view
     * 
     * @param fileName - filename of the image
     * @return an image view
     * @throws FileNotFoundException
     */
    private ImageView setImage(String fileName) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(fileName);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    
    /**
     * Create a vbox for file upload
     * 
     * @return vbox for file upload
     * @throws FileNotFoundException
     */
    private VBox fileSelection() throws FileNotFoundException {
        VBox upload = new VBox();
        upload.setSpacing(20);
        
		FileChooser fileChooser = new FileChooser();
		// show file chooser dialog for upload
		Button btn1 = new Button("Upload File");
		btn1.setOnAction(e -> {
			selectedFile = fileChooser.showOpenDialog(mainMenu);
		});
		upload.getChildren().add(btn1);
        // button for remove data scene
		Button btn2 = new Button("Remove File");
		btn2.setOnAction(e -> {
            RemoveFile remove = new RemoveFile(mainMenu);
            Scene removeScene = remove.setUp();
            mainMenu.setScene(removeScene);
            mainMenu.show();
        });
		
        ImageView imageView3 = setImage("cheese2.png");
        imageView3.setFitHeight(80);
        imageView3.setFitWidth(80);
        imageView3.setPreserveRatio(true);
        upload.getChildren().addAll(btn2, imageView3);
        upload.setAlignment(Pos.BOTTOM_LEFT);
        
        return upload;
    }
    
}


