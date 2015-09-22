import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class alertBox {

	public static void display(String title, String message){
		
		Stage alertBoxStage =  new Stage();
		
		alertBoxStage.initModality(Modality.APPLICATION_MODAL);
		alertBoxStage.setTitle(title);
		alertBoxStage.setMinWidth(250);
		
		Label theMessage = new Label();
		theMessage.setText(message);
		
		Button closeButton = new Button();
		closeButton.setText("I got it!");
		closeButton.setOnAction(e -> alertBoxStage.close());
		
		VBox boxLayout = new VBox(10);
		boxLayout.setPadding(new Insets(20, 20, 20, 20));
		boxLayout.getChildren().addAll(theMessage, closeButton);
		boxLayout.setAlignment(Pos.CENTER);
		
		Scene alertBoxScene = new Scene(boxLayout, 300, 100);
		alertBoxStage.setScene(alertBoxScene);
		alertBoxStage.showAndWait();
		
	}
	
}