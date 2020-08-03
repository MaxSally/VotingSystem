package edu.unl.cse.csce361.voting_system.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ElectionOfficialEntity extends AdminEntity implements ElectionOfficial {

    @Id
    @GeneratedValue
    private Long id;

    public ElectionOfficialEntity(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean createQuestion(String electionName, String questionText) {
        return false;
    }

    @Override
    public boolean createAnswer(Question question, String answerText) {
        return false;
    }

    @Override
    public boolean createElection(String name, LocalDate startTime, LocalDate endTime, boolean status) {
        return false;
    }

    @Override
    public boolean updateQuestion(String electionName, String originalQuestionText, String updatedQuestionText) {
        return false;
    }

    @Override
    public boolean updateAnswer(Question question, String originalAnswerText, String updatedAnswerText) {
        return false;
    }

    @Override
    public boolean removeQuestion(String electionName, String questionText) {
        return false;
    }

    @Override
    public boolean removeAnswer(Question question, String answerText) {
        return false;
    }

    @Override
    public boolean startElection(Election election) {
        return false;
    }

    @Override
    public boolean endElection(Election election) {
        return false;
    }
}
