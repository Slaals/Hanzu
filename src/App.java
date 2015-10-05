
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	
	private TextField txtHanzi;
	
	private boolean isValid;
	
	private VBox root;
	private PracticeView practice;

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(root, 800, 600);
		
		scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
		
		stage.setTitle("Hanzu");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void init() throws Exception {
		Button btnAdd = new Button("Add");
		btnAdd.setPrefWidth(75);
		btnAdd.setPrefHeight(45);
		
		Button btnRefresh = new Button("Refresh");
		btnRefresh.setPrefWidth(75);
		btnRefresh.setPrefHeight(45);

		txtHanzi = new TextField();
		txtHanzi.setAlignment(Pos.CENTER);
		isValid = false;
		validWord();

		ToggleGroup group = new ToggleGroup();
		
		RadioButton radioAlpha = new RadioButton("α");
		RadioButton radioHanzi = new RadioButton("汉字");
		radioHanzi.setSelected(true);
		radioAlpha.setToggleGroup(group);
		radioHanzi.setToggleGroup(group);
		
		/********* ADD PANE *********/
		
		HBox paneAdd = new HBox(15);
		paneAdd.setAlignment(Pos.CENTER);
		
		txtHanzi.setPromptText("汉字");
		
		paneAdd.getChildren().add(txtHanzi);
		paneAdd.getChildren().add(btnAdd);
		
		/*--------------------------*/
		
		/********* ADD START *********/
		
		HBox paneParam = new HBox(25);
		paneParam.setAlignment(Pos.CENTER);
		
		paneParam.getChildren().add(btnRefresh);
		paneParam.getChildren().add(radioAlpha);
		paneParam.getChildren().add(radioHanzi);
		
		/*--------------------------*/
		root = new VBox(25);
		
		practice = new PracticeView(radioHanzi.isSelected());
		
		root.getChildren().add(paneAdd);
		root.getChildren().add(new Separator(Orientation.HORIZONTAL));
		root.getChildren().add(paneParam);
		root.getChildren().add(new Separator(Orientation.HORIZONTAL));
		root.getChildren().add(practice);
		
		/******** EVENTS ************/
		
		btnAdd.setOnAction(event -> {
			if(isValid) {
				Dictionary.getInstance().addEntry(txtHanzi.getText());
				txtHanzi.setText("");
				isValid = false;
				validWord();
				txtHanzi.requestFocus();
			}
		});
		
		txtHanzi.setOnKeyReleased(event -> {
			KeyEvent kEvent = (KeyEvent) event;
			
			if(kEvent.getCode().equals(KeyCode.ENTER) && isValid) {
				Dictionary.getInstance().addEntry(txtHanzi.getText());
				txtHanzi.setText("");
				isValid = false;
				validWord();
				txtHanzi.requestFocus();
			} else {
				if(Dictionary.getInstance().isWordExists(txtHanzi.getText()) 
						|| txtHanzi.getText().equals("")) {
					isValid = false;
					validWord();
				} else {
					isValid = true;
					validWord();
				}
			}
		});
		
		btnRefresh.setOnAction(event -> {
			practice.setMode(radioHanzi.isSelected());
			practice.startSession();
		});
		
		practice.startSession();
	}
	
	private void validWord() {
		txtHanzi.getStyleClass().removeAll("unvalid", "valid");
		if(isValid) {
			txtHanzi.getStyleClass().add("valid");
		} else {
			txtHanzi.getStyleClass().add("unvalid");
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
