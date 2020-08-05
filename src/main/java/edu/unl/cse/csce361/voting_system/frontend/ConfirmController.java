package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController extends ScreenController implements Initializable{
	
	@FXML
	private ListView<String> listView;


	static class Cell extends ListCell<String>{
		Label question = new Label();
		ChoiceBox<String> answerList = new ChoiceBox<String>();
		
		public Cell() {
			super();
			HBox hbox = new HBox(question, answerList);
			hbox.setSpacing(10);
		}
		
		public void updateItem(String ballot, boolean empty) {
			super.updateItem(ballot, empty);
			setText(null);
			setGraphic(null);
			
			if(ballot != null && !empty) {
				question.setText("hi");
				answerList.setValue("Select your choice");
				//answerList.setItems(null); 
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		private ObservableList<String> data = null;
		listView.setItems(data);
		listView.setCellFactory(param -> new Cell());
	}

    public void goBack(javafx.event.ActionEvent event) throws IOException{
        //all we should have to do here is navigate them back to the voting screen.
        switchScreen(event, "voting_screen.fxml");
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
    public void submitVotes(javafx.event.ActionEvent event) throws IOException{
        //TODO move the submit votes from the voting screen to here. Pull vote info from locally saved vote information
        switchScreen(event, "thank_you.fxml");
    }
}
