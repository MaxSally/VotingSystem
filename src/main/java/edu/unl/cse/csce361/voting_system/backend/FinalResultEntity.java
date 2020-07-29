package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FinalResultEntity implements  FinalResult{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String answerOption;

    public FinalResultEntity() {
    }

    public FinalResultEntity(String answerOption) {
        this.answerOption = answerOption;
    }

    @Override
    public AnswerOption getWinningAnswerOptionByQuestion(Question question) {
        return null;
    }
}
