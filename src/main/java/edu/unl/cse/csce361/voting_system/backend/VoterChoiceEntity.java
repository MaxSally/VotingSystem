package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.*;
import java.util.List;

@Entity
public class VoterChoiceEntity implements VoterChoice {

    @Id
    @GeneratedValue
    private Long id;

    private String voter;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerOptionEntity answerOption;

    public VoterChoiceEntity(String voter, Long selectedAnswerOptionIndex) {
        setVoter(voter);
        setAnswerOption(selectedAnswerOptionIndex);
    }

    public VoterChoiceEntity() {
    }

    @Override
    public List<AnswerOption> getVoterSelection(Voter voter) {
        return null;
    }

    private void setVoter(String voter){
        VoterEntity voterEntity = null;
        try {
            voterEntity = HibernateUtil.getSession().bySimpleNaturalId(VoterEntity.class).load(voter);
        } catch (Exception e) {
            System.err.println("Error while loading Election: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    VoterChoiceEntity.setVoter(String).");
            voterEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned Election.");
        } finally {
            if (voterEntity != null) {
                voterEntity.addVoter(this);
            } else {
                this.voter = null;
            }
        }
    }

    private void setAnswerOption(Long selectedAnswerOptionIndex){
        AnswerOptionEntity answerOptionEntity = null;
        try {
            answerOptionEntity = HibernateUtil.getSession().byId(AnswerOptionEntity.class).load(selectedAnswerOptionIndex);
        } catch (Exception e) {
            System.err.println("Error while loading Election: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    VoterChoiceEntity.setAnswerOption(Long)");
            answerOptionEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned Election.");
        } finally {
            if (answerOptionEntity != null) {
                answerOptionEntity.addAnswerOption(this);
            } else {
                this.answerOption = null;
            }
        }
    }

    public void setVoter(VoterEntity voter){
        this.voter = EncryptionUtil.encrypt(voter.getSSN());
    }

    public void setAnswerOption(AnswerOptionEntity answerOption){
        this.answerOption = answerOption;
    }

    public AnswerOptionEntity getAnswerOption() {
        return answerOption;
    }

    public Long getId(){
        return id;
    }
}
