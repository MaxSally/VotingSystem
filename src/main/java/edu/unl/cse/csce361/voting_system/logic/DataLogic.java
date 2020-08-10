package edu.unl.cse.csce361.voting_system.logic;

import edu.unl.cse.csce361.voting_system.backend.*;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class DataLogic {
    private static DataLogic instance;
    private List<QuestionAnswer> lstQuestionAnswer;
    private Voter currentVoter;
    private Election currentElection;
    private Admin currentAdmin;
    private ElectionOfficial currentOfficial;

    public static DataLogic getInstance(){
        if(instance == null){
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic(){
        lstQuestionAnswer = new ArrayList<>();
        currentVoter = null;
        currentAdmin = Backend.getInstance().getAdminByUsername("superuser 999");
        // next sprint we will dynamically change the current election
        currentElection = Backend.getInstance().getElectionByName("Nov2020");
        currentOfficial = null;

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

    public boolean adminLogIn(String name, String password) {
        currentAdmin = Backend.getInstance().adminLogIn(name, password);
        return currentAdmin != null;
    }

    public String getCurrentElectionName() {
        return currentElection.getName();
    }

    public Map<String, String> getAllVoterStatus(){
        return Backend.getInstance().getAllVoterStatus(currentAdmin, currentElection.getName());
    }

    public VoterStatus getVoterAndStatus(String name, String status) {
        return new VoterStatus(name, status);
    }

    public Map<String, Map<String, Long>> getFinalResult(){
        return Backend.getInstance().getFinalResult(currentAdmin, currentElection.getName());
    }

    public BallotResult getBallotResult(String questionText, String answerOptionText, Long votes) {
        return new BallotResult(questionText, answerOptionText, votes);
    }

    public void createNewElectionFromModel(String electionName, Map<String, List<String>> questions, LocalDate startTime, LocalDate endTime, boolean status) {
        if (currentAdmin instanceof ElectionOfficial) {
            Backend.getInstance().createNewElection((ElectionOfficial) currentAdmin, 
            		electionName, startTime, endTime, status);

            for (Map.Entry<String, List<String>> question : questions.entrySet()) {
                Backend.getInstance().createNewQuestion((ElectionOfficial) currentAdmin, 
                		electionName, question.getKey());
                List<String> answerList = question.getValue();
                for (String answer : answerList) {
                    Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, 
                    		question.getKey(), answer, electionName);
                }
            }
        }
    }
    
    public void addNewQuestion(String electionName, String newQuestionText) {
    	Backend.getInstance().createNewQuestion((ElectionOfficial) currentAdmin, 
        		electionName, newQuestionText);
    }
    
    public void addNewAnswerOption(String electionName, String questionText, String newAnswerText) {
    	Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, 
        		questionText, newAnswerText, electionName);
    }
    
    public void removeQuestion(String electionName, String questionText){
        Backend.getInstance().removeQuestion((ElectionOfficial) currentAdmin, electionName, questionText);
    }
    
    public void removeAnswer(String electionName, String questionText, String answerText){
        Backend.getInstance().removeAnswer((ElectionOfficial) currentAdmin, questionText, answerText, electionName);
    }
    
    public void updateElectionName(String originalElectionName, String updatedElectionName){
        Backend.getInstance().updateElectionName((ElectionOfficial) currentAdmin, originalElectionName, updatedElectionName);
    }
    
    public void updateQuestion(String electionName, String originalQuestionText, String updatedQuestionText){
        Backend.getInstance().updateQuestion((ElectionOfficial) currentAdmin, electionName, originalQuestionText, updatedQuestionText);
    }
    
    public void updateAnswer(String questionText, String originalAnswerText, String updatedAnswerText, String electionName){
        Backend.getInstance().updateAnswer((ElectionOfficial) currentAdmin, questionText, originalAnswerText, updatedAnswerText, electionName);
    }
    
    public List<QuestionAnswer> getQuestionAnswerByElection(String electionName) {
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        List<QuestionAnswer> lstCurrentQA = new ArrayList<>();
        for(String question : questions) {
            List<Pair<String, Long> > answerOptions = Backend.getInstance().getAllAnswersByQuestion(question, electionName);
            QuestionAndAnswerOption questionAndAnswerOption = new QuestionAndAnswerOption(electionName, question);
            questionAndAnswerOption.setAnswerOptions(answerOptions);
            lstCurrentQA.add(questionAndAnswerOption);
        }
        lstQuestionAnswer = lstCurrentQA;
        return lstCurrentQA;
    }

    public boolean isElectionOfficial() {
        if(Backend.getInstance().isElectionOfficial()) {
            return true;
        } else {
            return false;
        }
    }

    public void registerNewVoter(String name, String ssn){
        Backend.getInstance().registerToVote(name, ssn);
    }

}

