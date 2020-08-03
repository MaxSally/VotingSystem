package edu.unl.cse.csce361.voting_system.backend;

public interface Voter {
    String getName();

    boolean logIn(String name, String ssn);

    boolean vote(Long answerOptionIndex);

    String getPastVotingDescription();

    boolean hasVoted();

    void setVoterStatus(boolean status);
}
