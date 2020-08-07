package edu.unl.cse.csce361.voting_system.backend;

import java.util.Map;

public interface Voter {
    String getName();

    boolean logIn(String ssn);

    boolean vote(Long answerOptionIndex);

    Map<String, String> getVoterVoteResult(String electionName);

    boolean hasVoted(String electionName);

    void addVotedElection(String electionName);
}
