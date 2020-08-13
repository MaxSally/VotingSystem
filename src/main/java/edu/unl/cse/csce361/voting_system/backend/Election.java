package edu.unl.cse.csce361.voting_system.backend;

import java.time.LocalDate;
import java.util.List;

public interface Election {

    public static  final int MAXIMUM_NAME_LENGTH = 20;
    public String PREPARE_PHASE = "Prepare phase";
    public String VOTING_PHASE = "Voting phase";
    public String FINISH_PHASE = "Finish phase";
    String getElectionName();

    List<Question> getAssociatedQuestions();

    void addVoter(VoterEntity voter);

    boolean isAvailableForEdit();

    void setElectionName(String updatedElectionName);

    void setStatus(String status);

    LocalDate getStartTime();

    LocalDate getEndTime();
    
    boolean isInProgress();
}
