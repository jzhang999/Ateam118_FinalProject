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
    
}


