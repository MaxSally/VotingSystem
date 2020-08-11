package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.frontend.VoteController.Cell;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationScreenController extends ScreenController implements Initializable{
	
	@FXML
	private Label electionName;
	
	@FXML
	private ListView<Map.Entry<String, String>> listView;
	
	private Map<String, String> questionWithSelectedAnswer; 
	
	ObservableList<Map.Entry<String, String>> voterChoice = FXCollections.observableArrayList();

	class Cell extends ListCell<Map.Entry<String, String>> {

		VBox vbox;
		Label question = new Label();
		Label answer = new Label();
		
		public Cell() {
			super();
			vbox = new VBox(question, answer);
		}
	
		public void updateItem(Map.Entry<String, String> ballot, boolean empty) {
			super.updateItem(ballot, empty);
			setText(null);
			setGraphic(null);
			
			if(ballot != null && !empty) {
				question.setText(ballot.getKey());
				answer.setText(ballot.getValue());
				setGraphic(vbox);
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		electionName.setText(DataLogic.getInstance().getCurrentElectionName());
		questionWithSelectedAnswer = DataLogic.getInstance().getQuestionWithAnswerList();
		
		
		for(Map.Entry<String, String> questionAnswer : questionWithSelectedAnswer.entrySet()) {
			voterChoice.add(questionAnswer);
		}
		
		listView.setItems(voterChoice);
		listView.setCellFactory(param -> new Cell());
	}
	
	public void goBackToVoteScreen(javafx.event.ActionEvent event) throws IOException {
		switchScreen(event, "voting_screen.fxml");
	}
	
	public void submitVotes(javafx.event.ActionEvent event) throws IOException{
    	alertScreen(event, "Alert!", "Make sure that you have chosen the choice that you want as you wont be able to go back", 
    			"If everything is good, click 'ok'", "Go Back", "Ok");
    }
	
	public void alertScreen(javafx.event.ActionEvent event, String title, String message, String message2, String backButton, String confirmButton) {

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
        
        Button confirm = new Button(confirmButton);
        confirm.setOnAction(e -> {
			try {
		    	DataLogic.getInstance().submitVote(questionWithSelectedAnswer);
		    	DataLogic.getInstance().getQuestionWithAnswerList().clear();
				switchScreen(event, "thankYou_screen.fxml");
				window.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        Button goBack = new Button(backButton);
        goBack.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, label2, goBack, confirm);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
	
}
