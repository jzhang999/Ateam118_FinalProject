package application;

import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RemoveFile {
    private Stage removeFile; // stage to represent removing file
    private static final int WINDOW_WIDTH = 400; // width of main page
    private static final int WINDOW_HEIGHT = 700; // height of main page
    private static final  String TITLE = "File Uploading History"; // title of app
    
    public RemoveFile(Stage mainMenu) {
        this.removeFile = mainMenu;
    }

    public Scene setUp() {
        // set the title
        removeFile.setTitle(TITLE);
  
        // create a remove file scene
        VBox root = new VBox(); 
        Label label = new Label("History");
        Text files = new Text("2019-1\n2019-2\n2020-1\n2021-4\n");
        TextField fileName = new TextField();
        Button btn = new Button("Remove File");
        
        Button back = new Button("Back");
        back.setOnAction(e -> {
            MainPage mainPage = new MainPage(removeFile);
            Scene mainScene = null;
            try {
                mainScene = mainPage.mainSceneSetUp();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            removeFile.setScene(mainScene);
            removeFile.show();
        });
        
        root.getChildren().addAll(label, files, fileName, btn, back);
        
        Scene removeScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
  
        return removeScene;
    }
}
