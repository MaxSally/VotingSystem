package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AnswerOptionEntity implements  AnswerOption{

    @Id
    @GeneratedValue
    private Long answerId;

    @Column
    private String answerText;

    @Column
    private Question question;

    @Column
    private Election election;

    @Column
    private boolean status;

    public AnswerOptionEntity(String answerText, Question question, Election election) {
        this.answerText = answerText;
        this.question = question;
        this.election = election;
        status = true;
    }

    public AnswerOptionEntity() {
    }

    void setStatus(boolean status){
        this.status = status;
    }

    @Override
    public String getAnswerText() {
        return null;
    }
}
