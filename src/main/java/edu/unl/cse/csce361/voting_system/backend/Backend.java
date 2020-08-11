package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Voter voterLogIn(String name, String ssn) {
        Voter currentVoter = VoterEntity.getVoterBySSN(ssn);
        return (currentVoter != null && currentVoter.logIn(name)?currentVoter: null);
    }

    public Admin adminLogIn(String username, String password) {
        Admin currentAdmin = AdminEntity.getAdminByUsername(username);
        return (currentAdmin != null && currentAdmin.logIn(password)? currentAdmin : null);
    }

    public Voter registerToVote(String name, String ssn) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        Voter voter = null;
        try {
        	if(VoterEntity.getVoterBySSN(ssn) == null) {
        		session.beginTransaction();
        		voter = new VoterEntity(name, ssn);
        		session.saveOrUpdate(voter);
        		session.getTransaction().commit();
            } else {
                System.err.println("A voter with the ssn " + ssn + " already exists");
            }
        } catch (HibernateException exception) {
            System.err.println("encounter hibernate problem" + exception);
            session.getTransaction().rollback();
        }
        return voter;
    }

    public Admin registerAdminAccount(String username, String password, boolean isElectionOfficial) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        Admin admin = null;
        try {
            admin = AdminEntity.getAdminByUsername(username);
            if(admin == null) {
                session.beginTransaction();
                if(isElectionOfficial){
                    admin = new ElectionOfficialEntity(username, password);
                }else{
                    admin = new AdminEntity(username, password);
                }

                session.saveOrUpdate(admin);
                session.getTransaction().commit();
            } else {
                System.err.println("An admin account with the username " + username + " already exists");
            }
        } catch (HibernateException exception) {
            System.err.println("encounter hibernate problem" + exception);
            session.getTransaction().rollback();
        }
        return admin;
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
            answerOptions.add(new Pair<>(answerOptionEntity.getAnswerText(), answerOptionEntity.getId()));
        }
        return answerOptions;
    }

    public boolean submitVote(Voter voter, Long answerOptionIndex){
        return voter.vote(answerOptionIndex);
    }

    public Map<String, String> getAllVoterStatus(Admin admin, String electionName){
        return admin.getAllVoterStatus(electionName);
    }

    public Election getElectionByName(String electionName){
        return ElectionEntity.getElectionByName(electionName);
    }

    public Voter getVoterBySSN(String SSN){
        return VoterEntity.getVoterBySSN(SSN);
    }

    public Admin getAdminByUsername(String username) {
        return AdminEntity.getAdminByUsername(username);
    }

    public Map<String, String> getVoterVoteResult(Voter voter, String electionName) {
        return voter.getVoterVoteResult(electionName);
    }

    public void addVotedElection(Voter voter, String electionName) {
        voter.addVotedElection(electionName);
    }

    public boolean createNewElection(ElectionOfficial electionOfficial, String electionName, LocalDate startTime,
                                     LocalDate endTime) {
        return electionOfficial.createElection(electionName, startTime, endTime);
    }

    public boolean createNewQuestion(ElectionOfficial electionOfficial, String electionName, String questionText) {
        return electionOfficial.createQuestion(electionName, questionText);
    }

    public boolean createNewAnswer(ElectionOfficial electionOfficial, String questionText, String answerText,
                                   String electionName) {
        return electionOfficial.createAnswer(QuestionEntity.getQuestionsByName(questionText, electionName), answerText);
    }

    public boolean updateElectionName(ElectionOfficial electionOfficial, String originalElectionName,
                                      String updatedElectionName) {
        return electionOfficial.updateElectionName(originalElectionName, updatedElectionName);
    }

    public boolean updateQuestion(ElectionOfficial electionOfficial, String electionName, String originalQuestionText,
                                  String updatedQuestionText) {
        return electionOfficial.updateQuestion(electionName, originalQuestionText, updatedQuestionText);
    }

    public boolean updateAnswer(ElectionOfficial electionOfficial, String questionText, String originalAnswerText,
                                String updatedAnswerText, String electionName) {
        return electionOfficial.updateAnswer(QuestionEntity.getQuestionsByName(questionText, electionName),
                originalAnswerText, updatedAnswerText);
    }

    public boolean removeElection(ElectionOfficial electionOfficial, String electionName) {
        return electionOfficial.removeElection(electionName);
    }

    public boolean removeQuestion(ElectionOfficial electionOfficial, String electionName, String questionText) {
        return electionOfficial.removeQuestion(electionName, questionText);
    }

    public boolean removeAnswer(ElectionOfficial electionOfficial, String questionText, String answerText, String electionName) {
        return electionOfficial.removeAnswer(QuestionEntity.getQuestionsByName(questionText, electionName), answerText);
    }

    public boolean startElection(ElectionOfficial electionOfficial, String electionName) {
        return electionOfficial.startElection(electionName);
    }

    public boolean endElection(ElectionOfficial electionOfficial, String electionName) {
        return electionOfficial.endElection(electionName);
    }

    public Map<String, Map<String, Long>> getFinalResult(Admin admin, String electionName){
        return admin.getFinalResult(electionName);
    }

    public List<Map<String, String>> getAllVoterVoteResult(Admin admin, String electionName){
        return admin.getAllVoterVoteResult(electionName);
    }
    

    public Map<String, List<String>> getAllWinner(Admin admin, String electionName){
        return admin.getFinalWinner(electionName);
    }

    public boolean isElectionOfficial(Admin currentAdmin) {
    	return currentAdmin instanceof ElectionOfficialEntity; 
    }

    public List<Election> getAllInactiveElections(){
        return ElectionEntity.getAllInactiveElection();
    }

    public LocalDate getStartTimeForElection(String currentElection){
        return ElectionEntity.getElectionByName(currentElection).getStartTime();
    }

    public LocalDate getEndTimeForElection(String currentElection){
        return ElectionEntity.getElectionByName(currentElection).getEndTime();
    }

}
