package edu.unl.cse.csce361.voting_system.backend;


import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static edu.unl.cse.csce361.voting_system.backend.AnswerOptionEntity.ABSTAIN_VOTE;

@Entity
public class QuestionEntity implements Question {

    static QuestionEntity getQuestionsByName(String questionName, String electionName){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        QuestionEntity question = null;
        try {
            List<QuestionEntity> questions = session.createQuery("From QuestionEntity where questionText = '" + questionName + "'", QuestionEntity.class).list();
            for(QuestionEntity questionEntity : questions) {
                if(questionEntity.getElection().getElectionName().equals(electionName)) {
                    question = questionEntity;
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Question " + questionName + ". " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return question;
    }

    @Id
    @GeneratedValue
    private Long questionId;

    @Column
    private String questionText;

    @Column
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    private ElectionEntity election;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerOptionEntity> answerOptions;

    public QuestionEntity() {
    }

    public QuestionEntity(String questionText, String electionName) {
        super();
        this.questionText = questionText;
        setElection(electionName);
        this.status = true;
        answerOptions = new ArrayList<>();
        AnswerOptionEntity abstainVote = new AnswerOptionEntity(electionName, questionText, ABSTAIN_VOTE);
        Session session = HibernateUtil.getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(abstainVote);
            session.getTransaction().commit();
        }catch (HibernateException exception){
            System.err.println("Encounter hibernate problems while adding abstain vote to the database " + exception);
            session.getTransaction().rollback();
        }
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public Election getElection() {
        return election;
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

    public void setQuestionText(String updatedQuestionText) {
        questionText = updatedQuestionText;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
