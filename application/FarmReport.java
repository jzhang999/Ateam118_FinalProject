package application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.FileChooser;
import java.nio.file.Files;

/**
 * The Farm Report class helps to display the annual report of the farm
 * 
 * @author Yucheng Lin, Jing Zhang
 *
 */
public class FarmReport {
	private Stage farmReport; // stage to represent AnnualReport
	private static final int WINDOW_WIDTH = 400; // width of main page
	private static final int WINDOW_HEIGHT = 700; // height of main page
	private static final String TITLE = "Farm Report"; // title of app
	private TableView<Data> table = new TableView<Data>();
	private final ObservableList<Data> data = FXCollections.observableArrayList(new Data("1", "1000", "8.3%"),
			new Data("2", "1000", "8.3%"), new Data("3", "1000", "8.3%"), new Data("4", "1000", "8.3%"),
			new Data("5", "1000", "8.3%"), new Data("6", "1000", "8.3%"), new Data("7", "1000", "8.3%"),
			new Data("8", "1000", "8.3%"), new Data("9", "1000", "8.3%"), new Data("10", "1000", "8.3%"),
			new Data("11", "1000", "8.3%"), new Data("12", "1000", "8.3%"));

	public FarmReport(Stage mainMenu) {
		this.farmReport = mainMenu;
	}

	public Scene setUp() {
		// set the title
		farmReport.setTitle(TITLE);

		// create a annual report scene
		VBox root = farmR(); // Main layout of annual report
		Scene annualScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		return annualScene;
	}

	public VBox farmR() {
		VBox v1 = new VBox();

		// first line part
		HBox h1 = new HBox();
		h1.setSpacing(10);
		Button back = new Button("Back");
		// button set of returning to main menu
		back.setOnAction(e -> {
			MainPage mainPage = new MainPage(farmReport);
			Scene mainScene = null;
			try {
				mainScene = mainPage.mainSceneSetUp();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			farmReport.setScene(mainScene);
			farmReport.show();
		});
		h1.getChildren().add(back);
		Button Enter = new Button("Confirm");			

		// add prompt in hbox1
		TextField idT = new TextField();
		idT.setPromptText("Farm ID ");
		idT.setMaxWidth(60);
		String idS = idT.getText();

		// add user input info in hbox1
		TextField yearT = new TextField();
		yearT.setPromptText("Year ");
		yearT.setMaxWidth(60);
		String yearS = yearT.getText();
		
		h1.getChildren().addAll(idT, yearT, Enter);   
		v1.getChildren().add(h1);

		// table view
		table.setEditable(true);

		TableColumn firstNameCol = new TableColumn("Month");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Data, String>("firstName"));

		TableColumn lastNameCol = new TableColumn("Weights");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Data, String>("lastName"));

		TableColumn emailCol = new TableColumn("Percentage");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<Data, String>("email"));

		table.setItems(data);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		HBox h2 = new HBox(); // The second horizontal sub box
		Button ID = new Button("ID");
		Button ascendingO = new Button("Ascending Order");
		Button descendingO = new Button("Descending Order");
		Label txt2 = new Label("Sort By:  ");
		h2.getChildren().addAll(txt2, ID, ascendingO, descendingO);
		v1.getChildren().addAll(table, h2);

		saveButton(v1);

		return v1;
	}

	private void saveButton(VBox v1) {
		// show file chooser dialog
		Button save = new Button("Save File");
		save.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			String defaultFileName = "SavedData.txt";
			fileChooser.setInitialFileName(defaultFileName);
			File saved = fileChooser.showOpenDialog(farmReport);
			if (saved != null) {
				SaveFile(getString(data), saved);
				v1.getChildren().add(new Label("Save Success!"));
			}
		});
		v1.getChildren().add(save);
	}

	private String getString(ObservableList<Data> data) {
		String a = "Farm Report: \n";
		a = a + "Month\t" + "Weights\t" + "Percentage\t" + "\n";
		for (Data d : data) {
			a = a + d.getFirstName() + "\t" + d.getLastName() + "\t" + d.getEmail() + "\n";
		}
		return a;
	}

	private void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static class Data {

		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;

		private Data(String fName, String lName, String email) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}
	}

}
