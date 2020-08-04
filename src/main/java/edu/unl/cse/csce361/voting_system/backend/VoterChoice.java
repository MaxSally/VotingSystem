package edu.unl.cse.csce361.voting_system.backend;

import java.util.List;

public interface VoterChoice {
    List<AnswerOption> getVoterSelection(Voter voter);
}
