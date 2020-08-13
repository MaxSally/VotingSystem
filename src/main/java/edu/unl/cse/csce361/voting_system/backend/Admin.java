package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;
import java.util.Map;

public interface Admin {

    boolean logIn(String password);

    Map<String, String> getAllVoterStatus(String electionName);

    Map<String, Map<String, Long>> getFinalResult(String electionName);

    List<Map<String, String>> getAllVoterVoteResult(String electionName);

    Map<String, List<String>> getFinalWinner(String electionName);
}
