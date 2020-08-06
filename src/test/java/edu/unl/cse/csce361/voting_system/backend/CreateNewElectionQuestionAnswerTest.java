package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class CreateNewElectionQuestionAnswerTest {

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
    public void testAddQuestionToActiveElection() {
        String electionName = "Nov2020";
        String questionText = "What is your name?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertFalse(result);
        assertFalse(questions.contains(questionText));
    }

    @Test
    public void testAddQuestionToInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "What is your name?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(result);
        assertTrue(questions.contains(questionText));
    }

    @Test
    public void testAddAnswerToActiveElection() {
        String questionText = "Who is the next mayor?";
        String electionName = "Nov2020";
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        String answerText = "Chloe Galinsky";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createAnswer(question, answerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(answerText)) {
                hasAnswerText = true;
            }
        }
        assertFalse(result);
        assertFalse(hasAnswerText);
    }

    @Test
    public void testAddAnswerToInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "How are you doing?";
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        String answerText = "Great";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createAnswer(question, answerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(answerText)) {
                hasAnswerText = true;
            }
        }
        assertTrue(result);
        assertTrue(hasAnswerText);
    }

    @Test
    public void testCreateElectionAlreadyExists() {
        String electionName = "Nov2020";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        LocalDate startTime = LocalDate.of(2020,11,10);
        LocalDate endTime = LocalDate.of(2020,11,11);
        boolean result = admin.createElection(electionName, startTime, endTime, false);
        assertFalse(result);
    }

    @Test
    public void testCreateNewElection() {
        String electionName = "Nov2022";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        LocalDate startTime = LocalDate.of(2022,11,10);
        LocalDate endTime = LocalDate.of(2022,11,11);
        boolean result = admin.createElection(electionName, startTime, endTime, false);
        assertTrue(result);
    }
}
