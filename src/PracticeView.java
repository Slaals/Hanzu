
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;


public class PracticeView extends GridPane {
	
	private Label lblHanzi;
	private TextField txtTrad;
	private TextArea txtDescTrad;
	private Button btnCheck;
	private Button btnNext;
	
	private Practice session;
	
	private boolean isHanzi;
	
	public PracticeView(boolean mode) {
		super();
		
		this.isHanzi = mode;
		
		init();
		
		FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
		btnPane.setAlignment(Pos.CENTER);
		btnPane.setHgap(20);
		
		btnPane.getChildren().add(btnCheck);
		btnPane.getChildren().add(btnNext);
		
		add(lblHanzi, 0, 0);
		add(txtTrad, 1, 0);
		add(txtDescTrad, 1, 1);
		add(btnPane, 0, 2);
		
		setRowSpan(lblHanzi, 2);
		setColumnSpan(btnPane, 2);
		
		setHgap(15);
		setVgap(15);
		
		setAlignment(Pos.CENTER);
		
	}
	
	public void setMode(boolean isHanzi) {
		this.isHanzi = isHanzi;
	}
	
	private void nextWord() {
		session.next();
		
		if(!isHanzi) {
			lblHanzi.setText("");
			txtTrad.setText(session.getTranslate());
			txtDescTrad.setText(session.getDesc());
		} else {
			lblHanzi.setText(session.getHanzi());
			txtTrad.setText("");
			txtDescTrad.setText("");
		}
	}
	
	public void startSession() {
		session = new Practice();
		nextWord();
	}
	
	private void init() {
		lblHanzi = new Label();
		txtTrad = new TextField();
		txtDescTrad = new TextArea();
		
		txtTrad.setEditable(false);
		txtDescTrad.setEditable(false);
		
		txtTrad.setAlignment(Pos.CENTER);
		
		txtTrad.setId("trad");
		
		btnCheck = new Button("Check");
		btnNext = new Button("Next");
		btnCheck.setPrefWidth(75);
		btnNext.setPrefWidth(75);
		btnCheck.setPrefHeight(45);
		btnNext.setPrefHeight(45);
		
		lblHanzi.setPrefHeight(200);
		lblHanzi.setPrefWidth(450);
		lblHanzi.setMaxWidth(Double.MAX_VALUE);
		
		lblHanzi.setAlignment(Pos.CENTER);
		lblHanzi.setTextAlignment(TextAlignment.CENTER);
		
		lblHanzi.setId("hanzi");
		
		btnCheck.setOnAction(event -> {
			show();
		});
		
		btnNext.setOnAction(event -> {
			nextWord();
		});
	}
	
	private void show() {
		txtTrad.setText(session.getTranslate());
		txtDescTrad.setText(session.getDesc());
		lblHanzi.setText(session.getHanzi());
	}
	

}
