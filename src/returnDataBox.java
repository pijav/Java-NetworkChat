

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class returnDataBox {
	static boolean result;
	public static boolean display(String title, String message){
		Stage returnDataBoxStage =  new Stage();
		
		returnDataBoxStage.initModality(Modality.APPLICATION_MODAL);
		returnDataBoxStage.setTitle(title);
		returnDataBoxStage.setMinWidth(250);
		
		Label theMessage = new Label();
		theMessage.setText(message);
		
		Button yesButton = new Button();
		Button noButton = new Button();
		
		yesButton.setText("Yes");
		noButton.setText("No");
		
		yesButton.setOnAction(e -> {
			result = true;
			returnDataBoxStage.close();
		});
		
		noButton.setOnAction(e -> {
			result = false;
			returnDataBoxStage.close();
		});
		
		
		
		VBox boxLayout = new VBox(10);
		boxLayout.getChildren().addAll(theMessage, yesButton, noButton);
		boxLayout.setAlignment(Pos.CENTER);
		
		Scene returnDataBoxScene = new Scene(boxLayout, 300, 100);
		returnDataBoxStage.setScene(returnDataBoxScene);
		returnDataBoxStage.showAndWait();
		
		
		return result;
	}
}




