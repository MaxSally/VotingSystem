package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface Election {
    String getDescription();

    List<Question> getAssociatedQuestions();
}
