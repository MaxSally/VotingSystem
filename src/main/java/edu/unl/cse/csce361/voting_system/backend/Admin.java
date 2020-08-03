package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;
import java.util.Map;

public interface Admin {

    boolean logIn(String password);

    String getFinalResult();

    boolean createQuestion(String questionText, List<String> answerOption);

    boolean updateAnswerOption(Question question, List<String> answerOption);

    boolean removeAnswerOption(Question question, AnswerOption answerOption);

    boolean updateQuestion(Question question, String newQuestionText);

    Map<String, String> getAllVoterStatus(String electionName);
}
