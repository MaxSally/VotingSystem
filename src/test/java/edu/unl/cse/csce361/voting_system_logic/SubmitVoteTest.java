package edu.unl.cse.csce361.voting_system_logic;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import edu.unl.cse.csce361.voting_system.backend.*;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SubmitVoteTest extends TestTemplate {

    @Test
    public void submitVote(){
        String electionName = "Nov2020";
        String voterSSN = "839480320";
        DataLogic.getInstance().setCurrentElection(electionName);
        DataLogic.getInstance().setCurrentVoter(voterSSN);
        Map<String, String> userSelections = new HashMap<>();
        userSelections.put("Who is the next mayor?", "Pat Mann");
        userSelections.put("Who is the next city council?", "Inky");
        userSelections.put("Shall there be a 25¢ tax on cherries?", "No");
        userSelections.put("Shall liquor licenses be required for electronic bars?", "Yes");
        userSelections.put("Shall electronic race tracks be held liable for electronic car crashes?", "No");
        userSelections.put("Who is the next Sheriff?", AnswerOption.ABSTAIN_VOTE);
        DataLogic.getInstance().getAllQuestionsAndAnswers();
        boolean success = DataLogic.getInstance().submitVote(userSelections);
        assertTrue(success);
    }
    
    @Test
    public void testCreateNewElection() {
    	String electionName = "Nov2022";
    	String adminUser = "Batman";
    	String password = "4b9f66817cf5ae30903c9a7bb53da984";
    	DataLogic.getInstance().adminLogIn(adminUser, password);
    	//DataLogic.getInstance().createNewElectionFromModel(electionName, LocalDate.of(2022, 4, 20), LocalDate.of(2022, 5, 20));
    	List<Election> questionAnswer = Backend.getInstance().getAllInactiveElections();
    	for(Election election : questionAnswer) {
    		System.out.println(election.getElectionName());
    	}
    }
    
    @Test
    public void testCreateNewQuestion() {
    	String electionName = "Nov2021";
    	String question = "What is your name?";
    	String adminUser = "Batman";
    	String password = "4b9f66817cf5ae30903c9a7bb53da984";
    	DataLogic.getInstance().adminLogIn(adminUser, password);
    	DataLogic.getInstance().addNewQuestion(electionName, question);
    	List<QuestionAnswer> questionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName);
    	for(QuestionAnswer election : questionAnswer) {
    		System.out.println(election.getQuestionText());
    		
    	}
    }
    
    @Test
    public void testCreateNewAnswer() {
    	String electionName = "Nov2021";
    	String question = "What is your name?";
    	String answer = "yes";
    	String adminUser = "Batman";
    	String password = "4b9f66817cf5ae30903c9a7bb53da984";
    	DataLogic.getInstance().adminLogIn(adminUser, password);
    	DataLogic.getInstance().addNewQuestion(electionName, question);
    	DataLogic.getInstance().addNewAnswerOption(electionName, question, answer);
    	List<QuestionAnswer> questionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName);
    	for(QuestionAnswer election : questionAnswer) {
    		System.out.println(election.getQuestionText());
    		System.out.println(election.getAnswerText());
    		
    	}
    }
    
    @Test
    public void testCreateElectionModel() {
    	String electionName = "Nov2026";
    	String question = "What is your name?";
    	List<String> answer = new ArrayList<>();
    	answer.add("yes");
    	answer.add("No");
    	answer.add("");
    	Map<String, List<String>> questionWithAnswer = new HashMap<>();
    	questionWithAnswer.put(question, answer);
    	LocalDate startDate = LocalDate.of(2026, 4, 23);
    	LocalDate endDate = LocalDate.of(2026, 4, 30);
    	String adminUser = "Batman";
    	String password = "4b9f66817cf5ae30903c9a7bb53da984";
    	DataLogic.getInstance().adminLogIn(adminUser, password);
    	DataLogic.getInstance().createNewElectionFromModel(electionName, questionWithAnswer, startDate, endDate);
    	List<QuestionAnswer> questionAnswer = DataLogic.getInstance().getQuestionAnswerByElection(electionName);
    	for(QuestionAnswer election : questionAnswer) {
    		System.out.println(election.getQuestionText());
    		System.out.println(election.getAnswerText());
    		
    	}
    }
}
