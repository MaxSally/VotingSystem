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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConfirmationScreenController extends ScreenController implements Initializable{
	
	@FXML
	private Label electionName;
	
	@FXML
	private ListView<Map.Entry<String, String>> listView;
	
	private Map<String, String> questionWithSelectedAnswer; 

	class Cell extends ListCell<Map.Entry<String, String>> {

		VBox vbox;
		Label question;
		Label answer;
		
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
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//questionWithSelectedAnswer = new HashMap<>();
		
		//electionName.setText(DataLogic.getInstance().getCurrentElectionName());

		//List<QuestionAnswer> questionsAndAnswer = DataLogic.getInstance().getAllQuestionsAndAnswers();
		//listView.setItems(FXCollections.observableArrayList(questionsAndAnswer));
		
		//listView.setCellFactory(param -> new Cell());
		
	}
	
	public void goBackToVoteScreen(javafx.event.ActionEvent event) throws IOException {
		switchScreen(event, "voting_screen.fxml");
	}
	
}
