package edu.unl.cse.csce361.voting_system.frontend;

import javafx.beans.property.SimpleStringProperty;

public class ElectionWinner {
	
	private SimpleStringProperty question, winningAnswer;
	
	public ElectionWinner(String question, String winningAnswer) {
        this.question = new SimpleStringProperty(question);
        this.winningAnswer = new SimpleStringProperty(winningAnswer);
    }
	
	public String getQuestion() {
		return question.get();
	}
	
	public void setQuestion(String question) {
        this.question = new SimpleStringProperty(question);
    }
	
	public String getWinningAnswer() {
		return winningAnswer.get();
	}
	
	public void setWinningAnswer(String winningAnswer) {
        this.winningAnswer = new SimpleStringProperty(winningAnswer);
    }
}
