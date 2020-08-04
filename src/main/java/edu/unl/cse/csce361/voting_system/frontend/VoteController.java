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
    	List<String> question = new ArrayList<>();
        //cycle through each answer and save each vote into a list (we might want to do this when they change the vote answer, just in case the session messes up.)
        //DataLogic.getInstance().submitVote(null);
    	//alertScreen("ARE YOU SURE?", "Be sure to review your answer", "If your answers are correct, click CONFIRM", "GO BACK", "CONFIRM");
    	//question.add(listView.getItems().get(0).getQuestionText());
    	//question.add(listView);
    	/*
    	List<Pair<String, String>> questionsWithSelectedAnswersList = new ArrayList<>();
    	//Pair<String, String> questionWithSelectedAnswer = new Pair<String, String>(null, null);
    	for(int i = 0; i < selectedAnswer.size(); i++) {
			 questionsWithSelectedAnswersList.add(new Pair<String, String>(listView.getItems().get(i).getQuestionText(), selectedAnswer.get(i)));
		}
    	*/
    	
    	//System.out.println(questionsWithSelectedAnswersList);
    	DataLogic.getInstance().submitVote(selectedAnswer);
    	switchScreen(event, "confirm_screen.fxml");

    }

}
