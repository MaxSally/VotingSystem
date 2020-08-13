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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateElectionController extends ScreenController implements Initializable {
	
	@FXML
	private Label electionName;
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		electionName.setText(DataLogic.getInstance().getEditElectionName());
		useNewElection();
		question.setOnAction(e -> useNewQuestion());
	} 
	
	public void useNewElection() {
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getText());
		List<String> selectedQuestion = new ArrayList<>();
		
		for(QuestionAnswer question : selectedQuestionAnswer) {
			selectedQuestion.add(question.getQuestionText());
		}
		
		question.setItems(FXCollections.observableArrayList(selectedQuestion));
		answer.getItems().clear();
	}
	
	public void useNewQuestion() {
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getText());
		
		for(QuestionAnswer questions : selectedQuestionAnswer) {
			if(question.getSelectionModel().getSelectedItem().equals(questions.getQuestionText())) {
				answer.setItems(FXCollections.observableArrayList(questions.getAnswerText()));
			}
		}
		
		editQuestionTextField.setText(question.getSelectionModel().getSelectedItem());
	}
	
	public void updateElectionName(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateElectionName(electionName.getText(), editNameTextField.getText());
    }
	
	public void updateQuestionText(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateQuestion(electionName.getText(), question.getSelectionModel().getSelectedItem(), editQuestionTextField.getText());
    }
	
	public void updateAnswerText(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().updateAnswer(question.getValue(), answer.getSelectionModel().getSelectedItem(), editAnswerTextField.getText(), electionName.getText());
    }
	
	public void createNewQuestion(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().addNewQuestion(electionName.getText(), newQuestionText.getText());
		
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getText());
		List<String> selectedQuestion = new ArrayList<>();
		
		for(QuestionAnswer question : selectedQuestionAnswer) {
			selectedQuestion.add(question.getQuestionText());
		}
	}
	
	public void createNewAnswer(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().addNewAnswerOption(electionName.getText(), question.getSelectionModel().getSelectedItem(), 
				newAnswerOptionText.getText());
		List<QuestionAnswer> selectedQuestionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName.getText());

		for(QuestionAnswer questions : selectedQuestionAnswer) {
			if(question.getSelectionModel().getSelectedItem().equals(questions.getQuestionText())) {
				answer.setItems(FXCollections.observableArrayList(questions.getAnswerText()));
			}
		}
	}

	public void removeQuestion(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().removeQuestion(electionName.getText(), question.getSelectionModel().getSelectedItem());
	}
	
	public void removeAnswer(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().removeAnswer(electionName.getText(), question.getSelectionModel().getSelectedItem(), 
				answer.getSelectionModel().getSelectedItem());
	}
	
	public void removeElection(javafx.event.ActionEvent event) throws IOException {
		DataLogic.getInstance().removeElection(electionName.getText());
		switchScreen(event, "elections_screen.fxml");
	}
	
	public void editElectionName(javafx.event.ActionEvent event) throws IOException {
		editNameTextField.setVisible(true);
		updateName.setVisible(true);
		editNameTextField.setText(electionName.getText());
	}
	
	public void editQuestionText(javafx.event.ActionEvent event) throws IOException {
		editQuestionTextField.setVisible(true);
		updateQuestion.setVisible(true);
		editQuestionTextField.setText(question.getSelectionModel().getSelectedItem());
	}
	
	public void editAnswerText(javafx.event.ActionEvent event) throws IOException {
		editAnswerTextField.setVisible(true);
		updateAnswer.setVisible(true);
		editAnswerTextField.setText(answer.getSelectionModel().getSelectedItem());
	}
	
	public void addNewQuestion(javafx.event.ActionEvent event) throws IOException {
		newQuestionText.setVisible(true);
		addQuestion.setVisible(true);
	}
	
	public void addNewAnswerOption(javafx.event.ActionEvent event) throws IOException {
		newAnswerOptionText.setVisible(true);
		addAnswerOption.setVisible(true);
	}
	
	public void cancel(javafx.event.ActionEvent event) throws IOException {
		switchScreen(event, "elections_screen.fxml");
	}
	
	public void finishEdit(javafx.event.ActionEvent event) throws IOException {
		switchScreen(event, "elections_screen.fxml");
	}
}
