package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
            session.getTransaction().commit();
            for(AnswerOptionEntity answerOptionEntity : answers) {
                if(answerOptionEntity.getQuestion().getQuestionText().equals(questionText)) {
                    answerIndex = answerOptionEntity.getId();
                }
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load answerText " + answerText + ". " + exception.getMessage());
        }
        return answerIndex;
    }

    static AnswerOptionEntity getAnswerOptionByQuestionAndAnswerOptionName(String questionText, String answerText) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        AnswerOptionEntity answer = null;
        try {
            List<AnswerOptionEntity> answers = session.createQuery("From AnswerOptionEntity where answerText = '" + answerText + "'",
                    AnswerOptionEntity.class).list();
            session.getTransaction().commit();
            for(AnswerOptionEntity answerOptionEntity : answers) {
                if(answerOptionEntity.getQuestion().getQuestionText().equals(questionText)) {
                    answer = answerOptionEntity;
                }
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load answerText " + answerText + ". " + exception.getMessage());
        }
        return answer;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private Long answerId;

    @Column
    private String answerText;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionEntity question;

    @OneToMany(mappedBy = "answerOption", cascade = CascadeType.ALL)
    private List<VoterChoiceEntity> voterChoices;

    @Column
    private boolean status;

    public AnswerOptionEntity(String electionName, String question, String answerText) {
        this.answerText = answerText;
        setQuestion(question, electionName);
        status = true;
        voterChoices = new ArrayList<>();
        answerId = idCount;
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

    public Long getId() {
        return id;
    }

    public QuestionEntity getQuestion() {
        return question;
    }
    
    private void setQuestion(String questionText, String electionName){
        QuestionEntity questionEntity = QuestionEntity.getQuestionsByName(questionText, electionName);
        if (questionEntity != null) {
            questionEntity.addAnswerOption(this);
        } else {
            this.question = null;
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

    public void setAnswerText(String updateAnswerText) {
        answerText = updateAnswerText;
    }

    public boolean getStatus() {
        return status;
    }
}
