package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ElectionStatus extends ScreenController {
	private SimpleStringProperty electionName;
	private Button updateButton;
	
	public ElectionStatus(String electionName, Button updateButton) {
        this.electionName = new SimpleStringProperty(electionName);
        this.updateButton = updateButton;
        //this.status = new SimpleStringProperty(status);
        updateButton.setOnAction(e -> {
			try {
				//DataLogic.getInstance().setElection(electionName);
				switchScreen(e, "update_election.fxml");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

    }
	
	public String getElectionName() {
		return electionName.get();
	}
	
	public void setElectionName(String electionName) {
        this.electionName = new SimpleStringProperty(electionName);
    }
	
	public void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
	}
	
	public Button getUpdateButton() {
		return updateButton;
	}
	/*
	public String getStatus() {
		return status.get();
	}
	
	public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }*/
}

