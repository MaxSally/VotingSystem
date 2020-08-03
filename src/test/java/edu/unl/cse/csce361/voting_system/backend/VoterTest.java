package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class VoterTest {

    Backend backend;
    List<Long> answerOptionIndex;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        backend = Backend.getInstance();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        DatabasePopulator.createAdmin().forEach(session::saveOrUpdate);
        DatabasePopulator.createElection().forEach(session::saveOrUpdate);
        DatabasePopulator.createQuestion().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        List<AnswerOption> answerOptions = DatabasePopulator.createAnswerOption();
        session.beginTransaction();
        for(AnswerOption answerOption: answerOptions){
            session.saveOrUpdate(answerOption);
        }
        session.getTransaction().commit();
        answerOptionIndex = DatabasePopulator.getAnswerOptionIndex();
        session.beginTransaction();
        DatabasePopulator.createVoterChoice(answerOptionIndex).forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        DatabasePopulator.setVoterStatus();
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
    }

    @Test
    public void testLogIn() {
        String name = "A";
        String ssn = "123456789";

         Voter voter = Backend.getInstance().voterLogIn(name, ssn);
         assertTrue(voter != null);
    }

    @Test
    public void testLogInSSN11() {
        String name = "A";
        String ssn = "12345678901";

        Voter voter = Backend.getInstance().voterLogIn(name, ssn);
        assertTrue(voter == null);
    }

    @Test
    public void testRegisterVoter() {
        String name = "Chloe";
        String ssn = "121212121";

        Voter voter = Backend.getInstance().registerToVote(name, ssn);
        assertEquals(voter.getName(), name);
    }

    @Test
    public void testRegisterVoterInvalidSSN() {
        String name = "Max";
        String ssn = "12121212100";
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid SSN length. The required length is 9");

        Voter voter = Backend.getInstance().registerToVote(name, ssn);
        assertNull(voter);
    }

    @Test
    public void testGetAllQuestionsByElection() {
        String electionName = "Nov2020";
        String expectedQuestion1 = "Who is the next mayor?";
        String expectedQuestion2 = "Who is the next city council?";
        String expectedQuestion3 = "Who is the next Sheriff?";
        int expectedSize = 6;
        
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(questions.size() == expectedSize);
        assertEquals(expectedQuestion1, questions.get(0));
        assertEquals(expectedQuestion2, questions.get(1));
        assertEquals(expectedQuestion3, questions.get(2));
    }

    @Test
    public void testGetAllAnswersByQuestion() {
        String questionName = "Who is the next mayor?";
        String expectedAnswer1 = "Pat Mann";
        String expectedAnswer2 = "Dawn Keykong";
        String electionName = "Nov2020";
        int expectedSize = 2;

        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionName, electionName);
        assertTrue(answers.size() == expectedSize);
        assertEquals(expectedAnswer1, answers.get(0).getKey());
        assertEquals(expectedAnswer2, answers.get(1).getKey());
    }

    @Test
    public void testGetAnswerIndex() {
        String questionText = "Shall there be a 25¢ tax on cherries?";
        String answerText = "No";
        Long expectedID = answerOptionIndex.get(3);
        Long actualID = AnswerOptionEntity.getAnswerOptionIndexByName(questionText, answerText);
        System.out.println(actualID);
        assertTrue(expectedID == actualID);
    }

    @Test
    public void testGetVoterSelection() {
        String voterSSN = "123879456";
        Voter voter = VoterEntity.getVoterBySSN(voterSSN);
        String electionName = "Nov2020";
        String question = "Who is the next mayor?";
        Map<String, String> result = Backend.getInstance().getVoterVoteResult(voter, electionName);
        assertTrue(result.containsKey(question));
        for(Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println("key: " + entry.getKey() + "\n value: " + entry.getValue());
        }
    }

    @Test
    public void testGetAllVoterStatus() {
        String electionName = "Nov2020";
        String voter1Name = "A";
        String voter2Name = "B";
        String voter3Name = "C";
        Admin adminEntity = new AdminEntity();
        Map<String, String> result = adminEntity.getAllVoterStatus(electionName);
        assertTrue(result.containsKey(voter1Name));
        assertTrue(result.containsKey(voter2Name));
        assertTrue(result.containsKey(voter3Name));
        for(Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println("key: " + entry.getKey() + "\n value: " + entry.getValue());
        }
    }

    // No assert since this prints out a list :v
    @Test
    public void testGetVotedVoterInElection(){
        String electionName = "Nov2020";
        ElectionEntity election = ElectionEntity.getElectionByName(electionName);
        Set<VoterEntity> voters = election.getVoters();
        for(VoterEntity voter: voters){
            System.out.println(voter.getName());
        }
    }
}
