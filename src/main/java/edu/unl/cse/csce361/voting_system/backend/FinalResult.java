package edu.unl.cse.csce361.voting_system.backend;

public interface FinalResult {
    AnswerOption getWinningAnswerOptionByQuestion(Question question);
}
