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
    private Election currentEditElection;

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
        currentEditElection = Backend.getInstance().getElectionByName("Nov2020");;

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

    public void setCurrentEditElection(String electionName) {
        currentEditElection = Backend.getInstance().getElectionByName(electionName);;
    }

    public String getCurrentEditElectionName() {
        return currentEditElection.getName();
    }

    public void createNewElection(String electionName, List<QuestionAnswer> questions, LocalDate startTime, LocalDate endTime, boolean status) {
        if (currentAdmin instanceof ElectionOfficial) {
            Backend.getInstance().createNewElection((ElectionOfficial) currentAdmin, electionName, startTime, endTime, status);

            for (QuestionAnswer question : questions) {
                Backend.getInstance().createNewQuestion((ElectionOfficial) currentAdmin, electionName, question.getQuestionText());
                List<String> answerList = question.getAnswerText();
                for (String answers : answerList) {
                    Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, question.getQuestionText(), answers, electionName);
                }
            }
        }
    }
    public void updateElection(String electionName, List<QuestionAnswer> questions, LocalDate startTime, LocalDate endTime, boolean status){
        List<QuestionAnswer> oldQuestions = getAllEditableQuestionsAnswers();
        if (currentAdmin instanceof ElectionOfficial) {
            Backend.getInstance().updateElectionName((ElectionOfficial) currentAdmin, currentElection.getName(), electionName);

            for (int i = 0; i < questions.size(); i++ ) {
                Backend.getInstance().updateQuestion((ElectionOfficial) currentAdmin, electionName, oldQuestions.get(i).getQuestionText(), questions.get(i).getQuestionText());

                //for (int j = 0; j < ) {
                  //  Backend.getInstance().createNewAnswer((ElectionOfficial) currentAdmin, question.getQuestionText(), answers, electionName);
              //  }
            }
        }

    }
    public List<QuestionAnswer> getAllEditableQuestionsAnswers() {
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(currentEditElection.getName());
        List<QuestionAnswer> lstCurrentQA = new ArrayList<>();
        for(String question : questions) {
            List<Pair<String, Long> > answerOptions = Backend.getInstance().getAllAnswersByQuestion(question, currentEditElection.getName());
            QuestionAndAnswerOption questionAndAnswerOption = new QuestionAndAnswerOption(currentEditElection.getName(), question);
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

    public boolean isAuditor() {
        if(!Backend.getInstance().isElectionOfficial()) {
            return true;
        } else {
            return false;
        }
    }
}

