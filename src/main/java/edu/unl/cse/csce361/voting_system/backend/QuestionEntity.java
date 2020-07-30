package edu.unl.cse.csce361.voting_system.backend;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionEntity implements Question {

    static QuestionEntity getQuestionsByName(String questionName){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        QuestionEntity question = null;
        try {
            question = session.bySimpleNaturalId(QuestionEntity.class).load(questionName);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Question " + questionName + ". " + exception.getMessage());
        }
        return question;
    }

    @Id
    @GeneratedValue
    private Long questionId;

    @NaturalId
    @Column
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY)
    private ElectionEntity election;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerOptionEntity> answerOptions;

    public QuestionEntity() {
    }

    public QuestionEntity(String questionText, String election) {
        super();
        this.questionText = questionText;
        setElection(election);
        answerOptions = new ArrayList<>();
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public List<AnswerOptionEntity> getAssociatedAnswerOption() {
        return answerOptions;
    }

    private void setElection(String election) {
        ElectionEntity electionEntity = null;
        try {
            electionEntity = HibernateUtil.getSession().bySimpleNaturalId(ElectionEntity.class).load(election);
        } catch (Exception e) {
            System.err.println("Error while loading Election: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    CarEntity.setElection(String).");
            electionEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned Election.");
        } finally {
            if (electionEntity != null) {
                electionEntity.addElection(this);
            } else {
                this.election = null;
            }
        }
    }

    public void setElection(ElectionEntity election){
        this.election = election;
    }

    public void addAnswerOption(AnswerOption answerOption){
        if(answerOption instanceof AnswerOptionEntity){
            AnswerOptionEntity answerOptionEntity = (AnswerOptionEntity) answerOption;
            answerOptions.add(answerOptionEntity);
            answerOptionEntity.setQuestion(this);
        } else {
            throw new IllegalArgumentException("Expected AnswerOption, got " + answerOption.getClass().getSimpleName());
        }
    }


}
