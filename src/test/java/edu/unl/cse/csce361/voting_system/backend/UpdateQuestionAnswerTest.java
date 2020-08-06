package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UpdateQuestionAnswerTest {

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
    public void testUpdateQuestionToActiveElection() {
        String electionName = "Nov2020";
        String originalQuestionText = "Who is the next Sheriff?";
        String updatedQuestionText = "Who is the next Fire Chief?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.updateQuestion(electionName, originalQuestionText, updatedQuestionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertFalse(result);
        assertFalse(questions.contains(updatedQuestionText));
    }

    @Test
    public void testUpdateQuestionToInactiveElection() {
        String electionName = "Nov2021";
        String originalQuestionText = "How are you doing?";
        String updatedQuestionText = "How are you feeling?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.updateQuestion(electionName, originalQuestionText, updatedQuestionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(result);
        assertTrue(questions.contains(updatedQuestionText));
    }

    @Test
    public void testUpdateAnswerToActiveElection() {
        String electionName = "Nov2020";
        String questionText = "Who is the next Sheriff?";
        String originalAnswerText = "Q. Burte";
        String updatedAnswerText = "Burte. Q";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        boolean result = admin.updateAnswer(question, originalAnswerText, updatedAnswerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(updatedAnswerText)) {
                hasAnswerText = true;
            }
        }
        assertFalse(result);
        assertFalse(hasAnswerText);
    }

    @Test
    public void testUpdateAnswerToInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "How are you doing?";
        String originalAnswerText = "Meh";
        String updatedAnswerText = "Great";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        boolean result = admin.updateAnswer(question, originalAnswerText, updatedAnswerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(updatedAnswerText)) {
                hasAnswerText = true;
            }
        }
        assertTrue(result);
        assertTrue(hasAnswerText);
    }
}
