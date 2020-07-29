package edu.unl.cse.csce361.voting_system.backend;

public interface Voter {
    String getName();

    boolean logIn();

    boolean vote();

    String getPastVotingDescription();

    String getSSN();
}
