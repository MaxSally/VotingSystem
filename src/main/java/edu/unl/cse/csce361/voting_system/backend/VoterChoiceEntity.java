package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.*;
import java.util.List;

@Entity
public class VoterChoiceEntity implements VoterChoice {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Voter voter;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerOption answerOption;

    public VoterChoiceEntity(String voter, String answerOption) {
        setVoter(voter);
        setAnswerOption(answerOption);
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
                    "    CarEntity.setElection(String).");
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

    private void setAnswerOption(String answerOption){
        AnswerOptionEntity answerOptionEntity = null;
        try {
            answerOptionEntity = HibernateUtil.getSession().bySimpleNaturalId(AnswerOptionEntity.class).load(answerOption);
        } catch (Exception e) {
            System.err.println("Error while loading Election: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    CarEntity.setElection(String).");
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
        this.voter = voter;
    }

    public void setAnswerOption(AnswerOptionEntity answerOption){
        this.answerOption = answerOption;
    }
}
