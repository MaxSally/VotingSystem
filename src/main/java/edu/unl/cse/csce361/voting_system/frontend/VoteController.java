package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class VoteController extends ScreenController implements Initializable {

	@FXML
	private ListView<QuestionAnswer> listView;

	static class Cell extends ListCell<QuestionAnswer> {

		HBox hbox;
		Label question = new Label();
		ChoiceBox<String> answerChoiceList = new ChoiceBox<String>();
		Button button = new Button("Select");
		String selectedAnswer;
		
		public Cell() {
			super();
			hbox = new HBox(question, answerChoiceList, button);
			hbox.setSpacing(10);
			//selectedAnswer = answerChoiceList.getValue();
			//System.out.println(selectedAnswer);
			button.setOnAction(e -> System.out.println(answerChoiceList.getValue()));
		}
		
		public void updateItem(QuestionAnswer ballot, boolean empty) {

			super.updateItem(ballot, empty);
			setText(null);
			setGraphic(null);
			
			if(ballot != null && !empty) {
				question.setText(ballot.getQuestionText());
				answerChoiceList.setItems(FXCollections.observableArrayList(ballot.getAnswerText()));
				setGraphic(hbox);
			}
		}
		/*
		public String getAnswer() {
			return answerChoiceList.getValue();
		}*/
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
    	switchScreen(event, "confirm_screen.fxml");
    	//question.add(listView.getItems().get(0).getQuestionText());
    	//question.add(listView);

    	System.out.println(listView);
    }

}
