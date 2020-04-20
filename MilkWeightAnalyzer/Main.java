package application;

import java.util.List;

import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    // static dataBase;
    static MainPage mainPage; // main page for user interface
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        mainPage = new MainPage(primaryStage);
        Scene mainScene = mainPage.mainSceneSetUp();

        // Add the stuff and set the primary stage
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
           launch(args);
    }
}