package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import application.AnnualReport.Data;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataRangeReport {
	private Stage dataRangeReport; // stage to represent Data Range Report
	private static final int WINDOW_WIDTH = 400; // width of main page
	private static final int WINDOW_HEIGHT = 700; // height of main page
	private static final String TITLE = "Data Range Report"; // title of app
	private TableView<Data> table = new TableView<Data>();
	private final ObservableList<Data> data = FXCollections.observableArrayList(new Data("25", "567", "35%"),
			new Data("26", "678", "56%"), new Data("27", "789", "8%"), new Data("28", "890", "21%"),
			new Data("29", "123", "16%"));

	public DataRangeReport(Stage mainMenu) {
		this.dataRangeReport = mainMenu;
	}

	public Scene setUp() {
		// set the title
		dataRangeReport.setTitle(TITLE);

		// create a annual report scene
		VBox root = annualR(); // Main layout of annual report
		Scene dataRangeScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		return dataRangeScene;
	}

	public VBox annualR() {
		VBox v1 = new VBox(); // Box for the whole window

		HBox h1 = new HBox(); // The first horizontal sub box
		h1.setSpacing(10);
		Button back = new Button("Back");

		back.setOnAction(e -> {
			MainPage mainPage = new MainPage(dataRangeReport);
			Scene mainScene = null;
			try {
				mainScene = mainPage.mainSceneSetUp();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			dataRangeReport.setScene(mainScene);
			dataRangeReport.show();
		});

		Text txt1 = new Text("Enter YY/M/D:  ");
		TextField usertxt1 = new TextField();
		Button Enter = new Button("Confirm");
		h1.getChildren().addAll(back, txt1, usertxt1, Enter);

		table.setEditable(true);

		TableColumn firstNameCol = new TableColumn("Farm ID");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Data, String>("farmID"));

		TableColumn lastNameCol = new TableColumn("Weights");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Data, String>("weights"));

		TableColumn emailCol = new TableColumn("Percentage");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<Data, String>("percentage"));

		table.setItems(data);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		HBox h2 = new HBox(); // The second horizontal sub box
		Button ID = new Button("ID");
		Button ascendingO = new Button("Ascending Order");
		Button descendingO = new Button("Descending Order");
		Text txt2 = new Text("Sort By:  ");
		h2.getChildren().addAll(txt2, ID, ascendingO, descendingO);

		v1.getChildren().addAll(h1, table, h2);
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
			File saved = fileChooser.showOpenDialog(dataRangeReport);
			if (saved != null) {
				SaveFile(getString(data), saved);
				v1.getChildren().add(new Label("Save Success!"));
			}
		});
		v1.getChildren().add(save);

	}

	private String getString(ObservableList<Data> data) {
		String a = "Data Range Report: \n";
		a = a + "Farm ID\t" + "Weights\t" + "Percentage\t" + "\n";
		for (Data d : data) {
			a = a + d.getFarmID() + "\t" + d.getWeights() + "\t" + d.getPercentage() + "\n";
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

		private final SimpleStringProperty farmID;
		private final SimpleStringProperty weights;
		private final SimpleStringProperty percentage;

		private Data(String fName, String lName, String email) {
			this.farmID = new SimpleStringProperty(fName);
			this.weights = new SimpleStringProperty(lName);
			this.percentage = new SimpleStringProperty(email);
		}

		public String getFarmID() {
			return farmID.get();
		}

		public void setFarmID(String fName) {
			farmID.set(fName);
		}

		public String getWeights() {
			return weights.get();
		}

		public void setWeights(String fName) {
			weights.set(fName);
		}

		public String getPercentage() {
			return percentage.get();
		}

		public void setPercentage(String fName) {
			percentage.set(fName);
		}
	}
}
