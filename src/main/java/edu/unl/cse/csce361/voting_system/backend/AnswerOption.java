package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface AnswerOption {
    String ABSTAIN_VOTE = "Abstain Vote";

    String getAnswerText();

    boolean getStatus();

    Long getId();
}
