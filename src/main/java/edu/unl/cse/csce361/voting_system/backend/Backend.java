package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static edu.unl.cse.csce361.voting_system.backend.VoterEntity.REQUIRED_SSN_LENGTH;

public class Backend {

    private static Backend instance;

    private Backend() {
        super();
    }

    public static Backend getInstance() {
        if (instance == null) {
            instance = new Backend();
        }
        return instance;
    }

    public boolean logIn(String name, String ssn) {
        Voter currentVoter = VoterEntity.getVoterBySSN(ssn);
        return currentVoter != null && currentVoter.getName().equals(name);
    }

    public Voter registerToVote(String name, String ssn) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        Voter voter = null;
        try {
            if(VoterEntity.validateSSN(ssn)) {
                if(VoterEntity.getVoterBySSN(ssn) == null) {
                    session.beginTransaction();
                    voter = new VoterEntity(name, ssn);
                    session.saveOrUpdate(voter);
                    session.getTransaction().commit();
                } else {
                    System.err.println("A voter with the ssn " + ssn + " already exists");
                }
            } else {
                System.err.println("The ssn " + ssn + "is invalid. It must be " + REQUIRED_SSN_LENGTH + " digits");
            }
        } catch (HibernateException exception) {
            System.err.println("encounter hibernate problem" + exception);
            session.getTransaction().rollback();
        }
        return voter;
    }

    public List<String> getAllQuestionsByElection(String electionName) {
        Election election = ElectionEntity.getElectionByName(electionName);
        List<String> questionAsString = new ArrayList<>();
        for(QuestionEntity questionEntity: election.getAssociatedQuestions()){
            questionAsString.add(questionEntity.getQuestionText());
        }
        return questionAsString;
    }

    public List<Pair<String, Long>> getAllAnswersByQuestion(String questionName, String electionName) {
        Question question = QuestionEntity.getQuestionsByName(questionName, electionName);
        List<Pair<String, Long>> answerOptions = new ArrayList<>();
        for(AnswerOptionEntity answerOptionEntity: question.getAssociatedAnswerOption()){
            answerOptions.add(new Pair<>(answerOptionEntity.getAnswerText(), answerOptionEntity.getAnswerId()));
        }
        return answerOptions;
    }

    public boolean submitVote(Voter voter, Long answerOptionIndex){
        return voter.vote(answerOptionIndex);
    }

    public boolean getVoterStatus(Voter voter){
        return voter.hasVoted();
    }

    public void setVoterStatus(Voter voter, boolean status){
        voter.setVoterStatus(status);
    }

    public Election getElectionByName(String electionName){
        return ElectionEntity.getElectionByName(electionName);
    }

    public Voter getVoterBySSN(String SSN){
        return VoterEntity.getVoterBySSN(SSN);
    }

    public Map<String, String> getPastVotingDescription(Voter voter, String electionName) {
        return voter.getPastVotingDescription(electionName);
    }

}
