package edu.unl.cse.csce361.voting_system.backend;


import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionEntity implements Question {

    @Id
    @GeneratedValue
    private Long questionId;

    @Column
    private String questionText;

    @Column
    private Election election;

    public QuestionEntity() {
    }

    public QuestionEntity(String questionText, Election election) {
        this.questionText = questionText;
        this.election = election;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public List<AnswerOption> getAssociatedAnswerOption() {
        return new ArrayList<>();
    }
}
