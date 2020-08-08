package edu.unl.cse.csce361.voting_system.logic;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class BallotResult {
	private SimpleStringProperty questionText, answerOptionText;
	private SimpleLongProperty votes;
	
	public BallotResult(String questionText, String answerOptionText, Long votes) {
        this.questionText = new SimpleStringProperty(questionText);
        this.answerOptionText = new SimpleStringProperty(answerOptionText);
        this.votes = new SimpleLongProperty(votes);
    }
	
	public String getQuestionText() {
		return questionText.get();
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = new SimpleStringProperty(questionText);
	}
	
	public String getAnswerOptionText() {
		return answerOptionText.get();
	}
	
	public void setAnswerOptionText(String answerOptionText) {
		this.answerOptionText = new SimpleStringProperty(answerOptionText);
	}
	
	public Long getVotes() {
		return votes.get();
	}
	
	public void setVotes(Long votes) {
		this.votes = new SimpleLongProperty(votes);
	}
	
}
