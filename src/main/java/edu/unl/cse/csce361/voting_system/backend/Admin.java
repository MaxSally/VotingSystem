package edu.unl.cse.csce361.voting_system.backend;

import java.util.Map;

public interface Admin {

    boolean logIn(String password);

    Map<String, String> getAllVoterStatus(String electionName);

    Map<QuestionEntity, Map<AnswerOptionEntity, Long>> getFinalResult(String electionName);

    boolean startElection(String electionName);

    boolean endElection(String electionName);
}
