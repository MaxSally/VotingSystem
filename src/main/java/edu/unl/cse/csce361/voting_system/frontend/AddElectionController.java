package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class AddElectionController extends ScreenController implements Initializable {

	@FXML
	private TextField electionNameTextField;
	
	@FXML
	private TextField questionTextField;
	
	@FXML
	private TextField answerOptionTextField1;
	
	@FXML
	private TextField answerOptionTextField2;
	
	@FXML
	private TextField answerOptionTextField3;
	
	@FXML
	private TextField answerOptionTextField4;
	
	@FXML
	private ChoiceBox<String> questions;
	
	@FXML 
	private DatePicker startDate;
	
	@FXML 
	private DatePicker endDate;
	
	//List<QuestionAnswer> questionAnswer = new ArrayList<>();
	Map<String, List<String>> questionAnswer = new HashMap<>();
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	questions.setOnAction(e -> displaySelectedQuestion());
    }
    
    public void addNewQuestion(javafx.event.ActionEvent event) throws IOException {
    	List<String> answerOption = new ArrayList<>();

    	answerOption.add(answerOptionTextField1.getText());
    	answerOption.add(answerOptionTextField2.getText());
    	answerOption.add(answerOptionTextField3.getText());
    	answerOption.add(answerOptionTextField4.getText());
    	questionAnswer.put(questionTextField.getText(), answerOption);
    	questions.getItems().add(questionTextField.getText());
    
    	questionTextField.clear();
    	answerOptionTextField1.clear();
    	answerOptionTextField2.clear();
    	answerOptionTextField3.clear();
    	answerOptionTextField4.clear();
    }
    
    public void displaySelectedQuestion() {
    	if(questions != null) {
    		String selectedQuestion = questions.getSelectionModel().getSelectedItem();
    		questionTextField.setText(selectedQuestion);
    		for(Map.Entry<String, List<String>> question : questionAnswer.entrySet()) {
    			if(selectedQuestion.equals(question.getKey())) {
    				answerOptionTextField1.setText(question.getValue().get(0));
    				answerOptionTextField2.setText(question.getValue().get(1));
    				answerOptionTextField3.setText(question.getValue().get(2));;
    				answerOptionTextField4.setText(question.getValue().get(3));;
    			}
    		}
    	}
    }
    
    public void createNewElectionModel(javafx.event.ActionEvent event) throws IOException {
    	alertScreen(event, "Alert!", "is everything correct?", "If everything is ok, click 'ok'", "Go Back", "Ok");
    }
    
    public void cancel(javafx.event.ActionEvent event) throws IOException {
    	switchScreen(event, "elections_screen.fxml");
    }
    
    public void alertScreen(javafx.event.ActionEvent event, String title, String message, String message2, String backButton, String confirmButton) {

        Stage window = new Stage();
        //application modality allows the application and screen below to remain open,
        //but non-functional until the alert screen closes
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(150);
        window.setMinWidth(350);

        Label label = new Label();
        label.setText(message);
        Label label2 = new Label();
        label2.setText(message2);
        
        Button confirm = new Button(confirmButton);
        confirm.setOnAction(e -> {
			try {
		    	DataLogic.getInstance().createNewElectionFromModel(electionNameTextField.getText(), 
		    			startDate.getValue(), endDate.getValue(), false);
				switchScreen(event, "elections_screen.fxml");
				window.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        Button goBack = new Button(backButton);
        goBack.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, label2, goBack, confirm);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
