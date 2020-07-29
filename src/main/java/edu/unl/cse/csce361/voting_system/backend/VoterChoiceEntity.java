package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class VoterChoiceEntity implements VoterChoice {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    VoterEntity voter;

    @Column
    private AnswerOption answerOption;

    public VoterChoiceEntity(VoterEntity voter, AnswerOption answerOption) {
        this.voter = voter;
        this.answerOption = answerOption;
    }

    public VoterChoiceEntity() {
    }

    @Override
    public List<AnswerOption> getVoterSelection(Voter voter) {
        return null;
    }
}
