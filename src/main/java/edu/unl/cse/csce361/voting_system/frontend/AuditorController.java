package edu.unl.cse.csce361.voting_system.frontend;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.VoterStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuditorController implements Initializable{

	@FXML
	private TableView<VoterStatus> tableView;
	
	@FXML
	private TableColumn<VoterStatus, String> voterName;
	
	@FXML
	private TableColumn<VoterStatus, String> voterStatus;
	
	ObservableList<VoterStatus> voterList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Map<String, String> voterWithStatus = DataLogic.getInstance().getAllVoterStatus();
			
		for(Map.Entry<String, String> voter : voterWithStatus.entrySet()) {
			voterList.add(DataLogic.getInstance().getVoterAndStatus(voter.getKey(), voter.getValue()));
		}

		voterName.setCellValueFactory(new PropertyValueFactory<VoterStatus, String>("name"));
        voterStatus.setCellValueFactory(new PropertyValueFactory<VoterStatus, String>("status"));
		
		tableView.setItems(voterList);
	}
}
