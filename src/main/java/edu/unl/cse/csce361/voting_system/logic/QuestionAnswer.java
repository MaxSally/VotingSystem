package edu.unl.cse.csce361.voting_system.logic;

import javafx.util.Pair;

import java.util.List;

public interface QuestionAnswer {
    String getQuestionText();

    List<String> getAnswerText();

    List<Pair<String, Long>>getAnswerOptionWithId();

}
