package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class ElectionsController extends ScreenController implements Initializable {

	@FXML
	private TableView<ElectionStatus> tableView;
	
	@FXML
	private TableColumn<ElectionStatus, String> electionName;
	
	@FXML
	private TableColumn<ElectionStatus, Button> editButton;
	
	@FXML
	private TableColumn<ElectionStatus, Button> startButton;
	
	@FXML
	private TableColumn<ElectionStatus, Button> endButton;
	
	private ObservableList<ElectionStatus> name = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        List<String> electionList = DataLogic.getInstance().getInactiveElectionList();
		
        for(String election : electionList) {
        	name.add(new ElectionStatus(election, new Button("Edit"), new Button("Start"), new Button("End")));
        }
        
        electionName.setCellValueFactory(new PropertyValueFactory<ElectionStatus, String>("electionName"));
        editButton.setCellValueFactory(new PropertyValueFactory<ElectionStatus, Button>("editButton"));
        startButton.setCellValueFactory(new PropertyValueFactory<ElectionStatus, Button>("startButton"));
        endButton.setCellValueFactory(new PropertyValueFactory<ElectionStatus, Button>("endButton"));
        
        tableView.setItems(name);;
    }

    public void goToUpdate(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "update_election.fxml");
    }

    public void goToAdd(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "add_election.fxml");
    }

    public void goToAudit(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "auditor.fxml");
    }
   
    public void logOut(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "admin_login.fxml");
    }

}

