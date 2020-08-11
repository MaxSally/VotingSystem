package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuditorController extends ScreenController implements Initializable {
	
	@FXML
	private Label electionName;
	
	@FXML
	private TableView<ElectionWinner> winnerTable;

	@FXML
	private TableColumn<ElectionWinner, String> question;
	
	@FXML
	private TableColumn<ElectionWinner, String> winningAnswer;
	
	@FXML
	private TableView<VoterStatus> voterTable;

	@FXML
	private TableColumn<VoterStatus, String> voterName;
	
	@FXML
	private TableColumn<VoterStatus, String> voterStatus;
	
	@FXML
	private TableView<BallotResult> ballotTable;
	
	@FXML
	private TableColumn<BallotResult, String> questionText;
	
	@FXML
	private TableColumn<BallotResult, String> answerOptionText;
	
	@FXML
	private TableColumn<BallotResult, Long> numberOfVotes;
	
	ObservableList<ElectionWinner> winnerList = FXCollections.observableArrayList();
	ObservableList<VoterStatus> voterList = FXCollections.observableArrayList();
	ObservableList<BallotResult> questionWithAnswerResultList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		electionName.setText(DataLogic.getInstance().getCurrentElectionName());
		
		//Voter and status table
		Map<String, String> voterWithStatus = DataLogic.getInstance().getAllVoterStatus();
			
		for(Map.Entry<String, String> voter : voterWithStatus.entrySet()) {
			voterList.add(new VoterStatus(voter.getKey(), voter.getValue()));
		}

		voterName.setCellValueFactory(new PropertyValueFactory<VoterStatus, String>("name"));
        voterStatus.setCellValueFactory(new PropertyValueFactory<VoterStatus, String>("status"));

		voterTable.setItems(voterList);
		
		//ballot table
		Map<String, Map<String, Long>> getFinalResult = DataLogic.getInstance().getFinalResult();
		Map<String, Long> answerChoiceWithResult = new HashMap<>();
		
		for(Map.Entry<String, Map<String, Long>> questionWithAnswerResult : getFinalResult.entrySet()) {
			answerChoiceWithResult = questionWithAnswerResult.getValue();
			for(Map.Entry<String, Long> answerWithResult : answerChoiceWithResult.entrySet()) {
				questionWithAnswerResultList.add(new BallotResult(questionWithAnswerResult.getKey(), 
						answerWithResult.getKey(), answerWithResult.getValue()));
			}
		}
				
		questionText.setCellValueFactory(new PropertyValueFactory<BallotResult, String>("questionText"));
        answerOptionText.setCellValueFactory(new PropertyValueFactory<BallotResult, String>("answerOptionText"));
        numberOfVotes.setCellValueFactory(new PropertyValueFactory<BallotResult, Long>("votes"));

		ballotTable.setItems(questionWithAnswerResultList);

		//winnerTable
		Map<String, List<String>> winnerResult = DataLogic.getInstance().getWinnerResult();
		
		for(Map.Entry<String, List<String>> question : winnerResult.entrySet()) {
			for(String answer : question.getValue()) {
				winnerList.add(new ElectionWinner(question.getKey(), answer));
			}
		}

		question.setCellValueFactory(new PropertyValueFactory<ElectionWinner, String>("question"));
        winningAnswer.setCellValueFactory(new PropertyValueFactory<ElectionWinner, String>("winningAnswer"));

		winnerTable.setItems(winnerList);
	}
	
	public void logOut(javafx.event.ActionEvent event) throws IOException {
		switchScreen(event, "admin_login.fxml");
	}
}
