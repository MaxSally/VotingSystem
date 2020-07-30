package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;
import java.util.Set;

public interface Election {
    String getElectionName();

    List<QuestionEntity> getAssociatedQuestions();
}
