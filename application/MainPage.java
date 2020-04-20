package application;

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
import javafx.stage.Stage;

public class MainPage {

    private Stage mainMenu; // primary stage to represent main menu
    private static final int WINDOW_WIDTH = 400; // width of main page
    private static final int WINDOW_HEIGHT = 700; // height of main page
    private static final  String APP_TITLE = "Welcome to Milk Weight!"; // title of app

  
    public MainPage(Stage primaryStage) {
        this.mainMenu = primaryStage;
    }
    
    /**
     * set up the main scence
     * @return
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
        
        // set left image view
        ImageView imageView3 = setImage("cheese2.png");
        imageView3.setFitHeight(80);
        imageView3.setFitWidth(80);
        imageView3.setPreserveRatio(true);
        root.setLeft(imageView3);
        BorderPane.setAlignment(imageView3, Pos.BOTTOM_CENTER);
        
        // set center, user choices to redirect
        VBox layout = new VBox();
        layout = choices(layout, mainScene);
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
    private VBox choices(VBox layout, Scene mainScene) {       
       layout.setSpacing(20); 

       Label label = new Label("Your Choices: ");
       label.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));      
       layout.getChildren().add(label);

       Button btn1 = new Button("Farm Report");
       btn1.setOnAction(e -> FarmReport(mainScene));
       btn1.setPrefSize(200, 80);
       btn1.setStyle("-fx-font-size:20");
       layout.getChildren().add(btn1);
       
       Button btn2 = new Button("Annual Report");
       btn2.setPrefSize(200, 80);
       btn2.setStyle("-fx-font-size:20");
       layout.getChildren().add(btn2);

       Button btn3 = new Button("Monthly Report");
       btn3.setPrefSize(200, 80);
       btn3.setStyle("-fx-font-size:20");
       layout.getChildren().add(btn3);

       Button btn4 = new Button("Date Range Report");
       btn4.setPrefSize(200, 80);
       btn4.setStyle("-fx-font-size:20");
       layout.getChildren().add(btn4);
       
       return layout;
    }
    
    private ImageView setImage(String fileName) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(fileName);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    
    private class FarmReport(Scene mainScene){
		Scene FARMREPORT;
		mainMenu.setScene(FARMREPORT);
		
		BorderPane b1 = new BorderPane();
		FARMREPORT = new Scene(b1, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// add hbox1
		HBox h1 = new HBox();
		Button back = new Button("Back");
		back.setOnAction(e -> mainMenu.setScene(mainScene));
		h1.getChildren().add(back);
		Label Fmrpt = new Label("Farm Report");
		h1.getChildren().add(Fmrpt);
		
		//add prompt in hbox1
		TextField idT = new TextField();
		idT.setPromptText("Farm ID ");
		idT.setMaxWidth(60);
		String idS = idT.getText();
		
		//add user input info in hbox1
		TextField yearT = new TextField();
		yearT.setPromptText("Year ");
		yearT.setMaxWidth(60);
		String yearS = yearT.getText();
		h1.getChildren().add(idT);
		h1.getChildren().add(yearT);
		
		VBox v1 = new VBox();
		v1.getChildren().add(h1);
		v1.getChildren().add(new Label("The Milk Weights for " + idS + " in " + yearS + " are listed below: "));
		b1.setTop(v1);
		
		//create hard-coded grid pane to show data
		GridPane g2 = new GridPane();
		g2.add(new Label("Month"), 0, 0);
		g2.add(new Label("Jan"), 0, 1);
		g2.add(new Label("Feb"), 0, 2);
		g2.add(new Label("Mar"), 0, 3);
		g2.add(new Label("Apr"), 0, 4);
		g2.add(new Label("May"), 0, 5);
		g2.add(new Label("Jun"), 0, 6);
		g2.add(new Label("Jul"), 0, 7);
		g2.add(new Label("Aug"), 0, 8);
		g2.add(new Label("Sep"), 0, 9);
		g2.add(new Label("Oct"), 0, 10);
		g2.add(new Label("Nov"), 0, 11);
		g2.add(new Label("Dec"), 0, 12);
		g2.add(new Label("Weights"), 1, 0);
		g2.add(new Label("10000000"), 1, 1);
		g2.add(new Label("10000000"), 1, 2);
		g2.add(new Label("10000000"), 1, 3);
		g2.add(new Label("10000000"), 1, 4);
		g2.add(new Label("10000000"), 1, 5);
		g2.add(new Label("10000000"), 1, 6);
		g2.add(new Label("10000000"), 1, 7);
		g2.add(new Label("10000000"), 1, 8);
		g2.add(new Label("10000000"), 1, 9);
		g2.add(new Label("10000000"), 1, 10);
		g2.add(new Label("10000000"), 1, 11);
		g2.add(new Label("10000000"), 1, 12);
		b1.setCenter(g2);
		
		// add sort options
		HBox h3 = new HBox();
		h3.getChildren().add(new Label("SORT BY:"));
		Button mthS = new Button("Month Order");
		Button ascS = new Button("Asscending Order");
		Button desS = new Button("Descending Order");
		h3.getChildren().add(mthS);
		h3.getChildren().add(ascS);
		h3.getChildren().add(desS);
		b1.setBottom(h3);
	}
}


