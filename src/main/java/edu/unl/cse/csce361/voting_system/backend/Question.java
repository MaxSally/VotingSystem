package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface Question {
    String getQuestionText();

    List<AnswerOptionEntity> getAssociatedAnswerOption();

    Election getElection();

    void setStatus(boolean status);
}
