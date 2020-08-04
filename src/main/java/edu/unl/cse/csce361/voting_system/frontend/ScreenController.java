package edu.unl.cse.csce361.voting_system.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ScreenController {
    public void switchScreen(javafx.event.ActionEvent event, String screen) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screen));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /*
    public void alertScreen(String title, String message, String message2, String noButton, String yesButton) {

        Stage window = new Stage();
        //application modality allows the application and screen below to remain open,
        //but non-functional until the alert screen closes
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(150);
        window.setMinWidth(350);

        Label label = new Label();
        label.setText(message);
        Label label2 = new Label();
        label2.setText(message2);
        
        Button yes = new Button(yesButton);
        //yes.setOnAction(e -> switchScreen(e, "login.fxml"));
        yes.setOnAction(e -> {
			try {
				FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("login.fxml").toExternalForm());
		        Parent parent = loader.load();
				Scene scene = new Scene(parent);
				Stage stage = (Stage) yes.getScene().getWindow();
				stage.setScene(scene);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        Button tryAgain = new Button(noButton);
        tryAgain.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, label2, tryAgain, yes);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }*/
    
    /*
    public boolean alertScreen(String title, String message, String message2, String noButton, String yesButton) {

    	boolean selectedButton;
    	
        Stage window = new Stage();
        //application modality allows the application and screen below to remain open,
        //but non-functional until the alert screen closes
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(150);
        window.setMinWidth(350);

        Label label = new Label();
        label.setText(message);
        Label label2 = new Label();
        label2.setText(message2);
        
        Button yes = new Button(yesButton);
        //yes.setOnAction(e -> switchScreen(e, "login.fxml"));
        yes.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent e) {
        		window.close();
        		return selectedButton = true;
        	}
        });
                
        Button tryAgain = new Button(noButton);
        tryAgain.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, label2, tryAgain, yes);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        
    }*/
    
}
