package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AnswerOptionEntity implements  AnswerOption{

    private static Long idCount = 0L;

    static Long getAnswerOptionIndexByName(String questionText, String answerText) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long answerIndex = null;
        try {
            List<AnswerOptionEntity> answers = session.createQuery("From AnswerOptionEntity where answerText = '" + answerText + "'",
                    AnswerOptionEntity.class).list();
            for(AnswerOptionEntity answerOptionEntity : answers) {
                if(answerOptionEntity.getQuestion().equals(questionText)) {
                    answerIndex = answerOptionEntity.getPersonalID();
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load answerText " + answerText + ". " + exception.getMessage());
        }
        return answerIndex;
    }

    @Id
    @GeneratedValue
    private Long answerId;

    @NaturalId
    private Long personalId;

    @Column
    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionEntity question;

    @OneToMany(mappedBy = "answerOption", cascade = CascadeType.ALL)
    private List<VoterChoiceEntity> voterChoices;

    @Column
    private boolean status;

    public AnswerOptionEntity(String question, String answerText) {
        this.answerText = answerText;
        setQuestion(question);
        status = true;
        voterChoices = new ArrayList<>();
        personalId = idCount;
        idCount++;
    }

    public AnswerOptionEntity() {
    }

    void setStatus(boolean status){
        this.status = status;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

    public Long getPersonalID() {
        return personalId;
    }

    public QuestionEntity getQuestion() {
        return question;
    }
    
    private void setQuestion(String question){
        QuestionEntity questionEntity = null;
        try {
            questionEntity = HibernateUtil.getSession().bySimpleNaturalId(QuestionEntity.class).load(question);
        } catch (Exception e) {
            System.err.println("Error while loading Question: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    QuestionEntity.setQuestion(String).");
            questionEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned Question.");
        } finally {
            if (questionEntity != null) {
                questionEntity.addAnswerOption(this);
            } else {
                this.question = null;
            }
        }
    }

    public void setQuestion(QuestionEntity question){
        this.question = question;
    }

    public void addAnswerOption(VoterChoice voterChoice){
        if(voterChoice instanceof VoterChoiceEntity){
            VoterChoiceEntity voterChoiceEntity = (VoterChoiceEntity) voterChoice;
            voterChoices.add(voterChoiceEntity);
            voterChoiceEntity.setAnswerOption(this);
        }else {
            throw new IllegalArgumentException("Expected VoterChoice, got " + voterChoice.getClass().getSimpleName());
        }
    }
}
