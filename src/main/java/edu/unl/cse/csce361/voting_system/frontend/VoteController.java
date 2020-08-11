package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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
	
	private String currentVoter;
	
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
				answerChoiceList.setValue("Abstain Vote");
				if(!(selectedAnswer.isEmpty())) {
					answerChoiceList.setValue(selectedAnswer.get(ballot.getQuestionText()));
				}
				answerChoiceList.setItems(FXCollections.observableArrayList(ballot.getAnswerText()));
				setGraphic(hbox);
				answerChoiceList.setOnAction(e -> selectedAnswer.put(ballot.getQuestionText(), answerChoiceList.getValue()));
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*currentVoter = " ";
		
		if(!(currentVoter.equals(DataLogic.getInstance().getCurrentVoterName()))){
			selectedAnswer = new HashMap<>();
			currentVoter = DataLogic.getInstance().getCurrentVoterName();
		}
		else {
			selectedAnswer = DataLogic.getInstance().getQuestionWithAnswerList(); 
		}*/
		
		selectedAnswer = DataLogic.getInstance().getQuestionWithAnswerList(); 
		electionName.setText(DataLogic.getInstance().getCurrentElectionName());

		List<QuestionAnswer> questionsAndAnswer = DataLogic.getInstance().getAllQuestionsAndAnswers();
		listView.setItems(FXCollections.observableArrayList(questionsAndAnswer));
		
		listView.setCellFactory(param -> new Cell());
	}

    public void cancel(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "login.fxml");
    }
    
    public void submitVotes(javafx.event.ActionEvent event) throws IOException { 
    	DataLogic.getInstance().setQuestionWithSelectedAnswer(selectedAnswer);
    	switchScreen(event, "confirm_screen.fxml");
    }

}
