package edu.unl.cse.csce361.voting_system.logic;

import edu.unl.cse.csce361.voting_system.backend.*;
import javafx.util.Pair;
import java.util.*;

public class DataLogic {
    private static DataLogic instance;
    private List<QuestionAnswer> lstQuestionAnswer;
    private Voter currentVoter;
    private Election currentElection;

    public static DataLogic getInstance(){
        if(instance == null){
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic(){
        lstQuestionAnswer = new ArrayList<>();
        currentVoter = null;
        // next sprint we will dynamically change the current election
        currentElection = Backend.getInstance().getElectionByName("Nov2020");
    }


    public boolean checkIfVoted(){
        return currentVoter.hasVoted(currentElection.getName());
    }

    public boolean submitVote(Map<String, String> voterSelections) {
        if(currentVoter.hasVoted(currentElection.getElectionName())){
            System.err.println("Voter has voted");
            return false;
        }
        Set<String> answeredQuestion = new HashSet<>();
        for(Map.Entry<String, String> vote : voterSelections.entrySet()) {
            String questionText = vote.getKey();
            String answerText = vote.getValue();
            for(QuestionAnswer questionAnswer: lstQuestionAnswer) {
                if(!questionAnswer.getQuestionText().equals(questionText)) {
                    continue;
                }
                for(Pair<String, Long> answerOption : questionAnswer.getAnswerOptionWithId()) {
                    if(!answerOption.getKey().equals(answerText)){
                        continue;
                    }
                    if(answeredQuestion.contains(questionText)) {
                        System.err.println("Voter answers a question more than once.");
                        return false;
                    }else {
                        answeredQuestion.add(questionText);
                        boolean status = Backend.getInstance().submitVote(currentVoter,
                                answerOption.getValue());
                        if(!status) {
                            System.err.println("Encounter problem while adding vote to the system.");
                            return false;
                        }
                    }
                }

            }
        }
        Backend.getInstance().addVotedElection(currentVoter, currentElection.getElectionName());
        return true;
    }

    public List<QuestionAnswer> getAllQuestionsAndAnswers() {
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(currentElection.getName());
        List<QuestionAnswer> lstCurrentQA = new ArrayList<>();
        for(String question : questions) {
            List<Pair<String, Long> > answerOptions = Backend.getInstance().getAllAnswersByQuestion(question, currentElection.getName());
            QuestionAndAnswerOption questionAndAnswerOption = new QuestionAndAnswerOption(currentElection.getName(), question);
            questionAndAnswerOption.setAnswerOptions(answerOptions);
            lstCurrentQA.add(questionAndAnswerOption);
        }
        lstQuestionAnswer = lstCurrentQA;
        return lstCurrentQA;
    }

    /*
    Right now setElection and setCurrentVoter are reserved for debug.
     */

    public void setElection(String electionName) {
        currentElection = Backend.getInstance().getElectionByName(electionName);
    }

    public boolean logIn(String name, String SNN) {
        currentVoter = Backend.getInstance().voterLogIn(name, SNN);
        return currentVoter != null;
    }

    public void setCurrentVoter(String SSN){
        currentVoter = Backend.getInstance().getVoterBySSN(SSN);
    }

    public String getCurrentVoterName() {
    	return currentVoter.getName();
    }

    public Map<String, String>getVoterVoteResult() {
        return Backend.getInstance().getVoterVoteResult(currentVoter, currentElection.getElectionName());
    }
    
    public String getCurrentElectionName() {
    	return currentElection.getName();
    }
}

