package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;
import java.util.List;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ElectionStatus extends ScreenController {
	private SimpleStringProperty electionName;
	private Button editButton;
	private Button startButton;
	private Button endButton;
	
	public ElectionStatus(String electionName, Button editButton, Button startButton, Button endButton) {
        this.electionName = new SimpleStringProperty(electionName);
        this.editButton = editButton;
        this.startButton = startButton;
        this.endButton = endButton;
        if(editButton.getText().equals("")) {
			editButton.setVisible(false);
		}
		else {
			editButton.setOnAction(e -> {
				try {
					DataLogic.getInstance().setEditElectionName(getElectionName());
					switchScreen(e, "update_election.fxml");
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
        
        if(startButton.getText().equals("")) {
    		startButton.setVisible(false);
    	}
    	else {
	        startButton.setOnAction(e2 -> {
	        	DataLogic.getInstance().setStartElection(getElectionName());
	        	
	    		startButton.setStyle("-fx-background-color: #32CD32;");
	    		editButton.setVisible(false);
			});
		}
        
        if(endButton.getText().equals("")) {
    		endButton.setVisible(false);
    	}
    	else {
    		endButton.setOnAction(e3 -> {
        		DataLogic.getInstance().setEndElection(getElectionName());
            	endButton.setStyle("-fx-background-color: #f11919;");
    		});
    	}
    }
	
	public String getElectionName() {
		return electionName.get();
	}
	
	public void setElectionName(String electionName) {
        this.electionName = new SimpleStringProperty(electionName);
    }
	
	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}
	
	public Button getEditButton() {
		return editButton;
	}
	
	public Button getStartButton() {
		return startButton;
	}
	
	public void setStartButton(Button startButton) {
		this.startButton = startButton;
	}
	
	public Button getEndButton() {
		return endButton;
	}
	
	public void setEndButton(Button endButton) {
		this.endButton = endButton;
	}
}

