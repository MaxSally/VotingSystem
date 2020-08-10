package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UpdateElectionController implements Initializable {
	
	@FXML
	private Button updateName;
	
	@FXML
	private Button updateQuestion;
	
	@FXML
	private Button updateAnswer;
	
	@FXML
	private Button addQuestion;
	
	@FXML
	private Button addAnswerOption;
	
	@FXML
	private ChoiceBox<String> electionName;
	
	@FXML
	private ChoiceBox<String> question;
	
	@FXML
	private ChoiceBox<String> answer;
	
	@FXML
	private TextField editNameTextField;
	
	@FXML
	private TextField editQuestionTextField;
	
	@FXML
	private TextField editAnswerTextField;
	
	@FXML
	private TextField newQuestionText;
	
	@FXML
	private TextField newAnswerOptionText;
	
	//TODO delete this
	private List<String> election = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//TODO delete this
		
		election.add("Nov2020");
		election.add("Nov2021");
		
		electionName.setItems(FXCollections.observableArrayList(election));
		
		electionName.setOnAction(e -> useNewElection());
		question.setOnAction(e -> useNewQuestion());
	} 
	
	public void useNewElection(){
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getValue());
		List<String> selectedQuestion = new ArrayList<>();
		
		for(QuestionAnswer question : selectedQuestionAnswer) {
			selectedQuestion.add(question.getQuestionText());
		}
		
		editNameTextField.setText(electionName.getValue());
		
		question.setItems(FXCollections.observableArrayList(selectedQuestion));
		answer.getItems().clear();
	}
	
	public void useNewQuestion() {
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getValue());
		
		for(QuestionAnswer questions : selectedQuestionAnswer) {
			if(question.getValue().equals(questions.getQuestionText())) {
				answer.setItems(FXCollections.observableArrayList(questions.getAnswerText()));
			}
		}
		
		editQuestionTextField.setText(question.getValue());
	}
	
	public void updateElectionName(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateElectionName(electionName.getValue(), editNameTextField.getText());
    }
	
	public void updateQuestionText(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateQuestion(electionName.getValue(), question.getValue(), editQuestionTextField.getText());
    }
	
	public void updateAnswerText(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateAnswer(question.getValue(), answer.getValue(), editAnswerTextField.getText(), electionName.getValue());
    }
	
	public void createNewQuestion(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().addNewQuestion(electionName.getValue(), newQuestionText.getText());
	}
	
	public void createNewAnswer(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().addNewAnswerOption(electionName.getValue(), question.getValue(), newAnswerOptionText.getText());
	}

	public void removeQuestion(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().removeQuestion(electionName.getValue(), question.getValue());
	}
	
	public void removeAnswer(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().removeAnswer(electionName.getValue(), question.getValue(), answer.getValue());
	}
	
	public void editElectionName(javafx.event.ActionEvent event) throws IOException {
		editNameTextField.setVisible(true);
		updateName.setVisible(true);
		editNameTextField.setText(electionName.getValue());
	}
	
	public void editQuestionText(javafx.event.ActionEvent event) throws IOException {
		editQuestionTextField.setVisible(true);
		updateQuestion.setVisible(true);
		editQuestionTextField.setText(question.getValue());
	}
	
	public void editAnswerText(javafx.event.ActionEvent event) throws IOException {
		editAnswerTextField.setVisible(true);
		updateAnswer.setVisible(true);
		editAnswerTextField.setText(answer.getValue());
	}
	
	public void addNewQuestion(javafx.event.ActionEvent event) throws IOException {
		newQuestionText.setVisible(true);
		addQuestion.setVisible(true);
	}
	
	
	public void addNewAnswerOption(javafx.event.ActionEvent event) throws IOException {
		newAnswerOptionText.setVisible(true);
		addAnswerOption.setVisible(true);
	}
}
