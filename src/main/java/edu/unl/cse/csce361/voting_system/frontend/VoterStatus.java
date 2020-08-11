package edu.unl.cse.csce361.voting_system.frontend;

import javafx.beans.property.SimpleStringProperty;

public class VoterStatus{
	private SimpleStringProperty name, status;
	
	public VoterStatus(String name, String status) {
        this.name = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty(status);
    }
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }
	
	public String getStatus() {
		return status.get();
	}
	
	public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }
}