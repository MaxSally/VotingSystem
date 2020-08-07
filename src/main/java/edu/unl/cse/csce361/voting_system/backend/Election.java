package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface Election {
    String getElectionName();

    String getName();

    List<QuestionEntity> getAssociatedQuestions();

    void addVoter(VoterEntity voter);

    boolean getAvailability();

    void setElectionName(String updatedElectionName);

    void setStatus(boolean status);
}
