package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface Voter {
    String getName();

    boolean logIn(String name, String ssn);

    boolean vote(Long answerOptionIndex);

    Map<String, String> getPastVotingDescription(String electionName);

    boolean hasVoted(String electionName);

    void setVoterStatus(boolean status);

    void addVotedElection(String electionName);
}
