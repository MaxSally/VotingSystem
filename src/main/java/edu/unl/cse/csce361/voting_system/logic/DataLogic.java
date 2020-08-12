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
    private Map<String,String> questionWithSelectedAnswer;
    private String editElectionName;

    public static DataLogic getInstance() { 
        if(instance == null) {
            instance = new DataLogic();
        }
        return instance;
    }

    private DataLogic() {
        lstQuestionAnswer = new ArrayList<>();
        currentVoter = null;
        currentAdmin = null;
        List<Election> inProgressElection = Backend.getInstance().getAllInProgressElections();
        if(inProgressElection.size() == 1) {
        	currentElection = inProgressElection.get(0);
        }
        else {
            currentElection = null;
        }
        questionWithSelectedAnswer = new HashMap<>();
        editElectionName = "";
    }
    
    public boolean logIn(String name, String SNN) {
        currentVoter = Backend.getInstance().voterLogIn(name, SNN);
        return currentVoter != null;
    }
    
    public void setCurrentVoter(String SSN) {
        currentVoter = Backend.getInstance().getVoterBySSN(SSN);
    }

    public String getCurrentVoterName() {
        return currentVoter.getName();
    }
    
    public String getCurrentName(String username) {
        return Backend.getInstance().getAdminByUsername(username).toString();
    }
    
    public boolean checkIfVoted() {
        return currentVoter.hasVoted(currentElection.getElectionName());
    }
    
    public Map<String, String>getVoterVoteResult() {
        return Backend.getInstance().getVoterVoteResult(currentVoter, currentElection.getElectionName());
    }
    
    public boolean adminLogIn(String name, String password) {
        currentAdmin = Backend.getInstance().adminLogIn(name, password);
        return currentAdmin != null;
    }
    
    public boolean isElectionOfficial() {
        if(Backend.getInstance().isElectionOfficial(currentAdmin)) {
        	return true;
        } else {
            return false;
        }
    }
    
    public void registerNewVoter(String name, String ssn) {
        Backend.getInstance().registerToVote(name, ssn);
    }
    
    public void registerNewAdmin(String name, String ssn, boolean electionOfficial) {
        Backend.getInstance().registerAdminAccount(name, ssn, electionOfficial);
    }
    
    public boolean isCurrentElectionActive() {
    	return currentElection != null;
    }
    
    //get all questions and answer for the current election 
    public List<QuestionAnswer> getAllQuestionsAndAnswers() {
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(currentElection.getElectionName());
        List<QuestionAnswer> lstCurrentQA = new ArrayList<>();
        for(String question : questions) {
            List<Pair<String, Long> > answerOptions = Backend.getInstance().getAllAnswersByQuestion(question, currentElection.getElectionName());
            QuestionAndAnswerOption questionAndAnswerOption = new QuestionAndAnswerOption(currentElection.getElectionName(), question);
            questionAndAnswerOption.setAnswerOptions(answerOptions);
            lstCurrentQA.add(questionAndAnswerOption);
        }
        lstQuestionAnswer = lstCurrentQA;
        return lstCurrentQA;
    }

    //submit voter's vote into the database
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
                    }
                    else {
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

    //setter and getter for currently in progress election
    public void setCurrentElection(String electionName) {
        currentElection = Backend.getInstance().getElectionByName(electionName);
    }

    public String getCurrentElectionName() {
        return currentElection.getElectionName();
    }
    
    //setter and getter for selected election to be edit/update
    public void setEditElectionName(String electionNameToBeEdit) {
    	editElectionName = electionNameToBeEdit;
    }
    
    public String getEditElectionName() {
        return editElectionName;
    }

    //get all the status of registered voter 
    public Map<String, String> getAllVoterStatus() {
        return Backend.getInstance().getAllVoterStatus(currentAdmin, currentElection.getElectionName());
    }

    //get the vote result for each answer in each question
    public Map<String, Map<String, Long>> getFinalResult() {
        return Backend.getInstance().getFinalResult(currentAdmin, currentElection.getElectionName());
    }
    
    //get the winner for the current election
    public Map<String, List<String>> getWinnerResult() {
    	return Backend.getInstance().getAllWinner(currentAdmin, currentElection.getElectionName());
    }

    public void createNewElectionFromModel(String electionName, Map<String, List<String>> questionsAnswer, LocalDate startTime, LocalDate endTime) {
        if (isElectionOfficial()) {
        	Backend.getInstance().createNewElection((ElectionOfficial)currentAdmin, 
            		electionName, startTime, endTime);
            for (Map.Entry<String, List<String>> question : questionsAnswer.entrySet()) {
                Backend.getInstance().createNewQuestion((ElectionOfficial) currentAdmin, 
                		electionName, question.getKey());
                List<String> answerList = question.getValue();
                System.out.println(answerList);
                System.out.println(answerList.size());
                for (String answer : answerList) {
                	if(!(answer.equals("") || answer == null)) {
                		Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, 
                				question.getKey(), answer, electionName);
                	}
                }
            }
        }
    }
    
    public void addNewQuestion(String electionName, String newQuestionText) {
    	if(isElectionOfficial()) {
	    	Backend.getInstance().createNewQuestion((ElectionOfficial) currentAdmin, electionName, newQuestionText);
    	}
    }
    
    public void addNewAnswerOption(String electionName, String questionText, String newAnswerText) {
	    if(isElectionOfficial()) {
	    	Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, questionText, newAnswerText, electionName);
	    }
    }
    
    public void removeQuestion(String electionName, String questionText) {
    	if(isElectionOfficial()) {
    		Backend.getInstance().removeQuestion((ElectionOfficial) currentAdmin, electionName, questionText);
    	}
    }
    
    public void removeAnswer(String electionName, String questionText, String answerText) {
        if(isElectionOfficial()) {
        	Backend.getInstance().removeAnswer((ElectionOfficial) currentAdmin, questionText, answerText, electionName);
        }
    }
    
    public void updateElectionName(String originalElectionName, String updatedElectionName) {
    	if(isElectionOfficial()) {
    		Backend.getInstance().updateElectionName((ElectionOfficial) currentAdmin, originalElectionName, updatedElectionName);
    	}
    }
    
    public void updateQuestion(String electionName, String originalQuestionText, String updatedQuestionText) {
        if(isElectionOfficial()) {
        	Backend.getInstance().updateQuestion((ElectionOfficial) currentAdmin, electionName, originalQuestionText, updatedQuestionText);
        }
    }
    
    public void updateAnswer(String questionText, String originalAnswerText, String updatedAnswerText, String electionName) {
    	if(isElectionOfficial()) {
            Backend.getInstance().updateAnswer((ElectionOfficial) currentAdmin, questionText, originalAnswerText, updatedAnswerText, electionName);

    	}
    }
    
    //get question and answer choice based on the selected election from front end
    //not according to the currentElection
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
    
    //used in VoteController and ConfirmationScreenController
    //used to parse data around both of the controller
    public void setQuestionWithSelectedAnswer(Map<String, String> questionWithAnswer) {
    	questionWithSelectedAnswer.putAll(questionWithAnswer);
    }
    
    public Map<String, String> getQuestionWithAnswerList() {
    	return questionWithSelectedAnswer;
    }
    
    //get a list of election based the election status
    public List<String> getInactiveElectionList() {
    	List<String> inactiveElectionStringList = new ArrayList<>();
    	List<Election> inactiveElection = Backend.getInstance().getAllInactiveElections();
    	
    	for(Election election : inactiveElection) {
    		inactiveElectionStringList.add(election.getElectionName());
    	}
    	return inactiveElectionStringList;
    }
    
    public List<String> getAllInProgressElectionList() {
    	List<String> allElectionStringList = new ArrayList<>();
    	List<Election> allElection = Backend.getInstance().getAllInProgressElections();
    	
    	for(Election election : allElection) {
    		allElectionStringList.add(election.getElectionName());
    	}
    	return allElectionStringList;
    }
    
    public List<String> getAllElectionList() {
    	List<String> allElectionStringList = new ArrayList<>();
    	List<Election> allElection = Backend.getInstance().getAllElections();
    	
    	for(Election election : allElection) {
    		allElectionStringList.add(election.getElectionName());
    	}
    	return allElectionStringList;
    }
    
    //set election status
    public boolean setStartElection(String electionName) {
    	if(isElectionOfficial()) {
    		if(currentElection.isInProgress()) {
    			return false;
    		}
    		currentElection = Backend.getInstance().startElection((ElectionOfficial) currentAdmin, electionName);
    	}
    	return currentElection != null;
    }
    
    public boolean setEndElection(String electionName) {
    	if(isElectionOfficial()) {
    		return Backend.getInstance().endElection((ElectionOfficial) currentAdmin, electionName);
    	}
    	return false;
    }
    
    //get the duration of the selected election
    public LocalDate getElectionStartDate(String electionName) {
    	return Backend.getInstance().getStartTimeForElection(electionName);
    }
    
    public LocalDate getElectionEndDate(String electionName) {
    	return Backend.getInstance().getEndTimeForElection(electionName);
    }
}

