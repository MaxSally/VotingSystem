package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AnswerOptionEntity implements  AnswerOption{

    private static Long idCount = 0L;

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
    private Set<VoterChoiceEntity> voterChoices;

    @Column
    private boolean status;

    public AnswerOptionEntity(String question, String answerText) {
        this.answerText = answerText;
        setQuestion(question);
        status = true;
        voterChoices = new HashSet<>();
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
        return null;
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
                    "    CarEntity.setQuestion(String).");
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
