package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class VoteController extends ScreenController implements Initializable {

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
		//TODO modify this method to save the vote information in a local variable and navigate to confirm page. Move submitVote method to confirm screen.
    	DataLogic.getInstance().submitVote(selectedAnswer);
    	switchScreen(event, "thankYou_screen.fxml");
    }

}
