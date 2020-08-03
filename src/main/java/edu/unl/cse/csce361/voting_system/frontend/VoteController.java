package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class VoteController extends ScreenController implements Initializable{
	@FXML
	private ListView<QuestionAnswer> listView;
	
	@FXML
	private Label electionYear;
	
	ObservableList<QuestionAnswer> data = FXCollections.observableArrayList();
	
	static class Cell extends ListCell<QuestionAnswer>{
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
				System.out.println(ballot.getQuestionText());
				answerChoiceList.setItems(FXCollections.observableArrayList(ballot.getAnswerText()));
				setGraphic(hbox);
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		List<QuestionAnswer> questionsAndAnswer = new ArrayList<>();
		questionsAndAnswer = DataLogic.getInstance().getAllQuestionsAndAnswers();
		
		for(QuestionAnswer s : questionsAndAnswer) {
			data.add(s);
		}
		
		listView.setItems(data);
		listView.setCellFactory(param -> new Cell());
	}

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
    public void submitVotes(javafx.event.ActionEvent event) throws IOException{
        //cycle through each answer and save each vote into a list (we might want to do this when they change the vote answer, just in case the session messes up.)
        switchScreen(event, "confirm_screen.fxml");
    }

}
