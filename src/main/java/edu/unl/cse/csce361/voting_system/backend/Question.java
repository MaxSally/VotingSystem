package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface Question {
    String getQuestionText();

    List<AnswerOption> getAssociatedAnswerOption();

    Election getElection();

    void setStatus(boolean status);

    boolean getStatus();
}
