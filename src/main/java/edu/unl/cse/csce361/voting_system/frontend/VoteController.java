package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class VoteController extends ScreenController implements Initializable {
	
	@FXML
	private Label electionName;
	
	@FXML
	private ListView<QuestionAnswer> listView;
	
	private Map<String, String> selectedAnswer; 

	class Cell extends ListCell<QuestionAnswer> {

		HBox hbox;
		Label question = new Label();
		ChoiceBox<String> answerChoiceList = new ChoiceBox<String>();
		
		public Cell() {
			super();
			hbox = new HBox(question, answerChoiceList);
			hbox.setSpacing(10);
		}

		public void updateItem(QuestionAnswer ballot, boolean empty) {

			super.updateItem(ballot, empty);
			setText(null);
			setGraphic(null);
			
			if(ballot != null && !empty) {
				question.setText(ballot.getQuestionText());
				answerChoiceList.setItems(FXCollections.observableArrayList(ballot.getAnswerText()));
				setGraphic(hbox);
				answerChoiceList.setOnAction(e -> selectedAnswer.put(ballot.getQuestionText(), answerChoiceList.getValue()));
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedAnswer = new HashMap<>();
		electionName.setText(DataLogic.getInstance().getCurrentElectionName());

		List<QuestionAnswer> questionsAndAnswer = DataLogic.getInstance().getAllQuestionsAndAnswers();
		listView.setItems(FXCollections.observableArrayList(questionsAndAnswer));
		
		listView.setCellFactory(param -> new Cell());
		
	}

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
    
    public void submitVotes(javafx.event.ActionEvent event) throws IOException{
    	alertScreen(event, "Alert!", "Make sure that you have chosen the choice that you want as you wont be able to go back", 
    			"If everything is good, click 'ok' and click 'Submit Vote'", "Go Back", "Ok");
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
		    	//DataLogic.getInstance().submitVote(selectedAnswer);
				switchScreen(event, "confirm_screen.fxml");
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
