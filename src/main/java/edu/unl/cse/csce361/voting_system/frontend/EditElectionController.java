package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class EditElectionController extends ScreenController implements Initializable {

	@FXML
    private ListView<QuestionAnswer> lstElectionData;
	
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
				questionText.setText(ballot.getQuestionText());
				//answerOptionText.setText(ballot.getAnswerText());
				setGraphic(vbox);
				
			}
		}
	}
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // temporary array for testing.
        List<String> answers = new ArrayList<String>();
        answers.add("a_A");
        answers.add("a_B");
        answers.add("a_C");
        Map<String,List<String>> election = new HashMap<String,List<String>>();
        election.put("q_1", answers);
        election.put("q_2", answers);
        election.put("q_3", answers);
        /*Map<String, Map<String,List<String>>> elections = new HashMap<String, Map<String,List<String>>>();
        elections.put("e_1", election);
        elections.put("e_2", election);
        elections.put("e_3", election);*/
        ///////////////////////TODO DELETE THE ABOVE CODE WHEN DONE TESTING OR HAVE LOGIC METHOD!!!//////////////////////////////////////
        
		List<QuestionAnswer> questionsAndAnswer = DataLogic.getInstance().getAllQuestionsAndAnswers();
		lstElectionData.setItems(FXCollections.observableArrayList(questionsAndAnswer));
		
		lstElectionData.setCellFactory(param -> new Cell());

        //initially create a single question in a pane (textView)
        //below the question, in the same pane is a list of answers (one answer by default) with a plus button that adds a new TextView element that accepts an answer

        //at the end, we'll need a button that adds a new question to the listview. may want to make the question pane a component.
        //button to remove question in the pane (small x, link?)

        //this list will initially be populated by the current election
    }

    public void submitElectionEdit(javafx.event.ActionEvent event) throws IOException{
        //get information from dynamically created form elements
        //submit that information
        switchScreen(event, "elections_screen.fxml");
    }    

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //TODO unassign selected election
        switchScreen(event, "elections_screen.fxml");
    }



}
