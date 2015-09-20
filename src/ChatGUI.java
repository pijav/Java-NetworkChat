import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatGUI extends Application {
	Scene mainScene;
	Pane topScene;
	Pane midScene;
	Pane midLeftScene;
	Pane midRightScene;
	Pane bottomScene;

	static TextField nameInput;
	static TextArea messageInput;
	TextArea usersList;
	static TextArea chatField;
	static String userName;

	public static String getName() {
		return nameInput.getText();
	}

	public static String getUserName() {
		return userName;
	}

	public String getMessage() {
		return messageInput.getText();
	}

	public void setMessage(String args) {
		messageInput.setText(args);
	}

	public static void setDisplay(String args) {
		chatField.setText(args);
	}

	Label currentUsers;

	Button connect;
	Button disconnect;
	Button sendMessage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize main layout and add Scene to layout
		BorderPane border = new BorderPane();
		mainScene = new Scene(border, 690, 455);

		// initialize top pane and top elements
		HBox topBox = new HBox(15);
		topBox.setPadding(new Insets(10, 10, 10, 10));
		nameInput = new TextField();
		nameInput.setPromptText("Ваш ник");
		connect = new Button();
		connect.setText("Connect");
		disconnect = new Button();
		disconnect.setText("Disconnect");
		disconnect.setDisable(true);
		// add elements to Box
		topBox.getChildren().addAll(nameInput, connect, disconnect);
		// initialize topPane and add Box to pane
		topScene = new Pane();
		topScene.getChildren().addAll(topBox);

		// initialize mid pane and mid elements
		HBox midBox = new HBox();
		midBox.setPadding(new Insets(0, 10, 10, 10));
		midLeftScene = new Pane();
		midRightScene = new Pane();
		// add elements to Box and add Box to Scene
		midBox.getChildren().addAll(midLeftScene, midRightScene);
		// initialize midPane and add Box to pane
		midScene = new Pane();
		midScene.getChildren().addAll(midBox);
		// initialize mid elements RIGHT and LEFT
		chatField = new TextArea();
		currentUsers = new Label();
		currentUsers.setText("Current users:");
		usersList = new TextArea();
		// add elements to Scene and customize them
		midLeftScene.getChildren().addAll(chatField);
		chatField.setMinSize(580, 300);
		chatField.setMaxSize(580, 300);
		usersList.setMinSize(90, 278);
		usersList.setMaxSize(90, 278);
		usersList.setEditable(false);
		chatField.setWrapText(true);
		chatField.setEditable(false);
		chatField.setStyle(" -fx-background-color: -fx-outer-border,"
				+ "-fx-inner-border, -fx-body-color;  -fx-background-insets: 0, 1, 2;"
				+ "-fx-background-radius: 5, 4, 3;");
		usersList.setStyle(" -fx-background-color: -fx-outer-border,"
				+ "-fx-inner-border, -fx-body-color;  -fx-background-insets: 0, 1, 2;"
				+ "-fx-background-radius: 5, 4, 3;");
		// create VBox for right scene
		VBox rightPanelBox = new VBox(5);
		rightPanelBox.setPadding(new Insets(0, 0, 10, 10));
		rightPanelBox.getChildren().addAll(currentUsers, usersList);
		rightPanelBox.setAlignment(Pos.CENTER);
		midRightScene.getChildren().addAll(rightPanelBox);

		// initialize top pane and top elements
		HBox bottomBox = new HBox(10);
		bottomBox.setPadding(new Insets(10, 10, 10, 10));
		messageInput = new TextArea();
		messageInput.setPromptText("Введите текст");
		messageInput.setMinSize(580, 100);
		messageInput.setMaxSize(580, 100);
		sendMessage = new Button();
		sendMessage.setText("Отправить");
		sendMessage.setDisable(true);
		// add elements to Box
		bottomBox.getChildren().addAll(messageInput, sendMessage);
		// initialize topPane and add Box to pane
		bottomScene = new Pane();
		bottomScene.getChildren().addAll(bottomBox);

		// Set all panes in right order
		border.setTop(topScene);
		border.setCenter(midScene);
		border.setBottom(bottomScene);

		// action logic
		newEngine engine = new newEngine();
		connect.setOnAction(e -> {
			
			engine.connect("localhost", 3000);
			sendMessage.setDisable(false);
			disconnect.setDisable(false);
			connect.setDisable(true);
			//send nickname with special preambula
			setMessage(".nickname" + getName());
			engine.send();
		});

		sendMessage.setOnAction(e -> {
			try {
				engine.send();
				sendMessage.requestFocus();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		disconnect.setOnAction(e -> {
			sendMessage.setDisable(true);
			disconnect.setDisable(true);
			connect.setDisable(false);
			//init quit with special preambula
			setMessage(".bye");
			engine.send();
			

		});

		// Set stage
		primaryStage.setTitle("NetworkChat (work in progress)");
		primaryStage.setScene(mainScene);
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});

		primaryStage.show();
		primaryStage.setResizable(false);

	}

	private void closeProgram() {
		boolean result = returnDataBox.display("Подтвердите выход", "Выйти из программы?");
		if (result) {

			Platform.exit();
		}
	}
}
