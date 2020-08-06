package edu.unl.cse.csce361.voting_system.backend;

import java.time.LocalDate;

public interface ElectionOfficial {

    boolean createQuestion(String electionName, String questionText);

    boolean createAnswer(Question question, String answerText);

    boolean createElection(String name, LocalDate startTime, LocalDate endTime, boolean status);

    boolean updateQuestion(String electionName, String originalQuestionText, String updatedQuestionText);

    boolean updateAnswer(Question question, String originalAnswerText, String updatedAnswerText);

    boolean updateElectionName(String originalElectionName, String updatedElectionName);

    boolean removeQuestion(String electionName, String questionText);

    boolean removeAnswer(Question question, String answerText);

    boolean removeElection(String electionName);
}
