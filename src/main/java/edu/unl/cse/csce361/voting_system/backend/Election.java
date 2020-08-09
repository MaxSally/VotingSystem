package edu.unl.cse.csce361.voting_system.backend;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.List;

public interface Election {
    String getElectionName();

    String getName();

    List<QuestionEntity> getAssociatedQuestions();

    void addVoter(VoterEntity voter);

    boolean isAvailableForEdit();

    void setElectionName(String updatedElectionName);

    void setStatus(boolean status);

    LocalDate getStartTime();

    LocalDate getEndTime();
}
