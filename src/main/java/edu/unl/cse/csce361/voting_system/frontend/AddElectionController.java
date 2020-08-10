package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAndAnswerOption;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class AddElectionController extends ScreenController implements Initializable {

	@FXML
    private ListView<QuestionAnswer> lstElectionData;
	
	@FXML
	private Button addQuestionButton;

	class Cell extends ListCell<QuestionAnswer> {
		
		VBox questionVbox;
		VBox answerChoiceVbox;
		VBox vbox;
		HBox answerChoiceOne;
		HBox questionAndDelete;
		Label question = new Label("Question:");
		Label answerOption = new Label("Answer Options:");
		TextField questionText = new TextField();
		TextField answerOptionText = new TextField();
		Button addButton = new Button("Add");
		Button eraseButton = new Button("Delete");
		Label empty = new Label("");
		
		public Cell() {
			super();
			questionText.setPrefWidth(180);
			answerOptionText.setPrefWidth(180);
			questionAndDelete = new HBox(question, eraseButton);
			questionAndDelete.setSpacing(100);
			questionVbox = new VBox(questionAndDelete, questionText);
			answerChoiceOne = new HBox(answerOptionText, empty);
			answerChoiceVbox = new VBox(answerOption, answerChoiceOne);
			vbox = new VBox(questionVbox, answerChoiceVbox, addButton);
			eraseButton.setOnAction(e -> getListView().getItems().remove(getItem()));
			
			addButton.setOnAction(e ->{
				Button deleteButton = new Button("X");
				deleteButton.setStyle("-fx-background-color: #f11919; -fx-text-fill: #ffffff");
		        TextField text = new TextField();
				text.setStyle("-fx-pref-width: 150");
				HBox answerChoice = new HBox(text, deleteButton);
		        answerChoiceVbox.getChildren().add(answerChoice);
		        deleteButton.setOnAction(event -> answerChoiceVbox.getChildren().remove(answerChoice));
		    }); 
		}
		
		public void updateItem(QuestionAnswer ballot, boolean empty) {
			super.updateItem(ballot, empty);
			setText(null);
			setGraphic(null);
			
			if(ballot != null && !empty) {
				setGraphic(vbox);
				
			}
		}
	}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	QuestionAnswer defaultQuestion = new QuestionAndAnswerOption();
    	lstElectionData.getItems().add(defaultQuestion);
		lstElectionData.setCellFactory(param -> new Cell());
    }
    
    public void addNewQuestionCell(javafx.event.ActionEvent event) throws IOException{
    	QuestionAnswer defQuestion = new QuestionAndAnswerOption();
    	lstElectionData.getItems().add(defQuestion);
    }
    
    public void addElection(javafx.event.ActionEvent event) throws IOException{
        //get election data from all procedurally generated form elements and create a new election
        switchScreen(event, "elections_screen.fxml");
    }
    
    public void cancel(javafx.event.ActionEvent event) throws IOException{
    	QuestionAnswer selected = lstElectionData.getSelectionModel().getSelectedItem();
    	lstElectionData.getItems().remove(selected);
        //switchScreen(event, "elections_screen.fxml");
    }
}
