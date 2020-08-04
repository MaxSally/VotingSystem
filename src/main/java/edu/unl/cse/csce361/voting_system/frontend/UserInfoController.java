package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.frontend.VoteController.Cell;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class UserInfoController extends ScreenController implements Initializable{
    @FXML 
    private TextArea txtVoteInfo;
    
    @FXML 
    private Text txtUsername;
    
    @FXML 
    private Button btnCancel;
    
    @FXML 
    private Button btnProceedToVote;
    
    @FXML 
    private ListView<Map.Entry<String, String>> lstVoteInfo;

    private ObservableList<Map.Entry<String, String>> data = FXCollections.observableArrayList();
	
	static class Cell extends ListCell<Map.Entry<String, String>>{
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
		// TODO Auto-generated method stub
		txtUsername.setText(DataLogic.getInstance().getCurrentVoterName());
		
		Map<String, String> questionAnswer = DataLogic.getInstance().getVoterVoteResult();
		
		for(Map.Entry<String, String> entry : questionAnswer.entrySet()) {
			data.add(entry);
		}
		
		lstVoteInfo.setItems(data);
		lstVoteInfo.setCellFactory(param -> new Cell());
	}

    public void logout(javafx.event.ActionEvent event) throws IOException {
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }

    public void proceed(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "voting_screen.fxml");
    }






}